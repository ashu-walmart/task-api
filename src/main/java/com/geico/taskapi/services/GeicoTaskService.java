package com.geico.taskapi.services;

import com.geico.taskapi.domain.GeicoTask;

import java.util.List;
import java.util.Optional;

public interface GeicoTaskService {
    List<GeicoTask> all();

    GeicoTask createTask(GeicoTask task);

    GeicoTask findTask(Long id);

    GeicoTask upsertTask(GeicoTask newTask, Long id);

    void deleteTask(Long id);
}
