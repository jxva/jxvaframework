package org.jxva.dao.test;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jxva.dao.Parser;
import com.jxva.dao.Statement;
import com.jxva.dao.entity.StatementType;
import com.jxva.dao.parser.JqlParser;
import com.jxva.log.Logger;
import com.jxva.util.FileUtil;

public class BatchTest extends BaseDataSource{

	private static final Logger log=Logger.getLogger(BatchTest.class);
	private static final Parser parser=new JqlParser();
	
	public static void main(String[] args) throws SQLException {
		String txt=FileUtil.read(new File("E:/eclipse/workspace/JxvaFramework/src/org/jxva/dao/test/jql.txt"));
		String[] jqls=txt.split("\n");
		Connection conn=datasource.getConnection();
		for(String jql:jqls){
			if(jql.startsWith("--"))continue;
			log.info(jql);
			Statement statement=parser.parse(jql);
			String sql=statement.getSql();
			//log.info(sql);
			PreparedStatement ps=conn.prepareStatement(sql);
			if(statement.getStatementType()>StatementType.SELECT){
				ps.executeUpdate();
			}else{
				ResultSet rs=ps.executeQuery();
				rs.close();
			}
			ps.close();
		}
		conn.close();
	}
}
