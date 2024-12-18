package yeim.jpa_scheduler.auth.service;

import static yeim.jpa_scheduler.common.exception.enums.AuthExceptionType.AUTH_FAILED;
import static yeim.jpa_scheduler.common.exception.enums.MemberExceptionType.EMAIL_DUPLICATE;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeim.jpa_scheduler.auth.domain.MemberCreate;
import yeim.jpa_scheduler.auth.domain.MemberLogin;
import yeim.jpa_scheduler.common.exception.CustomException;
import yeim.jpa_scheduler.common.utils.PasswordEncoder;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	public Member createMember(MemberCreate memberCreate) {
		Member member = memberRepository.findByEmail(memberCreate.getEmail()).orElse(null);
		if (member != null) {
			throw new CustomException(EMAIL_DUPLICATE);
		}
		return memberRepository.create(Member.from(passwordEncoder, memberCreate));
	}

	public Member validateMember(MemberLogin memberLogin) {
		Member member = memberRepository.findByEmail(memberLogin.getEmail())
			.orElseThrow(() -> new CustomException(AUTH_FAILED));
		if (!member.verifyPassword(passwordEncoder, memberLogin.getPassword())) {
			throw new CustomException(AUTH_FAILED);
		}
		return member;
	}
}
