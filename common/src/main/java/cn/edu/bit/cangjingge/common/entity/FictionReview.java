package cn.edu.bit.cangjingge.common.entity;

import lombok.Data;

@Data
public class FictionReview {

    // 书评ID
    private Long id;

    // 用户ID
    private Long userId;

    // 评分
    private Integer rate;

    // 内容
    private String content;

}
