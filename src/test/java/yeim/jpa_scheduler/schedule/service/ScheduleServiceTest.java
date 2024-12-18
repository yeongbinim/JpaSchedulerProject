package yeim.jpa_scheduler.schedule.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
		Member member = fakeMemberRepository.create(new Member(
			null,
			"testuser",
			"testuser@test.test",
			"password",
			null,
			null));
		fakeScheduleRepository.create(new Schedule(
			null,
			member,
			"미팅",
			"프로젝트 관련 미팅",
			null,
			null
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
		// when
		List<Schedule> schedules = scheduleService.getAllSchedules();

		// then
		assertThat(schedules).isNotNull();
		assertThat(schedules).hasSize(1);
		assertThat(schedules.stream().map(Schedule::getTitle).toList())
			.containsExactlyInAnyOrder("미팅");
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
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("찾을 수 없습니다.");
	}
}
