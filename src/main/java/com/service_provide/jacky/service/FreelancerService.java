package com.service_provide.jacky.service;

import com.service_provide.jacky.model.entity.Freelancer;
import com.service_provide.jacky.model.vo.ServiceResult;

public interface FreelancerService {
    /**
     * 添加或修改自由职业者
     */
    ServiceResult saveFreelancer(Freelancer freelancer);

    /**
     * 根据删除自由职业者信息
     */
    ServiceResult removeFreelancerById(Integer id);

    /**
     * 查询所有的自由职业者
     */
    ServiceResult getAllFreelancers();

    /**
     * 根据 id 查询指定的自由职业者
     */
    ServiceResult getFreelancerById(Integer id);
}
