package javawebdev.petsns.config;


import javawebdev.petsns.chat.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket        // WebSocket 활성화
@RequiredArgsConstructor
@EnableAsync            // 비동기 처리
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler chatHandler;

    // Endpoint = /chat
    // 도메인이 다른 서버에서도 접속 가능하도록 setAllowedOrigins("*") 추가
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
    }

}
