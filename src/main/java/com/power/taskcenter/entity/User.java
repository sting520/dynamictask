package com.power.taskcenter.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class User implements Serializable {

    private static final long serialVersionUID = -3200103254689137288L;


    @NotBlank(message = "用户名不能为空")
    private String username;

    @JsonIgnore
    @NotBlank(message = "密码不能为空")
    private String password;


}