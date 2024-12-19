package yeim.jpa_scheduler.schedule.infrastructure;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import yeim.jpa_scheduler.schedule.domain.Schedule;

@Repository
public class MemoryScheduleRepository implements ScheduleRepository {

	private final Map<Long, Schedule> store = new ConcurrentHashMap<>();
	private final AtomicLong sequence = new AtomicLong();

	@Override
	public Schedule create(Schedule schedule) {
		schedule.setId(sequence.incrementAndGet());
		store.put(schedule.getId(), schedule);

		return schedule;
	}

	@Override
	public Page<Schedule> findAll(Pageable pageable) {
		List<Schedule> schedules = new ArrayList<>(store.values());
		schedules.sort(Comparator.comparing(Schedule::getUpdatedAt).reversed());
		int start = (int) pageable.getOffset();
		int end = Math.min((start + pageable.getPageSize()), schedules.size());
		return new PageImpl<>(schedules.subList(start, end), pageable, schedules.size());
	}

	@Override
	public Optional<Schedule> findById(Long id) {
		return Optional.ofNullable(store.get(id));
	}

	@Override
	public Schedule update(Schedule schedule) {
		schedule.setId(schedule.getId());
		store.put(schedule.getId(), schedule);
		return schedule;
	}

	@Override
	public void delete(Long id) {
		store.remove(id);
	}
}
