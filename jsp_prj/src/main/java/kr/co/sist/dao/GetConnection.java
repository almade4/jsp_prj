package kr.co.sist.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class GetConnection {

	private static GetConnection gc;
	
	private GetConnection() {
	}
	
	public static GetConnection getInstance() {
		if(gc == null) {
			gc=new GetConnection();
		}
		return gc;
	}//getInstance
	
	public Connection getConn(String dbcpName) throws SQLException {
		Connection con = null;
		
		try {
		//1. JNDI(이름으로 객체를 찾는 자바의 기술) 사용객체 생성
		Context ctx=new InitialContext();

		//2. 이름으로 DBCP에 DB와 연결하고 있는 객체(javax.sql.DataSource) 얻기
		//DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/dbcp");
		DataSource ds=(DataSource)ctx.lookup("java:comp/env/jdbc/"+dbcpName);

		//3. Connection 얻기
		con=ds.getConnection();
		}catch(NamingException ne) {
			ne.printStackTrace();
		}
		
		return con;
	}//getConn
	
	public void dbClose(ResultSet rs, Statement stmt, Connection con)throws SQLException{
		
		try {
			if(rs != null) {rs.close();}//end if
			if(stmt != null) {stmt.close();}//end if
		}finally {
			if(con != null) {con.close();}//end if
		}//end finally
	}//dbClose
	
}//class
