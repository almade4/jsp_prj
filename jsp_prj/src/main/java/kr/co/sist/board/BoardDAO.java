package kr.co.sist.board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.sist.dao.GetConnnection;
import kr.co.sist.user.member.MemberDTO;

public class BoardDAO {
	private static BoardDAO mpDAO;
	private BoardDAO() {
	}//MyPageDAO
	
	public static BoardDAO getInstance() {
		if(mpDAO == null) {
			mpDAO=new BoardDAO();
		}//end if
		return mpDAO;
	}//getInstance
	
	public int selectTotalCount(RangeDTO rDTO)throws SQLException {
		int totalCount=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		GetConnnection gc=GetConnnection.getInstance();
		
		try {
			//커넥션얻기
			con=gc.getConn("dbcp");
			//쿼리문 수행 객체 얻기
			StringBuilder selectTotal=new StringBuilder();
			selectTotal.append("select count(*) cnt from board ");
			
			if(rDTO.getKeyword() != null &&!rDTO.getKeyword().isEmpty()) {
				//검색 키워드가 있을 때 쿼리문이 변경되어야한다.(동적쿼리의 생성기준)
				selectTotal.append("where instr(").append( rDTO.getField())
				.append(",?) != 0");
			}//end if
			 
			pstmt=con.prepareStatement(selectTotal.toString());
			//바인드 변수에 값 설정
			if(rDTO.getKeyword() != null &&!rDTO.getKeyword().isEmpty()) {
				//검색 키워드가 있을 때 바인드 변수에 값이 설정되어야한다.
				pstmt.setString(1, rDTO.getKeyword());
			}//end if
			
			//쿼리문 실행 후 결과 얻기
			rs=pstmt.executeQuery();
			if(rs.next()){
				totalCount=rs.getInt("cnt");
			}//end if
		}finally {
			gc.dbClose(rs, pstmt, con);
		}//end finally		
		
		return totalCount;
	}//selectTotalCount
	
	public List<BoardDTO> selectBoard(RangeDTO rDTO)throws SQLException {
		List<BoardDTO> boardList=new ArrayList<BoardDTO>();
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		GetConnnection gc=GetConnnection.getInstance();
		
		try {
			//커넥션얻기
			con=gc.getConn("dbcp");
			//쿼리문 수행 객체 얻기
			StringBuilder selectBoard=new StringBuilder();
			selectBoard
			.append("	select num, id, title, input_date,cnt,upfile				")
			.append("	from (select NUM, ID, TITLE, INPUT_DATE, CNT,upfile	,			")
			.append("		 	row_number() over( order by input_date desc) rnum	")
			.append("			from board											");
			
			if(rDTO.getKeyword() != null &&!rDTO.getKeyword().isEmpty()) {
				selectBoard.append(" where instr(")
				.append(rDTO.getField()).append(",?) != 0 ");
			}//end if
			
			selectBoard.append("	) where rnum between ? and ?									");
			pstmt=con.prepareStatement(selectBoard.toString());
			//바인드 변수에 값 설정
			
			int bindInd=0;
			if(rDTO.getKeyword() != null &&!rDTO.getKeyword().isEmpty()) {
				pstmt.setString(++bindInd, rDTO.getKeyword());
			}//end if
			
			pstmt.setInt(++bindInd, rDTO.getStartNum());
			pstmt.setInt(++bindInd, rDTO.getEndNum());
			
			//쿼리문 실행 후 결과 얻기
			rs=pstmt.executeQuery();
			BoardDTO bDTO=null;
			while(rs.next()){
				bDTO=new BoardDTO();
				bDTO.setNum(rs.getInt("num"));
				bDTO.setId(rs.getString("id"));
				bDTO.setTitle(rs.getString("title"));
				bDTO.setInputDate(rs.getDate("input_date"));
				bDTO.setCnt(rs.getInt("cnt"));
				bDTO.setUpfile(rs.getString("upfile"));
				
				boardList.add(bDTO);
			}//end if
		}finally {
			gc.dbClose(rs, pstmt, con);
		}//end finally		
		
		return boardList;
	}//selectBoard
	
	public BoardDTO selectBoardDetail(int num)throws SQLException {
		BoardDTO bDTO=null;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		
		GetConnnection gc=GetConnnection.getInstance();
		
		try {
			//커넥션얻기
			con=gc.getConn("dbcp");
			//쿼리문 수행 객체 얻기
			StringBuilder selectBoard=new StringBuilder();
			selectBoard
			.append("	select  id, title, content,input_date,cnt,ip	")
			.append("	from 	board								")
			.append("	where 	num=?								");
			pstmt=con.prepareStatement(selectBoard.toString());
			//바인드 변수에 값 설정
			
			pstmt.setInt(1, num);
			
			//쿼리문 실행 후 결과 얻기
			rs=pstmt.executeQuery();
			if(rs.next()){
				bDTO=new BoardDTO();
				bDTO.setNum(num);
				bDTO.setId(rs.getString("id"));
				bDTO.setTitle(rs.getString("title"));
				bDTO.setInputDate(rs.getDate("input_date"));
				bDTO.setCnt(rs.getInt("cnt"));
				bDTO.setIp(rs.getString("ip"));
				
				Clob clob=rs.getClob("content");
				StringBuilder content=new StringBuilder();
				if(clob != null) {//글의 내용이 존재
				BufferedReader br=new BufferedReader(clob.getCharacterStream());
				try {
					String temp="";
					while( (temp=br.readLine()) != null) {
						content.append(temp).append("\n");
					}//end while
					if(br != null) { br.close(); }//end if
				}catch(IOException ie) {
					ie.printStackTrace();
				}//end catch
				}//end if
				
				bDTO.setContent(content.toString());
				
			}//end if
		}finally {
			gc.dbClose(rs, pstmt, con);
		}//end finally		
		
		return bDTO;
	}//selectBoardDetail
	
	public void updateCnt(int num)throws SQLException {
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		GetConnnection gc=GetConnnection.getInstance();
		
		try {
			//커넥션얻기
			con=gc.getConn("dbcp");
			//쿼리문 수행 객체 얻기
			StringBuilder insertBoard=new StringBuilder();
			insertBoard
			.append("	update board	")
			.append("	set cnt=cnt+1 where num=?	");
			
			pstmt=con.prepareStatement(insertBoard.toString());
			//바인드 변수에 값 설정
			
			pstmt.setInt(1, num);
			
			//쿼리문 실행 후 결과 얻기
			pstmt.executeUpdate();
			
		}finally {
			gc.dbClose(null, pstmt, con);
		}//end finally		
		
	}//updateCnt
	
	public void insertBoard(BoardDTO bDTO)throws SQLException {
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		GetConnnection gc=GetConnnection.getInstance();
		
		try {
			//커넥션얻기
			con=gc.getConn("dbcp");
			
			boolean flag= bDTO.getUpfile() != null;
			//쿼리문 수행 객체 얻기
			StringBuilder insertBoard=new StringBuilder();
			insertBoard
			.append("	insert into board(num, id, title, content,ip");
			if( flag ) {
				insertBoard.append(",upfile");
			}//end if
			insertBoard.append(")	")
			.append("	values(seq_board.nextval,?,?,?,?");
			if( flag ) {
				insertBoard.append(",?");
			}//end if
			
			insertBoard.append(")");
			
			pstmt=con.prepareStatement(insertBoard.toString());
			//바인드 변수에 값 설정
			
			pstmt.setString(1, bDTO.getId());
			pstmt.setString(2, bDTO.getTitle());
			pstmt.setString(3, bDTO.getContent());
			pstmt.setString(4, bDTO.getIp());
			
			if( flag ) {
				pstmt.setString(5, bDTO.getUpfile());
			}//end if
			//쿼리문 실행 후 결과 얻기
			pstmt.executeUpdate();
			
		}finally {
			gc.dbClose(null, pstmt, con);
		}//end finally		
		
	}//insertBoard
	
	public int updateBoard(BoardDTO bDTO)throws SQLException {
		
		int cnt=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		GetConnnection gc=GetConnnection.getInstance();
		
		try {
			//커넥션얻기
			con=gc.getConn("dbcp");
			//쿼리문 수행 객체 얻기
			StringBuilder updateBoard=new StringBuilder();
			updateBoard
			.append("	update board					")
			.append("	set title=?, content=?, ip=?	")
			.append("	where	id=? and num=? 			");
			
			pstmt=con.prepareStatement(updateBoard.toString());
			//바인드 변수에 값 설정
			
			pstmt.setString(1, bDTO.getTitle());
			pstmt.setString(2, bDTO.getContent());
			pstmt.setString(3, bDTO.getIp());
			pstmt.setString(4, bDTO.getId());
			pstmt.setInt(5, bDTO.getNum());
			
			//쿼리문 실행 후 결과 얻기
			cnt=pstmt.executeUpdate();
			
		}finally {
			gc.dbClose(null, pstmt, con);
		}//end finally
		
		return cnt;
	}//updateBoard
	
	public int deleteBoard(BoardDTO bDTO)throws SQLException {
		
		int cnt=0;
		
		Connection con=null;
		PreparedStatement pstmt=null;
		
		GetConnnection gc=GetConnnection.getInstance();
		
		try {
			//커넥션얻기
			con=gc.getConn("dbcp");
			//쿼리문 수행 객체 얻기
			StringBuilder deleteBoard=new StringBuilder();
			deleteBoard
			.append("	delete from board										")
			.append("	where	id=? and num=? 	");
			
			pstmt=con.prepareStatement(deleteBoard.toString());
			//바인드 변수에 값 설정
			
			pstmt.setString(1, bDTO.getId());
			pstmt.setInt(2, bDTO.getNum());
			
			//쿼리문 실행 후 결과 얻기
			cnt=pstmt.executeUpdate();
			
		}finally {
			gc.dbClose(null, pstmt, con);
		}//end finally
		
		return cnt;
	}//deleteBoard
	
}//class
