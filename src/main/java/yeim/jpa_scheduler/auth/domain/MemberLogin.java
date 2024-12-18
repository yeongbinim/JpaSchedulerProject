package yeim.jpa_scheduler.auth.domain;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLogin {

	@NotEmpty
	private String email;
	@NotEmpty
	private String password;
}
