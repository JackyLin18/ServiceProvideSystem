package com.service_provide.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.common.enums.JSONResponseEnum;
import com.service_provide.jacky.model.dto.JSONResponse;
import com.service_provide.jacky.model.entity.User;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.UserService;
import com.service_provide.jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public JSONResponse user(@RequestParam String params, HttpServletRequest request) {
        // 参数JSON判断是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建user对象
        User user = new User();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        user.setId(id);
        // 获取name
        String name = paramJSONObject.getString("name");
        user.setName(name);
        // 获取电话号码
        String telNumber = paramJSONObject.getString("tel_number");
        user.setTelNumber(telNumber);

        ServiceResult serviceResult;
        try {
            // 保存user对象
            serviceResult = userService.saveUser(user);
        } catch (Exception ex) {
            // 捕获异常返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功状态
            // 获取更改的user对象的id
            Integer resultId = (Integer) serviceResult.getData().get("id");
            // 将修改的user信息存入session
            request.getSession().setAttribute("user", user);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(resultId);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 返回失败状态
        return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
    }
}
