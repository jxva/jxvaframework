package demo.dao.jdbc;



public class TestPreparedStatementEx {

//	public static void main(String[] args) throws SQLException {
//		DAOFactory factory=DAOFactory.getInstance();
//		DAO dao=factory.createDAO();
//		
//		Connection conn = dao.getConnection();
//		String s="fdaf\"fdsa\"dsds?afd%sas";
//		String sql = "UPDATE TABLE SET A='"+s+"',B=?,C=?,D=?,E=?,F=?,G=? WHERE Z=?";
//		PreparedStatementEx ps = PreparedStatementEx.getInstance(conn, sql);
//		ps.setString(1, "Afd\"sa");
//		ps.setString(2, s);
//		ps.setDate(3, new Date(System.currentTimeMillis()));
//		ps.setInt(4,6);
//		ps.setTimestamp(5,new Timestamp(System.currentTimeMillis()));
//		ps.setObject(6,"ddd");		
//		
//		System.out.println(PreparedStatementEx.getSql(ps));
//		dao.close();
//	}

}
