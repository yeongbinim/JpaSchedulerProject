package yeim.jpa_scheduler.member.controller;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.domain.MemberDelete;
import yeim.jpa_scheduler.member.domain.MemberResponse;
import yeim.jpa_scheduler.member.domain.MemberUpdate;
import yeim.jpa_scheduler.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

	private final MemberService memberService;

	@GetMapping
	public ResponseEntity<List<MemberResponse>> getAllMembers() {
		List<MemberResponse> members = memberService.getAllMembers().stream()
			.map(MemberResponse::from)
			.toList();
		return ResponseEntity
			.ok()
			.body(members);
	}

	@GetMapping("/{id}")
	public ResponseEntity<MemberResponse> getMember(@PathVariable Long id) {
		Member member = memberService.getMember(id);
		return ResponseEntity
			.ok()
			.body(MemberResponse.from(member));
	}

	@PutMapping("/{id}")
	public ResponseEntity<MemberResponse> updateMember(
		@PathVariable Long id,
		@Valid @RequestBody MemberUpdate memberUpdate
	) {
		Member member = memberService.updateMember(id, memberUpdate);
		return ResponseEntity
			.ok()
			.body(MemberResponse.from(member));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<MemberResponse> deleteMember(
		@PathVariable Long id,
		@Valid @RequestBody MemberDelete memberDelete
	) {
		memberService.deleteMember(id, memberDelete);
		return ResponseEntity
			.noContent()
			.build();
	}
}
