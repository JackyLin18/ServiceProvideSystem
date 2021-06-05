package com.service_provide.jacky.service.impl;

import com.service_provide.jacky.common.enums.CodeEnum;
import com.service_provide.jacky.mapper.FreelancerMapper;
import com.service_provide.jacky.model.entity.Freelancer;
import com.service_provide.jacky.model.vo.ServiceResult;
import com.service_provide.jacky.service.FreelancerService;
import com.service_provide.jacky.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("freelancerService")
@Transactional
public class FreelancerServiceImpl implements FreelancerService {
    private FreelancerMapper freelancerMapper;

    @Autowired
    public void setFreelancerMapper(FreelancerMapper freelancerMapper) {
        this.freelancerMapper = freelancerMapper;
    }

    @Override
    public ServiceResult saveFreelancer(Freelancer freelancer) {
        if (freelancer.getId() == null) {
            // 插入操作
            int insert = freelancerMapper.insert(freelancer);
            if (insert <= 0) {
                return ServiceResult.fail().setMessage("插入信息失败");
            }
        } else {
            // 更新操作
            int update = freelancerMapper.updateById(freelancer);
            if (update <= 0) {
                return ServiceResult.fail().setMessage("更新信息失败");
            }
        }
        return ServiceResult.ok("id", freelancer.getId());
    }

    @Override
    public ServiceResult removeFreelancerById(Integer id) {
        int delete = freelancerMapper.deleteById(id);
        return delete <= 0 ? ServiceResult.fail().setMessage("删除信息失败") : ServiceResult.ok();
    }

    @Override
    public ServiceResult getAllFreelancers() {
        List<Freelancer> freelancers = freelancerMapper.selectList(null);
        if (freelancers.size() == 0) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("freelancers", freelancers);
        return ServiceResult.ok().setData(data);
    }

    @Override
    public ServiceResult getFreelancerById(Integer id) {
        Freelancer freelancer = freelancerMapper.selectById(id);
        if (freelancer == null) {
            return ServiceResult.fail(CodeEnum.NULL_RESULT);
        }
        return ServiceResult.ok("freelancer", freelancer);
    }

    @Override
    public ServiceResult loginFreelancer(Integer id, String inputPassword) {
        if(ParamUtil.isParamNull(String.valueOf(id),inputPassword)){
            return ServiceResult.fail(CodeEnum.NULL_PARAM);
        }
        String rightPassword = freelancerMapper.selectPassword(id);
        if(inputPassword.equals(rightPassword)){
            return ServiceResult.ok();
        }
        return ServiceResult.fail().setMessage("密码错误");
    }
}
