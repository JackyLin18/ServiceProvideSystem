package com.service_provide.jacky.mapper;

import com.service_provide.jacky.model.entity.ServiceProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceProviderMapperPrintTest {
    private ServiceProviderMapper serviceProviderMapper;

    @Autowired
    public void setServiceProviderMapper(ServiceProviderMapper serviceProviderMapper) {
        this.serviceProviderMapper = serviceProviderMapper;
    }

    @Test
    public void testAddServiceProvider() {
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.setName("Jacky");
        serviceProvider.setAge(21);
        serviceProvider.setExpertScope("计算机");
        serviceProvider.setIntroduction("为人乐观开朗可靠");
        serviceProvider.setSex(1);
        serviceProvider.setTelNumber("19924339851");
        int insert = serviceProviderMapper.insert(serviceProvider);
        System.out.println(serviceProvider);
    }
}
