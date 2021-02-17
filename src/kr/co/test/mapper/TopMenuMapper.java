package kr.co.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import kr.co.test.beans.BoardInfoBean;

public interface TopMenuMapper {

	@Select("SELECT * FROM board_info ORDER BY board_info_idx")
	List<BoardInfoBean> getTopMenuList();
}
