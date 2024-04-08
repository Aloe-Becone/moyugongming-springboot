package com.shiroha.socketapi.grpc.client;

import com.google.protobuf.ByteString;
import com.shiroha.grpc.MessageExchangeGrpc;
import com.shiroha.grpc.MessageRequest;
import com.shiroha.grpc.MessageResponse;
import com.shiroha.socketapi.response.GrpcResponse;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@Setter
@RequiredArgsConstructor
public class MessageExchangeClient {

    private final MessageExchangeGrpc.MessageExchangeBlockingStub blockingStub;

    public GrpcResponse sendMessage(byte[] binary_image, int width, int height) {
        MessageRequest request = MessageRequest
                .newBuilder()
                .setBinaryImage(ByteString.copyFrom(binary_image))
                .setWidth(width)
                .setHeight(height)
                .build();
        try {
            MessageResponse response = blockingStub.sendMessage(request);
            GrpcResponse grpcResponse = new GrpcResponse();
            grpcResponse.setResult(response.getResult());
            grpcResponse.setEmpty(response.getIsEmpty());
            return grpcResponse;
        } catch (StatusRuntimeException e) {
            log.error("gRPC failed:{}", e.getStatus());
            return null;
        }
    }
}
