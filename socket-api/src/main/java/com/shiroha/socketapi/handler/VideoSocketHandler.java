package com.shiroha.socketapi.handler;


import com.shiroha.socketapi.grpc.client.MessageExchangeClient;
import com.shiroha.socketapi.response.GrpcResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户端上传视频，服务端回传手语识别结果和语音播报
 */
@Component
@Slf4j
@ControllerAdvice
public class VideoSocketHandler extends AbstractWebSocketHandler {

    // 缓冲队列
    private final ConcurrentLinkedQueue<String> messageQueue = new ConcurrentLinkedQueue<>();
    // 计数器
    private final AtomicInteger messageCount = new AtomicInteger(0);

    private MessageExchangeClient messageExchangeClient;

    @Autowired
    private void setMessageCount(MessageExchangeClient messageExchangeClient) {
        this.messageExchangeClient = messageExchangeClient;
    }

    private void addToMessageQueue(String message) {
        messageQueue.offer(message);
    }

    private void checkAndSendMergedMessage(WebSocketSession session) {
        if (messageQueue.size() >= 2 || messageCount.get() > 2) {
            sendMergedMessage(session);
            messageCount.set(0);
        }
    }

    // 合并消息并发送给客户端
    private void sendMergedMessage(WebSocketSession session) {
        StringBuilder mergedMessage = new StringBuilder();
        while (!messageQueue.isEmpty()) {
            String message = messageQueue.poll();
            mergedMessage.append(message);
        }

        try {
            if (!mergedMessage.isEmpty() && session.isOpen()) {
                session.sendMessage(new TextMessage(mergedMessage.toString()));
            } else {
                log.warn("WebSocket session is closed, unable to send merged message.");
            }
        } catch (IOException e) {
            log.error("Failed to send merged message: {}", e.getMessage());
        }
    }

    @Override
    public void handleBinaryMessage(@NonNull WebSocketSession session, @NonNull BinaryMessage message) throws Exception {
        try {
            byte[] bytes = message.getPayload().array();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            buffer.order(ByteOrder.LITTLE_ENDIAN);
            int width = buffer.getShort();
            int height = buffer.getShort();

            int extraDataLength = bytes.length - 4;
            byte[] extraData = new byte[extraDataLength];
            System.arraycopy(bytes, 4, extraData, 0, extraDataLength);
            GrpcResponse response = messageExchangeClient.sendMessage(extraData, width, height);
            if (response != null && !response.isEmpty()) {
                String lastMessage = messageQueue.peek();
                if (lastMessage == null || !lastMessage.equals(response.getResult())) {
                    addToMessageQueue(response.getResult());
                    checkAndSendMergedMessage(session);
                }
            }
        } catch (NullPointerException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, @NonNull TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        session.sendMessage(new TextMessage("pong"));
    }

    @Override
    protected void handlePongMessage(@NonNull WebSocketSession session, @NonNull PongMessage message) throws Exception {
        try {
            super.handlePongMessage(session, message);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) throws Exception {
        // 连接建立时执行任何初始化操作
        log.info("The connection is established,{}:{}", Objects.requireNonNull(session.getRemoteAddress()).getHostString(), session.getRemoteAddress().getPort());
        session.setBinaryMessageSizeLimit(524288);
        session.setTextMessageSizeLimit(524288);
    }

    @Override
    public void afterConnectionClosed(@NonNull WebSocketSession session, @NonNull CloseStatus status) throws Exception {
        // 连接关闭时执行任何清理操作
        log.info("connect is closed:time:{},code:{},reason:{}}", System.currentTimeMillis(), status.getCode(), status.getReason());
    }
}
