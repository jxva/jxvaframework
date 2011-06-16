package study.rbac;

import java.util.HashMap;
import java.util.Map;

import org.jxva.mvc.entity.ResType;

import com.jxva.plugin.PluginException;
import com.jxva.plugin.Pluginable;

public class RbacConfig implements Pluginable {

	
	public static Map<String,ResType> rbac=new HashMap<String,ResType>();
	public void initialize()  throws PluginException{
		
      	
      	/**
		Element root = new DomUtil(Path.getJxvaHome()+"conf/sys.xml").getDomcument().getRootElement();
		//rbac系统资源配置
      	Element rbacs=root.getElement("rbac");
      	List<?> restypes=rbacs.elements("restype");
      	for(int i=0;i<restypes.size();i++){
      		Element res=(Element)restypes.get(i);
      		ResType rt=new ResType();
      		rt.setIsable(Boolean.valueOf(res.getText()));
      		rt.setRestype(res.attributeValue("type"));
      		rt.setRestypeid(Integer.valueOf(res.attributeValue("typeid")));
      		rbac.put(res.attributeValue("type"),rt);
      	}
		*/
	}
	/* (non-Javadoc)
	 * @see com.jxva.plugin.Pluginable#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}
