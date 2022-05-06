package com.geico.taskapi.controllers;

import com.geico.taskapi.domain.GeicoTask;
import com.geico.taskapi.domain.exception.GeicoTaskNotFoundException;
import com.geico.taskapi.services.GeicoTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GeicoTaskController {

	private final GeicoTaskService service;

	@GetMapping("/tasks")
	public List<GeicoTask> all() {
		return service.all();
	}

	@PostMapping("/tasks")
	GeicoTask newTask(@RequestBody GeicoTask task) {
		return service.createTask(task);
	}

	@GetMapping("/tasks/{id}")
	GeicoTask one(@PathVariable Long id) {
		return service.findTask(id)
				.orElseThrow(() -> new GeicoTaskNotFoundException(id));
	}

	@PutMapping("/tasks/{id}")
	GeicoTask upsertTask(@RequestBody GeicoTask newTask, @PathVariable Long id) {
		return service.upsertTask(newTask, id);
	}

	@DeleteMapping("/tasks/{id}")
	void deleteTask(@PathVariable Long id) {
		service.deleteTask(id);
	}
}
