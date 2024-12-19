package yeim.jpa_scheduler.schedule.comment.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentCreate {

	@NotNull
	@Size(max = 200, message = "내용은 200자 이내여야 합니다")
	private String content;
}
