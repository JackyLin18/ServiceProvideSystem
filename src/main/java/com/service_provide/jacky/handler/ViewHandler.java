package com.service_provide.jacky.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName ViewHandler
 * @Author Jacky
 * @Description
 **/
@Controller
public class ViewHandler {
    @RequestMapping(value = {"/login", "/index", "/"})
    public String login() {
        return "login";
    }
}
