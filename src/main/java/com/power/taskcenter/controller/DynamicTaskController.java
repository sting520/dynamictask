package com.power.taskcenter.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.power.taskcenter.entity.DynamicTaskConfig;
import com.power.taskcenter.entity.DynamicTaskLogs;
import com.power.taskcenter.exception.ServiceException;
import com.power.taskcenter.service.DyTaskManager;
import com.power.taskcenter.service.IDynamicTaskConfigService;
import com.power.taskcenter.service.IDynamicTaskLogsService;
import com.power.taskcenter.util.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Api(tags = "动态定时任务")
@RestController
@RequestMapping("/v1/api/dyTasks")
@Slf4j
@CrossOrigin
public class DynamicTaskController {


    @Autowired
    private DyTaskManager dyTaskManager;

    @Autowired
    private IDynamicTaskConfigService dynamicTaskConfigService;

    @Autowired
    private IDynamicTaskLogsService dynamicTaskLogsService;



    @GetMapping("/logsList")
    @ResponseBody
    public ResultBean getLogsList(@RequestParam(value = "page", defaultValue = "1") int page,
                                                       @RequestParam(value = "limit", defaultValue = "10")int limit,
                                                       DynamicTaskLogs obj) {
        Page<DynamicTaskLogs> pages = new Page<>(page,limit);
        QueryWrapper<DynamicTaskLogs> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.hasBlank(obj.getTaskName())){
            queryWrapper.like("task_name","%"+obj.getTaskName()+"%") ;
        }
        if (!StrUtil.hasBlank(obj.getStartDate())) {
            queryWrapper.ge("create_date", DateUtil.parse(obj.getStartDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }else{
            queryWrapper.ge("create_date", DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd") + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        }
        if (!StrUtil.hasBlank(obj.getEndDate())) {
            queryWrapper.le("create_date", DateUtil.parse(obj.getEndDate() + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }else{
            queryWrapper.le("create_date", DateUtil.parse(DateUtil.format(new Date(),"yyyy-MM-dd") + " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
        }
        queryWrapper.orderByDesc("id");
        IPage<DynamicTaskLogs> lst = dynamicTaskLogsService.page(pages,queryWrapper);

//    log.info(JSON.toJSONString(ResultBean.successData(lst.getTotal(),lst.getRecords())));
        return ResultBean.successData(lst.getTotal(),lst.getRecords());
    }

    @ApiOperation("任务配置列表")
    @GetMapping("/list")
    public ResultBean listTasks(DynamicTaskConfig obj) {
        QueryWrapper<DynamicTaskConfig> queryWrapper = new QueryWrapper<>();
        if (!StrUtil.hasBlank(obj.getName())){
            queryWrapper.like("name","%"+obj.getName()+"%") ;
        }
        queryWrapper.orderByDesc("id");
        List<DynamicTaskConfig> list = dynamicTaskConfigService.list(queryWrapper);
//        log.info(JSON.toJSONString(ResultBean.success(list)));
        return ResultBean.success(list);
    }
    @ApiOperation("任务配置列表")
    @GetMapping("/queryById")
    public ResultBean queryById(@RequestParam Long taskId) {

        return ResultBean.success(dynamicTaskConfigService.findById(taskId));
    }
    @ApiOperation("新增任务配置(status=0 将会启动任务)")
    @PostMapping("/add")
    public DynamicTaskConfig addTask(@RequestBody @Validated DynamicTaskConfig resource) {
        resource.setId(null);
        Date date = new Date();
        resource.setCreateTime(date);
        resource.setUpdateTime(date);
        DynamicTaskConfig config = dynamicTaskConfigService.addTask(resource);
        if (config != null && DynamicTaskConfig.Status.ENABLED == config.getStatus()) {
            dyTaskManager.scheduleTask(config.getBeanName(), config.getCron());
        }
        return config;
    }

    @ApiOperation("更新任务配置（将会重新运行任务)")
    @PutMapping("/update")
    public ResultBean update(@RequestBody @Validated DynamicTaskConfig resource) {
        if (resource.getId() == null) {
            throw new ServiceException("id无效");
        }

        DynamicTaskConfig config = dynamicTaskConfigService.getById(resource.getId());
        if (config == null) {
            throw new ServiceException("没有找到定时任务记录");
        }

        resource.setCreateTime(config.getCreateTime());
        resource.setUpdateTime(new Date());
        dynamicTaskConfigService.updateTask(resource);

        if (DynamicTaskConfig.Status.ENABLED == resource.getStatus()) {
            try {
                dyTaskManager.cancelTask(config.getBeanName());
                dyTaskManager.scheduleTask(resource.getBeanName(), resource.getCron());
            }
            catch (Throwable e) {
                e.printStackTrace();
            }
        }
        dynamicTaskLogsService.saveLogs(config,"更新任务");
        return ResultBean.success("ok");
    }

    @ApiOperation("删除任务（将会停止正在运行任务)")
    @DeleteMapping("/remove")
    public ResultBean removeTask(@RequestParam Long taskId) {
        DynamicTaskConfig config = dynamicTaskConfigService.removeTask(taskId);
        if (config != null) {
            dyTaskManager.cancelTask(config.getBeanName());
        }
        dynamicTaskLogsService.saveLogs(config,"删除任务");
        return ResultBean.success("ok");
    }

    @ApiOperation("开始任务(通过配置taskId)")
    @GetMapping("/scheduleTask")
    public ResultBean scheduleTask(@RequestParam String taskName) {
        log.info("请求开始任务:{}", taskName);
        DynamicTaskConfig config = dynamicTaskConfigService.findByBeanName(taskName);
        if (config == null) {
            throw new ServiceException("没有找到配置记录:" + taskName);
        }
        config.setStatus(0);
        config.setUpdateTime(new Date());
        dynamicTaskConfigService.updateTask(config);
        dyTaskManager.scheduleTask(config.getBeanName(), config.getCron());
        dynamicTaskLogsService.saveLogs(config,"开始任务");
        return ResultBean.success("ok");
    }

    @ApiOperation("取消任务")
    @GetMapping("cancelTask")
    public ResultBean cancelTask(@RequestParam String taskName) {
        log.info("请求取消任务:" + taskName);
        DynamicTaskConfig config = dynamicTaskConfigService.findByBeanName(taskName);
        if (config == null) {
            throw new ServiceException("没有找到配置记录:" + taskName);
        }
        config.setStatus(1);
        config.setUpdateTime(new Date());
        dynamicTaskConfigService.updateTask(config);
        dyTaskManager.cancelTask(taskName);
        dynamicTaskLogsService.saveLogs(config,"取消任务");
        return  ResultBean.success("ok");
    }

    @ApiOperation("直接执行任务(异步)")
    @GetMapping("/runTask")
    public String runTask(@RequestParam String taskName) {
        log.info("请求执行任务:{}", taskName);
        dyTaskManager.runTask(taskName);
        return "ok";
    }

}
