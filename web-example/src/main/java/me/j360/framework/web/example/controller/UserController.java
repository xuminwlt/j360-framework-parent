package me.j360.framework.web.example.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.boot.controller.BaseController;
import me.j360.framework.common.web.context.SessionContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/api/user", tags = "用户接口")
@Controller
@RequestMapping("/api/user")
@Slf4j
public class UserController extends BaseController {


    //Add Http Header
    //Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ1c2VyIiwic3ViIjoidXVpZDIiLCJpc3MiOiJqMzYwLm1lIiwiZXhwIjoxNTgzNjQ2Nzk0LCJpYXQiOjE1NTIwMjQzOTQsIm5vbmNlIjoxNTUyMDI0Mzk0NjAwLCJqdGkiOiIxMTEifQ.bLJu7dss2BwkyOEtk7CKIxHKtkQzUOaRXLJySRnzX18
    //Client-Agent:ISSUE/1.0.0/1/2/7.1/iPhone 5s (A1457/A1518/A1528/A1530)/7EAB70B1-624F-463A-943C-E7FF235A9A0C/wifi/iOS

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public String info() {
        log.info("login user: {}", SessionContext.getBaseSessionUser());
        log.info("login uid: {}", SessionContext.getSessionId());
        log.info("login uid: {}", SessionContext.getBaseSessionUser().getAgent());
        return "SUCCESS";
    }

    @RequestMapping(value = "/guest", method = RequestMethod.GET)
    @ResponseBody
    public String guest() {
        return "SUCCESS";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    @ResponseBody
    public String test() {
        return "SUCCESS";
    }
}
