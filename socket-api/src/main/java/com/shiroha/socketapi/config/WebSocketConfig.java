package com.shiroha.socketapi.config;


import com.shiroha.socketapi.handler.VideoSocketHandler;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * WebSocket服务端配置类
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(@NonNull WebSocketHandlerRegistry registry) {
        registry.addHandler(videoSocketHandler(), "ws/video").setAllowedOrigins("*");
    }

    @Bean
    public WebSocketHandler videoSocketHandler() {
        return new VideoSocketHandler();
    }
}
