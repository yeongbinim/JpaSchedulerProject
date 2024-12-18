package yeim.jpa_scheduler.schedule.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaScheduleRepository extends JpaRepository<ScheduleEntity, Long> {

}
