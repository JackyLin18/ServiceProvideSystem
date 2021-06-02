package com.service_provide.jacky.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("freelancer")
public class Freelancer {
    private Integer id;
    private String name;
    private String idCardNumber;
    private String familyAddress;
}
