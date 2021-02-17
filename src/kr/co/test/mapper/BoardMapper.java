package kr.co.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import kr.co.test.beans.ContentBean;

public interface BoardMapper {
	
	@SelectKey(statement = "SELECT content_seq.nextval FROM dual", keyProperty = "content_idx", before=true, resultType=int.class)
	
	@Insert("INSERT INTO board_content(content_idx, content_subject, content_text, "
			+ "content_file, content_writer_idx, content_board_idx, content_date) "
			+"VALUES(#{content_idx}, #{content_subject}, #{content_text}, #{content_file, jdbcType=VARCHAR},"
			+" #{content_writer_idx}, #{content_board_idx}, SYSDATE)")
	void addContentInfo(ContentBean writeContentBean);
	
	@Select("SELECT board_info_name FROM board_info WHERE board_info_idx=#{board_inf_idx}")
	String getBoardInfoName(int board_info_idx);
	
	@Select("SELECT a1.content_idx, a1.content_subject, "
			+ "a2.user_name as content_writer_name, "
			+ "to_char(a1.content_date, 'YYYY-MM-DD') as content_date "
			+ "FROM board_content a1, board_user a2 "
			+ "WHERE a1.content_writer_idx = a2.user_idx "
			+ "AND a1.content_board_idx = #{board_info_idx}"
			+ "ORDER BY a1.content_idx desc")
	List<ContentBean> getContentList(int board_info_idx);
	
	@Select("SELECT a2.user_name as content_writer_name, "
			+ "to_char(a1.content_date,'YYYY-MM-DD') as content_date, "
			+ "a1.content_subject, a1.content_text, a1.content_file "
			+ "FROM board_content a1, board_user a2 "
			+ "WHERE a1.content_writer_idx = a2.user_idx "
			+ "AND content_idx=#{content_idx}")
	ContentBean getContentInfo(int content_idx);
}
