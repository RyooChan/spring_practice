package com.board.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

// secured를 이용해서 누군가만 메소드 호출 가능하도록 config해두기
// 이를 사용해서 외부에서 postman등의 방법을 통해 원하지 않는 동작을 하지 못하도록 제한 가능하다.

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class MethodSecurityConfig
        extends GlobalMethodSecurityConfiguration {
}