package com.geico.taskapi.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeicoTask {

    private @Id @GeneratedValue Long id;
    private String name;
    private String description;
    private LocalDate dueLocalDate;
    private LocalDate startLocalDate;
    private LocalDate endLocalDate;
    private GeicoTaskPriority geicoTaskPriority;
    private GeicoTaskStatus geicoTaskStatus;

}