package com.power.taskcenter.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;



@Data

@TableName(value = "task_logs")
@KeySequence(value = "SEQ_task_logs")
public class DynamicTaskLogs extends Model<DynamicTaskLogs> {


    private static final long serialVersionUID = 6768301758128683780L;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    private Long taskId;
    private String taskName;
    private String taskBean;
    private String taskCron;
    private Date createDate;
    private String logContent;

    @TableField(exist = false)
    private String startDate;

    @TableField(exist = false)
    private String endDate;

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
