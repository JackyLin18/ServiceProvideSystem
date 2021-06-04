package com.service_provide.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.common.enums.JSONResponseEnum;
import com.service_provide.jacky.model.dto.JSONResponse;
import com.service_provide.jacky.model.entity.Freelancer;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.FreelancerService;
import com.service_provide.jacky.util.ListUtil;
import com.service_provide.jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/freelancer")
public class FreelancerController {
    private FreelancerService freelancerService;

    @Autowired
    public void setFreelancerService(FreelancerService freelancerService) {
        this.freelancerService = freelancerService;
    }

    /***
     * 保存自由职业者信息(新增或修改)
     * @param params 前端传来的 JSON
     * @param request HttpServletRequest 对象
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public JSONResponse freelancer(@RequestParam String params, HttpServletRequest request) {
        // 参数JSON判断是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建freelancer对象
        Freelancer freelancer = new Freelancer();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        freelancer.setId(id);
        // 获取name
        String name = paramJSONObject.getString("name");
        freelancer.setName(name);
        // 获取身份证号码
        String idCardNumber = paramJSONObject.getString("id_card_number");
        freelancer.setIdCardNumber(idCardNumber);
        // 获取家庭住址
        String familyAddress = paramJSONObject.getString("family_address");
        freelancer.setFamilyAddress(familyAddress);

        // 尝试保存freelancer对象
        ServiceResult serviceResult;
        try {
            serviceResult = freelancerService.saveFreelancer(freelancer);
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
            // 获取更改的freelancer对象的id
            Integer resultId = (Integer) serviceResult.getData().get("id");
            // 将修改的freelancer信息存入session
            request.getSession().setAttribute("freelancer", freelancer);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(resultId);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空状态
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 返回失败状态
        return JSONResponseEnum.FAIL_RESPONSE.getResponseValue();
    }

    /***
     * 根据自由职业者 id 查询自由职业者信息
     * @param id 指定的自由职业者的 id
     */
    @ResponseBody
    @GetMapping
    public JSONResponse freelancer(@RequestParam Integer id) {
        // 检查是否携带必要参数
        if (id == null) {
            return JSONResponseEnum.PARAMETER_MISSING_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            // 查询指定id的freelancer数据
            serviceResult = freelancerService.getFreelancerById(id);
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
            // 获取返回结果中的freelancer数据
            Freelancer freelancer = (Freelancer) serviceResult.getData().get("freelancer");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(freelancer);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 状态码为返回值为空
            // 返回响应
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        // 发送其它错误
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    /**
     * 获取所有的自由职业者信息
     */
    @ResponseBody
    @GetMapping("/all")
    public JSONResponse freelancers() {
        ServiceResult serviceResult;
        try {
            // 获取所有freelancer
            serviceResult = freelancerService.getAllFreelancers();
        } catch (Exception ex) {
            // 捕获异常并返回失败状态
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取freelancer集合
            List<Freelancer> freelancers = ListUtil.castList(
                    serviceResult.getData().get("freelancers"), Freelancer.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(freelancers);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }
}
