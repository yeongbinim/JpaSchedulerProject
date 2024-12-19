package yeim.jpa_scheduler.schedule.comment.infrastructure;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;
import yeim.jpa_scheduler.schedule.comment.domain.Comment;

@Repository
public class MemoryCommentRepository implements CommentRepository {

	private final Map<Long, Comment> store = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong();

	@Override
	public Comment create(Comment schedule) {
		schedule.setId(sequence.incrementAndGet());
		store.put(schedule.getId(), schedule);

		return schedule;
	}

	@Override
	public List<Comment> findByScheduleId(Long scheduleId) {
		return store.values().stream()
			.filter(c -> c.getSchedule().getId().equals(scheduleId))
			.toList();
	}

	@Override
	public Optional<Comment> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Comment update(Comment schedule) {
		schedule.setId(schedule.getId());
		store.put(schedule.getId(), schedule);
		return schedule;
	}

	@Override
	public void delete(Long id) {
		store.remove(id);
	}
}
