package yeim.jpa_scheduler.member.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDelete {

	@NotEmpty
	private String password;
}
