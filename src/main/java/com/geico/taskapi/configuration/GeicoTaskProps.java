package com.geico.taskapi.configuration;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "geico.task")
public class GeicoTaskProps {
    int maxOpenHighTasksForADueDate = 100;
}
