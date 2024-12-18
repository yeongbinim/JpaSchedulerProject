package yeim.jpa_scheduler.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeim.jpa_scheduler.auth.domain.MemberCreate;
import yeim.jpa_scheduler.auth.domain.MemberLogin;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final MemberRepository memberRepository;

	public Member createMember(MemberCreate memberCreate) {
		return memberRepository.create(Member.from(memberCreate));
	}

	public Member validateMember(MemberLogin memberLogin) {
		return memberRepository.findByEmail(memberLogin.getEmail())
			.orElseThrow(() -> new IllegalArgumentException("해당 이메일의 사용자를 찾을 수 없습니다."));
	}
}
