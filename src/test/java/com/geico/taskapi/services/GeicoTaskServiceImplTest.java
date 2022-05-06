package com.geico.taskapi.services;

import com.geico.taskapi.configuration.GeicoTaskProps;
import com.geico.taskapi.domain.GeicoTask;
import com.geico.taskapi.domain.exception.BadTaskInputException;
import com.geico.taskapi.domain.exception.TooManyOpenHighTasksException;
import com.geico.taskapi.repositories.GeicoTaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GeicoTaskServiceImplTest {

    @Mock
    private GeicoTaskRepository repository;
    @Mock
    private GeicoTaskProps props;
    @InjectMocks
    private GeicoTaskServiceImpl service;

    @Test
    void all() {
        //setup
        List<GeicoTask> expected = new ArrayList<>();
        expected.add(GeicoTask.builder().id(1l).build());
        expected.add(GeicoTask.builder().id(2l).build());
        expected.add(GeicoTask.builder().id(3l).build());
        when(repository.findAll()).thenReturn(expected);

        //execute
        List<GeicoTask> actual = service.all();

        //verify
        assertEquals(expected, actual);
        verify(repository, times(1)).findAll();
    }

    @Test
    void createTask() {
        //setup
        when(props.getMaxOpenHighTasksForADueDate()).thenReturn(100);
        GeicoTask expected = GeicoTask.builder().id(1l).dueLocalDate(LocalDate.now()).build();
        when(repository.getTaskCountForDueDate(expected.getDueLocalDate())).thenReturn(3);
        when(repository.save(expected)).thenReturn(expected);

        //execute
        GeicoTask actual = service.createTask(expected);

        //verify
        assertEquals(expected, actual);
        verify(props, times(1)).getMaxOpenHighTasksForADueDate();
        verify(repository, times(1)).save(expected);
    }

    @Test
    void createTooManyTask() {
        //setup
        when(props.getMaxOpenHighTasksForADueDate()).thenReturn(100);
        GeicoTask expected = GeicoTask.builder().id(1l).dueLocalDate(LocalDate.now()).build();
        when(repository.getTaskCountForDueDate(expected.getDueLocalDate())).thenReturn(103);

        //execute
        TooManyOpenHighTasksException thrown = assertThrows(TooManyOpenHighTasksException.class,
                () -> service.createTask(expected), "Should throw exception");

        //verify
        assertEquals("Too many high priority open tasks for the same due date: "
                + expected.getDueLocalDate(), thrown.getMessage());
        verify(props, times(1)).getMaxOpenHighTasksForADueDate();
        verify(repository, times(0)).save(expected);
    }

    @Test
    void createTaskInPast() {
        //setup
        GeicoTask expected = GeicoTask.builder().id(1l)
                .dueLocalDate(LocalDate.now().minusDays(3)).build();

        //execute
        BadTaskInputException thrown = assertThrows(BadTaskInputException.class,
                () -> service.createTask(expected), "Should throw exception");
        //verify
        assertEquals("Due Date can not be in the past: "
                + expected.getDueLocalDate(), thrown.getMessage());
        verify(props, times(0)).getMaxOpenHighTasksForADueDate();
        verify(repository, times(0)).save(expected);
    }

    @Test
    void findTask() {
        //setup
        GeicoTask expected = GeicoTask.builder().id(1001l).dueLocalDate(LocalDate.now()).build();
        when(repository.findById(1001l)).thenReturn(Optional.of(expected));

        //execute
        GeicoTask actual = service.findTask(1001l);

        //verify
        assertEquals(expected, actual);
    }

    @Test
    void upsertTask() {

        //setup
        when(props.getMaxOpenHighTasksForADueDate()).thenReturn(100);
        when(repository.getTaskCountForDueDate(LocalDate.now())).thenReturn(3);
        GeicoTask found = GeicoTask.builder().id(2001l).dueLocalDate(LocalDate.now()).build();
        when(repository.findById(2001l)).thenReturn(Optional.of(found));
        GeicoTask expected = GeicoTask.builder().id(2001l)
                .dueLocalDate(LocalDate.now().plusDays(2)).build();
        when(repository.save(expected)).thenReturn(expected);

        //execute
        GeicoTask actual = service.upsertTask(expected, 2001l);

        //verify
        assertEquals(expected, actual);
        verify(repository,times(1)).save(expected);
    }

    @Test
    void upsertTooManyTasks() {

        //setup
        when(props.getMaxOpenHighTasksForADueDate()).thenReturn(100);
        GeicoTask expected = GeicoTask.builder().id(2001l)
                .dueLocalDate(LocalDate.now().plusDays(2)).build();
        when(repository.getTaskCountForDueDate(expected.getDueLocalDate())).thenReturn(103);

        //execute
        TooManyOpenHighTasksException thrown = assertThrows(TooManyOpenHighTasksException.class,
                () -> service.upsertTask(expected, 2001l), "Should throw exception");

        //verify
        assertEquals("Too many high priority open tasks for the same due date: "
                + expected.getDueLocalDate(), thrown.getMessage());
        verify(repository,times(0)).save(expected);
    }

    @Test
    void upsertTaskDueInPast() {

        //setup
        GeicoTask expected = GeicoTask.builder().id(2001l)
                .dueLocalDate(LocalDate.now().minusDays(2)).build();

        //execute
        BadTaskInputException thrown = assertThrows(BadTaskInputException.class,
                () -> service.upsertTask(expected, 2001l), "Should throw exception");

        //verify
        assertEquals("Due Date can not be in the past: "
                + expected.getDueLocalDate(), thrown.getMessage());
        verify(repository,times(0)).save(expected);
    }

    @Test
    void deleteTask() {

        //execute
        service.deleteTask(3001l);

        //verify
        verify(repository, times(1)).deleteById(3001l);
    }

}