package yeim.jpa_scheduler.member.infrastructure;

import static lombok.AccessLevel.PRIVATE;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import yeim.jpa_scheduler.member.domain.Member;

@Entity
@Table(name = "member")
@NoArgsConstructor
@AllArgsConstructor(access = PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class MemberEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public static MemberEntity from(Member member) {
		return new MemberEntity(
			member.getId(),
			member.getName(),
			member.getEmail(),
			member.getPassword(),
			member.getCreatedAt(),
			member.getUpdatedAt()
		);
	}

	public Member toModel() {
		return new Member(
			id,
			name,
			email,
			password,
			createdAt,
			updatedAt
		);
	}
}
