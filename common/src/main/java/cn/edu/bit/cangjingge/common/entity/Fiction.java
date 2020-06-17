package cn.edu.bit.cangjingge.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Fiction {

    // 小说ID
    private Long id;

    // 标题
    private String title;

    // 简介
    private String description;

    // 创建时间
    private Date createTimestamp;

    // 上次修改时间
    private Date modifiedTimestamp;

}
