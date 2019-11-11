package com.power.taskcenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power.taskcenter.entity.DynamicTaskConfig;
import com.power.taskcenter.entity.DynamicTaskLogs;
import com.power.taskcenter.mapper.DynamicTaskLogsMapper;
import com.power.taskcenter.service.IDynamicTaskLogsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
@Slf4j
public class DynamicTaskLogsServiceImpl extends ServiceImpl<DynamicTaskLogsMapper, DynamicTaskLogs> implements IDynamicTaskLogsService {


    @Override
    public void saveLogs(DynamicTaskConfig obj, String logContent) {
        DynamicTaskLogs logs = new DynamicTaskLogs();
        logs.setTaskId(obj.getId());
        logs.setTaskBean(obj.getBeanName());
        logs.setTaskName(obj.getName());
        logs.setTaskCron(obj.getCron());
        logs.setLogContent(logContent);
        logs.setCreateDate(new Date());
        this.save(logs);
    }
}
