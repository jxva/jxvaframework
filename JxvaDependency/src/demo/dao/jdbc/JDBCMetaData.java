package demo.dao.jdbc;

import java.sql.*;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;

class JDBCMetaData {
	public static void main(String args[]) {
		try {
			// Load the database driver
			// Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" ) ;

			// Get a connection to the database
			// Connection conn = DriverManager.getConnection(
			// "jdbc:odbc:Database" ) ;
			DAO dao = DAOFactory.getInstance().createDAO();
			Connection conn = dao.getJdbcTemplate().getConnection();
			// Print all warnings
			for (SQLWarning warn = conn.getWarnings(); warn != null; warn = warn
					.getNextWarning()) {
				System.out.println("SQL Warning:");
				System.out.println("State  : " + warn.getSQLState());
				System.out.println("Message: " + warn.getMessage());
				System.out.println("Error  : " + warn.getErrorCode());
			}

			// Get a statement from the connection
			Statement stmt = conn.createStatement();

			// Execute the query
			ResultSet rs = stmt.executeQuery("SELECT * FROM TBL_URL");

			// Get the metadata
			ResultSetMetaData md = rs.getMetaData();

			// Print the column labels
			for (int i = 1; i <= md.getColumnCount(); i++)
				System.out.print(md.getColumnLabel(i) + " ");
			System.out.println();

			// Loop through the result set
			while (rs.next()) {
				for (int i = 1; i <= md.getColumnCount(); i++)
					System.out.print(rs.getString(i) + " ");
				System.out.println();
			}

			// Close the result set, statement and the connection
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException se) {
			System.out.println("SQL Exception:");

			// Loop through the SQL Exceptions
			while (se != null) {
				System.out.println("State  : " + se.getSQLState());
				System.out.println("Message: " + se.getMessage());
				System.out.println("Error  : " + se.getErrorCode());

				se = se.getNextException();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static String getDatabaseInformation(Connection conn)
			throws Exception {
		DatabaseMetaData meta = conn.getMetaData();
		if (meta == null) {
			return null;
		}

		// Oracle (and some other vendors) do not
		// support some of the following methods
		// (such as getDatabaseMajorVersion() and
		// getDatabaseMajorVersion()); therefore,
		// we need to use a try-catch block.
		try {
			int majorVersion = meta.getDatabaseMajorVersion();

			int minorVersion = meta.getDatabaseMinorVersion();

			String productName = meta.getDatabaseProductName();
			String productVersion = meta.getDatabaseProductVersion();
			return "";
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("could not get the database information:"
					+ e.toString());
		}
	}
}
