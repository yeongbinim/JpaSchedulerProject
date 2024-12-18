package yeim.jpa_scheduler.member.service;

import java.util.List;
import java.util.stream.Collectors;
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
		return memberRepository.create(Member.from(memberCreate));
	}

	public List<MemberResponse> getAllMembers() {
		return memberRepository.findAll().stream()
			.map(MemberResponse::from)
			.collect(Collectors.toList());
	}

	public Member getMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("찾을 수 없다."));
	}

	public Member updateMember(Long id, MemberUpdate memberUpdate) {
		Member member = getMember(id);
		if (!member.verifyPassword(memberUpdate.getPassword())) {
			throw new IllegalArgumentException("비밀번호 잘못됨");
		}
		return memberRepository.update(member.update(memberUpdate));
	}

	public void deleteMember(Long id, MemberDelete memberDelete) {
		Member member = getMember(id);
		if (!member.verifyPassword(memberDelete.getPassword())) {
			throw new IllegalArgumentException("비밀번호 잘못됨");
		}
		memberRepository.delete(id);
	}
}
