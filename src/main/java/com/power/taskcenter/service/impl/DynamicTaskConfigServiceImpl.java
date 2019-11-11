package com.power.taskcenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.power.taskcenter.entity.DynamicTaskConfig;
import com.power.taskcenter.exception.ServiceException;
import com.power.taskcenter.mapper.DynamicTaskConfigMapper;
import com.power.taskcenter.service.IDynamicTaskConfigService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
public class DynamicTaskConfigServiceImpl extends ServiceImpl<DynamicTaskConfigMapper, DynamicTaskConfig> implements IDynamicTaskConfigService {

    @Override
    public List<DynamicTaskConfig> findAllByStatus(int status) {
        QueryWrapper<DynamicTaskConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("status", status);
        wrapper.orderByAsc("id");
        return this.list(wrapper);
    }

    @Override
    public DynamicTaskConfig findByBeanName(String beanName) {
        if (StringUtils.isBlank(beanName)) {
            return null;
        }
        QueryWrapper<DynamicTaskConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("bean_name", beanName);
        return this.getOne(wrapper);
    }

    @Override
    public DynamicTaskConfig addTask(DynamicTaskConfig resource) {
        DynamicTaskConfig config = findByBeanName(resource.getBeanName());
        if (config != null) {
            throw new ServiceException("beanName已经存在，不可重复");
        }

        boolean save = this.save(resource);
        if (save) {
            return resource;
        }
        return null;
    }

    @Override
    public void updateTask(DynamicTaskConfig resource) {
        DynamicTaskConfig config = this.getById(resource.getId());
        if (config == null) {
            throw new ServiceException("没有找到定时任务记录");
        }

        if (!config.getBeanName().equals(resource.getBeanName())) {
            if (null != findByBeanName(resource.getBeanName())) {
                throw new ServiceException("beanName已经存在，不可重复");
            }
        }

        this.updateById(resource);
    }

    @Override
    public DynamicTaskConfig removeTask(Long taskId) {
        DynamicTaskConfig config = this.getById(taskId);
        if (config == null) {
            return null;
        }
        this.removeById(config);
        return config;
    }

    @Override
    public DynamicTaskConfig findById(Long taskId) {
        DynamicTaskConfig config = this.getById(taskId);
        if (config == null) {
            return null;
        }
        return config;
    }

}
