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

}
