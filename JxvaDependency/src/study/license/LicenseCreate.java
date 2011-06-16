
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

import java.io.File;
import java.net.InetAddress;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;

import com.jxva.entity.Hex;
import com.jxva.entity.MD5;
import com.jxva.util.DateUtil;
import com.jxva.util.FileUtil;

public class LicenseCreate {

	private static final String L[] = { "Development", "Enterprise",
			"Evaluation", "Personal", "Unlimited" };
	private static String Company;
	private static String ProductID;
	private static String Plateform;
	private static String Version;
	private static String License;

	/**
	 * 设置公司名称
	 * 
	 * @return
	 */
	public static String getCompany() {
		return Company;
	}

	public static void setCompany(String company) {
		Company = company;
	}

	/**
	 * 设置产品编号
	 * 
	 * @return
	 */
	public static String getProductID() {
		return ProductID;
	}

	public static void setProductID(String productid) {
		ProductID = productid;
	}

	/**
	 * 设置平台名称
	 * 
	 * @return
	 */
	public static String getPlateform() {
		return Plateform;
	}

	public static void setPlateform(String plateform) {
		Plateform = plateform;
	}

	/**
	 * 设置平台版本
	 * 
	 * @return
	 */
	public static String getVersion() {
		return Version;
	}

	public static void setVersion(String version) {
		Version = version;
	}

	/**
	 * 设置版本名称
	 */
	public static String getLicense() {
		return License;
	}

	public static void setLicense(String license) {
		License = license;
	}

	public static boolean isDevelopment() {
		return getLicense().equals(L[0]);
	}

	public static boolean isEnterprise() {
		return getLicense().equals(L[1]);
	}

	public static boolean isEvaluation() {
		return getLicense().equals(L[2]);
	}

	public static boolean isPersonal() {
		return getLicense().equals(L[3]);
	}

	public static boolean isUnlimited() {
		return getLicense().equals(L[4]);
	}

	/**
	 * 将私钥从字符串转换为PrivateKey型
	 * 
	 * @param prikey
	 * @return PrivateKey
	 */
	public static PrivateKey getPrivateKey(String prikey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			PKCS8EncodedKeySpec priKeySpec = new PKCS8EncodedKeySpec(Hex.decode(prikey));
			return keyFactory.generatePrivate(priKeySpec);
		} catch (Exception e) {
			System.out.println("私密有误");
		}
		return null;
	}

	/**
	 * 得到服务器端的序列号
	 * 
	 * @return String
	 */
	public static String getSysSerial() {
		try {
			StringBuffer sb = new StringBuffer();
			String gethost = InetAddress.getLocalHost().getHostName() + "_"+ InetAddress.getLocalHost().getHostAddress();
			String OriginSerial = MD5.hmac(MD5.encrypt(gethost), PubKey.getkey());
			sb.append(OriginSerial.substring(1, 6) + "-");
			sb.append(OriginSerial.substring(7, 12) + "-");
			sb.append(OriginSerial.substring(13, 18) + "-");
			sb.append(OriginSerial.substring(19, 24) + "-");
			sb.append(OriginSerial.substring(25, 30));
			return sb.toString().toUpperCase();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取过期时间
	 * 
	 * @return
	 */
	public static Date getExpired() {
		long ONE_DAY=3600*24;
		if (isDevelopment()) {
			return DateUtil.getDateByOffsetSeconds(180*ONE_DAY);
		} else if (isEvaluation()) {
			return DateUtil.getDateByOffsetSeconds(30*ONE_DAY);
		} else if (isPersonal()) {
			return DateUtil.getDateByOffsetSeconds(90*ONE_DAY);
		} else {
			return DateUtil.getDateByOffsetSeconds(365*ONE_DAY);
		}
	}

	/**
	 * 获取系统主机IP地址
	 * 
	 * @return String
	 */
	public static String getHostname() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取系统计算机名称
	 * 
	 * @return String
	 */
	public static String getComputername() {
		try {
			return InetAddress.getLocalHost().getHostName();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成符合签名标准的加密数据
	 * 
	 * @return String
	 */
	public static String strLicense() {
		StringBuffer sb = new StringBuffer();
		sb.append("License=" + getLicense() + "\n");
		sb.append("Company=" + getCompany() + "\n");
		sb.append("ProductID=" + getProductID() + "\n");
		if (isEnterprise() || isUnlimited())
			sb.append("Serial=" + getSysSerial() + "\n");
		sb.append("Expired=" + getExpired() + "\n");
		if (isEvaluation() || isPersonal()) {
			sb.append("Hostname=127.0.0.1\n");
		} else if (isEnterprise()) {
			sb.append("Hostname=" + getHostname() + "\n");
		}
		sb.append("Plateform=" + getPlateform() + "\n");
		sb.append("Version=" + getVersion() + "\n");
		return sb.toString();
	}

	/**
	 * 生成xml认证文件
	 * 
	 * @param
	 * @return String
	 */
	public static String xmlLicense() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<JxvaFramework>\n");
		sb.append("	<License>" + getLicense() + "</License>\n");
		sb.append("	<Company>" + getCompany() + "</Company>\n");
		sb.append("	<ProductID>" + getProductID() + "</ProductID>\n");
		sb.append("	<Serial>" + getSysSerial() + "</Serial>\n");
		sb.append("	<Expired>" + getExpired() + "</Expired>\n");
		if (!isEnterprise()) {
			sb.append("	<Hostname>127.0.0.1</Hostname>\n");
		} else {
			sb.append("	<Hostname>" + getHostname() + "</Hostname>\n");
		}
		sb.append("	<Features>\n");
		sb.append("		<Plateform>" + getPlateform() + "</Plateform>\n");
		sb.append("		<Version>" + getVersion() + "</Version>\n");
		sb.append("	</Features>\n");
		sb.append("	<Signature>" + getSignature() + "</Signature>\n");
		sb.append("</JxvaFramework>\n");
		return sb.toString();
	}

	/**
	 * 利用私钥对数据进行签名,得到签名后的指纹
	 */
	public static String getSignature() {
		try {
			Signature signature = Signature.getInstance("DSA");
			signature.initSign(getPrivateKey(PriKey.getkey()));
			signature.update(strLicense().getBytes());
			return Hex.encode(signature.sign());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean saveLicense(String savepath) {
		if (FileUtil.write(new File(savepath), xmlLicense(), "UTF-8"))
			return true;
		return false;
	}

	/**********************************************************************/

	/**
	 * 将公密从字符串转换为PublicKey型
	 * 
	 * @param prikey
	 * @return PrivateKey
	 */
	public static PublicKey getPublicKey(String pubkey) {
		try {
			KeyFactory keyFactory = KeyFactory.getInstance("DSA");
			X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(Hex.decode(pubkey));
			return keyFactory.generatePublic(pubKeySpec);
		} catch (Exception e) {
			System.out.println("公密有误");
		}
		return null;
	}

	/**
	 * 验证公私密钥对的合法性
	 * 
	 * @param args
	 */
	public static boolean isValid() {
		try {
			Signature signCheck = Signature.getInstance("DSA");
			signCheck.initVerify(getPublicKey(PubKey.getkey()));
			signCheck.update(strLicense().getBytes());
			if (signCheck.verify(Hex.decode(getSignature()))) {
				System.out.println("合法");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String args[]) {
		System.out.println(strLicense());
		System.out.println(getSignature());
		System.out.println(xmlLicense());
		System.out.println(isValid());
	}
}