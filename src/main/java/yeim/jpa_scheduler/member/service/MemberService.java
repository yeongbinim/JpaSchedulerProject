package yeim.jpa_scheduler.member.service;

import static yeim.jpa_scheduler.common.exception.enums.MemberExceptionType.MEMBER_NOT_FOUND;
import static yeim.jpa_scheduler.common.exception.enums.MemberExceptionType.PASSWORD_NOT_MATCH;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeim.jpa_scheduler.common.exception.CustomException;
import yeim.jpa_scheduler.common.utils.PasswordEncoder;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.domain.MemberDelete;
import yeim.jpa_scheduler.member.domain.MemberUpdate;
import yeim.jpa_scheduler.member.infrastructure.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Member getMember(Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
	}

	public Member updateMember(Long id, MemberUpdate memberUpdate) {
		Member member = getMember(id);
		if (!member.verifyPassword(passwordEncoder, memberUpdate.getPassword())) {
			throw new CustomException(PASSWORD_NOT_MATCH);
		}
		return memberRepository.update(member.update(memberUpdate));
	}

	public void deleteMember(Long id, MemberDelete memberDelete) {
		Member member = getMember(id);
		if (!member.verifyPassword(passwordEncoder, memberDelete.getPassword())) {
			throw new CustomException(PASSWORD_NOT_MATCH);
		}
		memberRepository.delete(id);
	}
}
