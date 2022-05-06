package com.geico.taskapi.configuration;

import com.geico.taskapi.domain.GeicoTask;
import com.geico.taskapi.domain.GeicoTaskPriority;
import com.geico.taskapi.domain.GeicoTaskStatus;
import com.geico.taskapi.repositories.GeicoTaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(GeicoTaskRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(buildTaskForHomeWork()));
            log.info("Preloading " + repository.save(buildTaskForBingeOnStarWars()));
        };
    }

    private GeicoTask buildTaskForBingeOnStarWars() {
        return GeicoTask.builder()
                .name("Binge Watch Star Wars")
                .description("Watch all Star Wars movies in chronological order")
                .dueLocalDate(LocalDate.now().plusDays(5))
                .startLocalDate(LocalDate.now().plusDays(2))
                .geicoTaskPriority(GeicoTaskPriority.LOW)
                .geicoTaskStatus(GeicoTaskStatus.NEW)
                .build();
    }

    private GeicoTask buildTaskForHomeWork() {
        return GeicoTask.builder()
                .name("Homework Task")
                .description("Complete homework assignment for Geico")
                .dueLocalDate(LocalDate.now().plusDays(2))
                .startLocalDate(LocalDate.now().minusDays(1))
                .geicoTaskPriority(GeicoTaskPriority.HIGH)
                .geicoTaskStatus(GeicoTaskStatus.IN_PROGRESS)
                .build();
    }
}