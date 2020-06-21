package cn.edu.bit.cangjingge.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("小说书评")
@Data
public class FictionReview {

    @ApiModelProperty("书评ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("评论小说ID")
    private Long fictionId;

    @ApiModelProperty("评分")
    private Integer rate;

    @ApiModelProperty("内容")
    private String content;

}
