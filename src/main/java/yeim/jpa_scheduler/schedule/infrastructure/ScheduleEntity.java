package yeim.jpa_scheduler.schedule.infrastructure;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yeim.jpa_scheduler.member.infrastructure.MemberEntity;
import yeim.jpa_scheduler.schedule.domain.Schedule;

@Entity
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ScheduleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private MemberEntity writer;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content")
	private String content;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public static ScheduleEntity from(Schedule schedule) {
		return new ScheduleEntity(
			schedule.getId(),
			MemberEntity.from(schedule.getMember()),
			schedule.getTitle(),
			schedule.getContent(),
			schedule.getCreatedAt(),
			schedule.getUpdatedAt()
		);
	}

	public Schedule toModel() {
		return new Schedule(
			id,
			writer.toModel(),
			title,
			content,
			createdAt,
			updatedAt
		);
	}
}
