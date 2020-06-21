package cn.edu.bit.cangjingge.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("小说章节")
@Data
public class FictionChapter {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("章节ID")
    private Long chapterId;

    @ApiModelProperty("小说ID")
    private Long fictionId;

    @ApiModelProperty("章节名称")
    private String title;

    @ApiModelProperty("内容")
    private String content;

}
