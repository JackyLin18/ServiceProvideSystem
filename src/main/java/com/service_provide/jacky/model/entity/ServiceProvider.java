package com.service_provide.jacky.model.entity;

import lombok.Data;

@Data
public class ServiceProvider {
    private Integer id;
    private String name;
    private String telNumber;
    private Integer age;
    private Integer sex;
    private String expertScope;
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
