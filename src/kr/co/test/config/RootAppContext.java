package kr.co.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.SessionScope;

import kr.co.test.beans.UserBean;

//프로젝트작업시 사용할 bean을 정의하는 클래스
//SpringTestXML 에서 xml로 셋팅했을때 
//    /WebContent/WEB-INF/config/root-context.xml을 담당하는부분이랑 똑같음(단지 자바파일일뿐ㅋ)

@Configuration
public class RootAppContext {

	@Bean("loginUserBean")
	@SessionScope
	public UserBean loginUserBean() {
		return new UserBean();
	}
}
