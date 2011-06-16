package study.auth;



public class User {
	/**
	 * 用户->多角色解决方案
	 * @param args
	 */
	public static void main(String[] args) {
		String multirole="finance_admin|human_user|product_user";
		String[] role_array=multirole.split("\\|");
		
		for(int i=0;i<role_array.length;i++){
				System.out.println(Right.isRight("productu_fdsa",role_array[i]));
		}
	}
}

