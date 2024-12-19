package yeim.jpa_scheduler.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static yeim.jpa_scheduler.common.exception.enums.MemberExceptionType.MEMBER_NOT_FOUND;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yeim.jpa_scheduler.auth.domain.MemberCreate;
import yeim.jpa_scheduler.common.exception.CustomException;
import yeim.jpa_scheduler.common.utils.PasswordEncoder;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.domain.MemberDelete;
import yeim.jpa_scheduler.member.domain.MemberUpdate;
import yeim.jpa_scheduler.member.infrastructure.MemoryMemberRepository;

public class MemberServiceTest {

	private MemberService memberService;
	private final PasswordEncoder passwordEncoder = new PasswordEncoder(4);

	@BeforeEach
	void init() {
		MemoryMemberRepository fakeMemberRepository = new MemoryMemberRepository();
		memberService = new MemberService(fakeMemberRepository, passwordEncoder);
		fakeMemberRepository.create(
			Member.from(passwordEncoder,
				new MemberCreate("user1", "user1@test.test", "password1"))
		);
		fakeMemberRepository.create(
			Member.from(passwordEncoder,
				new MemberCreate("user2", "user2@test.test", "password2"))
		);
	}

	@Test
	void 회원_조회_테스트() {
		// given
		Long memberId = 1L;

		// when
		Member result = memberService.getMember(memberId);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("user1");
	}

	@Test
	void 회원_전체_조회_테스트() {
		// given
		// when
		List<Member> members = memberService.getAllMembers();

		// then
		assertThat(members).hasSize(2);
		assertThat(members.stream().map(Member::getName).toList())
			.containsExactlyInAnyOrder("user1", "user2");
	}

	@Test
	void 회원_수정_테스트() {
		// given
		Long memberId = 1L;
		MemberUpdate memberUpdate = new MemberUpdate(
			"updated1",
			"updated1@test.test",
			"password1");

		// when
		Member updatedMember = memberService.updateMember(memberId, memberUpdate);

		// then
		assertThat(updatedMember.getName()).isEqualTo("updated1");
		assertThat(updatedMember.getEmail()).isEqualTo("updated1@test.test");
	}

	@Test
	void 회원_삭제_테스트() {
		// given
		Long memberId = 1L;

		// when
		memberService.deleteMember(memberId, new MemberDelete("password1"));

		// then
		assertThatThrownBy(() -> memberService.getMember(memberId))
			.isInstanceOf(CustomException.class)
			.extracting(exception -> (CustomException) exception)
			.extracting(CustomException::getCode)
			.isEqualTo(MEMBER_NOT_FOUND.getCode());
	}
}
