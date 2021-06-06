package com.service_provide.jacky.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("task")
public class Task {
    // 任务id
    @TableId(type = IdType.AUTO)
    private Integer id;
    // 用户id
    private Integer userId;
    // 服务者id
    private Integer providerId;
    // 任务名
    private String name;
    // 工作内容
    private String content;
    // 工作地点
    private String place;
    // 工作开始时间
    private Timestamp startTime;
    // 工作要求
    private String taskRequire;
    // 工作酬劳
    private Double repay;
    // 工作状态（0为待接取，1为已被接取但未完成，2为已完成）
    private Integer state;
}
