create database if not exists `cangjingge`;

use `cangjingge`;

create table if not exists `review` (
    `id` bigint(255) not null comment 'id',
    `userId` bigint(255) not null comment '用户id',
    `fictionId` bigint(255) not null comment '小说id',
    `rate` int(2) not null comment '小说id',
    `content` longtext not null comment '评论内容',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;