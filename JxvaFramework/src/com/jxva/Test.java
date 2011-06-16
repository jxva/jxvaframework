package com.jxva;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.dao.JdbcTemplate;
import com.jxva.entity.IpTransform;

public class Test {

	private static final DAOFactory factory=DAOFactory.getInstance();
	
	/**
	 * @param args
	 * @throws SQLException 
	 */
	public static void main(String[] args) throws SQLException {
		DAO dao=factory.createDAO();
		JdbcTemplate jdbc=dao.getJdbcTemplate();
		Connection conn=jdbc.getConnection();
		PreparedStatement ps=conn.prepareStatement("select card_id,ip from tbl_sales_card");
		ResultSet rs= ps.executeQuery();
		while(rs.next()){
			int cardId=rs.getInt(1);
			Long ip=IpTransform.encode(rs.getString(2));
			PreparedStatement ps1=conn.prepareStatement("update tbl_sales_card set ip1=? where card_id=?");
			ps1.setInt(1,ip.intValue());
			ps1.setInt(2,cardId);
			ps1.executeUpdate();
			//System.out.println(rs.getInt(1));
		}		
		dao.close();

	}

}
