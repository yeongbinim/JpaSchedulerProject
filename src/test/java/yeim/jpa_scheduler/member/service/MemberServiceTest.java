package yeim.jpa_scheduler.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.domain.MemberCreate;
import yeim.jpa_scheduler.member.domain.MemberDelete;
import yeim.jpa_scheduler.member.domain.MemberUpdate;
import yeim.jpa_scheduler.member.infrastructure.MemoryMemberRepository;

public class MemberServiceTest {

	private MemberService memberService;
	private MemoryMemberRepository fakeMemberRepository;

	@BeforeEach
	void init() {
		fakeMemberRepository = new MemoryMemberRepository();
		memberService = new MemberService(fakeMemberRepository);
	}

	@Test
	void 회원_생성_테스트() {
		// given
		MemberCreate memberCreate = new MemberCreate("testuser", "testuser@test.test", "password");

		// when
		Member member = memberService.createMember(memberCreate);

		// then
		assertThat(member.getId()).isNotNull();
		assertThat(member.getName()).isEqualTo("testuser");
	}

	@Test
	void 회원_조회_테스트() {
		// given
		MemberCreate memberCreate = new MemberCreate("testuser", "testuser@test.test", "password");
		Member createdMember = memberService.createMember(memberCreate);

		// when
		Member result = memberService.getMember(createdMember.getId());

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getName()).isEqualTo("testuser");
	}

	@Test
	void 회원_전체_조회_테스트() {
		// given
		MemberCreate memberCreate1 = new MemberCreate("user1", "user1@test.test", "password1");
		MemberCreate memberCreate2 = new MemberCreate("user2", "user2@test.test", "password2");
		MemberCreate memberCreate3 = new MemberCreate("user3", "user3@test.test", "password3");

		memberService.createMember(memberCreate1);
		memberService.createMember(memberCreate2);
		memberService.createMember(memberCreate3);

		// when
		List<Member> members = memberService.getAllMembers();

		// then
		assertThat(members).hasSize(3);
		assertThat(members.stream().map(Member::getName).toList())
			.containsExactlyInAnyOrder("user1", "user2", "user3");
	}

	@Test
	void 회원_수정_테스트() {
		// given
		MemberCreate memberCreate = new MemberCreate("testuser", "testuser@test.test", "password");
		Member createdMember = memberService.createMember(memberCreate);
		MemberUpdate memberUpdate = new MemberUpdate("testuser2", "testuser2@test.test",
			"password");

		// when
		Member updatedMember = memberService.updateMember(createdMember.getId(), memberUpdate);

		// then
		assertThat(updatedMember.getName()).isEqualTo("testuser2");
		assertThat(updatedMember.getEmail()).isEqualTo("testuser2@test.test");
	}

	@Test
	void 회원_삭제_테스트() {
		// given
		MemberCreate memberCreate = new MemberCreate("testuser", "testuser@test.test", "password");
		Member createdMember = memberService.createMember(memberCreate);

		// when
		memberService.deleteMember(createdMember.getId(), new MemberDelete("password"));

		// then (이거 맞나??)
		assertThat(fakeMemberRepository.findById(createdMember.getId())).isEmpty();
	}
}
