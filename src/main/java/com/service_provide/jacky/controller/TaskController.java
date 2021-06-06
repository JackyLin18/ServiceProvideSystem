package com.service_provide.jacky.controller;

import com.alibaba.fastjson.JSONObject;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.common.enums.JSONResponseEnum;
import com.service_provide.jacky.model.dto.JSONResponse;
import com.service_provide.jacky.model.entity.Task;
import com.service_provide.jacky.model.entity.User;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.TaskService;
import com.service_provide.jacky.util.ListUtil;
import com.service_provide.jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.List;

/**
 * @ClassName TaskController
 * @Author Jacky
 * @Description
 **/
@Controller
@RequestMapping("/task")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public JSONResponse task(@RequestParam String params, HttpServletRequest request) {
        // 参数JSON判断是否为空
        if (ParamUtil.isParamNull(params)) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建task对象
        Task task = new Task();
        // 获取id
        Integer id = paramJSONObject.getInteger("id");
        task.setId(id);
        // 获取userId
        Integer userId = ((User) request.getSession().getAttribute("user")).getId();
        task.setUserId(userId);
        // 获取name
        String name = paramJSONObject.getString("name");
        task.setName(name);
        // 获取工作内容
        String content = paramJSONObject.getString("content");
        task.setContent(content);
        // 获取工作地点
        String place = paramJSONObject.getString("place");
        task.setPlace(place);
        // 获取开始时间
        Timestamp startTime = paramJSONObject.getTimestamp("start_time");
        task.setStartTime(startTime);
        // 获取工作要求
        String require = paramJSONObject.getString("require");
        System.out.println("=======================================");
        System.out.println(require);
        System.out.println("=======================================");
        task.setTaskRequire(require);
        // 获取工作酬劳
        Double repay = paramJSONObject.getDouble("repay");
        task.setRepay(repay);

        ServiceResult serviceResult;
        try {
            // 保存user对象
            System.out.println(task);
            serviceResult = taskService.saveTask(task);
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
            // 获取更改的task对象的id
            Integer resultId = (Integer) serviceResult.getData().get("id");
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
    public JSONResponse task(@RequestParam Integer id) {
        if (id == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            serviceResult = taskService.getTaskById(id);
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 状态码为成功
            // 获取返回结果中的task数据
            Task task = (Task) serviceResult.getData().get("task");
            // 返回响应
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue().setData(task);
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
    public JSONResponse tasks() {
        ServiceResult serviceResult;
        try {
            // 尝试获取任务列表
            serviceResult = taskService.getAllTasks();
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取task集合
            List<Task> tasks = ListUtil.castList(
                    serviceResult.getData().get("tasks"),
                    Task.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue()
                    .setData(tasks);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @GetMapping("/option")
    public JSONResponse tasks(@RequestParam String params) {
        // 将JSON字符串转换为JSONObject对象
        JSONObject paramJSONObject = JSONObject.parseObject(params);
        // 构建task对象
        Task task = new Task();
        // 获取name
        String name = paramJSONObject.getString("name");
        task.setName(name);
        // 获取工作内容
        String content = paramJSONObject.getString("content");
        task.setContent(content);
        // 获取工作地点
        String place = paramJSONObject.getString("place");
        task.setPlace(place);
        // 获取工作酬劳
        Double minRepay = paramJSONObject.getDouble("min_repay");
        Double maxRepay = paramJSONObject.getDouble("max_repay");

        ServiceResult serviceResult;
        try {
            serviceResult = taskService.getOptionalTasks(task, maxRepay, minRepay);
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取task集合
            List<Task> tasks = ListUtil.castList(
                    serviceResult.getData().get("tasks"),
                    Task.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue()
                    .setData(tasks);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @GetMapping("/user")
    public JSONResponse tasksByUId(@RequestParam Integer userId) {
        if (userId == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            serviceResult = taskService.getTasksByUserId(userId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取task集合
            List<Task> tasks = ListUtil.castList(
                    serviceResult.getData().get("tasks"),
                    Task.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue()
                    .setData(tasks);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @GetMapping("/serviceProvider")
    public JSONResponse tasksByPId(@RequestParam Integer serviceProviderId) {
        if (serviceProviderId == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            serviceResult = taskService.getTasksByServiceProviderId(serviceProviderId);
        } catch (Exception ex) {
            ex.printStackTrace();
            return JSONResponseEnum.DATABASE_ERROR_RESPONSE.getResponseValue();
        }
        // 获取状态码
        Integer resultCode = serviceResult.getCode();
        // 判断状态码
        if (resultCode.equals(CodeEnum.SUCCESS.getCode())) {
            // 成功
            // 获取task集合
            List<Task> tasks = ListUtil.castList(
                    serviceResult.getData().get("tasks"),
                    Task.class);
            return JSONResponseEnum.SUCCESS_RESPONSE.getResponseValue()
                    .setData(tasks);
        } else if (resultCode.equals(CodeEnum.NULL_RESULT.getCode())) {
            // 返回值为空
            return JSONResponseEnum.NULL_RESULT_RESPONSE.getResponseValue();
        }
        return JSONResponseEnum.OTHER_ERROR_RESPONSE.getResponseValue();
    }

    @ResponseBody
    @DeleteMapping
    public JSONResponse removeTask(@RequestParam Integer id) {
        // 检查是否携带必要参数
        if (id == null) {
            return JSONResponseEnum.NULL_PARAM_RESPONSE.getResponseValue();
        }
        ServiceResult serviceResult;
        try {
            serviceResult = taskService.removeTaskById(id);
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
