package yeim.jpa_scheduler.schedule.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleCreate {

	@NotBlank
	@Size(max = 40, message = "제목은 40자 이내여야 합니다")
	private String title;

	@NotNull
	@Size(max = 200, message = "내용은 200자 이내여야 합니다")
	private String content;
}
