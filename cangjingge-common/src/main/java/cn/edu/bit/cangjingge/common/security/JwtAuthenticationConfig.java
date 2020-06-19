package cn.edu.bit.cangjingge.common.security;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${cangjingge.security.jwt.url:/login}")
    private String url;

    @Value("${cangjingge.security.jwt.header:Authorization}")
    private String header;

    @Value("${cangjingge.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${cangjingge.security.jwt.expiration:#{24*60*60}}")
    private int expiration; // default 24 hours

    @Value("${cangjingge.security.jwt.secret:123}")
    private String secret;
}
