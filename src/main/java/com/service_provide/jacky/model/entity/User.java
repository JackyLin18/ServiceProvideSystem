package com.service_provide.jacky.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user")
public class User {
    // 用户id
    private Integer id;
    // 姓名
    private String name;
    // 电话号码
    private String telNumber;
}
