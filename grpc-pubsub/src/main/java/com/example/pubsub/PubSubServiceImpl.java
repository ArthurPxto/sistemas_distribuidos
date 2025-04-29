package com.example.pubsub;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class PubSubServiceImpl extends PubSubServiceGrpc.PubSubServiceImplBase {
    private final ConcurrentHashMap<String, StreamObserverWrapper> subscribers = new ConcurrentHashMap<>();
    private final ConcurrentLinkedQueue<Message> messageQueue = new ConcurrentLinkedQueue<>();

    @Override
    public void subscribe(SubscribeRequest request, StreamObserver<Message> responseObserver) {
        String subscriberName = request.getSubscriberName();
        System.out.println("Subscriber connected: " + subscriberName);

        // Cria um wrapper para o StreamObserver
        StreamObserverWrapper wrapper = new StreamObserverWrapper(responseObserver);
        subscribers.put(subscriberName, wrapper);

        // Envia mensagens existentes
        messageQueue.forEach(wrapper::sendMessage);

        // Configura o handler para quando o cliente cancelar
        wrapper.setOnCancelHandler(() -> {
            System.out.println("Subscriber disconnected: " + subscriberName);
            subscribers.remove(subscriberName);
        });
    }

    @Override
    public void publish(Message request, StreamObserver<PublishResponse> responseObserver) {
        System.out.println("Publishing message: " + request.getContent());
        messageQueue.offer(request);

        // Envia para todos os subscribers ativos
        subscribers.forEach((name, wrapper) -> {
            wrapper.sendMessage(request);
        });

        PublishResponse response = PublishResponse.newBuilder()
            .setStatus("Message published successfully")
            .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    // Wrapper para StreamObserver que trata cancelamentos
    private static class StreamObserverWrapper {
        private final StreamObserver<Message> observer;
        private final AtomicBoolean active = new AtomicBoolean(true);
        private Runnable onCancelHandler;

        public StreamObserverWrapper(StreamObserver<Message> observer) {
            this.observer = observer;
        }

        public void setOnCancelHandler(Runnable handler) {
            this.onCancelHandler = handler;
        }

        public void sendMessage(Message message) {
            if (active.get()) {
                try {
                    observer.onNext(message);
                } catch (Exception e) {
                    active.set(false);
                    if (onCancelHandler != null) {
                        onCancelHandler.run();
                    }
                }
            }
        }
    }
}