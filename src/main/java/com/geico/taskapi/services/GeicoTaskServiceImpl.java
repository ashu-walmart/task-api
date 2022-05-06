package com.geico.taskapi.services;

import com.geico.taskapi.configuration.GeicoTaskProps;
import com.geico.taskapi.domain.GeicoTask;
import com.geico.taskapi.domain.exception.BadTaskInputException;
import com.geico.taskapi.domain.exception.TooManyOpenHighTasksException;
import com.geico.taskapi.repositories.GeicoTaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GeicoTaskServiceImpl implements GeicoTaskService {
    private final GeicoTaskRepository repository;
    private final GeicoTaskProps props;

    @Override
    public List<GeicoTask> all() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public GeicoTask createTask(GeicoTask task) {
        validateDueDateInFuture(task.getDueLocalDate());
        validateMaxDueOnADate(task.getDueLocalDate());
        return repository.save(task);
    }

    @Override
    public Optional<GeicoTask> findTask(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public GeicoTask upsertTask(GeicoTask newTask, Long id) {
        validateMaxDueOnADate(newTask.getDueLocalDate());
        validateMaxDueOnADate(newTask.getDueLocalDate());

        return repository.findById(id)
                .map(task -> {
                    task.setName(newTask.getName());
                    task.setDescription(newTask.getDescription());
                    task.setGeicoTaskPriority(newTask.getGeicoTaskPriority());
                    task.setGeicoTaskStatus(newTask.getGeicoTaskStatus());
                    task.setDueLocalDate(newTask.getDueLocalDate());
                    task.setStartLocalDate(newTask.getStartLocalDate());
                    task.setEndLocalDate(newTask.getEndLocalDate());
                    return repository.save(task);
                })
                .orElseGet(() -> {
                    newTask.setId(id);
                    return repository.save(newTask);
                });
    }

    @Override
    public void deleteTask(Long id) {
        repository.deleteById(id);
    }

    // private methods
    private void validateDueDateInFuture(LocalDate dueDate) {
        if( dueDate.isBefore(LocalDate.now())) {
            throw new BadTaskInputException("Due Date can not be in the past: " + dueDate);
        }
    }

    private void validateMaxDueOnADate(LocalDate dueDate) {
        int count = repository.getTaskCountForDueDate(dueDate);
        if(props.getMaxOpenHighTasksForADueDate() <= count) {
            throw new TooManyOpenHighTasksException("Too many high priority open tasks " +
                    "for the same due date: " + dueDate);
        }
    }

}
