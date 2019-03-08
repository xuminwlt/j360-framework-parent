package me.j360.framework.common.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.Builder;
import lombok.Data;
import me.j360.framework.common.web.context.DefaultJwtSessionUser;

import java.util.Date;

/**
 * @author: min_xu
 * @date: 2019/1/10 4:53 PM
 * 说明：
 */
public class JwtUtil {

    public static DefaultJwtSessionUser createSessionOnce(String token) {
        DefaultJwtSessionUser sessionUser = new DefaultJwtSessionUser();
        sessionUser.setJwt(token);
        return sessionUser;
    }

    public static DecodedJWT verify(Algorithm algorithm, String issue, String token) {
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer(issue)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt;
    }

    public static String sigurate(Algorithm algorithm, JwtOptions jwtOptions) {
        String token = JWT.create()
                .withJWTId(jwtOptions.getCid())
                .withIssuer(jwtOptions.getIssuer())
                .withIssuedAt(jwtOptions.getIssueAt())
                .withExpiresAt(jwtOptions.getExpiresAt())
                .withAudience(jwtOptions.getAudience())
                .withSubject(jwtOptions.getSubject())
                .withClaim("nonce", jwtOptions.getNonce())
                .sign(algorithm);
        return token;
    }

    @Data
    @Builder
    public static class JwtOptions {
        private String cid;
        private String issuer;
        private Date issueAt;
        private Date expiresAt;
        private String subject;
        private long nonce;
        private String[] audience;

        private String signature;
    }

}
