package yeim.jpa_scheduler.schedule.comment.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.schedule.domain.Schedule;

@Getter
@AllArgsConstructor
public class Comment {

	@Setter
	private Long id;
	private Member member;
	private Schedule schedule;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static Comment from(Member member, Schedule schedule, CommentCreate commentCreate) {
		return new Comment(
			null,
			member,
			schedule,
			commentCreate.getContent(),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	public Comment update(CommentUpdate commentUpdate) {
		return new Comment(
			id,
			member,
			schedule,
			commentUpdate.getContent(),
			createdAt,
			LocalDateTime.now()
		);
	}
}
