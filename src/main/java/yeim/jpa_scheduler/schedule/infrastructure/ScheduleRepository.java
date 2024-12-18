package yeim.jpa_scheduler.schedule.infrastructure;

import java.util.List;
import java.util.Optional;
import yeim.jpa_scheduler.schedule.domain.Schedule;

public interface ScheduleRepository {

	Schedule create(Schedule schedule);

	List<Schedule> findAll();

	Optional<Schedule> findById(Long id);

	Schedule update(Schedule schedule);

	void delete(Long id);
}
