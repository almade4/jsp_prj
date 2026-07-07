package kr.co.sist.mypage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.sist.dao.GetConnection;
import kr.co.sist.user.member.MemberDTO;

public class MyPageDAO {

	private static MyPageDAO mpDAO;
	private MyPageDAO() {
		
	}
	public static MyPageDAO getInstance() {
		if(mpDAO == null) {
			mpDAO = new MyPageDAO();
		}
		return mpDAO;
	}
	
	public MemberDTO selectUserInfo(String id) throws SQLException{
		MemberDTO mDTO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		GetConnection gc = GetConnection.getInstance();
		
		try {
			//커넥션얻기
			con = gc.getConn("dbcp");
			
			//쿼리문 수행 객체 얻기
			String selectId="select name,email,phone,zipcode,address,address2,profile,ip,inputdate from web_member where id = ?";
			pstmt = con.prepareStatement(selectId);
			
			//바인드 변수에 값 설정
			pstmt.setString(1, id);
			
			//쿼리문 실행 후 결과 얻기
			rs = pstmt.executeQuery();
			if(rs.next()) {
				mDTO = new MemberDTO();
				mDTO.setId(id);
				mDTO.setName(rs.getString("name"));
				mDTO.setEmail(rs.getString("email"));
				mDTO.setPhone(rs.getString("phone"));
				mDTO.setZipcode(rs.getString("zipcode"));
				mDTO.setAddress(rs.getString("address"));
				mDTO.setAddress2(rs.getString("address2"));
				mDTO.setProfile(rs.getString("profile"));
				mDTO.setIp(rs.getString("ip"));
				mDTO.setInputDate(rs.getDate("inputdate"));
			}//end if
		}finally {
			gc.dbClose(rs, pstmt, con);
		}
		
		return mDTO;
	}
	
	public int updateUserProfile(String id, String profile) throws SQLException{
		int cnt = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		GetConnection gc = GetConnection.getInstance();
		
		try {
			//커넥션얻기
			con = gc.getConn("dbcp");
			
			//쿼리문 수행 객체 얻기
			String updateProfile="update web_member set profile=? where id = ?";
			pstmt = con.prepareStatement(updateProfile);
			//바인드 변수에 값 설정
			pstmt.setString(1, profile);
			pstmt.setString(2, id);
			//쿼리문 실행 후 결과 얻기
			cnt = pstmt.executeUpdate();
		}finally {
			gc.dbClose(null, pstmt, con);
		}//end finally
		
		return cnt;
	}//updateUserProfile
	
}
