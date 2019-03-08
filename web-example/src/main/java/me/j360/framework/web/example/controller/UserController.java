package me.j360.framework.web.example.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import me.j360.framework.boot.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "/api/user", tags = "用户接口")
@Controller
@RequestMapping("/api/user")
@Slf4j
public class UserController extends BaseController {


    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public String info() {
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
