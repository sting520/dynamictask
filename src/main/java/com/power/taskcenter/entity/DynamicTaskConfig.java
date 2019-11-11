package com.power.taskcenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


@ApiModel("DynamicTaskConfig动态任务配置")
@Data
@EqualsAndHashCode(of = "beanName", callSuper = false)
@TableName(value = "task_config")
@KeySequence(value = "SEQ_task_config")
public class DynamicTaskConfig extends Model<DynamicTaskConfig> {

    public interface Status{
        int ENABLED = 0;
        int DISABLED= 1;
    }

    private static final long serialVersionUID = 6768301758128683780L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty("名称")
    @NotBlank
    private String name;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("任务Bean名称")
    @NotBlank
    @TableField("bean_name")
    private String beanName;


    /*
     cron表达式
     */
    @ApiModelProperty("cron表达式")
    @NotBlank
    private String cron;

    @ApiModelProperty("状态：0启用 1禁用")
    @NotNull
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty("更新时间")
    @TableField("update_time")
    private Date updateTime;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
