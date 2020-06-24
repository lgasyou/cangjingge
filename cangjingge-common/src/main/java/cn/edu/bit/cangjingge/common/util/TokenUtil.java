package cn.edu.bit.cangjingge.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.apache.http.HttpHeaders;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class TokenUtil {

    private static final String USERNAME_CLAIM = "username";
    private static final String USER_ROLES = "roles";

    /**
     * 生成签名
     * @return 加密的token
     */
    public static String sign(
            String username,
            String roles,
            String secret,
            Date expiresAt
    ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // 附带username信息
            return JWT.create()
                    .withClaim(USERNAME_CLAIM, username)
                    .withClaim(USER_ROLES, roles)
                    .withExpiresAt(expiresAt)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    /**
     * 校验token的正确性
     */
    public static boolean verify(
            String token,
            String username,
            String roles,
            String secret
    ) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim(USERNAME_CLAIM, username)
                    .withClaim(USER_ROLES, roles)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static String getTokenFromHeaders() {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        assert sra != null;
        HttpServletRequest request = sra.getRequest();
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public static String getUsernameFromHeaders() {
        String token = getTokenFromHeaders();
        return getUsername(token);
    }


    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USERNAME_CLAIM).asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    public static String[] getUserRoles(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim(USER_ROLES).asString().split(",");
        } catch (JWTDecodeException e) {
            return null;
        }
    }

}
