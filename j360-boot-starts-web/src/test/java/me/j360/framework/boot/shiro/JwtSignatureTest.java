package me.j360.framework.boot.shiro;

import com.auth0.jwt.algorithms.Algorithm;
import me.j360.framework.common.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
/**
 * @author: min_xu
 */
public class JwtSignatureTest {

    private JwtSignature jwtSignature;

    @Before
    public void before() {
        Algorithm algorithm = Algorithm.HMAC256("1");
        jwtSignature = new JwtSignature(algorithm);
        jwtSignature.setJWT_ISSUER("J360");
    }

    @Test
    public void testCreateUser() throws Exception {
        String token = jwtSignature.createUser("1", "1");
        System.out.println(token);
        assertNotNull(token);
    }

    @Test
    public void testDecode() throws Exception {
        JwtUtil.JwtOptions options = jwtSignature.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VyIiwic3ViIjoiMSIsImlzcyI6IkozNjAiLCJleHAiOjE1ODM1ODExMTUsImlhdCI6MTU1MTk1ODcxNSwibm9uY2UiOjE1NTE5NTg3MTU5MTMsImp0aSI6IjEifQ.cOjeUxEzCFgBVQyGbfjeXp0pMIXolDOO7BGSoSnCB9M");
        System.out.println(options);
    }

    @Test
    public void testCheckJwt() throws Exception {

    }

    @Test
    public void testSignature() throws Exception {

    }
}