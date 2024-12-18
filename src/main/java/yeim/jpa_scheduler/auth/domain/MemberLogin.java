package yeim.jpa_scheduler.auth.domain;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberLogin {

	@NotNull
	@Pattern(
		regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$",
		message = "올바른 이메일 주소를 입력해주세요."
	)
	private String email;

	@NotNull
	@Pattern(
		regexp = "^[a-zA-Z][a-zA-Z0-9@#$%^&~+=?!*_-]{1,19}$",
		message = "비밀번호는 영문자로 시작하며, 영문자, 숫자, 특수문자(@#$%^&~+=?!*_-)만 사용 가능하며 1자에서 19자 사이여야 합니다."
	)
	private String password;
}
