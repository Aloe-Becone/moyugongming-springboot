package com.shiroha.socketapi.config;

import com.shiroha.grpc.MessageExchangeGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GrpcConfig {

    @Value("${python-service.host}")
    String host;

    @Value("${python-service.port}")
    int port;

    private ManagedChannel channel;

    @Bean
    public MessageExchangeGrpc.MessageExchangeBlockingStub MessageExchangeBlockingStub() {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .keepAliveTimeout(1, TimeUnit.MINUTES)
                .keepAliveWithoutCalls(true)
                .build();
        return MessageExchangeGrpc.newBlockingStub(channel);
    }

    @Bean
    public ManagedChannel managedChannel() {
        if (channel == null || channel.isShutdown() || channel.isTerminated()) {
            channel = ManagedChannelBuilder.forAddress(host, port)
                    .usePlaintext()
                    .idleTimeout(20, TimeUnit.SECONDS)
                    .keepAliveTimeout(10, TimeUnit.SECONDS)
                    .keepAliveWithoutCalls(true)
                    .build();
        }
        return channel;
    }

    @PreDestroy
    public void cleanup() {
        if (channel != null && !channel.isShutdown()) {
            channel.shutdown();
            try {
                channel.awaitTermination(5, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
