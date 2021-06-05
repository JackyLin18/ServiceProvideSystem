package com.service_provide.jacky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("freelancer")
public class Freelancer {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;
    private String password;
    private String idCardNumber;
    private String familyAddress;
}
