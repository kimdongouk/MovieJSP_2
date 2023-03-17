package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;

public class JDBConnect {
	//MySQL 연결
	
	public Connection con = null;
	public Statement stmt = null;
	public PreparedStatement psmt = null;
	public ResultSet rs = null;
	
	public JDBConnect() {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://192.168.0.9/myhello?serverTimezone=UTC";
		String id = "newuser";
		String pwd = "wkqk1234!";
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("conn 1 성공!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public JDBConnect(String driver,String url,String id, String pwd) {
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, id, pwd);
			System.out.println("conn 2 성공!!");
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			if(rs != null) rs.close();
			if(stmt != null) stmt.close();
			if(psmt != null) psmt.close();
			if(con != null) con.close();
			System.out.println("JDBC 자원 해제");
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
