package study.auth;

public class Auth {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(hasRight(1, "1000"));// 单权限判断
		System.out.println(hasAndRight(new int[] { 1, 2, 4 }, "110111000")); // 多权限与操作判断
		System.out.println(hasOrRight(new int[] { 1, 2, 4 }, "0110000")); // 多权限或操作判断
		System.out.println(getRight(new int[] { 1, 3, 4, 8, 9, 12, 15, 20 })); // 通过权限ID数组得到权限串
		System.out.println(getIds("10110001100100100001")); // 通过权限串得到权限ID数组
		System.out.println(getRight(new int[] { 1, 3, 4, 8, 9, 12, 15, 20 }).equals("10110001100100100001"));
	}

	/**
	 * 通过权限串得到权限ID数组
	 * 
	 * @param right
	 *            权限串
	 * @return
	 */
	public static String getIds(String right) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < right.length(); i++) {
			if (Integer.parseInt(right.substring(i, i + 1)) == 1) {
				sb.append((i + 1) + ",");
			}
		}
		return sb.toString().substring(0, sb.toString().length() - 1);
	}

	/**
	 * 通过权限ID数组得到权限串,权限ID数组元素必须已从小到大排序
	 * 
	 * @param ids
	 * @return
	 */
	public static String getRight(int[] ids) {
		//定义一个权限最大值的数组
		char[] cs = new char[ids[ids.length - 1] + 1];
		//为字符数组cs填充字符为'0'
		java.util.Arrays.fill(cs,'0');
		//将拥有的权限设为1
		for (int i = 0; i < ids.length; i++) {
			cs[ids[i]] = '1';
		}
		return new String(cs).substring(1,cs.length);
	}

	/**
	 * 单权限判断
	 * 
	 * @param id
	 *            权限ID
	 * @param right
	 *            具备的权限串
	 * @return
	 */
	public static boolean hasRight(int id, String right) {
		return Integer.parseInt(right.substring(id - 1, id)) == 1;
	}

	/**
	 * 多权限与操作判断
	 * 
	 * @param ids
	 *            权限ID数组
	 * @param right
	 *            具备的权限串
	 * @return
	 */
	public static boolean hasAndRight(int[] ids, String right) {
		for (int i = 0; i < ids.length; i++) {
			if (Integer.parseInt(right.substring(ids[i] - 1, ids[i])) == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 多权限或操作判断
	 * 
	 * @param ids
	 *            权限ID数组
	 * @param right
	 *            具备的权限串
	 * @return
	 */
	public static boolean hasOrRight(int[] ids, String right) {
		for (int i = 0; i < ids.length; i++) {
			if (Integer.parseInt(right.substring(ids[i] - 1, ids[i])) == 1) {
				return true;
			}
		}
		return false;
	}
}
