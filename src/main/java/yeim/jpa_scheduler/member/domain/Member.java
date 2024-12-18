package yeim.jpa_scheduler.member.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import yeim.jpa_scheduler.auth.domain.MemberCreate;
import yeim.jpa_scheduler.common.utils.PasswordEncoder;

@Getter
@AllArgsConstructor
public class Member {

	@Setter
	private Long id;
	private String name;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static Member from(PasswordEncoder encoder, MemberCreate memberCreate) {
		return new Member(
			null,
			memberCreate.getName(),
			memberCreate.getEmail(),
			encoder.encode(memberCreate.getPassword()),
			LocalDateTime.now(),
			LocalDateTime.now()
		);
	}

	public Member update(MemberUpdate memberUpdate) {
		return new Member(
			id,
			memberUpdate.getName(),
			memberUpdate.getEmail(),
			password,
			createdAt,
			LocalDateTime.now()
		);
	}

	public boolean verifyPassword(PasswordEncoder encoder, String password) {
		return encoder.matches(password, this.password);
	}
}
