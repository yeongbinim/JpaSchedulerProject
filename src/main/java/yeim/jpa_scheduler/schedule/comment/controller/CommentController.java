package yeim.jpa_scheduler.schedule.comment.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
import yeim.jpa_scheduler.schedule.comment.domain.Comment;
import yeim.jpa_scheduler.schedule.comment.domain.CommentCreate;
import yeim.jpa_scheduler.schedule.comment.domain.CommentResponse;
import yeim.jpa_scheduler.schedule.comment.domain.CommentUpdate;
import yeim.jpa_scheduler.schedule.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules/{schedule_id}/comments")
public class CommentController {

	private final CommentService commentService;

	@PostMapping
	public ResponseEntity<Void> createComment(
		@PathVariable("schedule_id") Long scheduleId,
		@RequestAttribute Long memberId,
		@Valid @RequestBody CommentCreate scheduleCreate) {
		Comment comment = commentService.createComment(memberId, scheduleId, scheduleCreate);
		return ResponseEntity
			.created(URI.create("/schedules/" + comment.getId()))
			.build();
	}

	@GetMapping
	public ResponseEntity<List<CommentResponse>> getComments(
		@PathVariable("schedule_id") Long scheduleId
	) {
		List<CommentResponse> schedules = commentService.getCommentsByScheduleId(scheduleId)
			.stream()
			.map(CommentResponse::from)
			.toList();
		return ResponseEntity
			.ok()
			.body(schedules);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CommentResponse> updateComment(
		@PathVariable("schedule_id") Long scheduleId,
		@PathVariable Long id,
		@Valid @RequestBody CommentUpdate scheduleUpdate
	) {
		Comment comment = commentService.updateComment(id, scheduleUpdate);
		return ResponseEntity
			.ok()
			.body(CommentResponse.from(comment));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<CommentResponse> deleteComment(
		@PathVariable("schedule_id") Long scheduleId,
		@PathVariable Long id
	) {
		commentService.deleteComment(id);
		return ResponseEntity
			.noContent()
			.build();
	}
}
