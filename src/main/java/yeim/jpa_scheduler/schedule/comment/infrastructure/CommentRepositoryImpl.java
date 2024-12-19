package yeim.jpa_scheduler.schedule.comment.infrastructure;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import yeim.jpa_scheduler.schedule.comment.domain.Comment;

@Repository
@Primary
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

	private final JpaCommentRepository jpaCommentRepository;

	@Override
	public Comment create(Comment comment) {
		return jpaCommentRepository.save(CommentEntity.from(comment)).toModel();
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return jpaCommentRepository.findById(id).map(CommentEntity::toModel);
	}

	@Override
	public List<Comment> findByScheduleId(Long scheduleId) {
		return jpaCommentRepository.findByScheduleId(scheduleId).stream()
			.map(CommentEntity::toModel)
			.toList();
	}

	@Override
	public Comment update(Comment member) {
		return jpaCommentRepository.save(CommentEntity.from(member)).toModel();
	}

	@Override
	public void delete(Long id) {
		jpaCommentRepository.deleteById(id);
	}
}
