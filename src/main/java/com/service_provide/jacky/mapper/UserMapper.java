package com.service_provide.jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.service_provide.jacky.model.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    @Select("select password from user where id = #{id}")
    String selectPassword(Integer id);
}
