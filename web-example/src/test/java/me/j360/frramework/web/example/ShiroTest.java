package me.j360.frramework.web.example;


import me.j360.framework.boot.shiro.JwtSignature;
import me.j360.framework.common.util.JwtUtil;
import me.j360.framework.common.web.context.DefaultSessionUser;
import me.j360.framework.web.example.BootstrapApplication;
import me.j360.framework.web.example.configuration.RedissonSessionStorageDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: min_xu
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {BootstrapApplication.class})
@ActiveProfiles("local")
public class ShiroTest {

    @Autowired
    private JwtSignature jwtSignature;
    @Autowired
    private RedissonSessionStorageDAO sessionStorageDAO;
    @Test
    public void testEncode() {
        String token = jwtSignature.createUser("111", "uuid2");
        System.out.println("\n------------------");
        System.out.println(token);
        System.out.println("------------------");
    }

    @Test
    public void testDecode() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VyIiwic3ViIjoidXVpZDIiLCJpc3MiOiJqMzYwLm1lIiwiZXhwIjoxNTgzNjQ2Nzk0LCJpYXQiOjE1NTIwMjQzOTQsIm5vbmNlIjoxNTUyMDI0Mzk0NjAwLCJqdGkiOiIxMTEifQ.bLJu7dss2BwkyOEtk7CKIxHKtkQzUOaRXLJySRnzX18";
        JwtUtil.JwtOptions options = jwtSignature.decode(token);
        System.out.println("\n------------------");
        System.out.println(options);
        System.out.println("------------------");
    }

    @Test
    public void testSaveSessionUser() {
        DefaultSessionUser sessionUser = new DefaultSessionUser();
        sessionUser.setSessionId("uuid2");
        sessionUser.setUid(3L);
        sessionUser.setCid("111");
        sessionStorageDAO.save(sessionUser);
    }

    @Test
    public void testGetSessionUser() {
        DefaultSessionUser sessionUser = (DefaultSessionUser) sessionStorageDAO.get("uuid2");
        System.out.println("\n------------------");
        System.out.println(String.format("%s, %s, %s", sessionUser.getSessionId(), sessionUser.getUid(), sessionUser.getCid()));
        System.out.println("------------------");
    }
}
