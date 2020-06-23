package cn.edu.bit.cangjingge.common.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@ToString
@Component
public class JwtAuthenticationConfig {

    @Value("${cangjingge.security.jwt.url:/auth/token}")
    private String url;

    @Value("${cangjingge.security.jwt.header:Authorization}")
    private String header;

    @Value("${cangjingge.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${cangjingge.security.jwt.expiration:#{15*24*60*60}}")
    private int expiration; // default 15 days

    @Value("${cangjingge.security.jwt.secret:123}")
    private String secret;
}
