package com.example.pubsub;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;

public class PubSubServer {
    // Porta padrão onde o servidor irá escutar conexões
    private final int port = 50051;
    
    // Instância do servidor gRPC
    private final Server server;

    /**
     * Construtor que inicializa o servidor gRPC.
     * Configura a porta e adiciona a implementação do serviço.
     */
    public PubSubServer() {
        // Configuração do servidor gRPC
        server = ServerBuilder.forPort(port)
            .addService(new PubSubServiceImpl())  // Registra nossa implementação do serviço
            .build();  // Constrói a instância do servidor
    }

    /**
     * Inicia o servidor gRPC.
     * @throws IOException Se houver erro ao iniciar o servidor
     */
    public void start() throws IOException {
        // Inicia o servidor
        server.start();
        System.out.println("Server started, listening on " + port);

        // Configura um hook para desligamento gracioso
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            PubSubServer.this.stop();  // Garante o desligamento adequado
            System.err.println("*** server shut down");
        }));
    }

    /**
     * Para o servidor gRPC de forma controlada.
     */
    public void stop() {
        if (server != null) {
            // Inicia o processo de desligamento
            server.shutdown();
        }
    }

    /**
     * Bloqueia a thread principal até que o servidor seja terminado.
     * @throws InterruptedException Se a thread for interrompida
     */
    public void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            // Mantém o servidor rodando até ser explicitamente terminado
            server.awaitTermination();
        }
    }

    /**
     * Método principal para iniciar a aplicação servidor.
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        // Cria e inicia o servidor
        final PubSubServer server = new PubSubServer();
        server.start();
        
        // Mantém o servidor rodando até receber sinal de término
        server.blockUntilShutdown();
    }
}
