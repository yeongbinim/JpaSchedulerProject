package yeim.jpa_scheduler.schedule.comment.infrastructure;

import java.util.List;
import java.util.Optional;
import yeim.jpa_scheduler.schedule.comment.domain.Comment;

public interface CommentRepository {

	Comment create(Comment comment);

	List<Comment> findByScheduleId(Long scheduleId);

	Optional<Comment> findById(Long id);

	Comment update(Comment schedule);

	void delete(Long id);
}
