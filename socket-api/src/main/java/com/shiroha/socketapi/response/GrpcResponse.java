package com.shiroha.socketapi.response;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class GrpcResponse {
    String result;
    boolean isEmpty;
}
