package yeim.jpa_scheduler.schedule.comment.service;

import static yeim.jpa_scheduler.common.exception.enums.CommentExceptionType.COMMENT_NOT_FOUND;
import static yeim.jpa_scheduler.common.exception.enums.MemberExceptionType.MEMBER_NOT_FOUND;
import static yeim.jpa_scheduler.common.exception.enums.ScheduleExceptionType.SCHEDULE_NOT_FOUND;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeim.jpa_scheduler.common.exception.CustomException;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.infrastructure.MemberRepository;
import yeim.jpa_scheduler.schedule.comment.domain.Comment;
import yeim.jpa_scheduler.schedule.comment.domain.CommentCreate;
import yeim.jpa_scheduler.schedule.comment.domain.CommentUpdate;
import yeim.jpa_scheduler.schedule.comment.infrastructure.CommentRepository;
import yeim.jpa_scheduler.schedule.domain.Schedule;
import yeim.jpa_scheduler.schedule.infrastructure.ScheduleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final MemberRepository memberRepository;
	private final ScheduleRepository scheduleRepository;

	public Comment createComment(Long memberId, Long scheduleId, CommentCreate commentCreate) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		Schedule schedule = scheduleRepository.findById(scheduleId)
			.orElseThrow(() -> new CustomException(SCHEDULE_NOT_FOUND));
		Comment comment = Comment.from(member, schedule, commentCreate);
		return commentRepository.create(comment);
	}

	@Transactional(readOnly = true)
	public List<Comment> getCommentsByScheduleId(Long scheduleId) {
		return commentRepository.findByScheduleId(scheduleId);
	}

	@Transactional(readOnly = true)
	public Comment getComment(Long id) {
		return commentRepository.findById(id)
			.orElseThrow(() -> new CustomException(COMMENT_NOT_FOUND));
	}

	public Comment updateComment(Long id, CommentUpdate commentUpdate) {
		// TODO 작성자만 수정 가능해야함
		Comment comment = getComment(id);
		return commentRepository.update(comment.update(commentUpdate));
	}

	public void deleteComment(Long id) {
		// TODO 작성자 & 일정주인만 수정 가능해야함
		commentRepository.delete(id);
	}
}
