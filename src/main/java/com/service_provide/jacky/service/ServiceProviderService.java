package com.service_provide.jacky.service;

import com.service_provide.jacky.model.entity.ServiceProvider;
import com.service_provide.jacky.model.vo.ServiceResult;

public interface ServiceProviderService {
    /**
     * 添加、修改服务提供者
     */
    ServiceResult saveServiceProvider(ServiceProvider serviceProvider);

    /**
     * 删除服务提供者
     */
    ServiceResult removeServiceProviderById(Integer id);

    /**
     * 查询所有的服务提供者
     */
    ServiceResult getAllServiceProviders();

    /**
     * 模糊查询服务提供者
     */
    ServiceResult getOptionalServiceProviders(ServiceProvider serviceProvider, Integer maxAge, Integer minAge);

    /**
     * 根据 id 查询服务提供者
     */
    ServiceResult getServiceProviderById(Integer id);

    /**
     * 根据自由职业者 id 查询服务提供者
     */
    ServiceResult getServiceProviderByFreelancerId(Integer freelancerId);

    /**
     * 验证服务提供者密码
     **/
    ServiceResult loginServiceProvider(Integer id, String inputPassword);
}
