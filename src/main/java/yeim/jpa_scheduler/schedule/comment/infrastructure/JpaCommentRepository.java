package yeim.jpa_scheduler.schedule.comment.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCommentRepository extends JpaRepository<CommentEntity, Long> {
	
	List<CommentEntity> findByScheduleId(Long scheduleId);
}
