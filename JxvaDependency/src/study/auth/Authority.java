package study.auth;

public class Authority {
	/**
	 * 
	 * @param userPurview 是用户具有的总权限 
	 * @param optPurview 是一个操作要求的权限为一个整数（没有经过权的！）
	 * @return
	 */
	public static boolean checkPower(long userPurview, int optPurview) 
	{ 
	   long purviewValue = (long)Math.pow(2, optPurview); 
	   return (userPurview & purviewValue) == purviewValue; 
	} 
	
	public static void main(String[] args) {
		//String[] a={"查询A","添加A","修改A","删除A","查询B","添加B","修改B","删除B","查询C","添加C","修改C","删除C"};
		//修改A,删除A,查询B=2^2+2^3+2^4=4+8+16=28
		System.out.println(checkPower(28,2));
		//查询A,删除B,修改C=2^0+2^7+2^10=1+128+1024=1153
		System.out.println(checkPower(1153,8));
		/*
		long userPurview=28;
		int optPurview=2;
		 long purviewValue = (long)Math.pow(2, optPurview); 
		 if((userPurview & purviewValue) == purviewValue) {
		  			System.out.println("true");
		}else{
			System.out.println("false");
		}
		*/
	}
}
