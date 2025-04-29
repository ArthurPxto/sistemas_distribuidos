package com.example.pubsub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class Subscriber {
    public static void main(String[] args) {
        // 1. Configuração do Canal de Comunicação
        // Cria um canal gRPC para se conectar ao servidor local na porta 50051
        // usePlaintext() desabilita criptografia (apenas para desenvolvimento)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()  // Não usar em ambiente de produção!
                .build();

        // 2. Criação do Stub Assíncrono
        // Prepara um cliente assíncrono para comunicação não-bloqueante
        PubSubServiceGrpc.PubSubServiceStub asyncStub = PubSubServiceGrpc.newStub(channel);

        // 3. Preparação da Requisição de Assinatura
        // Constrói a mensagem de solicitação com identificador do assinante
        SubscribeRequest request = SubscribeRequest.newBuilder()
                .setSubscriberName("Subscriber1")  // Identificador único
                .build();

        System.out.println("Subscribing...");
        
        // 4. Registro do Assinante
        // Faz a chamada RPC assíncrona para se inscrever
        asyncStub.subscribe(request, new StreamObserver<Message>() {
            /**
             * Callback invocado quando uma nova mensagem é recebida
             * @param value A mensagem recebida do servidor
             */
            @Override
            public void onNext(Message value) {
                // Processa cada mensagem recebida
                System.out.println("Received: " + value.getContent());
            }

            /**
             * Callback para tratamento de erros
             * @param t Objeto contendo detalhes do erro
             */
            @Override
            public void onError(Throwable t) {
                System.err.println("Subscription error:");
                t.printStackTrace();  // Log detalhado do erro
            }

            /**
             * Callback quando o servidor finaliza o stream
             */
            @Override
            public void onCompleted() {
                System.out.println("Subscription completed");
            }
        });

        // 5. Mantém o Programa Ativo
        // Bloqueia a thread principal por 30 segundos para continuar recebendo mensagens
        try {
            Thread.sleep(30000);  // Mantém conexão por 30 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();  // Trata possível interrupção
        }

        // 6. Finalização Controlada
        // Encerra a conexão com o servidor
        channel.shutdown();
    }
}