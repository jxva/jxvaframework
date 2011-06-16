package study.rbac;

import study.rbac.util.StringUtil;

/**
 * 权限验证系统
 * 规则: 0:有权限 1:不需要权限 2:无权限 3:未登录 4:系统出错
 * @author  The Jxva Framework Foundation
 * @date 2008-04-14 04:20
 */

public class Permission {
	
	/**
	 * 对Url资源的访问进行权限判断
	 * @param url
	 * @param userinfo
	 * @return 有权限:true 无权限:false
	 */
	public static boolean hasPermission(String url,UserInfo userinfo){
    	Integer privilegeid=Resource.getPrivilegeid(url);
    	if(privilegeid==0){
    		return true;//资源无需要验证(资源未授权到资源权限库中)
    	}else{
    		if(!userinfo.hasLogin())return false;//如果没有登录
    		if(StringUtil.addSign(userinfo.getUserdata().getUserprivileges().toString()).indexOf(StringUtil.addSign(privilegeid.toString()))>-1){
    			return true;
    		}
    	}
    	return false;
    }
}
