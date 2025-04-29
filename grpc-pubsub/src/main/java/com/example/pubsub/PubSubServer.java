package com.example.pubsub;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class PubSubServer {
    private final int port = 50051;
    private final Server server;

    public PubSubServer() {
        // Criação do servidor gRPC real
        server = ServerBuilder.forPort(port)
            .addService(new PubSubServiceImpl())  // Adiciona o serviço
            .build();
    }

    public void start() throws IOException {
        server.start();
        System.out.println("Server started, listening on " + port);

        // Adiciona um hook para desligar o servidor quando a JVM for finalizada
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            PubSubServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    public void blockUntilShutdown() throws InterruptedException {
        // Agora chamamos o método blockUntilShutdown() no servidor real
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        final PubSubServer server = new PubSubServer();
        server.start();
        server.blockUntilShutdown();  // Espera o servidor ser finalizado
    }
}
