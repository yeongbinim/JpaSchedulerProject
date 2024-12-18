package yeim.jpa_scheduler.schedule.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import yeim.jpa_scheduler.member.domain.Member;

@Getter
@AllArgsConstructor
public class Schedule {

	@Setter
	private Long id;
	private Member member;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static Schedule from(Member member, ScheduleCreate schedule) {
		return new Schedule(
			null,
			member,
			schedule.getTitle(),
			schedule.getContent(),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	public Schedule update(ScheduleUpdate schedule) {
		return new Schedule(
			id,
			member,
			schedule.getTitle(),
			schedule.getContent(),
			createdAt,
			LocalDateTime.now()
		);
	}
}
