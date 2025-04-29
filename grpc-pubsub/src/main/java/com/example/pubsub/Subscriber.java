package com.example.pubsub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class Subscriber {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        PubSubServiceGrpc.PubSubServiceStub asyncStub = PubSubServiceGrpc.newStub(channel);

        SubscribeRequest request = SubscribeRequest.newBuilder()
                .setSubscriberName("Subscriber1")
                .build();

        System.out.println("Subscribing...");
        
        asyncStub.subscribe(request, new StreamObserver<Message>() {
            @Override
            public void onNext(Message value) {
                System.out.println("Received: " + value.getContent());
            }

            @Override
            public void onError(Throwable t) {
                System.err.println("Subscription error:");
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Subscription completed");
            }
        });

        // Mantém o subscriber ativo por mais tempo
        try {
            Thread.sleep(30000);  // Espera 30 segundos para receber mensagens
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        channel.shutdown();
    }
}