package yeim.jpa_scheduler.member.infrastructure;

import java.util.List;
import java.util.Optional;
import yeim.jpa_scheduler.member.domain.Member;

public interface MemberRepository {

	Member create(Member member);

	List<Member> findAll();

	Optional<Member> findById(Long id);

	Member update(Member member);

	void delete(Long id);
}
