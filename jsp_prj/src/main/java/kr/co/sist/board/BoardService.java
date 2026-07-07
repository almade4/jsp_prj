package kr.co.sist.board;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.json.simple.JSONObject;

import kr.co.sist.chipher.DataDecryption;
import kr.co.sist.user.member.MemberDTO;

public class BoardService {
	private int totalCount;
	private int pageScale;
	/**
	 * 게시글의 총 레코드
	 * @return
	 */
	public int totalCount( RangeDTO rDTO) {
		int cnt=0;
		BoardDAO bDAO=BoardDAO.getInstance();
		try {
			cnt=bDAO.selectTotalCount(rDTO);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		totalCount=cnt;
		return cnt;			
	}//totalCount
	
	/**
	 * 한 화면에 보여질 게시글의 수
	 * @return 
	 */
	public int pageScale() {
		pageScale=10;
		return pageScale;
	}
	
	/**
	 *  총 페이지 수
	 * @param totalCount 총 게시글 수
	 * @param pageScale 한화면에 보여질 게시글의 수
	 * @return
	 */
	public int totalPage(int totalCount,int pageScale) {
		int totalPage=(int)Math.ceil( (double)totalCount/pageScale);
		return totalPage;
	}
	
	/**
	 * 현제 페이지를 입력받아서 정수로 변환 후 반환.
	 * @param tempPage
	 * @return
	 */
	public int currentPage(String tempPage) {
		int currentPage=1;
		if( tempPage != null){ //pagination을 클릭했을 때 1,2,3,4  해당 페이지 번호가 입력
			currentPage=Integer.parseInt(tempPage);
		}//end if
		return currentPage;
	}//currentPage
	
	/**
	 * 조회할 글의 시작번호 
	 * @param currentPage 현재페이지
	 * @param pageScale 한화면에 보여질 게시글의 수
	 * @return
	 */
	public int startNum(int currentPage, int pageScale) {
		int startNum= currentPage * pageScale-pageScale+1;
		return startNum;
	}//startNum
	
	/**
	 * 조회할 글의 끝번호
	 * @param startNum 시작번호
	 * @param pageScale 한화면에 보여질 게시글의 수
	 * @return
	 */
	public int endNum(int startNum,int pageScale ) {
		int endNum=startNum+pageScale-1;
		return endNum;
	}//endNum
	
	/**
	 * 시작번호와 끝번호, 검색키워드, 검색필드를 받아서 해당 시작번호와 끝번호 사이의 
	 * 글을 검색하는 일
	 * @param rDTO 시작번호, 끝번호, 검색키워드, 검색필드
	 * @return
	 */
	public List<BoardDTO> searchBoard(RangeDTO rDTO){
		 List<BoardDTO> listBoard=null;
		 BoardDAO bDAO=BoardDAO.getInstance();
		 try {
			 listBoard=bDAO.selectBoard( rDTO);
		 } catch (SQLException e) {
			 e.printStackTrace();
		 }//end catch
		 
		 return listBoard;
	}//searchBoard
	
	public BoardDTO searchBoardDetail(int num){
		BoardDTO bDTO=null;
		BoardDAO bDAO=BoardDAO.getInstance();
		try {
			//글 번호에 해당하는 글을 읽고
			bDTO=bDAO.selectBoardDetail( num );
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
		return bDTO;
	}//searchBoardDetail
	
	public void modifyCount(int num){
		BoardDAO bDAO=BoardDAO.getInstance();
		try {
			//카운트 1 증가 시킨다.
			bDAO.updateCnt(num);
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		
	}//modifyCount
	
	
	
	public boolean addBoard(BoardDTO bDTO) {
		boolean flag=false;
		
		BoardDAO bDAO=BoardDAO.getInstance();
		try {
			bDAO.insertBoard( bDTO);
			flag=true;
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		return flag;
	}//addBoard
	
	public boolean modifyBoard(BoardDTO bDTO) {
		boolean flag=false;
		
		BoardDAO bDAO=BoardDAO.getInstance();
		try {
			flag=bDAO.updateBoard( bDTO)==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		return flag;
	}//modifyBoard
	
	public boolean removeBoard(BoardDTO bDTO) {
		boolean flag=false;
		
		BoardDAO bDAO=BoardDAO.getInstance();
		try {
			flag=bDAO.deleteBoard( bDTO)==1;
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
		return flag;
	}//removeBoard
	
}//class
