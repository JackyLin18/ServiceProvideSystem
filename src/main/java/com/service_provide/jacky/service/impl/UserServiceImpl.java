package com.service_provide.jacky.service.impl;

import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.mapper.UserMapper;
import com.service_provide.jacky.model.entity.User;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public ServiceResult saveUser(User user) {
        if (user.getId() == null) {
            // 插入数据
            int insert = userMapper.insert(user);
            if (insert == 0) {
                return ServiceResult.fail().setMessage("插入信息失败");
            }
        } else {
            // 更新数据
            int update = userMapper.updateById(user);
            if (update == 0) {
                return ServiceResult.fail().setMessage("更新信息失败");
            }
        }
        return ServiceResult.ok("id", user.getId());
    }

    @Override
    public ServiceResult removeUser(Integer id) {
        int delete = userMapper.deleteById(id);
        return delete <= 0 ? ServiceResult.fail(CodeEnum.NULL_RESULT) : ServiceResult.ok();
    }

    @Override
    public ServiceResult getUserById(Integer id) {
        User user = userMapper.selectById(id);
        return user == null ? ServiceResult.fail(CodeEnum.NULL_RESULT) :
                ServiceResult.ok("user", user);
    }

    @Override
    public ServiceResult getAllUsers() {
        List<User> users = userMapper.selectList(null);
        if (users.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("users", users);
        return ServiceResult.ok().setData(data);
    }
}
