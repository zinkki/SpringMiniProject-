package kr.co.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Spring MVC 프로젝트에 관련된 설정을 하는 클래스
// SrpingTestXML프로젝트에서 /WebContent/WEB-INF/config/servlet-context.xml의 부분임

@Configuration

//Controller어노테이션이 셋팅되어있는 클래스를 Controller로 등록한다
@EnableWebMvc

//스캔할 패키지를 지정한다
@ComponentScan("kr.co.test.controller")
public class ServletAppContext implements WebMvcConfigurer{
	
	//Controller의 메서드가 반환하는 jsp의 이름앞뒤에 경로와 확장자를 붙여주도록 설정!
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		WebMvcConfigurer.super.configureViewResolvers(registry);
		registry.jsp("/WEB-INF/views/" , ".jsp");
	}
	//정적파일의 경로를 맵핑한당
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/**").addResourceLocations("/resources/");
	}
}
