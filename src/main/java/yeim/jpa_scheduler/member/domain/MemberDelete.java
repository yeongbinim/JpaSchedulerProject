package yeim.jpa_scheduler.member.domain;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDelete {

	@NotNull
	private String password;
}
