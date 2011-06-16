package test.dao.query;

import java.util.List;

public class Test {

	/**
	 * 在我的查询条件中，如果你用的是"IN"查询，允许你传入数组或者集合进去，他会自动分析数据，构造IN的项目 
	 * @param args
	 */
	public static void main(String[] args) {
		List operationList = dao.gets(RightOperation.class,   
		    new QueryCondition()   
		     .putCondition("objectCode", permission.getRi_ob())   
	     .putCondition("code", QueryExpression.OP_IN, new String[]{"system", "right"})  
		     .addOrder("code").setPageSize(1).setPageNo(1));  

	}

}
