package cn.edu.bit.cangjingge.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("小说")
@Data
public class Fiction {

    @ApiModelProperty("小说ID")
    private Long id;

    @ApiModelProperty("作者id")
    private Long authorId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("简介")
    private String description;

    @ApiModelProperty("创建时间")
    private Date createTimestamp;

    @ApiModelProperty("上次修改时间")
    private Date modifiedTimestamp;

}
