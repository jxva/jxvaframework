package demo.graph;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * 读取远程图片
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-28 16:14:43 by Jxva
 */
public class PictureThief {
	public PictureThief() {
	}

	public static void main(String[] args) {
		try {
			URL url = new URL("http://images.sohu.com/uiue/sohu_logo/2005/index_logo.gif");
			java.io.BufferedInputStream bis = new BufferedInputStream(url.openStream());
			byte[] bytes = new byte[100];
			OutputStream bos = new FileOutputStream(new File("C:\\index_logo.gif"));
			int len;
			while ((len = bis.read(bytes)) > 0) {
				bos.write(bytes, 0, len);
			}
			bis.close();
			bos.flush();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}