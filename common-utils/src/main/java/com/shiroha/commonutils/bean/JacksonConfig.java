package com.shiroha.commonutils.bean;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.shiroha.commonutils.serializer.GrantedAuthoritySerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;

@Configuration
public class JacksonConfig {
    @Bean
    public SimpleModule customSerializerModule() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(GrantedAuthority.class, new GrantedAuthoritySerializer());
        return module;
    }
}
