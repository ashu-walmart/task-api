package com.geico.taskapi;

import com.geico.taskapi.configuration.GeicoTaskProps;
import com.geico.taskapi.domain.GeicoTask;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GeicoTaskApiApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	private HttpHeaders headers;

	@BeforeEach
	public void setup() {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Test
	@Order(1)
	void canListAllTasks() {
		assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/tasks",
				String.class)).isEqualTo(getTextFromFile("/expectedListAllResponse.json"));
	}

	@Test
	@Order(2)
	void canCreateNewTask() {
		ResponseEntity<GeicoTask> createdGeicoTask =
				createGeicoTask("/createTaskPayload.json", GeicoTask.class);

		assertThat(this.restTemplate.getForObject(
				"http://localhost:" + port + "/tasks/" + createdGeicoTask.getBody().getId(),
				String.class)).isEqualTo(getTextFromFile("/expectedCreateTaskResponse.json"));
	}

	@Test
	@Order(3)
	void canUpdateExistingTask() {
		ResponseEntity<GeicoTask> createdGeicoTask =
				createGeicoTask("/createTaskPayload.json", GeicoTask.class);

		ResponseEntity<GeicoTask> updatedGeicoTask = this.restTemplate.exchange(
				"http://localhost:" + port + "/tasks/" + createdGeicoTask.getBody().getId(),
				HttpMethod.PUT,
				new HttpEntity<>(getTextFromFile("/updateTaskPayload.json"), headers),
				GeicoTask.class);

		assertThat(this.restTemplate.getForObject(
				"http://localhost:" + port + "/tasks/" + updatedGeicoTask.getBody().getId(),
				String.class)).isEqualTo(getTextFromFile("/expectedUpdateTaskResponse.json"));

	}

	@Test
	@Order(4)
	void canDeleteTask() {
		ResponseEntity<GeicoTask> createdGeicoTask =
				createGeicoTask("/createTaskPayload.json", GeicoTask.class);

		this.restTemplate.delete(
				"http://localhost:" + port + "/tasks/" + createdGeicoTask.getBody().getId());

		assertThat(this.restTemplate.getForEntity(
				"http://localhost:" + port + "/tasks/" + createdGeicoTask.getBody().getId(),
				String.class).getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
	}

	@Test
	@Order(5)
	void canNotCreateTooManyTasks() {

		createGeicoTask("/createTaskPayload.json", String.class);
		createGeicoTask("/createTaskPayload.json", String.class);
		ResponseEntity<String> createdGeicoTask =
				createGeicoTask("/createTaskPayload.json", String.class);

		assertThat(createdGeicoTask.getStatusCode()).isEqualByComparingTo(HttpStatus.CONFLICT);
	}

	@Test
	@Order(6)
	void canNotCreateTaskWithDueDateInPast() {

		ResponseEntity<String> createdGeicoTask =
				createGeicoTask("/createTaskInPastPayload.json", String.class);

		assertThat(createdGeicoTask.getStatusCode()).isEqualByComparingTo(HttpStatus.BAD_REQUEST);
	}

	//Private methods
	private String getTextFromFile(String resource) {
		InputStream inputStream = this.getClass().getResourceAsStream(resource);
		return new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8))
				.lines()
				.collect(Collectors.joining("\n"));
	}

	private <T> ResponseEntity<T> createGeicoTask(String resource, Class<T> clazz) {
		HttpEntity<String> request =
				new HttpEntity<>(getTextFromFile(resource), headers);

		return this.restTemplate.postForEntity("http://localhost:" + port + "/tasks",
				request, clazz);
	}
}
