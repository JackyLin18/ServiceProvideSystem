create database service_provide_sys;

use service_provide_sys;

create TABLE `freelancer`
(
    `id`             INT NOT NULL AUTO_INCREMENT COMMENT '自由职业者唯一标识符',
    `name`           VARCHAR(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '自由职业者姓名',
    `id_card_number` VARCHAR(18) COMMENT '自由职业者身份证号码',
    `family_address` VARCHAR(50) COMMENT '自由职业者家庭地址',
    PRIMARY KEY (`id`)
);

create TABLE `service_provider`
(
    `id`            INT         NOT NULL AUTO_INCREMENT COMMENT '服务者唯一标识符',
    `freelancer_id` INT COMMENT '自由职业者id',
    `name`          VARCHAR(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '服务者姓名',
    `tel_number`    VARCHAR(11) NOT NULL COMMENT '服务者联系电话',
    `age`           SMALLINT    NULL COMMENT '服务者年龄',
    `sex`           SMALLINT    NOT NULL DEFAULT 0 COMMENT '服务者性别（0为未知，1为男，2为女）',
    `expert_scope`  VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '服务者擅长领域',
    `introduction`  VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '服务者自我介绍',
    FOREIGN KEY (`freelancer_id`) REFERENCES freelancer (`id`),
    PRIMARY KEY (`id`)
);

create TABLE `user`
(
    `id`         INT         NOT NULL AUTO_INCREMENT COMMENT '用户唯一标识符',
    `name`       VARCHAR(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '用户姓名',
    `tel_number` VARCHAR(11) NOT NULL COMMENT '用户联系电话',
    PRIMARY KEY (`id`)
);

create TABLE `task`
(
    `id`          INT NOT NULL AUTO_INCREMENT COMMENT '任务id',
    `user_id`     INT NOT NULL COMMENT '发布任务的用户的id',
    `provider_id` INT COMMENT '接取任务的服务提供者id',
    `name`        VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '任务名',
    `content`     VARCHAR(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '工作内容',
    `place`       VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '工作地点',
    `start_time`  DATETIME COMMENT '工作开始时间',
    `require`     VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '工作要求',
    `repay`       DOUBLE COMMENT '工作酬劳',
    `state`       SMALLINT DEFAULT 0 COMMENT '任务状态(0为待接取，1为已被接取，2为已完成)',
    FOREIGN KEY (`user_id`) REFERENCES user (`id`),
    FOREIGN KEY (`provider_id`) REFERENCES service_provider (`id`),
    PRIMARY KEY (`id`)
)