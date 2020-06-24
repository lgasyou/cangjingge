package cn.edu.bit.cangjingge.user.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ApiModel("用户基本信息")
@Data
@Entity
public class User {

    @ApiModelProperty("用户ID")
    @Id
    @GeneratedValue
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

}
