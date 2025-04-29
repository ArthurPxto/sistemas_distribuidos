package com.example.pubsub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class Publisher {
    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        PubSubServiceGrpc.PubSubServiceStub stub = PubSubServiceGrpc.newStub(channel);

        // Enviar uma mensagem de publicação
        Message message = Message.newBuilder().setContent("Mensagem enviada para amo voces lindaos").build();
        
        // Usando FutureStub para garantir que a mensagem seja enviada
        PubSubServiceGrpc.PubSubServiceFutureStub futureStub = PubSubServiceGrpc.newFutureStub(channel);
        try {
            futureStub.publish(message).get();
            System.out.println("Message published successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Espera um pouco antes de fechar
        Thread.sleep(1000);
        channel.shutdown();
    }
}