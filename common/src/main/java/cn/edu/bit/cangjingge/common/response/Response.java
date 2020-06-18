package cn.edu.bit.cangjingge.common.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("API统一返回结构")
@Data
public class Response<T> {

    @ApiModelProperty("状态码")
    private Integer status;

    @ApiModelProperty("时间戳")
    private Long timestamp;

    @ApiModelProperty("状态信息")
    private String message;

    @ApiModelProperty("错误信息")
    private String reason = "";

    @ApiModelProperty("返回数据")
    private T data;

}
