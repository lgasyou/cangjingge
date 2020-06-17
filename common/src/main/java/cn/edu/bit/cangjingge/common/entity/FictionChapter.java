package cn.edu.bit.cangjingge.common.entity;

import lombok.Data;

@Data
public class FictionChapter {

    // 章节ID
    private Long id;

    // 小说ID
    private Long fictionId;

    // 内容
    private String content;

}
