package com.example.pubsub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class Publisher {
    public static void main(String[] args) throws InterruptedException {
        // 1. Criação do Canal de Comunicação
        // Configura uma conexão com o servidor gRPC no localhost, porta 50051
        // usePlaintext() indica que a conexão não usará SSL/TLS (apenas para desenvolvimento)
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()  // Não usar em produção!
                .build();

        // 2. Preparação da Mensagem
        // Cria uma mensagem usando o padrão Builder do Protobuf
        // A mensagem contém o conteúdo "Mensagem extremamente importante"
        Message message = Message.newBuilder()
                .setContent("Mensagem extremamente importante")
                .build();
        
        // 3. Configuração do Cliente Assíncrono
        // Cria um stub (cliente) que retorna Futures para operações assíncronas
        // O FutureStub é ideal quando precisamos confirmar o recebimento da mensagem
        PubSubServiceGrpc.PubSubServiceFutureStub futureStub = PubSubServiceGrpc.newFutureStub(channel);
        
        try {
            // 4. Publicação da Mensagem
            // Envia a mensagem e espera pela confirmação com .get()
            // Isso bloqueia até receber resposta do servidor ou timeout
            futureStub.publish(message).get();
            
            // 5. Confirmação de Envio
            System.out.println("Mensagem publicada com sucesso");
        } catch (Exception e) {
            // 6. Tratamento de Erros
            // Captura qualquer exceção durante o envio (servidor offline, etc)
            e.printStackTrace();
        }

        // 7. Finalização Controlada
        // Espera 1 segundo para garantir processamento antes de fechar
        Thread.sleep(1000);
        
        // 8. Encerramento do Canal
        // Fecha a conexão com o servidor de forma limpa
        channel.shutdown();
    }
}