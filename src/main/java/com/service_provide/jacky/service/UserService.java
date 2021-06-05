package com.service_provide.jacky.service;

import com.service_provide.jacky.model.entity.User;
import com.service_provide.jacky.model.vo.ServiceResult;

public interface UserService {
    /**
     * 添加、更新用户信息
     */
    ServiceResult saveUser(User user);

    /**
     * 根据 id 删除用户
     */
    ServiceResult removeUser(Integer id);

    /**
     * 根据 id 查用户
     */
    ServiceResult getUserById(Integer id);

    /**
     * 获取所有用户
     */
    ServiceResult getAllUsers();

    /**
     * 验证用户密码是否正确
     **/
    ServiceResult loginUser(Integer id,String inputPassword);
}
