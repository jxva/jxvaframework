
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

import com.jxva.entity.Encoding;
import com.jxva.entity.Path;
import com.jxva.util.XmlUtil;
import com.jxva.xml.Element;

/**
 * 得到Xml文件中的所有标签值
 * 
 * @author Administrator
 * 
 */

public class Params {

	private static Element root = null;
	private static final String L[] = { "Development", "Enterprise",
			"Evaluation", "Personal", "Unlimited" };

	public Params(String licfile) {
		try {
			root =XmlUtil.getDocument(new File(licfile),Encoding.UTF_8).getRootElement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getLicense() {
		return root.getElement("License").getText();
	}

	public static String getCompany() {
		return root.getElement("Company").getText();
	}

	public static String getProductID() {
		return root.getElement("ProductID").getText();
	}

	public static String getSerial() {
		return root.getElement("Serial").getText().toUpperCase();
	}

	public static String getExpired() {
		return root.getElement("Expired").getText();
	}

	public static String getHostname() {
		return root.getElement("Hostname").getText();
	}

	public static String getPlateform() {
		return root.getElement("Features").getElement("Plateform").getText();
	}

	public static String getVersion() {
		return root.getElement("Features").getElement("Version").getText();
	}

	public static String getSignature() {
		return root.getElement("Signature").getText();
	}

	public static String getSignInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("License=" + getLicense() + "\n");
		sb.append("Company=" + getCompany() + "\n");
		sb.append("ProductID=" + getProductID() + "\n");
		if (isEnterprise() || isUnlimited())
			sb.append("Serial=" + getSerial() + "\n");
		sb.append("Expired=" + getExpired() + "\n");
		if (!isDevelopment() && !isUnlimited())
			sb.append("Hostname=" + getHostname() + "\n");
		sb.append("Plateform=" + getPlateform() + "\n");
		sb.append("Version=" + getVersion() + "\n");
		return sb.toString();
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

	public static void main(String args[]) {
		new Params(Path.WEB_INF_PATH + "lic/jlicense.lic");
		System.out.println(Params.getSerial());
		System.out.println(Params.getSignInfo());
		System.out.println(Params.getSignature());
	}
}
