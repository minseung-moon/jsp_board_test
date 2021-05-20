package DBConn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBConn {
	public static void main(String[] args) {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String uid = "abc";
		String upw = "1234";
		String query = "select * from ORG_USER";
		
		try {
			Class.forName(driver);
		
			connection = DriverManager.getConnection(url, uid, upw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			while (resultSet.next()) {
				String id = resultSet.getString("user_id");
				String pw = resultSet.getString("user_pw");
				System.out.println(id + " : " + pw);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (resultSet != null)
			try {				
				resultSet.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}

			if (statement != null)
			try {
				statement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			if (connection != null)
			try {
				connection.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
