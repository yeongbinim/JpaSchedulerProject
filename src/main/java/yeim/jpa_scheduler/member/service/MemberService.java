package yeim.jpa_scheduler.member.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.domain.MemberCreate;
import yeim.jpa_scheduler.member.domain.MemberDelete;
import yeim.jpa_scheduler.member.domain.MemberResponse;
import yeim.jpa_scheduler.member.domain.MemberUpdate;
import yeim.jpa_scheduler.member.infrastructure.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	public Member createMember(MemberCreate memberCreate) {
		return null;
	}

	public List<MemberResponse> getAllMembers() {
		return List.of();
	}

	public Member getMember(Long id) {
		return null;
	}

	public Member updateMember(Long id, MemberUpdate memberUpdate) {
		return null;
	}

	public void deleteMember(Long id, MemberDelete memberDelete) {
	}
}
