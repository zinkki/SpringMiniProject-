package kr.co.test.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

//public class SpringConfigClass implements WebApplicationInitializer{
//
//	@Override
//	public void onStartup(ServletContext servletContext) throws ServletException {
//
////원래는 /WebContent/WEB-INF/web.xml 에서 <> 이렇게 생긴태그로 다 설정했던거를 자바로설정하기
//		
//		//Spring MVC 프로젝트 설정을위해 작성하는 클래스의 객체를 생성한다.
//		AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
//		servletAppContext.register(ServletAppContext.class);
//		
//		//요청발생시 요청을 처리하는 서블릿을 DispatcherServlet으로 설정해준다.
//		DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);		
//		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", dispatcherServlet);
//		
//		//부가설정
//		servlet.setLoadOnStartup(1); //가장먼저받아드리겠당
//		servlet.addMapping("/");
//		
//		//Bean을 정의하는 클래스를 지정한다.
//		AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
//		rootAppContext.register(RootAppContext.class);
//		
//		//리스너설정
//		ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
//		servletContext.addListener(listener);
//		
//		//파라미터인코딩설정
//		FilterRegistration.Dynamic filter = servletContext.addFilter("encodingFilter", CharacterEncodingFilter.class);
//		filter.setInitParameter("encoding", "UTF-8");
//		filter.addMappingForServletNames(null, false, "dispatcher");
//	}
//
//}

public class SpringConfigClass extends AbstractAnnotationConfigDispatcherServletInitializer {
	//DispatcherServlet에 맵핑할 요청주소를 셋팅한다
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	//SpringMVC프로젝트 설정을 위한 클래스를 지정한다.
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {ServletAppContext.class};
	}
	
	//프로젝트에서 사용할 Bean들을 정의하기위한 클래스를 지정한다.
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootAppContext.class};
	}
	//파라미터 인코딩 필터설정
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
		encodingFilter.setEncoding("UTF-8");
		return new Filter[] {encodingFilter};
	}
}
