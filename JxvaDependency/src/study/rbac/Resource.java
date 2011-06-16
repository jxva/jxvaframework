package study.rbac;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jxva.mvc.entity.ResType;

import study.cache.Cache;
import study.cache.CacheFactory;
import study.rbac.model.Up;

import com.jxva.dao.DAO;
import com.jxva.dao.DAOFactory;
import com.jxva.plugin.AppPlugin;

public class Resource {
	
	/**
	 * 判断是否开启Url资源授权功能
	 * @return 开启:true 关闭:false
	 */
	public static boolean isableUrl(){
		return getResType("url").getIsable();
	}
	
	/**
	 * 判断是否开启Action资源授权功能
	 * @return 开启:true 关闭:false
	 */
	public static boolean isableAction(){
		return getResType("action").getIsable();
	}
	
	/**
	 * 判断是否开启Menu资源授权功能
	 * @return 开启:true 关闭:false
	 */
	public static boolean isableMenu(){
		return getResType("menu").getIsable();
	}
	
	/**
	 * 获取资源类型
	 * @param type
	 * @return ResType
	 */
	public static ResType getResType(String type){
		return (ResType)RbacConfig.rbac.get(type);
	}
	
	/**
	 * 获取资源类型列表
	 * @return List<ResType>
	 */
	public static List<ResType> getResTypes(){
		return (List<ResType>)RbacConfig.rbac.values();
	}
	
	/**
	 * 将Url资源映射到Map中
	 * @return Map<String,Up>
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,Up> getUrlMap(){
		Map<String,Up> map=new HashMap<String,Up>();
		boolean iscache=Boolean.valueOf(AppPlugin.getPara("cache","urlmap")).booleanValue();
		if(iscache){//采用缓存
			Cache cache=CacheFactory.getInstance().createCache("dd");
			if(cache.get("cache-urlmap")==null){
				DAO dao=DAOFactory.getInstance().createDAO();
		    	List<Up> list=(List<Up>)dao.find("from Up where isclose=?",0);
		    	for(Object obj:list){
		    		Up ju=(Up)obj;
		    		map.put(ju.getUrlname(),ju);
		    	}
		    	dao.close();
		    	cache.put("cache-urlmap",map);
			}
			return (Map<String,Up>)cache.get("cache-urlmap");
		}else{//不采用缓存
			DAO dao=DAOFactory.getInstance().createDAO();
	    	List<Up> list=dao.find(Up.class);
	    	for(Object obj:list){
	    		Up ju=(Up)obj;
	    		map.put(ju.getUrlname(),ju);
	    	}
	    	dao.close();
	    	return map;
		}
	}
	
	/**
	 * 通过Url获取权限Id
	 * @param url
	 * @return privilegeid
	 */
	public static int getPrivilegeid(String url){
		if(getUrlMap().containsKey(url)){
			return ((Up)getUrlMap().get(url)).getPrivilegeid();
		}else{
			return 0;
		}
	}
}
