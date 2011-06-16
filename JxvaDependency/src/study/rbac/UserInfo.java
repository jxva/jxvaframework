package study.rbac;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserInfo {
	
	private HttpServletRequest request;
	
	private HttpSession getSession(){
		return request.getSession(true);
	}
	
	public UserInfo(HttpServletRequest request){
		this.request=request;
	}
	
	/**
	 * 获取用户数据
	 * @return
	 */
	public UserData getUserdata(){
		if(getSession().getAttribute("userinfo")!=null){
			return (UserData)getSession().getAttribute("userinfo");
		}
		return new UserData();
	}
	
	/**
	 * 判断用户是否已经登录
	 * @return
	 */
	public boolean hasLogin(){
		return getUserdata().getUserid()==null?false:true;
	}
}
