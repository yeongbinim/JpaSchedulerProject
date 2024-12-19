package yeim.jpa_scheduler.schedule.comment.infrastructure;

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
import yeim.jpa_scheduler.schedule.comment.domain.Comment;
import yeim.jpa_scheduler.schedule.infrastructure.ScheduleEntity;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class CommentEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private MemberEntity member;

	@ManyToOne
	@JoinColumn(name = "schedule_id")
	private ScheduleEntity schedule;

	@Column(name = "content", nullable = false)
	private String content;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public static CommentEntity from(Comment comment) {
		return new CommentEntity(
			comment.getId(),
			MemberEntity.from(comment.getMember()),
			ScheduleEntity.from(comment.getSchedule()),
			comment.getContent(),
			comment.getCreatedAt(),
			comment.getUpdatedAt()
		);
	}

	public Comment toModel() {
		return new Comment(
			id,
			member.toModel(),
			schedule.toModel(),
			content,
			createdAt,
			updatedAt
		);
	}
}
