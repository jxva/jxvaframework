package demo.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.imageio.ImageIO;

public final class ImageUtils11 {
	private ImageUtils11() {
	}

	/**
	 * 从指定文件名读取图像，目前只支持读取以下格式图像：bmp,wbmp,gif,jpge,png。
	 * 
	 * @param file
	 *            文件名
	 * @return BufferedImage 图像
	 * @since 1.0
	 * 
	 * 
	 *        <pre>
	 * BufferedImage image;
	 * image = ImageUtils.readImage(new File(&quot;myImage.jpg&quot;));
	 * image = ImageUtils.readImage(new File(&quot;myImage.gif&quot;));
	 * image = ImageUtils.readImage(new File(&quot;myImage.bmp&quot;));
	 * image = ImageUtils.readImage(new File(&quot;myImage.png&quot;));
	 * </pre>
	 */
	public static BufferedImage readImage(File file) {
		BufferedImage image = null;
		if (file != null && file.isFile() && file.exists()) {
			try {
				image = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return image;
	}

	/**
	 * 从文件流读取图像，目前只支持读取以下格式图像：bmp,wbmp,gif,jpge,png。
	 * 
	 * @param input
	 *            输入文件流
	 * @return BufferedImage 图像
	 * @since 1.0
	 */
	public static BufferedImage readImage(InputStream input) {
		BufferedImage image = null;
		if (input != null) {
			try {
				image = ImageIO.read(input);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return image;
	}

	/**
	 * 从URL读取图像，目前只支持读取以下格式图像：bmp,wbmp,gif,jpge,png。
	 * 
	 * @param url
	 * @return BufferedImage 图像
	 * @since 1.0
	 */
	public static BufferedImage readImage(URL url) {
		BufferedImage image = null;
		if (url != null) {
			try {
				image = ImageIO.read(url);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}
		return image;
	}

	/**
	 * 获得指定图像的像素宽
	 * 
	 * @param image
	 *            图像
	 * @return 图像的像素宽
	 * @since 1.0
	 */
	public static int getWidth(BufferedImage image) {
		return image.getWidth();
	}

	/**
	 * 获得指定图像的像素高
	 * 
	 * @param image
	 *            图像
	 * @return 图像的像素高
	 * @since 1.0
	 */
	public static int getHeight(BufferedImage image) {
		return image.getHeight();
	}

	/**
	 * 按指定格式输出<code>BufferedImage</code>到文件out中，如果没有指定image或formatName或输出文件out,
	 * 则do nothing 。目前只支持写入以下格式图像：bmp,wbmp,jpeg,png。
	 * 
	 * @param image
	 *            图像
	 * @param formatName
	 *            格式
	 * @param out
	 *            输出文件
	 * @throws IOException
	 * @since 1.0
	 * @see com.sitechasia.webx.core.utils.image.ImageConstants.FormatName <pre>
	 * ImageUtils.writeImage(imageToSave, ImageConstants.FormatName.BMP, new File(
	 * 		&quot;newImage.bmp&quot;));
	 * ImageUtils.writeImage(imageToSave, ImageConstants.FormatName.JPEG, new File(
	 * 		&quot;newImage.jpg&quot;));
	 * ImageUtils.writeImage(imageToSave, ImageConstants.FormatName.PNG, new File(
	 * 		&quot;newImage.png&quot;));
	 * ImageUtils.writeImage(imageToSave, ImageConstants.FormatName.WBMP, new File(
	 * 		&quot;newImage.wbmp&quot;));
	 * </pre>
	 * 
	 */
	public static void writeImage(BufferedImage image, String formatName,
			File out) throws IOException {
		if (image != null && formatName != null && !"".equals(formatName)
				&& out != null) {
			ImageIO.write(image, formatName, out);
		}
	}

	/**
	 * 按指定格式输出<code>BufferedImage</code>到out中，如果没有指定image或formatName或输出流out, 则do
	 * nothing 。目前只支持写入以下格式图像：bmp,wbmp,jpeg,png。
	 * 
	 * @param image
	 *            图像
	 * @param formatName
	 *            格式
	 * @param out
	 *            输出流
	 * @throws IOException
	 * @since 1.0
	 * @see com.sitechasia.webx.core.utils.image.ImageConstants.FormatName
	 */
	public static void writeImage(BufferedImage image, String formatName,
			OutputStream out) throws IOException {
		if (image != null && formatName != null && !"".equals(formatName)
				&& out != null) {
			ImageIO.write(image, formatName, out);
		}
	}

	/**
	 * 按指定像素大小缩放图像
	 * 
	 * @param image
	 *            图像
	 * @param width
	 *            宽度像素
	 * @param heigth
	 *            高度像素
	 * @return 缩放后的图像
	 */
	public static BufferedImage zoom(BufferedImage image, int width, int heigth) {

		BufferedImage tag = new BufferedImage(width, heigth,
				BufferedImage.TYPE_INT_RGB);
		/*
		 * Image.SCALE_SMOOTH 的缩略算法 生成缩略图片的平滑度的 优先级比速度高 生成的图片质量比较好 但速度慢
		 */
		tag.getGraphics().drawImage(
				image.getScaledInstance(width, heigth, Image.SCALE_SMOOTH), 0,
				0, null);
		return tag;
	}

	/**
	 * 按指定比例缩放图像
	 * 
	 * @param image
	 *            原图像
	 * @param sx
	 *            x轴缩放比例
	 * @param sy
	 *            y轴缩放比例
	 * @return 缩放后的图像
	 */
	public static BufferedImage zoom(BufferedImage image, double sx, double sy) {

		int width = (int) (image.getWidth() * sx);
		int heigth = (int) (image.getHeight() * sy);
		return zoom(image, width, heigth);
	}

	/**
	 * 旋转 因为旋转后造成图像缺失，在旋转前需要平移一段距离
	 * 
	 * @param image
	 *            原图
	 * @param angle
	 *            旋转角度
	 * @param color
	 *            无图像部分的填充色,默认值为{255,255,255}
	 * @return
	 */
	public static BufferedImage rotate(BufferedImage image, Double angle,
			Integer[] color) {
		int width = image.getWidth();
		int height = image.getHeight();

		angle = (360 - angle);

		// 无图像部分填充的颜色默认值
		if (color == null) {
			Integer[] tempColors = { 0, 0, 0 };
			color = tempColors;
		}

		// 需要平移的距离
		double _x;
		double _y;
		if (angle <= 90) {
			_x = 0;
			_y = (width * Math.sin(angle / 180 * Math.PI));
		} else if (angle <= 180) {
			_x = (width * Math.cos(angle / 180 * Math.PI)) * (-1);
			_y = (width * Math.sin(angle / 180 * Math.PI) - height
					* Math.cos(angle / 180 * Math.PI));
		} else if (angle <= 270) {
			_x = (width * Math.cos(angle / 180 * Math.PI) + height
					* Math.sin(angle / 180 * Math.PI))
					* (-1);
			_y = (height * Math.cos(angle / 180 * Math.PI)) * (-1);
		} else {
			_x = (height * Math.sin(angle / 180 * Math.PI)) * (-1);
			_y = 0;
		}

		// 旋转后的图像大小
		int _w = (int) Math.round(width
				* Math.abs(Math.cos(angle / 180 * Math.PI)) + height
				* Math.abs(Math.sin(angle / 180 * Math.PI)));
		int _h = (int) Math.round(width
				* Math.abs(Math.sin(angle / 180 * Math.PI)) + height
				* Math.abs(Math.cos(angle / 180 * Math.PI)));

		// 平移图像
		AffineTransform affineTransform = AffineTransform.getTranslateInstance(
				_x, _y);

		// 使用_w，_h作为新图像的宽高
		BufferedImage dstImage = new BufferedImage(_w, _h, image.getType());

		// 无图像部分填充颜色
		Graphics2D biContext = dstImage.createGraphics();
		biContext.setColor(new Color(color[0], color[1], color[2]));
		biContext.fillRect(0, 0, _w, _h);
		biContext.drawImage(dstImage, 0, 0, null);

		// 旋转
		affineTransform.rotate(java.lang.Math.toRadians(360 - angle));
		AffineTransformOp affineTransformOp = new AffineTransformOp(
				affineTransform, 1);
		return affineTransformOp.filter(image, dstImage);
	}

	/**
	 * 
	 * 裁剪
	 * 
	 * @param image
	 *            原图
	 * @param x
	 *            起点坐标x
	 * @param y
	 *            起点坐标y
	 * @param destWidth
	 *            裁剪后宽度
	 * @param destHeight
	 *            裁剪后高度
	 * @return
	 */
	public static BufferedImage cut(BufferedImage image, int x, int y,
			int destWidth, int destHeight) {

		// 裁剪图像
		ImageFilter cropFilter = new CropImageFilter(x, y, destWidth,
				destHeight);
		Image destImage = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(image.getSource(), cropFilter));

		// 将destImage转换为bufferedImage
		BufferedImage dstBufferedImage = new BufferedImage(destWidth,
				destHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D biContext = dstBufferedImage.createGraphics();
		biContext.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		biContext.drawImage(destImage, 0, 0, null);

		return dstBufferedImage;
	}

}