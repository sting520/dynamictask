package com.power.taskcenter.service;


public interface DyTaskManager {

    /**
     * 直接执行定时任务
     */
    boolean runTask(String beanName);

    /**
     * 开始定时任务
     *
     * @param beanName
     * @param cron
     *
     * @return
     */
    boolean scheduleTask(String beanName, String cron);

    /**
     * 取消定时任务
     *
     * @param beanName
     *
     * @return
     */
    boolean cancelTask(String beanName);

}
