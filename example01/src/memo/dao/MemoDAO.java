package memo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import memo.dto.MemoDTO;




public class MemoDAO {
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs =null;
	
	
	
	public void connectDB() {
		try {
			String driver = "oracle.jdbc.driver.OracleDriver";
			String dbUrl = "jdbc:oracle:thin:@localhost:1521/xe";
			String dbId="example01";
			String dbPasswd = "1234";
			
			Class.forName(driver);
			
			conn = DriverManager.getConnection(dbUrl,dbId,dbPasswd);
			System.out.println("--오라클 접속 성공--");
				
			}catch(Exception e) {
				System.out.println("--오라클 접속실패--");
				e.printStackTrace();
			}
	
	}
	
	public void disconnectDB() {
		try {
			if(rs!=null) {rs.close();}
			if(pstmt!=null) {pstmt.close();}
			if(conn!=null) {conn.close();}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int insert(MemoDTO dto){
		int result=0;
		connectDB();
		
		try {
			String sql = "insert into memo values(seq_memo.nextval, ?,?, sysdate)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getName());
			pstmt.setString(2, dto.getMemo());
			result = pstmt.executeUpdate();
		} catch(Exception e) {
			e.printStackTrace();
		}finally {
			disconnectDB();
		}
		return result;

		
	}	

	
	
	
	public ArrayList<MemoDTO> getList(int startRow,int endRow) {
		ArrayList<MemoDTO> list = new ArrayList<>();
		connectDB();
		try {
			String basic_sql = "select * from memo where id>? order by id desc";
			String sql = "";
			sql +="select * from (select A.*, Rownum Rnum from ";
			sql +="("+basic_sql+") A";
			sql +=") where Rnum >= ? and Rnum <= ?";

			pstmt=conn.prepareStatement(sql);
			pstmt.setInt(1,0);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				MemoDTO dto = new MemoDTO();
				dto.setId(rs.getInt("id"));
				dto.setName(rs.getString("name"));
				dto.setMemo(rs.getString("memo"));
				dto.setRegiDate(rs.getString("regiDate"));
				list.add(dto);
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnectDB();
		}
		return list;
	}
	
	public int getTotalRecord() {
		int result=0;
		connectDB();
		try {
			String sql = "select count(*) from memo where id>?";
			
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 0);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result=rs.getInt(1);
						
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			disconnectDB();
		}
		
		return result;
	}
	
	
	
	
	
	
	
}
