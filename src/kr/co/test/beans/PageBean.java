package kr.co.test.beans;

public class PageBean {

	//최소페이지번호
	private int min;
	//최대페이지번호
	private int max;
	//이전버튼의 페이지번호
	private int prevPage;
	//다음버튼의 페이지번호
	private int nextPage;
	//전체페이지갯수
	private int pageCnt;
	//현재페이지번호
	private int currentPage;
	
	//contentCnt:전체글갯수, currentPage:현재글번호, contentPageCnt:페이지당글의 갯수, paginationCnt:페이지버튼의갯수
	public PageBean(int contentCnt, int currentPage, int contentPageCnt, int paginationCnt) {
		//현재페이지번호
		this.currentPage = currentPage;
		//전체페이지갯수
		pageCnt = contentCnt/contentPageCnt;
		if(contentCnt % contentPageCnt > 0) {
			pageCnt++;
		}
		min = ((currentPage-1)/paginationCnt) * paginationCnt + 1;
		max = min + paginationCnt-1;
		if(max>pageCnt) {
			max=pageCnt;
		}
		prevPage = min-1;
		nextPage = max+1;
		if(nextPage>pageCnt) {
			nextPage = pageCnt;
		}
	}
	
	public int getMin() {
		return min;
	}
	public int getMax() {
		return max;
	}
	public int getPrevPage() {
		return prevPage;
	}
	public int getNextPage() {
		return nextPage;
	}
	public int getPageCnt() {
		return pageCnt;
	}
	public int getCurrentPage() {
		return currentPage;
	}
}
