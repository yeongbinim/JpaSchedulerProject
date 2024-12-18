package yeim.jpa_scheduler.common.filters;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AuthFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		HttpSession session = httpRequest.getSession(false);
		if (session == null || session.getAttribute("sessionId") == null) {
			httpResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
			httpResponse.getWriter().write("Unauthorized");
			return;
		}
		System.out.println(session.getAttribute("sessionId"));
		Long memberId = (Long) session.getAttribute("sessionId");
		request.setAttribute("memberId", memberId);
		chain.doFilter(request, response);
	}
}
