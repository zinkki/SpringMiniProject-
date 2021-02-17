package kr.co.test.service;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.test.beans.UserBean;
import kr.co.test.dao.UserDAO;

@Service
public class UserService {
	
	@Autowired
	private UserDAO userDAO;
	
	@Resource(name="loginUserBean")
	private UserBean loginUserBean;
	
	//아이디 중복체크
	public boolean checkUserIdExist(String user_id) {
		String user_name = userDAO.checkUserIdExist(user_id);
		if(user_name == null) {
			return true;
		}else {
			return false;
		}
	}
	//회원가입
	public void addUserInfo(UserBean joinUserBean) {
		userDAO.addUserInfo(joinUserBean);
	}
	//로그인...
	public void getLoginUserInfo(UserBean tempLoginUserBean) {
		UserBean tempLoginUserBean2 = userDAO.getLoginUserInfo(tempLoginUserBean);
		if(tempLoginUserBean2 != null) {
			loginUserBean.setUser_idx(tempLoginUserBean2.getUser_idx());
			loginUserBean.setUser_name(tempLoginUserBean2.getUser_name());
			loginUserBean.setUserLogin(true);
		}
	}
	//회원정보수정
	public void getModifyUserInfo(UserBean modifyUserBean) {
		UserBean tempModifyUserBean = userDAO.getModifyUserInfo(loginUserBean.getUser_idx());
		modifyUserBean.setUser_id(tempModifyUserBean.getUser_id());
		modifyUserBean.setUser_name(tempModifyUserBean.getUser_name());
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
	}
	//회원정보수정
	public void modifyUserInfo(UserBean modifyUserBean) {
		modifyUserBean.setUser_idx(loginUserBean.getUser_idx());
		userDAO.modifyUserInfo(modifyUserBean);
	}
}
