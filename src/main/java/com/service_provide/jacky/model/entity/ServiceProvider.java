package com.service_provide.jacky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("service_provider")
public class ServiceProvider {
    // 服务者id
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 对应的自由职业者id（可为空）
    private Integer freelancerId;
    // 服务者姓名
    private String name;
    // 服务者联系方式
    private String telNumber;
    // 服务者年龄
    private Integer age;
    // 服务者性别（0为未知，1为男，2为女）
    private Integer sex;
    // 服务者擅长领域
    private String expertScope;
    // 服务者自我介绍
    private String introduction;

    @Override
    public String toString() {
        return "ServiceProvider{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", age=" + age +
                ", sex=" + sex +
                ", expertScope='" + expertScope + '\'' +
                ", introduction='" + introduction + '\'' +
                '}';
    }
}
