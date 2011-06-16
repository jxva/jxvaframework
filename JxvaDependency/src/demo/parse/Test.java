package demo.parse;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sql1="SELECT au_id FROM authors WHERE au_fname = %first name% AND au_lname = %last name%";
		String sql="select user_id,username,sum(dd:dfa) from tbl_user where user_id=:userId";
		
		 String sql2 = "SELECT product_id, product_name, product_description,"
             + "list_price, min_price FROM product_information "
             + "WHERE product_name LIKE :pn_ame AND "
             + "list_price <= :price AND min_price <= :price";
		String s=NamedParameterUtils.parseSqlStatementIntoString(sql2);
		System.out.println(s);
		System.out.println(sql2.replaceAll(":[a-zA-Z0-9_]+","?"));
	}

}
