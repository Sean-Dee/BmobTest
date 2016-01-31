package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import bmob.bson.BSONObject;

public class JDBCUtil {

	private Connection conn;
	private Statement stmt;
	@SuppressWarnings("rawtypes")
	private List bsonList;

	/**
	 * 打开 MySQL 数据库连接
	 * 
	 * @return 数据库连接对象conn
	 */
	public Connection openDBConnection() {
		// JDBC driver name and database URL
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		final String DB_URL = "jdbc:mysql://localhost/bus?zeroDateTimeBehavior=convertToNull";
//		final String DB_URL = "jdbc:mysql://localhost/bus";

		// Database credentials
		final String USER = "root";
		final String PASS = "0305";

		try {
			Class.forName(JDBC_DRIVER);
			System.out.println("Connecting to database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * 关闭 MySQL 数据库连接
	 * 
	 * @return true
	 */
	public boolean closeDBConnection() {

		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List findAll() {

		conn = openDBConnection();
		System.out.println("Creating statement...");
		bsonList = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			String sql;
			sql = "SELECT * FROM ArrivalTime";
			System.out.println(sql);
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next()) {
				// Retrieve by column name
				System.out.println(
						rs.getString("BusRouteName") + " " + rs.getInt("Position") + " " + rs.getTimestamp("Ave_Time")
								+ " " + rs.getTimestamp("Total_Time") + " " + rs.getInt("Count_Time"));
				BSONObject bson = new BSONObject();
				bson.put("BusRouteName", rs.getString("BusRouteName"));
				bson.put("Position", rs.getInt("Position"));
				bson.put("Ave_Time", rs.getTimestamp("Ave_Time"));
				bson.put("Total_Time", rs.getTimestamp("Total_Time"));
				bson.put("Count_Time", rs.getInt("Count_Time"));
				bsonList.add(bson);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeDBConnection();
		}
		return bsonList;
	}

}
