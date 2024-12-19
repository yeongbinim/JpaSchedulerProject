package yeim.jpa_scheduler.schedule.infrastructure;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yeim.jpa_scheduler.schedule.domain.Schedule;

public interface ScheduleRepository {

	Schedule create(Schedule schedule);

	Page<Schedule> findAll(Pageable pageable);

	Optional<Schedule> findById(Long id);

	Schedule update(Schedule schedule);

	void delete(Long id);
}
