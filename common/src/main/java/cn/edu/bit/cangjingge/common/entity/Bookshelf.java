package cn.edu.bit.cangjingge.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("书架条目")
@Data
public class Bookshelf {

    @ApiModelProperty("书籍条目ID")
    private Long id;

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("小说ID")
    private Long fictionId;

}
