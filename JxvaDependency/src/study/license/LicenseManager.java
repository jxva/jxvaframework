
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
package study.license;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;

import com.jxva.entity.Hex;
import com.jxva.entity.Path;
import com.jxva.log.Logger;

public class LicenseManager {
	
	private License jlicense;
	private static Logger	jlog=Logger.getLogger(LicenseManager.class);
	
	public LicenseManager(String licfile)
	{
		this.jlicense=new License(licfile);
	}
	
	/**
	 * 将公密从字符串转换为PublicKey型
	 * @param prikey
	 * @return PrivateKey
	 */
	public PublicKey getPublicKey(String pubkey)
	{
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Hex.decode(pubkey));
			return keyFactory.generatePublic(pubKeySpec);			
		} catch (Exception e) {
			jlog.fatal("publickey is error!");
		}
		return null;
	}
	
	public boolean isValidLicense()
	{
		try {
			Signature signCheck = Signature.getInstance("DSA");
			signCheck.initVerify(getPublicKey(PubKey.getkey()));
			signCheck.update(Params.getSignInfo().getBytes());
			if (signCheck.verify(Hex.decode(Params.getSignature()))) {
				return true;
			}
		} catch (Exception e) {
			jlog.fatal("license is unvalid!");
		}
		return false;
	}
	
	public boolean isValid()
	{
		return jlicense.isValidParams()&&isValidLicense();
	}
	
	public static void main(String args[]) {
		LicenseManager j=new LicenseManager(Path.WEB_INF_PATH+"lic/jlicense.lic");
		System.out.println(j.isValid());
	}
}
