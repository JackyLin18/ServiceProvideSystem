package com.service_provide.jacky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.mapper.TaskMapper;
import com.service_provide.jacky.model.entity.Task;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    private TaskMapper taskMapper;

    @Autowired
    public void setTaskMapper(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public ServiceResult saveTask(Task task) {
        if (task.getId() == null) {
            // 添加操作
            int insert = taskMapper.insert(task);
            if (insert <= 0) {
                return ServiceResult.fail().setMessage("插入信息失败");
            }
        } else {
            // 更新操作
            int update = taskMapper.updateById(task);
            if (update <= 0) {
                return ServiceResult.fail().setMessage("更新信息失败");
            }
        }
        return ServiceResult.ok("id", task.getId());
    }

    @Override
    public ServiceResult removeTaskById(Integer id) {
        int delete = taskMapper.deleteById(id);
        if (delete <= 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok();
    }

    @Override
    public ServiceResult removeTasksByUserId(Integer userId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        taskMapper.delete(wrapper);
        return ServiceResult.ok();
    }

    @Override
    public ServiceResult getTaskById(Integer id) {
        Task task = taskMapper.selectById(id);
        if (task == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("task", task);
    }

    @Override
    public ServiceResult getTasksByUserId(Integer userId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        List<Task> tasks = taskMapper.selectList(wrapper);
        if (tasks.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("tasks", tasks);
        return ServiceResult.ok().setData(data);
    }

    @Override
    public ServiceResult getTasksByServiceProviderId(Integer serviceProviderId) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        wrapper.eq("provider_id", serviceProviderId);
        List<Task> tasks = taskMapper.selectList(wrapper);
        if (tasks.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("tasks", tasks);
        return ServiceResult.ok().setData(data);
    }

    @Override
    public ServiceResult getOptionalTasks(Task task, Double maxRepay, Double minRepay) {
        QueryWrapper<Task> wrapper = new QueryWrapper<>();
        // 按任务名
        if (task.getName() != null) {
            wrapper.like("name", task.getName());
        }
        // 按工作内容
        if (task.getContent() != null) {
            wrapper.like("content", task.getContent());
        }
        // 按工作地点
        if (task.getPlace() != null) {
            wrapper.like("place", task.getPlace());
        }
        // 按工作酬劳
        if (maxRepay != null) {
            wrapper.le("repay", maxRepay);
        }
        if (minRepay != null) {
            wrapper.ge("repay", minRepay);
        }
        List<Task> tasks = taskMapper.selectList(wrapper);
        if (tasks.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("tasks", tasks);
        return ServiceResult.ok().setData(data);
    }
}
