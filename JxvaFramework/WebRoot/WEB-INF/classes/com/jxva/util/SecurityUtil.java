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

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.jxva.entity.Hex;

/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2008-12-02 11:04:37 by Jxva
 */
public abstract class SecurityUtil {
	
	/**
	 * 将私钥从字符串转换为PrivateKey型
	 * @param privateKey
	 * @return
	 * @throws UtilException
	 */
	public static PrivateKey getPrivateKey(String privateKey) throws UtilException {
		try{
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Hex.decode(privateKey));
			return keyFactory.generatePrivate(priKeySpec);
		}catch(Exception e){
			throw new UtilException(e);
		}
	}
	
	/**
	 * 将公密从字符串转换为PublicKey型
	 * @param publicKey
	 * @return
	 * @throws UtilException
	 */
	public static PublicKey getPublicKey(String publicKey)throws UtilException  {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Hex.decode(publicKey));
			return keyFactory.generatePublic(pubKeySpec);
		}catch(Exception e){
			throw new UtilException(e);
		}
	}
	
	/**
	 * 
	 * @param publicKey
	 * @param signature
	 * @return
	 * @throws UtilException
	 */
	public static boolean checkSignature(String publicKey,String data,String signature){
		try{
			Signature _signature = Signature.getInstance("DSA");
			_signature.initVerify(getPublicKey(publicKey));
			_signature.update(data.getBytes());
			return _signature.verify(Hex.decode(signature));
		}catch(Exception e){
			return false;
		}
	}
	
	/**
	 * 利用私钥对数据进行签名,得到签名后的指纹
	 * @param privateKey
	 * @param data
	 * @return
	 * @throws UtilException
	 */
	public static String getSignature(String privateKey,String data) throws UtilException{
		try{
			Signature signature = Signature.getInstance("DSA");
			signature.initSign(getPrivateKey(privateKey));
			signature.update(data.getBytes());
			return Hex.encode(signature.sign());
		}catch(Exception e){
			throw new UtilException(e);
		}
	}
}
