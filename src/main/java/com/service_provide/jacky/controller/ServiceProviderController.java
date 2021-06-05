package com.service_provide.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.common.enums.JSONResponseEnum;
import com.service_provide.jacky.model.dto.JSONResponse;
import com.service_provide.jacky.model.entity.ServiceProvider;
import com.service_provide.jacky.model.entity.User;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.ServiceProviderService;
import com.service_provide.jacky.util.ListUtil;
import com.service_provide.jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @ClassName ServiceProviderController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/serviceProvider")
public class ServiceProviderController {
    private ServiceProviderService serviceProviderService;

    @Autowired
    public void setServiceProviderService(ServiceProviderService serviceProviderService) {
        this.serviceProviderService = serviceProviderService;
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public JSONResponse serviceProvider(@RequestParam String params,
                                        HttpServletRequest request) {
        // 参数JSON判断是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建serviceProvider对象
        ServiceProvider serviceProvider = new ServiceProvider();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        serviceProvider.setId(id);
        // 获取name
        String name = paramJSONObject.getString("name");
        serviceProvider.setName(name);
        // 获取密码
        String password = paramJSONObject.getString("password");
        serviceProvider.setPassword(password);
        // 获取电话号码
        String telNumber = paramJSONObject.getString("tel_number");
        serviceProvider.setTelNumber(telNumber);
        // 获取年龄
        Integer age = paramJSONObject.getInteger("age");
        serviceProvider.setAge(age);
        // 获取性别
        Integer sex = paramJSONObject.getInteger("sex");
        serviceProvider.setSex(sex);
        // 获取擅长领域
        String expertScope = paramJSONObject.getString("expert_scope");
        serviceProvider.setExpertScope(expertScope);
        // 获取自我介绍
        String introduction = paramJSONObject.getString("introduction");
        serviceProvider.setIntroduction(introduction);

        ServiceResult serviceResult;
        try {
            // 保存serviceProvider对象
            serviceResult = serviceProviderService.saveServiceProvider(serviceProvider);
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
            // 获取更改的serviceProvider对象的id
            Integer resultId = (Integer) serviceResult.getData().get("id");
            // 将修改的serviceProvider信息存入session
            request.getSession().setAttribute("serviceProvider", serviceProvider);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(resultId);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 返回失败状态
        return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @GetMapping
    public JSONResponse serviceProvider(@RequestParam Integer id) {
        if (id == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            serviceResult = serviceProviderService.getServiceProviderById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 状态码为成功
            // 获取返回结果中的serviceProvider数据
            ServiceProvider serviceProvider = (ServiceProvider) serviceResult.getData().get("serviceProvider");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(serviceProvider);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 状态码为返回值为空
            // 返回响应
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 发送其它错误
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @GetMapping("/all")
    public JSONResponse serviceProviders() {
        ServiceResult serviceResult;
        try {
            // 尝试获取服务提供者列表
            serviceResult = serviceProviderService.getAllServiceProviders();
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取serviceProvider集合
            List<ServiceProvider> serviceProviders = ListUtil.castList(
                    serviceResult.getData().get("serviceProviders"),
                    ServiceProvider.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue()
                    .setData(serviceProviders);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @GetMapping("/option")
    public JSONResponse serviceProviders(@RequestParam String params) {
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建serviceProvider对象
        ServiceProvider serviceProvider = new ServiceProvider();
        // 获取name
        String name = paramJSONObject.getString("name");
        serviceProvider.setName(name);
        // 获取年龄
        Integer minAge = paramJSONObject.getInteger("min_age");
        Integer maxAge = paramJSONObject.getInteger("max_age");
        // 获取性别
        Integer sex = paramJSONObject.getInteger("sex");
        serviceProvider.setSex(sex);
        // 获取擅长领域
        String expertScope = paramJSONObject.getString("expert_scope");
        serviceProvider.setExpertScope(expertScope);

        ServiceResult serviceResult;
        try {
            serviceResult = serviceProviderService.
                    getOptionalServiceProviders(serviceProvider, maxAge, minAge);
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取serviceProvider集合
            List<ServiceProvider> serviceProviders = ListUtil.castList(
                    serviceResult.getData().get("serviceProviders"),
                    ServiceProvider.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue()
                    .setData(serviceProviders);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @DeleteMapping
    public JSONResponse removeServiceProvider(@RequestParam Integer id) {
        // 检查是否携带必要参数
        if (id == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            serviceResult = serviceProviderService.removeServiceProviderById(id);
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
            return JSONResponseEnum.SUCCESS_WITHOUT_DATA_RESPONSE.getResponseValue();
        } else {
            // 失败状态
            return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
        }
    }
}
