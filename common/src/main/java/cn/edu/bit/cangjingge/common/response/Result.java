package cn.edu.bit.cangjingge.common.response;

import lombok.Data;

@Data
public class Result<T> {

    // 状态码
    private Integer status;

    // 时间戳
    private Long timestamp;

    // 附加信息
    private String message;

    // 错误信息
    private String reason = "";

    // 返回数据
    private T data;

}
