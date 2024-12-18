package yeim.jpa_scheduler.schedule.infrastructure;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import yeim.jpa_scheduler.schedule.domain.Schedule;

@Repository
@Primary
@RequiredArgsConstructor
public class ScheduleRepositoryImpl implements ScheduleRepository {

	private final JpaScheduleRepository jpaScheduleRepository;

	@Override
	public Schedule create(Schedule schedule) {

		return jpaScheduleRepository.save(ScheduleEntity.from(schedule)).toModel();
	}

	@Override
	public List<Schedule> findAll() {
		return jpaScheduleRepository.findAll().stream().map(ScheduleEntity::toModel).toList();
	}

	@Override
	public Optional<Schedule> findById(Long id) {
		return jpaScheduleRepository.findById(id).map(ScheduleEntity::toModel);
	}

	@Override
	public Schedule update(Schedule schedule) {
		return jpaScheduleRepository.save(ScheduleEntity.from(schedule)).toModel();
	}

	@Override
	public void delete(Long id) {
		jpaScheduleRepository.deleteById(id);
	}
}
