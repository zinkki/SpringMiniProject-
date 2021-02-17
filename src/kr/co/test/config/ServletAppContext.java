package kr.co.test.config;

import javax.annotation.Resource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import kr.co.test.beans.UserBean;
import kr.co.test.interceptor.CheckLoginInterceptor;
import kr.co.test.interceptor.TopMenuInterceptor;
import kr.co.test.mapper.BoardMapper;
import kr.co.test.mapper.TopMenuMapper;
import kr.co.test.mapper.UserMapper;
import kr.co.test.service.TopMenuService;

// Spring MVC 프로젝트에 관련된 설정을 하는 클래스
// SrpingTestXML프로젝트에서 /WebContent/WEB-INF/config/servlet-context.xml의 부분임

@Configuration

//Controller어노테이션이 셋팅되어있는 클래스를 Controller로 등록한다
@EnableWebMvc

//스캔할 패키지를 지정한다
@ComponentScan("kr.co.test.controller")
@ComponentScan("kr.co.test.dao")
@ComponentScan("kr.co.test.service")

@PropertySource("/WEB-INF/properties/db.properties")
public class ServletAppContext implements WebMvcConfigurer{
	
	@Value("${db.classname}")
	private String db_classname;
	@Value("${db.url}")
	private String db_url;
	@Value("${db.username}")
	private String db_username;
	@Value("${db.password}")
	private String db_password;
	
	@Autowired
	private TopMenuService topMenuService;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
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
	//DataBase접속정보를 관리하는 Bean
	@Bean
	public BasicDataSource dataSource() {
		BasicDataSource source = new BasicDataSource();
		source.setDriverClassName(db_classname);
		source.setUrl(db_url);
		source.setUsername(db_username);
		source.setPassword(db_password);
		return source;
	}
	//쿼리문과 접속정보를 관리하는 Bean객체
	@Bean
	public SqlSessionFactory factory(BasicDataSource source) throws Exception {
		SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
		factoryBean.setDataSource(source);
		SqlSessionFactory factory = factoryBean.getObject();
		return factory;
	}
	//쿼리문 실행을 위한 객체
	@Bean
	public MapperFactoryBean<BoardMapper> getBoardMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<BoardMapper> factoryBean = new MapperFactoryBean<BoardMapper>(BoardMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	//상단 탑메뉴매퍼등록
	@Bean
	public MapperFactoryBean<TopMenuMapper> getTopMenuMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<TopMenuMapper> factoryBean = new MapperFactoryBean<TopMenuMapper>(TopMenuMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	//회원아이디중복 매퍼등록
	@Bean
	public MapperFactoryBean<UserMapper> getUserMapper(SqlSessionFactory factory) throws Exception {
		MapperFactoryBean<UserMapper> factoryBean = new MapperFactoryBean<UserMapper>(UserMapper.class);
		factoryBean.setSqlSessionFactory(factory);
		return factoryBean;
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		
		TopMenuInterceptor topMenuInterceptor = new TopMenuInterceptor(topMenuService, loginUserBean);
		InterceptorRegistration reg1 = registry.addInterceptor(topMenuInterceptor);
		reg1.addPathPatterns("/**");
		
		CheckLoginInterceptor checkLoginInterceptor = new CheckLoginInterceptor(loginUserBean);
		InterceptorRegistration reg2 = registry.addInterceptor(checkLoginInterceptor);
		reg2.addPathPatterns("/user/modify", "/user/logout", "/board/*");
		reg2.excludePathPatterns("/board/main");
	}
	//혹시 에러날까봐 걍 해봄..원래 properties의 error메세지랑 db랑 같이쓰면 500에러난대..이거쓰면안난다네..근데 난 원래안나지만 걍 써봄
	//ㅁㅊ 걍 저거 주석처리한게 안되는거였음 ㅁㅊㅁㅊ MessageSource로 해야되는거여썽 낡은윤재성씨...
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}
	
	//에러메세지 properties파일의 error_message.properties에있는걸로 하고싶긔..ㅅㅂ이거안됨ㅋ..밑에껄로바꿨엉
//	@Bean
//	public ReloadableResourceBundleMessageSource massageSource() {
//		ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
//		res.setBasenames("/WEB-INF/properties/error_message");
//		return res;
//	}
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource res = new ReloadableResourceBundleMessageSource();
		res.setBasenames("/WEB-INF/properties/error_message");
		res.setDefaultEncoding("UTF-8");
		return res;
	}
	//이거 jsp에 enctpye="multipart/form-data"해서 유효성검사에서 걸려서 꼭 해줘야댐ㄷ3ㄷ
	@Bean 
	public StandardServletMultipartResolver multipartResolver() {
		return new StandardServletMultipartResolver();
	}
}
