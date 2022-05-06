package com.geico.taskapi.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "geico.task")
public class GeicoTaskProps {
    int maxOpenHighTasksForADueDate = 100;
}
