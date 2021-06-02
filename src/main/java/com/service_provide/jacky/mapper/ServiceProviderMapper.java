package com.service_provide.jacky.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.service_provide.jacky.model.entity.ServiceProvider;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ServiceProviderMapper extends BaseMapper<ServiceProvider> {
}
