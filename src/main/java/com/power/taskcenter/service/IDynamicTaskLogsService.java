package com.power.taskcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.power.taskcenter.entity.DynamicTaskConfig;
import com.power.taskcenter.entity.DynamicTaskLogs;
public interface IDynamicTaskLogsService extends IService<DynamicTaskLogs> {

        void saveLogs(DynamicTaskConfig obj,String logContent);
}
