create database if not exists `cangjingge`;

drop table if exists fiction;
create table if not exists `fiction`(
    `id` bigint(255) not null comment '主键',
    `authorId` bigint(255) not null comment '作者id',
    `title` varchar(64) not null comment '小说名',
    `description` varchar(1000) default null comment '简介',
    `createTimestamp` date default null comment '起始日期',
    `modifiedTimestamp` date default null comment '上次修改时间',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

drop table if exists chapter;
create table if not exists `chapter`(
    `id` bigint(255) not null comment '主键',
    `chapterId` bigint(255) not null comment '章节id',
    `fictionId` bigint(255) not null comment '小说id',
    `title` varchar(64) not null comment '标题',
    `content` longtext default null comment '小说内容',
    primary key (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


