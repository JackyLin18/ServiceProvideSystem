package com.service_provide.jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.service_provide.jacky.model.entity.Freelancer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface FreelancerMapper extends BaseMapper<Freelancer> {
    @Select("select password from freelancer where id = #{id}")
    String selectPassword(Integer id);
}
