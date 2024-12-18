package yeim.jpa_scheduler.schedule.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeim.jpa_scheduler.member.infrastructure.MemberRepository;
import yeim.jpa_scheduler.schedule.domain.Schedule;
import yeim.jpa_scheduler.schedule.domain.ScheduleCreate;
import yeim.jpa_scheduler.schedule.domain.ScheduleUpdate;
import yeim.jpa_scheduler.schedule.infrastructure.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final MemberRepository memberRepository;

	public Schedule createSchedule(Long memberId, ScheduleCreate scheduleCreate) {
		return null;
	}

	public List<Schedule> getAllSchedules() {
		return List.of();
	}

	public Schedule getSchedule(Long id) {
		return null;
	}

	public Schedule updateSchedule(Long id, ScheduleUpdate scheduleUpdate) {
		return null;
	}

	public void deleteSchedule(Long id) {
	}
}
