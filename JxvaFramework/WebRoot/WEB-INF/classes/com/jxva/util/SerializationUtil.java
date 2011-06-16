/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
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
 */
package com.jxva.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.jxva.entity.Encoding;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:26:40 by Jxva
 */
public abstract class SerializationUtil {
		
    /**
     * 深度拷贝对象
     * @param object
     * @return
     */
    public static Object depthClone(Serializable obj){
        return deserialize(serialize(obj));
    }
	
    /**
     * 序列化对象到字符串
     * @param obj 对象
     * @return 字符串
     * @throws UtilException
     */
	public static String serialize(Serializable obj)throws UtilException{
		if(obj==null)return null;
		ObjectOutputStream oos=null;
		try{
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			oos=new ObjectOutputStream(baos);
			oos.writeObject(obj);
			oos.flush();
			return baos.toString(Encoding.ISO_8859_1);
		}catch(Exception e){
			throw new UtilException(e);
		}finally{
			try {if(oos!=null)oos.close();} catch (IOException e) {}
		}
	}
	
	/**
	 * 将字符串反序列化为对象
	 * @param str 字符串
	 * @return 对象
	 * @throws UtilException
	 */
	public static Object deserialize(String str)throws UtilException{
		if(str==null)return null;
		ObjectInputStream ois=null;
		try{
			ois=new ObjectInputStream(new ByteArrayInputStream(str.getBytes(Encoding.ISO_8859_1)));
			return ois.readObject();
		}catch(Exception e){
			throw new UtilException(e);
		}finally{
			try {if(ois!=null)ois.close();} catch (IOException e) {}
		}
	}

}