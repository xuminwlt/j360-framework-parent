package me.j360.framework.boot.shiro;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.vip.vjtools.vjkit.collection.ArrayUtil;
import lombok.Setter;
import me.j360.framework.base.constant.DefaultErrorCode;
import me.j360.framework.base.exception.ServiceException;
import me.j360.framework.boot.shiro.dao.SessionStorageDAO;
import me.j360.framework.common.exception.BizException;
import me.j360.framework.common.util.JwtUtil;
import me.j360.framework.common.web.context.BaseSessionUser;
import me.j360.framework.common.web.context.DefaultJwtSessionUser;
import me.j360.framework.common.web.context.SessionContext;
import me.j360.framework.common.web.context.SessionUser;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author: min_xu
 * @date: 2019/1/11 5:11 PM
 * 说明：
 */
public class JwtSignature {

    private static String Authorization = "Authorization";
    private static String CID = "cid";
    private static String CODE = "code";

    //Auth 定义jwt内容
    public static final String JWT_AUD_GUEST = "guest";
    public static final String JWT_AUD_USET = "user";

    @Setter
    public String JWT_ISSUER;

    private Algorithm algorithm;
    private SessionStorageDAO sessionStorageDAO;

    public JwtSignature(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public JwtSignature(Algorithm algorithm, SessionStorageDAO sessionStorageDAO) {
        this.sessionStorageDAO = sessionStorageDAO;
        this.algorithm = algorithm;
    }

    public String createGuest(String cid) {
        return createJwt(cid, null, JWT_AUD_GUEST);
    }

    /**
     * pre step none
     * @param request
     * @return
     */
    public String createGuest(HttpServletRequest request) {
        //storage
        return createGuest(getCid(request));
    }

    public String createUser(String cid, String sessionId) {
        return createJwt(cid, sessionId, JWT_AUD_USET);
    }

    /**
     * pre step AuthcFilter
     * @return
     */
    public String createUser() {
        BaseSessionUser sessionUser = SessionContext.getBaseSessionUser();
        //storage
        String cid = sessionUser.getCid();
        String sessionId = sessionUser.getSessionId();
        String jwt = createUser(cid, sessionId);
        return jwt;
    }


    private String createJwt(String cid, String uuid, String aud) {
        try {
            DateTime dateTime = new DateTime();
            String token = JWT.create()
                    .withJWTId(cid)
                    .withIssuer(JWT_ISSUER)
                    .withIssuedAt(dateTime.toDate())
                    .withExpiresAt(dateTime.plusYears(1).toDate())
                    .withAudience(aud)
                    .withSubject(Optional.ofNullable(uuid).orElse(""))
                    .withClaim("nonce", System.currentTimeMillis())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
            throw new ServiceException(DefaultErrorCode.SYSTEM_ERROR,  exception);
        }
    }


    public String getJwt(HttpServletRequest httpServletRequest) {
        String authorization = httpServletRequest.getHeader(Authorization);
        if(StringUtils.isEmpty(authorization) || ! authorization.startsWith("Bearer ")){
            throw BizException.bizException.clone(DefaultErrorCode.AUTH_ACCESS_SESSION_ERROR);
        }
        return authorization.substring(7);
    }

    public JwtUtil.JwtOptions decode(String token) {
        DecodedJWT jwt = JwtUtil.verify(algorithm, JWT_ISSUER, token);
        return JwtUtil.JwtOptions.builder().subject(jwt.getSubject())
                .signature(jwt.getSignature())
                .audience(ArrayUtil.toArray(jwt.getAudience(), String.class))
                .cid(jwt.getId())
                .issueAt(jwt.getIssuedAt())
                .issuer(jwt.getIssuer())
                .nonce(jwt.getClaim("nonce").asLong())
                .expiresAt(jwt.getExpiresAt())
                .build();
    }


    public boolean checkJwt(String token) {
        try {
            JwtUtil.verify(this.algorithm, JWT_ISSUER, token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String signature(JwtUtil.JwtOptions options) {
        String credentials = JwtUtil.sigurate(algorithm, options);
        return credentials;
    }

    //get cid
    public String getCid(HttpServletRequest request) {
        return request.getParameter(CID);
    }

    //get code
    public String getCode(HttpServletRequest request) {
        return request.getParameter(CODE);
    }

    //create Session User
    private SessionUser createSessionUser(String jwt, String cid, Long uid, String sessionId) {
        DefaultJwtSessionUser sessionUser = new DefaultJwtSessionUser();
        sessionUser.setJwt(jwt);
        sessionUser.setCid(cid);
        sessionUser.setUid(uid);
        sessionUser.setSessionId(sessionId);
        return sessionUser;
    }
}
