package yeim.jpa_scheduler.schedule.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import yeim.jpa_scheduler.member.domain.Member;
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
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
		Schedule schedule = Schedule.from(member, scheduleCreate);
		return scheduleRepository.create(schedule);
	}

	public List<Schedule> getAllSchedules() {
		return scheduleRepository.findAll();
	}

	public Schedule getSchedule(Long id) {
		return scheduleRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("찾을 수 없습니다."));
	}

	public Schedule updateSchedule(Long id, ScheduleUpdate scheduleUpdate) {
		Schedule schedule = getSchedule(id);
		return scheduleRepository.update(schedule.update(scheduleUpdate));
	}

	public void deleteSchedule(Long id) {
		scheduleRepository.delete(id);
	}
}
