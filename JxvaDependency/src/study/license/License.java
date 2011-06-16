
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

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.jxva.entity.MD5;
import com.jxva.entity.Path;
import com.jxva.log.Logger;

/**
 * 得到系统中的需要得到的参数值同时与xml的参数值进行逻辑判断
 * 
 * @author Administrator
 * 
 */

public class License {
	private static Logger jlog = Logger.getLogger(License.class);

	public License(String licfile) {
		new Params(licfile);
	}

	public String getSysSerial() {
		try {
			StringBuffer sb = new StringBuffer();
			String gethost = InetAddress.getLocalHost().getHostName() + "_"
					+ InetAddress.getLocalHost().getHostAddress();
			String OriginSerial = MD5.hmac(MD5.encrypt(gethost),PubKey.getkey());
			sb.append(OriginSerial.substring(1, 6) + "-");
			sb.append(OriginSerial.substring(7, 12) + "-");
			sb.append(OriginSerial.substring(13, 18) + "-");
			sb.append(OriginSerial.substring(19, 24) + "-");
			sb.append(OriginSerial.substring(25, 30));
			return sb.toString().toUpperCase();
		} catch (Exception e) {
			jlog.trace(e);
		}
		return null;
	}

	public String getSysHostname() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (Exception e) {
			jlog.trace(e);
		}
		return null;
	}

	public boolean isExpired() {
		if (!Params.isUnlimited()) {
			long DAY = 24L * 60L * 60L * 1000L;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date d1 = new Date();
			Date d2 = null;
			try {
				d2 = df.parse(Params.getExpired());
			} catch (ParseException e) {
				jlog.trace(e);
			}
			if (((d2.getTime() - d1.getTime()) / DAY) <= 0) {
				jlog.info("License is expired!");
				return true;
			}
		}
		return false;
	}

	public boolean isValidHost() {
		if ((Params.isEvaluation() || Params.isPersonal())
				&& getSysHostname().equals(Params.getHostname())
				&& getSysHostname().equals("127.0.0.1")) {
			return true;
		} else if (Params.isEnterprise()
				&& getSysHostname().equals(Params.getHostname())) {
			return true;
		} else if (Params.isDevelopment() || Params.isUnlimited()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isValidSerial() {
		if ((Params.isEnterprise() || Params.isUnlimited())
				&& Params.getSerial().equals(getSysSerial())) {
			return true;
		} else if (Params.isDevelopment() || Params.isEvaluation()
				|| Params.isPersonal()) {
			return true;
		}
		return false;
	}

	public boolean isValidParams() {
		return !isExpired() && isValidHost() && isValidSerial();
	}

	public static void main(String args[]) {
		License jlicense = new License(Path.WEB_INF_PATH+ "lic/jlicense.lic");
		// System.out.println(JParams.getSerial());
		System.out.println(Params.getSignInfo());
		System.out.println(jlicense.isExpired());
		System.out.println(jlicense.isValidHost());
		System.out.println(jlicense.isValidParams());
	}
}