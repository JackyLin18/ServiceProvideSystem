package com.service_provide.jacky.handler;

import com.service_provide.jacky.model.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    @RequestMapping("/registerUser")
    public String registerUser() {
        return "registerUser";
    }
    @RequestMapping("/registerFreelancer")
    public String registerFreelancer() {
        return "registerFreelancer";
    }

    @RequestMapping("/userMain")
    public String userMain(HttpServletRequest request){
        return "userMain";
    }

    @RequestMapping("/userOwnMessage")
    public String userOwnMessage(HttpServletRequest request){
        return "userOwnMessage";
    }

    @RequestMapping("/searchServiceProvider")
    public String searchServiceProvider(){
        return "searchServiceProvider";
    }
}
