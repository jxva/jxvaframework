package demo.log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDispenser {

	private static class ThreadLocalConnection extends ThreadLocal {

		public Object initialValue() {

			try {
				return DriverManager.getConnection("");
			} catch (SQLException e) {
				return null;
			}

		}

	}

	private static ThreadLocalConnection conn = new ThreadLocalConnection();

	public static Connection getConnection() {

		return (Connection) conn.get();

	}

}