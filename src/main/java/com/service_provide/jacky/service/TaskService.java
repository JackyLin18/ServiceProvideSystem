package com.service_provide.jacky.service;

import com.service_provide.jacky.model.entity.Task;
import com.service_provide.jacky.model.vo.ServiceResult;

public interface TaskService {
    /**
     * 添加、修改任务
     */
    ServiceResult saveTask(Task task);

    /**
     * 根据任务 id 删除任务
     */
    ServiceResult removeTaskById(Integer id);

    /**
     * 根据用户 id 删除任务
     */
    ServiceResult removeTasksByUserId(Integer userId);

    /**
     * 根据任务 id 查询任务
     */
    ServiceResult getTaskById(Integer id);

    /**
     * 根据用户 id 查询任务
     */
    ServiceResult getTasksByUserId(Integer userId);

    /**
     * 根据服务提供者 id 查询任务
     */
    ServiceResult getTasksByServiceProviderId(Integer serviceProviderId);

    /**
     * 模糊查询任务
     */
    ServiceResult getOptionalTasks(Task task, Double maxRepay, Double minRepay);

    /**
     * 查询所有任务
     */
    ServiceResult getAllTasks();
}
