package com.power.taskcenter.tasks.custom;

import com.power.taskcenter.anno.RegisterTask;
import com.power.taskcenter.entity.DynamicTaskLogs;
import com.power.taskcenter.service.IDynamicTaskLogsService;
import com.power.taskcenter.tasks.DyTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;


@Component
@Slf4j
public class TestTwoDyTask implements DyTask {

    /**
     * name 名称
     * remark 简单描述
     * cron 计划时间
     * 默认第一次注册的任务都不是自动启动的
     */

    @Autowired
    private IDynamicTaskLogsService dynamicTaskLogsService;

    @RegisterTask( name = "动态任务2", remark = "简单配置任务信息", cron = "0/2 * * * * ?")
    @Override
    public void run() {

        DynamicTaskLogs logs = new DynamicTaskLogs();
        logs.setTaskId(0L);
        logs.setTaskBean("testOneDyTask");
        logs.setTaskName("动态任务2");
        logs.setTaskCron("0/2 * * * * ?");
        logs.setLogContent("执行动态任务1");
        logs.setCreateDate(new Date());
        dynamicTaskLogsService.save(logs);
        log.info("ExecuteProcDyTask 测试任务。。。" + LocalDateTime.now());
    }
}
