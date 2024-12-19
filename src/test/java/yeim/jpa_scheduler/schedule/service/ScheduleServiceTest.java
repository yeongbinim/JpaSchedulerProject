package yeim.jpa_scheduler.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static yeim.jpa_scheduler.common.exception.enums.ScheduleExceptionType.SCHEDULE_NOT_FOUND;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import yeim.jpa_scheduler.auth.domain.MemberCreate;
import yeim.jpa_scheduler.common.exception.CustomException;
import yeim.jpa_scheduler.common.utils.PasswordEncoder;
import yeim.jpa_scheduler.member.domain.Member;
import yeim.jpa_scheduler.member.infrastructure.MemoryMemberRepository;
import yeim.jpa_scheduler.schedule.domain.Schedule;
import yeim.jpa_scheduler.schedule.domain.ScheduleCreate;
import yeim.jpa_scheduler.schedule.domain.ScheduleUpdate;
import yeim.jpa_scheduler.schedule.infrastructure.MemoryScheduleRepository;

public class ScheduleServiceTest {

	private ScheduleService scheduleService;

	@BeforeEach
	void init() {
		MemoryMemberRepository fakeMemberRepository = new MemoryMemberRepository();
		MemoryScheduleRepository fakeScheduleRepository = new MemoryScheduleRepository();
		scheduleService = new ScheduleService(fakeScheduleRepository, fakeMemberRepository);
		Member member = fakeMemberRepository.create(Member.from(
			new PasswordEncoder(4),
			new MemberCreate("testuser", "testuser@test.test", "password")
		));
		fakeScheduleRepository.create(Schedule.from(
			member,
			new ScheduleCreate("미팅", "프로젝트 관련 미팅")
		));
		fakeScheduleRepository.create(Schedule.from(
			member,
			new ScheduleCreate("미팅2", "프로젝트 관련 미팅2")
		));
	}

	@Test
	void 일정_생성_테스트() {
		// given
		ScheduleCreate scheduleCreate = new ScheduleCreate("새로운 미팅", "점심 결정 미팅");

		// when
		Schedule createdSchedule = scheduleService.createSchedule(1L, scheduleCreate);

		// then
		assertThat(createdSchedule).isNotNull();
		assertThat(createdSchedule.getId()).isNotNull();
		assertThat(createdSchedule.getTitle()).isEqualTo("새로운 미팅");
		assertThat(createdSchedule.getContent()).isEqualTo("점심 결정 미팅");
	}

	@Test
	void 일정_조회_테스트() {
		// given
		Long scheduleId = 1L;

		// when
		Schedule schedule = scheduleService.getSchedule(scheduleId);

		// then
		assertThat(schedule).isNotNull();
		assertThat(schedule.getContent()).isEqualTo("프로젝트 관련 미팅");
		assertThat(schedule.getMember().getEmail()).isEqualTo("testuser@test.test");
	}

	@Test
	void 일정_전체_조회_테스트() {

		// given
		Pageable pageable = PageRequest.of(0, 10, Direction.DESC, "updatedAt");

		// when
		Page<Schedule> page = scheduleService.getAllSchedules(pageable);

		// then
		assertThat(page).isNotNull();
		assertThat(page.getContent()).hasSize(2);
		assertThat(page.getContent()).extracting(Schedule::getTitle)
			.containsExactlyInAnyOrder("미팅2", "미팅");
	}

	@Test
	void 일정_수정_테스트() {
		// given
		Long scheduleId = 1L;
		ScheduleUpdate scheduleUpdate = new ScheduleUpdate("업데이트된 미팅", "프로젝트 진행상황 업데이트");

		// when
		Schedule updatedSchedule = scheduleService.updateSchedule(scheduleId, scheduleUpdate);

		// then
		assertThat(updatedSchedule).isNotNull();
		assertThat(updatedSchedule.getTitle()).isEqualTo("업데이트된 미팅");
		assertThat(updatedSchedule.getContent()).isEqualTo("프로젝트 진행상황 업데이트");
	}

	@Test
	void 일정_삭제_후_조회_테스트() {
		// given
		Long scheduleId = 1L;

		// when
		scheduleService.deleteSchedule(scheduleId);

		// then
		assertThatThrownBy(() -> scheduleService.getSchedule(scheduleId))
			.isInstanceOf(CustomException.class)
			.extracting(exception -> (CustomException) exception)
			.extracting(CustomException::getCode)
			.isEqualTo(SCHEDULE_NOT_FOUND.getCode());
	}
}
