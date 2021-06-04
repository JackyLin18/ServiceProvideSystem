package com.service_provide.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.common.enums.JSONResponseEnum;
import com.service_provide.jacky.model.dto.JSONResponse;
import com.service_provide.jacky.model.entity.User;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.UserService;
import com.service_provide.jacky.util.ListUtil;
import com.service_provide.jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /***
     * 保存用户信息(新增或修改)
     * @param params 前端传来的 JSON
     * @param request HttpServletRequest 对象
     */
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

    /**
     * 根据 id 查询指定的用户
     * @param id 需要查询的用户的 id
     */
    @ResponseBody
    @GetMapping
    public JSONResponse user(@RequestParam Integer id) {
        // 检查是否携带必要参数
        if (id == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 查询指定id的user数据
            serviceResult = userService.getUserById(id);
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 状态码为成功
            // 获取返回结果中的user数据
            User user = (User) serviceResult.getData().get("user");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(user);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 状态码为返回值为空
            // 返回响应
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 发送其它错误
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /***
     * 获取所有的用户信息
     */
    @ResponseBody
    @GetMapping("/all")
    public JSONResponse users(){
        ServiceResult serviceResult;
        try{
            // 尝试获取用户列表
            serviceResult = userService.getAllUsers();
        }catch (Exception ex){
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取user集合
            List<User> users = ListUtil.castList(
                    serviceResult.getData().get("users"), User.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(users);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    
}
