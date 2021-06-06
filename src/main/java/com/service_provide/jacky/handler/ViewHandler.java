package com.service_provide.jacky.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
    public String userMain(HttpServletRequest request) {
        return "userMain";
    }

    @RequestMapping("/userOwnMessage")
    public String userOwnMessage(HttpServletRequest request) {
        return "userOwnMessage";
    }

    @RequestMapping("/searchServiceProvider")
    public String searchServiceProvider() {
        return "searchServiceProvider";
    }

    @RequestMapping("/addTask")
    public String addTask(HttpServletRequest request) {
        if (request.getSession().getAttribute("user") == null) {
            return "login";
        }
        return "addTask";
    }

    @RequestMapping("/userTasks")
    public String userTasks(HttpServletRequest request){
        if (request.getSession().getAttribute("user") == null) {
            return "login";
        }
        return "userTasks";
    }

    @RequestMapping("/freelancerMain")
    public String freelancerMain(HttpServletRequest request){
        if (request.getSession().getAttribute("freelancer") == null) {
            return "login";
        }
        return "freelancerMain";
    }

    @RequestMapping("/certification")
    public String certification(){
        return "certification";
    }

    @RequestMapping("/registerServiceProvider")
    public String registerServiceProvider(HttpServletRequest request){
        if (request.getSession().getAttribute("freelancer") == null) {
            return "login";
        }
        return "registerServiceProvider";
    }
}
