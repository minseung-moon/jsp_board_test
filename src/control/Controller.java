package control;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

@WebServlet("/")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAct(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doAct(request, response);
	}

	protected void doAct(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		DataSource dataSource = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet result = null;

		try {
			// Ŀ�ؼ�Ǯ ���� ��ü ����
			Context context = new InitialContext();
			dataSource = (DataSource) context.lookup
					("java:comp/env/jdbc/Oracle11g");
		} catch (NamingException e) {
			e.printStackTrace();
		}

		try {
			
			// Ŀ�ؼ� ��ü ���
			conn = dataSource.getConnection();
			
			// ���Ĵ� DB�۾� ����
			String sql = "select * from ORG_USER";
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeQuery();

			String id, pw;
			while (result.next()) {
				id = result.getString("user_id");
				pw = result.getString("user_pw");
				System.out.println(id + " : " + pw);
			}

		} catch (SQLException e) {
			e.printStackTrace();

		// �ڿ� �ݳ� �ʼ�
		} finally {
			if (result != null)
			try {				
				result.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (pstmt != null)
			try {
				pstmt.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			if (conn != null)
			try {
				conn.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}