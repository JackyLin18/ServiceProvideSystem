package com.service_provide.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.common.enums.JSONResponseEnum;
import com.service_provide.jacky.model.dto.JSONResponse;
import com.service_provide.jacky.model.entity.Freelancer;
import com.service_provide.jacky.model.entity.ServiceProvider;
import com.service_provide.jacky.model.entity.User;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.FreelancerService;
import com.service_provide.jacky.service.ServiceProviderService;
import com.service_provide.jacky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @ClassName LoginController
 * @Author Jacky
 * @Description
 **/
@Controller()
@RequestMapping("/login")
public class LoginController {
    private FreelancerService freelancerService;
    private UserService userService;
    private ServiceProviderService serviceProviderService;

    @Autowired
    public void setFreelancerService(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    @Autowired
    public void setServiceProviderService(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/user")
    public JSONResponse user(@RequestParam String params,
                             HttpServletRequest request) {
        return loginByType(User.class, params, request);
    }

    @ResponseBody
    @PostMapping("/freelancer")
    public JSONResponse freelancer(@RequestParam String params,
                                   HttpServletRequest request) {
        return loginByType(Freelancer.class, params, request);
    }

    @ResponseBody
    @PostMapping("/serviceProvider")
    public JSONResponse serviceProvider(@RequestParam String params,
                                        HttpServletRequest request) {
        return loginByType(ServiceProvider.class, params, request);
    }

    private JSONResponse loginByType(Class clazz, String params,
                                     HttpServletRequest request) {
        // 检查是否携带必要参数
        if (params == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        // 获取密码
        String inputPassword = paramJSONObject.getString("password");
        // 校验密码
        ServiceResult serviceResult;
        if (clazz == User.class) {
            try {
                serviceResult = userService.loginUser(id, inputPassword);
            } catch (Exception ex) {
                ex.printStackTrace();
                return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
            }
        } else if (clazz == Freelancer.class) {
            try {
                serviceResult = freelancerService.loginFreelancer(id, inputPassword);
            } catch (Exception ex) {
                ex.printStackTrace();
                return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
            }
        } else if (clazz == ServiceProvider.class) {
            try {
                serviceResult = serviceProviderService.loginServiceProvider(id, inputPassword);
            } catch (Exception ex) {
                ex.printStackTrace();
                return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
            }
        } else {
            return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            Object loginObj;
            if (clazz == User.class) {
                serviceResult = userService.getUserById(id);
                loginObj = serviceResult.getData().get("user");
                request.getSession().setAttribute("user", loginObj);
            } else if (clazz == Freelancer.class) {
                serviceResult = freelancerService.getFreelancerById(id);
                loginObj = serviceResult.getData().get("freelancer");
                request.getSession().setAttribute("freelancer", loginObj);
            } else {
                serviceResult = serviceProviderService.getServiceProviderById(id);
                loginObj = serviceResult.getData().get("serviceProvider");
                request.getSession().setAttribute("serviceProvider", loginObj);
            }
            // 成功状态
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(loginObj);
        } else if (resultCode.equals(CodeEnum.NULL_PARAM.getCode())) {
            // 参数为空
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 返回失败状态
        return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
    }

    @GetMapping("/out")
    @ResponseBody
    public JSONResponse logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.removeAttribute("serviceProvider");
        session.removeAttribute("freelancer");
        session.removeAttribute("user");
        return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
    }
}
