package com.example.pubsub;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PubSubServiceImpl extends PubSubServiceGrpc.PubSubServiceImplBase {
    
    // Mapa thread-safe para armazenar assinantes ativos (nome -> wrapper)
    private final ConcurrentHashMap<String, StreamObserverWrapper> subscribers = new ConcurrentHashMap<>();
    
    // Fila thread-safe para armazenar todas as mensagens publicadas
    private final ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    /**
     * Processa novas requisições de assinatura de clientes
     * @param request Contém informações do assinante
     * @param responseObserver Stream para enviar mensagens ao assinante
     */
    @Override
    public void subscribe(SubscribeRequest request, StreamObserver<Message> responseObserver) {
        String subscriberName = request.getSubscriberName();
        System.out.println("Assinante conectado: " + subscriberName);

        // Empacota o observer para tratar cancelamentos/erros
        StreamObserverWrapper wrapper = new StreamObserverWrapper(responseObserver);
        subscribers.put(subscriberName, wrapper);

        // Envia todas as mensagens existentes para o novo assinante
        messageQueue.forEach(wrapper::sendMessage);

        // Configura limpeza quando assinante desconectar
        wrapper.setOnCancelHandler(() -> {
            System.out.println("Assinante desconectado: " + subscriberName);
            subscribers.remove(subscriberName);
        });
    }

    /**
     * Processa publicação de novas mensagens
     * @param request A mensagem a ser publicada
     * @param responseObserver Stream para enviar confirmação
     */
    @Override
    public void publish(Message request, StreamObserver<PublishResponse> responseObserver) {
        System.out.println("Publicando mensagem: " + request.getContent());
        
        // Adiciona mensagem na fila
        messageQueue.offer(request);

        // Transmite para todos os assinantes ativos
        subscribers.forEach((name, wrapper) -> {
            wrapper.sendMessage(request);
        });

        // Envia confirmação de publicação
        PublishResponse response = PublishResponse.newBuilder()
            .setStatus("Mensagem publicada com sucesso")
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    /**
     * Classe wrapper para gerenciar StreamObserver com funcionalidades adicionais:
     * 1. Rastreia estado ativo
     * 2. Trata cancelamentos
     * 3. Fornece tratamento de erros
     */
    private static class StreamObserverWrapper {
        private final StreamObserver<Message> observer;  // O stream gRPC real
        private final AtomicBoolean active;              // Controla estado da conexão
        private Runnable onCancelHandler;               // Callback de limpeza

        public StreamObserverWrapper(StreamObserver<Message> observer) {
            this.observer = observer;
            this.active = new AtomicBoolean(true);
        }

        public void setOnCancelHandler(Runnable handler) {
            this.onCancelHandler = handler;
        }

        /**
         * Envia mensagem para o assinante de forma segura
         * @param message A mensagem a enviar
         */
        public void sendMessage(Message message) {
            if (active.get()) {  // Somente se conexão estiver ativa
                try {
                    observer.onNext(message);
                } catch (Exception e) {
                    // Marca como inativo e executa limpeza se falhar
                    active.set(false);
                    if (onCancelHandler != null) {
                        onCancelHandler.run();
                    }
                }
            }
        }
    }
}