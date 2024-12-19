package yeim.jpa_scheduler.schedule.comment.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponse {

	private Long id;
	private Long schedule_id;
	private String author;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static CommentResponse from(Comment comment) {
		return new CommentResponse(
			comment.getId(),
			comment.getSchedule().getId(),
			comment.getMember().getName(),
			comment.getContent(),
			comment.getCreatedAt(),
			comment.getUpdatedAt()
		);
	}
}
