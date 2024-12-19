package yeim.jpa_scheduler.schedule.controller;

import jakarta.validation.Valid;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeim.jpa_scheduler.schedule.domain.Schedule;
import yeim.jpa_scheduler.schedule.domain.ScheduleCreate;
import yeim.jpa_scheduler.schedule.domain.ScheduleResponse;
import yeim.jpa_scheduler.schedule.domain.ScheduleUpdate;
import yeim.jpa_scheduler.schedule.service.ScheduleService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {

	private final ScheduleService scheduleService;

	@PostMapping
	public ResponseEntity<Void> createSchedule(
		@RequestAttribute Long memberId,
		@Valid @RequestBody ScheduleCreate scheduleCreate
	) {
		Schedule schedule = scheduleService.createSchedule(memberId, scheduleCreate);
		return ResponseEntity
			.created(URI.create("/api/schedules/" + schedule.getId()))
			.build();
	}

	@GetMapping
	public ResponseEntity<Page<ScheduleResponse>> getAllSchedules(
		@PageableDefault(size = 10, sort = "updatedAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<ScheduleResponse> schedules = scheduleService.getAllSchedules(pageable)
			.map(ScheduleResponse::from);
		return ResponseEntity
			.ok()
			.body(schedules);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ScheduleResponse> getSchedule(@PathVariable Long id) {
		Schedule schedule = scheduleService.getSchedule(id);
		return ResponseEntity
			.ok()
			.body(ScheduleResponse.from(schedule));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ScheduleResponse> updateSchedule(
		@PathVariable Long id,
		@Valid @RequestBody ScheduleUpdate scheduleUpdate
	) {
		Schedule schedule = scheduleService.updateSchedule(id, scheduleUpdate);
		return ResponseEntity
			.ok()
			.body(ScheduleResponse.from(schedule));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ScheduleResponse> deleteSchedule(@PathVariable Long id) {
		scheduleService.deleteSchedule(id);
		return ResponseEntity
			.noContent()
			.build();
	}
}
