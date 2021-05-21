package db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import control.Ctrl;

public class UserInfoDAO {
	private HttpServletRequest request;
	private Context context;
	private DataSource dataSource;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet result;
	
	public UserInfoDAO(HttpServletRequest request) {
		this.request = request;
	}
	
	// ȸ������ ���� DB �Է�
	public int registerUserInfo(UserInfoDTO dto) {
		String sql = "";
		try {
			int check = isUserID(dto);
			if(check == Ctrl.TRUE) { // �̹� �����ϴ� ID�� ���
				return Ctrl.FALSE;
			}else if(check == Ctrl.FALSE) { // �������� �ʴ� ��� ���
				sql = "insert into ORG_USER (USER_ID, USER_PW) values (?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getUserID());
				pstmt.setString(2, dto.getUserPW());
				pstmt.executeUpdate();
				
				sql = "insert into ORG_USER_INFO values (?, ?, ?, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, dto.getUserID());
				pstmt.setString(2, dto.getUserName());
				pstmt.setString(3, dto.getUserPhone());
				pstmt.setString(4, dto.getUserEmail());
				pstmt.setString(5, dto.getUserGender());
				pstmt.executeUpdate();
				
				conn.commit();
				return Ctrl.TRUE;
			}
		}catch(Exception e){ // DB���� �� ���� �������� ���� �߻� �� �ѹ� ����
			try{
				conn.rollback();
			}catch(Exception e2){
				System.out.println("rollback Exception");
				e.printStackTrace();
			}
			
			System.out.println("DB Working Exception");
			e.printStackTrace();
		}finally{ // ���� �ڿ� ����
			dbClose();
		}
		
		// ������� ������ �Ѿ������ ���� �߻��� ���
		return Ctrl.EXCEPT;
	}
	
	// �α��� ����
	public int loginAccept() {
		try {
			dbConnect();
			String sql = "select user_ID, user_PW from ORG_USER where user_ID=?";
			String userID = request.getParameter("userID");
			String userPW = request.getParameter("userPW");
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			result = pstmt.executeQuery();
			// ������ TRUE
			result.next();
			if(result.getString("user_ID").equals(userID) && result.getString("user_PW").equals(userPW)){
				return Ctrl.TRUE;
			}
			
			
			// ������ FALSE
			return Ctrl.FALSE;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			dbClose();
		}
		
		// ������ ������� �Դٸ� ���� �߻� ��Ȳ
		return Ctrl.EXCEPT;
	}
	
	// ȸ������ ���
	public int getUserInfo() {
		String sql = "";
		UserInfoDTO dto = new UserInfoDTO();
		try {
			HttpSession session = request.getSession();
			dbConnect();
			
			sql = "select * from ORG_USER where USER_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, (String) session.getAttribute("userID"));
			result = pstmt.executeQuery();
			result.next();
			dto.setUserID(result.getString("USER_ID"));
			
			sql = "select * from ORG_USER_INFO where USER_ID = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getUserID());
			result = pstmt.executeQuery();
			result.next();
			dto.setUserName(result.getString("NAME"));
			dto.setUserEmail(result.getString("EMAIL"));
			dto.setUserPhone(result.getString("PHONE"));
			dto.setUserGender(result.getString("gender"));
			
			// ���ǿ� dto ��ü �߰�
			session.setAttribute("userInfo", dto);
			return Ctrl.TRUE;
		}catch(Exception e){
			System.out.println("userinfo DB select Fail");
			e.printStackTrace();
		}finally{
			dbClose();
		}
		
		return Ctrl.EXCEPT;
	}
	
	// ID ���� ���� Ȯ��
	private int isUserID(UserInfoDTO dto) throws Exception{
		dbConnect();
		String sql = "select user_ID from ORG_USER where user_ID = ?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, dto.getUserID());
		result = pstmt.executeQuery();
		
		if(result.next()) {
			return Ctrl.TRUE;
		}else{
			return Ctrl.FALSE;
		}
	}
	
	// DB ����
	private void dbConnect()throws Exception{
		context = new InitialContext();
		dataSource = (DataSource)context.lookup("java:comp/env/jdbc/Oracle11g");
		conn = dataSource.getConnection();
		conn.setAutoCommit(false);
	}
	
	// DB close
	private void dbClose() {
		if(result != null) {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
