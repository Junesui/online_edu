package com.zjm.eduucenter.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZengJinming
 * @since 2020/4/30
 */
@Data
public class LoginVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "密码")
    private String password;
}