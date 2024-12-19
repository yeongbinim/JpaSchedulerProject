package yeim.jpa_scheduler.schedule.service;

import static yeim.jpa_scheduler.common.exception.enums.MemberExceptionType.MEMBER_NOT_FOUND;
import static yeim.jpa_scheduler.common.exception.enums.ScheduleExceptionType.SCHEDULE_NOT_FOUND;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yeim.jpa_scheduler.common.exception.CustomException;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.infrastructure.MemberRepository;
import yeim.jpa_scheduler.schedule.domain.Schedule;
import yeim.jpa_scheduler.schedule.domain.ScheduleCreate;
import yeim.jpa_scheduler.schedule.domain.ScheduleUpdate;
import yeim.jpa_scheduler.schedule.infrastructure.ScheduleRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ScheduleService {

	private final ScheduleRepository scheduleRepository;
	private final MemberRepository memberRepository;

	public Schedule createSchedule(Long memberId, ScheduleCreate scheduleCreate) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(MEMBER_NOT_FOUND));
		Schedule schedule = Schedule.from(member, scheduleCreate);
		return scheduleRepository.create(schedule);
	}

	@Transactional(readOnly = true)
	public Page<Schedule> getAllSchedules(Pageable pageable) {
		return scheduleRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Schedule getSchedule(Long id) {
		return scheduleRepository.findById(id)
			.orElseThrow(() -> new CustomException(SCHEDULE_NOT_FOUND));
	}

	public Schedule updateSchedule(Long id, ScheduleUpdate scheduleUpdate) {
		// TODO 작성자만 수정 가능해야함
		Schedule schedule = getSchedule(id);
		return scheduleRepository.update(schedule.update(scheduleUpdate));
	}

	public void deleteSchedule(Long id) {
		// TODO 작성자만 삭제 가능해야함
		scheduleRepository.delete(id);
	}
}
