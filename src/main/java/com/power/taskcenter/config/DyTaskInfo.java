package com.power.taskcenter.config;

import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.config.Task;

import java.util.concurrent.ScheduledFuture;


@Data
public class DyTaskInfo {
    private final Task task;
    @Nullable
    volatile ScheduledFuture<?> future;

    public DyTaskInfo(Task task) {
        this.task = task;
    }
}
