
/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package study.oxm;

import java.sql.Timestamp;
import java.util.List;

public class OXMImpl implements OXM {

	public void delete(Class<? extends Object> cls, String where) {
		// TODO Auto-generated method stub
		
	}

	public void deleteByPrimaryKeys(Object obj) {
		// TODO Auto-generated method stub
		
	}

	public List<Object> find(Class<? extends Object> cls, String where) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object findByPrimaryKeys(Object obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> findOrderBy(Class<? extends Object> cls, String where,
			String orderby) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getAutoId(Class<? extends Object> cls) {
		// TODO Auto-generated method stub
		return null;
	}

	public java.util.Date getDate() {
		// TODO Auto-generated method stub
		return null;
	}

	public Timestamp getTime() {
		// TODO Auto-generated method stub
		return null;
	}

	public void insert(Object obj) {
		// TODO Auto-generated method stub
		
	}

	public void update(Object obj, String where) {
		// TODO Auto-generated method stub
		
	}

	public void updateByPrimaryKeys(Object obj) {
		// TODO Auto-generated method stub
		
	}

	protected String xmlpath;
	
	/**

	private JXml jxml = null;
	private Document doc = null;
	
	public OXMImpl() {
		jxml = JXml.getInstance();
	}

	
	public void insert(Object obj){
		try{
			Class<? extends Object> voClass = obj.getClass();
			doc = jxml.getDocument(getXmlFile(voClass));
			Element root = doc.getRootElement();
			root.attribute("auto").setValue(String.valueOf(Integer.parseInt(root.attributeValue("auto")) + 1));
			List<Field> fs = Reflect.getAllFields(voClass);
			Element element = root.addElement("rs").addAttribute("msgid", root.attributeValue("auto"));
			for (int i = 0; i < fs.size(); i++) {
				String name = fs.get(i).getName();
				if (name.equalsIgnoreCase(root.attributeValue("autonode")))continue;
				Object value = voClass.getMethod(Reflect.getGetterName(name), new Class[] {}).invoke(obj, new Object[] {});
				element.addElement(name).addAttribute("type",fs.get(i).getType().getName()).setText(value==null?"":value.toString());
			}
			jxml.saveXml(getXmlFile(voClass), doc);
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
	}
	
	public void deleteByPrimaryKeys(Object obj){
		try{
			Class<? extends Object> voClass = obj.getClass();
			doc = jxml.getDocument(getXmlFile(voClass));
			String msgid = voClass.getMethod("getMsgid", new Class[] {}).invoke(obj, new Object[] {}).toString();
			Element el = (Element) doc.selectSingleNode("//root/rs[@msgid='" + msgid + "']");
			doc.getRootElement().remove(el);
			jxml.saveXml(getXmlFile(voClass), doc);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	
	public void delete(Class<? extends Object> cls, String where) {
		try{
			doc = jxml.getDocument(getXmlFile(cls));
			where=where.replaceAll("msgid", "@msgid");
			where=where.replaceAll("null","''");  
			List<?> list=doc.selectNodes("//root/rs["+where+"]");
			for(int j=0;j<list.size();j++){
				doc.getRootElement().remove((Element)list.get(j));
			}
			jxml.saveXml(getXmlFile(cls), doc);
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
	}

	public void updateByPrimaryKeys(Object obj){
		try{
			Class<? extends Object> voClass = obj.getClass();
			doc = jxml.getDocument(getXmlFile(voClass));
			List<Field> fs = Reflect.getAllFields(voClass);
			Method method = voClass.getMethod("getMsgid", new Class[] {});
			String msgid = method.invoke(obj, new Object[] {}).toString();
			Element el = (Element) doc.selectSingleNode("//root/rs[@msgid='"+ msgid + "']");
			for (int i = 0; i < fs.size(); i++) {
				String name = fs.get(i).getName();
				Object value = voClass.getMethod(Reflect.getGetterName(name), new Class[] {}).invoke(obj, new Object[] {});
				if (name.equals("msgid")|| value == null)
					continue;
				el.element(name).setText(value.toString());
			}
			jxml.saveXml(getXmlFile(voClass), doc);
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
	}

	public void update(Object obj, String where){
		try{
			Class<? extends Object> voClass = obj.getClass();
			doc = jxml.getDocument(getXmlFile(voClass));
			List<Field> fs = Reflect.getAllFields(voClass);
			where=where.replaceAll("msgid", "@msgid");
			where=where.replaceAll("null","''"); 
			List<?> list=doc.selectNodes("//root/rs["+where+"]");
			for(int j=0;j<list.size();j++){
				Element el=(Element)list.get(j);
				for (int i = 0; i < fs.size(); i++) {
					String name = fs.get(i).getName();
					Object value =  voClass.getMethod(Reflect.getGetterName(name), new Class[] {}).invoke(obj, new Object[] {});
					if (name.equals("msgid")|| value == null)
						continue;
					el.element(name).setText(value.toString());
				}
			}
			jxml.saveXml(getXmlFile(voClass), doc);
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
	}


	

	public Object findByPrimaryKeys(Object obj){
		try{
			Class<? extends Object> voClass = obj.getClass();
			Object object = obj.getClass().newInstance();
			List<Field> fs = Reflect.getAllFields(voClass);
			doc = jxml.getDocument(getXmlFile(voClass));
			
			Method methInstance = voClass.getMethod("getMsgid", new Class[] {});
			String msgid = methInstance.invoke(obj, new Object[] {}).toString();
			Element el = (Element) doc.selectSingleNode("//root/rs[@msgid='" + msgid + "']");
			
			for (int i = 0; i < fs.size(); i++) {
	
				String name = fs.get(i).getName();
				String type = fs.get(i).getType().getName();
	
				String value =name!= "msgid"?el.elementText(name):msgid;
				if (value == null || (value.equals("") && !type.equals("java.lang.String"))) {
					continue;
				}
				if (type.equals("java.lang.String")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.lang.String.class }).invoke(object,	new Object[] { value });
				} else if (type.equals("java.lang.Integer")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.lang.Integer.class }).invoke(object, new Object[] { Integer.valueOf(value) });
				} else if (type.equals("java.lang.Short")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.lang.Short.class }).invoke(object,new Object[] { Short.valueOf(value) });
				} else if (type.equals("java.lang.Long")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.lang.Long.class }).invoke(object,new Object[] { Long.valueOf(value) });
				} else if (type.equals("java.sql.Date")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.sql.Date.class }).invoke(object,new Object[] { Date.valueOf(value) });
				} else if (type.equals("java.lang.Double")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.lang.Double.class }).invoke(object,	new Object[] { Double.valueOf(value) });
				} else if (type.equals("java.sql.Timestamp")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.sql.Timestamp.class }).invoke(object, new Object[] { Timestamp.valueOf(value) });
				} else if (type.equals("java.sql.Time")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.sql.Time.class }).invoke(object,new Object[] { Time.valueOf(value) });
				} else if (type.equals("java.lang.Character")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.lang.Character.class }).invoke(	object,	new Object[] { Character.valueOf(value.charAt(0)) });
				} else if (type.equals("java.math.BigDecimal")) {
					BeanUtil.getSetter(voClass, name,new Class[] { java.math.BigDecimal.class }).invoke(object,	new Object[] { new java.math.BigDecimal(value) });
				}
			}
			return object;
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
		return null;
	}
	
	public List<Object> findOrderBy(Class<? extends Object> cls,String where,String orderby){
		List<Object> objects=new ArrayList<Object>();
		try{
			objects=find(cls,where);
			if (!orderby.equals("")){
				orderby=orderby.trim();
				String str[]=orderby.split(" ");
				if (str.length<2){
					OXMSort.sort(objects,orderby,"asc");}
				else
					OXMSort.sort(objects,str[0],str[1]);
				
			}
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
		return objects;
	}
	
	
	public List<Object> find(Class<? extends Object> cls,String where){
		List<Object> objects=new ArrayList<Object>();
		try{
			where=(where==null||where==""?"msgid>0":where);
			List<Field> fs = Reflect.getAllFields(cls);
			doc = jxml.getDocument(getXmlFile(cls));
			where=where.replaceAll("msgid", "@msgid");
			where=where.replaceAll("null","''");  
			List<?> list=doc.selectNodes("//root/rs["+where+"]");
			for(int j=0;j<list.size();j++){
				Element el=(Element)list.get(j);
				Object object = cls.newInstance();
				for (int i = 0; i < fs.size(); i++) {
					String name = fs.get(i).getName();
					String type = fs.get(i).getType().getName();
	
					String value =name!= "msgid"?el.elementText(name):el.attributeValue("msgid");
					if (value == null|| (value.equals("") && !type.equals("java.lang.String"))) {
						continue;
					}
					if (type.equals("java.lang.String")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.lang.String.class }).invoke(object,	new Object[] { value });
					} else if (type.equals("java.lang.Integer")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.lang.Integer.class }).invoke(object, new Object[] { Integer.valueOf(value) });
					} else if (type.equals("java.lang.Short")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.lang.Short.class }).invoke(object,new Object[] { Short.valueOf(value) });
					} else if (type.equals("java.lang.Long")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.lang.Long.class }).invoke(object,new Object[] { Long.valueOf(value) });
					} else if (type.equals("java.sql.Date")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.sql.Date.class }).invoke(object,new Object[] { Date.valueOf(value) });
					} else if (type.equals("java.lang.Double")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.lang.Double.class }).invoke(object,	new Object[] { Double.valueOf(value) });
					} else if (type.equals("java.sql.Timestamp")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.sql.Timestamp.class }).invoke(object, new Object[] { Timestamp.valueOf(value) });
					} else if (type.equals("java.sql.Time")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.sql.Time.class }).invoke(object,new Object[] { Time.valueOf(value) });
					} else if (type.equals("java.lang.Character")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.lang.Character.class }).invoke(	object,	new Object[] { Character.valueOf(value.charAt(0)) });
					} else if (type.equals("java.math.BigDecimal")) {
						BeanUtil.getSetter(cls, name,new Class[] { java.math.BigDecimal.class }).invoke(object,	new Object[] { new java.math.BigDecimal(value) });
					}
					
				}
				objects.add(object);
			}		
		}catch(Exception e){
			OXMException.getStackTrace(e);
		}
		return objects;
	}
	
	public Long getAutoId(Class<? extends Object> cls){
		try{
			doc = jxml.getDocument(getXmlFile(cls));
			return Long.parseLong(doc.getRootElement().attributeValue("auto")) + 1;
		}catch(Exception e){
			OXMException.getStackTrace(e);
			return Long.parseLong("1");
		}
	}
	
	public Date getDate() {
		return new java.sql.Date(new java.util.Date().getTime());
	}

	public Timestamp getTime() {
		java.text.DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return Timestamp.valueOf(df.format(new java.util.Date()));
	}
	
	private String getXmlFile(Class<? extends Object> cls) {
		StringBuilder sb=new StringBuilder();
		sb.append(xmlpath);
		sb.append("/");
		sb.append(cls.getSimpleName());
		sb.append(".xml");
		return sb.toString();
	}
	
	**/
}

