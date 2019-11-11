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
public class TestOneDyTask implements DyTask {

    @Autowired
    private IDynamicTaskLogsService dynamicTaskLogsService;
    @RegisterTask(enable = true, override = false, name = "动态任务1", remark = "详细配置任务信息", cron = "0/2 * * * * ?", status = 1)
    @Override
    public void run() {
        DynamicTaskLogs logs = new DynamicTaskLogs();
        logs.setTaskId(0L);
        logs.setTaskBean("testOneDyTask");
        logs.setTaskName("动态任务1");
        logs.setTaskCron("0/2 * * * * ?");
        logs.setLogContent("执行动态任务1");
        logs.setCreateDate(new Date());
        dynamicTaskLogsService.save(logs);
        log.info("TestDyTask 测试任务。。。" + LocalDateTime.now());
    }
}
