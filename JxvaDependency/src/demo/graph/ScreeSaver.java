package demo.graph;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 抓取全屏
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-28 16:14:11 by Jxva
 */
public class ScreeSaver {
	public ScreeSaver() {
	}

	static void save(String path) throws Exception {
		Robot robot = new Robot();
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Rectangle rect = new Rectangle(0, 0, d.width, d.height);
		BufferedImage image = robot.createScreenCapture(rect);
		ImageIO.write(image, "jpg", new File(path));

	}

	public static void main(String[] args) {
		try {
			ScreeSaver.save("E:/test/2.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}