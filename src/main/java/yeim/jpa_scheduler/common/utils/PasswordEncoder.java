package yeim.jpa_scheduler.common.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

	private final int workFactor;

	public PasswordEncoder(@Value("${bcrypt.work_factor}") int workFactor) {
		this.workFactor = workFactor;
	}

	public String encode(CharSequence rawPassword) {
		return BCrypt.withDefaults().hashToString(workFactor, rawPassword.toString().toCharArray());
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		BCrypt.Result result = BCrypt.verifyer()
			.verify(rawPassword.toString().toCharArray(), encodedPassword);
		return result.verified;
	}
}
