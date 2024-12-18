package yeim.jpa_scheduler.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yeim.jpa_scheduler.common.filters.AuthFilter;

@Configuration
public class WebConfig {

	@Bean
	public FilterRegistrationBean<AuthFilter> authFilterRegistrationBean(AuthFilter authFilter) {
		FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
		registrationBean.setFilter(authFilter);
		registrationBean.addUrlPatterns("/api/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}
}