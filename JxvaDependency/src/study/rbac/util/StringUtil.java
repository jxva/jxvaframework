package study.rbac.util;

import java.util.HashSet;
import java.util.Set;

public class StringUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] s={"3,5,6","1,2,4,2,3,4,6,2,5,7,null,2,4,7,null,null,null"};
		System.out.println(getString(s));
	}

	/**
	 * 判断两个字符串中是否包含除分隔符外相同的字符
	 * 算法优化:较长字符串固定,短字符串拆隔循环比较
	 * @param s1 比较的字符串1
	 * @param s2 比较的字符串2
	 * @return 有相同:true,无相同:false;
	 */
	public static boolean isInc(String s1,String s2){
		if(s1.length()>s2.length()){
			return compare(s1,s2);
		}else{
			return compare(s2,s1);
		}
	}
	
	/**
	 * 判断两个字符串中是否包含除分隔符外相同的字符
	 * @param s1 比较的字符串1
	 * @param s2 比较的字符串2
	 * @return 有相同:true,无相同:false;
	 */
	private static boolean compare(String s1,String s2){
		String[] sa=addSign(s2).split("\\,");
		for(int i=0;i<addSign(s1).split("\\,").length-1;i++){
			if(addSign(s1).indexOf(addSign(sa[i+1]))>-1){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 将传入的字符串数组转换成单个字符串集合(不含重复元素)
	 * @param s
	 * @return
	 */
	public static String getString(String[] s){
		String tmp=null;
		StringBuilder sb=new StringBuilder();
		for(String str:s){
			if(sb.length()>0){
				sb.append(",");
			}	
			sb.append(str);
		}
		if(sb.toString().equals("null"))return tmp;
		tmp=addSign(sb.toString()).replaceAll(",null","");//去掉,null
		
		/**
		 * int[] a={1,1,2,10,10,22,22,2,3,3,4,5,6,6,7,8,9,8}; 
			List list=new ArrayList(); 
			Arrays.sort(a); 
			for(int i=a.length-1;i>0;i--){ 
				if(a[i]!=a[i-1]){ 
					list.add(a[i]); 
				} 
			} 
			
			list.add(a[0]); 
			System.out.print("重新整理后的顺序是"); 
				for(int j=0;j<list.size();j++){ 
					System.out.print(list.get(j)+" "); 
				} 
			} 

		 */
		
		//去掉重复元素begin
		String[] array=tmp.substring(1,tmp.length()-1).split(","); 
		Set<String> set=new HashSet<String>();
		for(int i=0;i<array.length;i++){
			set.add(array[i]);
		}
		tmp=set.toString();
		return tmp.substring(1,tmp.length()-1).replaceAll(", ",",");
	}
	
	/**
	 * 为字符串首尾加入逗号分隔符
	 * @param s
	 * @return 如:,s,
	 */
	public static String addSign(String s){
		StringBuilder sb=new StringBuilder();
		sb.append(",");
		sb.append(s);
		sb.append(",");
		return sb.toString();
	}
}
