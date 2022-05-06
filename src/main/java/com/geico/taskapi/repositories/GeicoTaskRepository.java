package com.geico.taskapi.repositories;

import com.geico.taskapi.domain.GeicoTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface GeicoTaskRepository extends JpaRepository<GeicoTask, Long> {

    @Query("SELECT COUNT(*) FROM GeicoTask t WHERE " +
            "t.dueLocalDate = ?1 " +
            "AND t.geicoTaskPriority = '0' " +
            "AND t.geicoTaskStatus <> '2' ")
    Integer getTaskCountForDueDate(LocalDate dueDate);

}
