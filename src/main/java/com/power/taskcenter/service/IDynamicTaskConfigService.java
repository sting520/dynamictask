package com.power.taskcenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.power.taskcenter.entity.DynamicTaskConfig;

import java.util.List;


public interface IDynamicTaskConfigService extends IService<DynamicTaskConfig> {

    List<DynamicTaskConfig> findAllByStatus(int status);

    DynamicTaskConfig findByBeanName(String beanName);

    DynamicTaskConfig addTask(DynamicTaskConfig resource);

    void updateTask(DynamicTaskConfig resource);

    DynamicTaskConfig removeTask(Long taskId);

    DynamicTaskConfig findById(Long taskId);
}
