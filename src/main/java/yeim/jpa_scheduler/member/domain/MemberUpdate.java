package yeim.jpa_scheduler.member.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdate {

	@NotEmpty
	private String name;
	@NotEmpty
	private String email;
	@NotEmpty
	private String password;

}
