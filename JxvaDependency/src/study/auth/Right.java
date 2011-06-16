package study.auth;

public class Right {

	//应用系统
	//private String[] s={"系统","平台","人事系统","财务计粮","生产管理","招聘系统"};
	//角色,五种角色
	//					超级管理员		系统管理员 应用管理员  子系统管理员 子系统用户
	//private String[] r={"administrator","system","manager","*_admin","*_user"};

	/**
	 * 简单的基于角色的权限继承控制系统,适合与操作权限
	 * @param args
	 */
	public static void main(String[] args) {
			//true
			System.out.println(isRight("manager","finger_admin"));
			System.out.println(isRight("system","finger_user"));
			System.out.println(isRight("system","manager"));
			System.out.println(isRight("finger_admin","finger_user"));
			System.out.println(isRight("finger_admin","finger_admin"));
			System.out.println(isRight("administrator","finger_admin"));
			
			System.out.println("=======================================");
			//false
			System.out.println(isRight("finger_user","finger_admin"));
			System.out.println(isRight("human_admin","finger_user"));
			System.out.println(isRight("human_admin","finger_user"));
			System.out.println(isRight("human_adminfds","finger_user"));
	}
	
	/**
	 * 将角色的字符串名称转为整形等级
	 * @param l
	 * @return
	 */
	public static int getLevel(String l){
		if(l.equals("administrator")){
			return 0;
		}else if(l.equals("system")){
			return 1;
		}else if(l.equals("manager")){
			return 2;
		}else if(l.indexOf("_admin")>-1){
			return 3;
		}else{
			return 4;
		}
	}
	
	/**
	 * 
	 * @param l	用户所拥有的角色
	 * @param n 操作需要的权限
	 * @return
	 */
	public static boolean isRight(String l,String n){
		if(n.indexOf("_user")>-1){
			/**
			 * 1.当用户拥有的角色权限大于操作需要的权限;
			 * 2.当用户拥有的角色[包含]当前操作需要的权限子系统名时
			 */
			return getLevel(l)<3||l.split("_")[0].equals(n.split("_")[0]);
		}else if(n.indexOf("_admin")>-1){
			/**
			 * 1.当用户拥有的角色权限大于操作需要的权限;
			 * 2.当用户拥有的角色[等于]当前操作需要的权限子系统名时
			 */
			return getLevel(l)<3||l.equals(n);
		}else{
			return getLevel(l)<=getLevel(n);
		}
	}
}
