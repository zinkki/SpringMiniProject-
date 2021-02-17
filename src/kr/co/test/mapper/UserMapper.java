package kr.co.test.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import kr.co.test.beans.UserBean;

public interface UserMapper {

	@Select("SELECT user_name FROM board_user WHERE user_id = #{user_id}")
	String checkUserIdExist(String user_id);
	
	@Insert("INSERT INTO board_user(user_idx,user_name,user_id,user_pw) "
			+ "VALUES(user_seq.nextval, #{user_name}, #{user_id}, #{user_pw})")
	void addUserInfo(UserBean joinUserBean);
	
	@Select("SELECT user_idx, user_name "
			+ "FROM board_user "
			+ "WHERE user_id=#{user_id} AND user_pw=#{user_pw}")
	UserBean getLoginUserInfo(UserBean tempLoginUserBean);
	
	@Select("SELECT user_id, user_name FROM board_user "
			+ "WHERE user_idx = #{user_idx}")
	UserBean getModifyUserInfo(int user_idx);
	
	@Update("UPDATE board_user SET user_pw=#{user_pw} WHERE user_idx=#{user_idx}")
	void modifyUserInfo(UserBean modifyUserBean);
}
