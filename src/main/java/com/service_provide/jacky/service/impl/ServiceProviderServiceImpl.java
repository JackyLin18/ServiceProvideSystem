package com.service_provide.jacky.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.mapper.ServiceProviderMapper;
import com.service_provide.jacky.model.entity.ServiceProvider;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.ServiceProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ServiceProviderServiceImpl implements ServiceProviderService {
    private ServiceProviderMapper serviceProviderMapper;

    @Autowired
    public void setServiceProviderMapper(ServiceProviderMapper serviceProviderMapper) {
        this.serviceProviderMapper = serviceProviderMapper;
    }

    @Override
    public ServiceResult saveServiceProvider(ServiceProvider serviceProvider) {
        if (serviceProvider.getId() == null) {
            // 插入操作
            int insert = serviceProviderMapper.insert(serviceProvider);
            if (insert <= 0) {
                return ServiceResult.fail().setMessage("插入信息失败");
            }
        } else {
            // 更新操作
            int update = serviceProviderMapper.updateById(serviceProvider);
            if (update <= 0) {
                return ServiceResult.fail().setMessage("更新信息失败");
            }
        }
        return ServiceResult.ok("id", serviceProvider.getId());
    }

    @Override
    public ServiceResult removeServiceProviderById(Integer id) {
        int delete = serviceProviderMapper.deleteById(id);
        return delete <= 0 ? ServiceResult.fail(CodeEnum.NULL_RESULT) : ServiceResult.ok();
    }

    @Override
    public ServiceResult getAllServiceProviders() {
        List<ServiceProvider> serviceProviders = serviceProviderMapper.selectList(null);
        if (serviceProviders.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("serviceProviders", serviceProviders);
        return ServiceResult.ok().setData(data);
    }

    @Override
    public ServiceResult getOptionalServiceProviders(ServiceProvider serviceProvider, Integer maxAge, Integer minAge) {
        QueryWrapper<ServiceProvider> wrapper = new QueryWrapper<>();
        // 按年龄
        if (maxAge != null) {
            wrapper.le("age", maxAge);
        }
        if (minAge != null) {
            wrapper.ge("age", minAge);
        }
        // 按性别
        if (serviceProvider.getSex() != null) {
            wrapper.eq("sex", serviceProvider.getSex());
        }
        // 按擅长领域
        if (serviceProvider.getExpertScope() != null) {
            wrapper.like("expert_scope", serviceProvider.getExpertScope());
        }
        List<ServiceProvider> serviceProviders = serviceProviderMapper.selectList(wrapper);
        if (serviceProviders.size() <= 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("serviceProviders", serviceProviders);
        return ServiceResult.ok().setData(data);
    }

    @Override
    public ServiceResult getServiceProviderById(Integer id) {
        ServiceProvider serviceProvider = serviceProviderMapper.selectById(id);
        return serviceProvider == null ? ServiceResult.fail(CodeEnum.NULL_RESULT) :
                ServiceResult.ok("serviceProvider", serviceProvider);
    }

    @Override
    public ServiceResult getServiceProviderByFreelancerId(Integer freelancerId) {
        QueryWrapper<ServiceProvider> wrapper = new QueryWrapper<>();
        wrapper.eq("freelancer_id", freelancerId);
        ServiceProvider serviceProvider = serviceProviderMapper.selectOne(wrapper);
        return serviceProvider == null ? ServiceResult.fail(CodeEnum.NULL_RESULT) :
                ServiceResult.ok("serviceProvider", serviceProvider);
    }
}
