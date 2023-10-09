package intership.Util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class ImageUtil {
	static byte[] bytes;

	/**
	 * 显示消息提示窗
	 */
	public static int msg(String message, Shell parent, int... styles) {
		int s = SWT.NONE;
		for (int style : styles) {
			s = s | style;
		}
		MessageBox mb = new MessageBox(parent, s);
		mb.setText("系统提示");
		mb.setMessage(message);
		return mb.open();
	}

	/**
	 * 窗口库居中
	 */
	public static void center(Shell shell) {
		int x = Display.getCurrent().getBounds().width - shell.getBounds().width;
		int y = Display.getCurrent().getBounds().height - shell.getBounds().height;
		x *= 0.5;
		y *= 0.4;
		shell.setLocation(x, y);
	}

	/**
	 * 图片自适应控件大小
	 */
	public static void fullImg(Control c) {
		PaintListener pl = new PaintListener() {
			public void paintControl(PaintEvent e) {
				Image img;
				if (e.getSource() instanceof Label) {
					img = ((Label) e.getSource()).getImage();
				} else if (e.getSource() instanceof Button) {
					img = ((Button) e.getSource()).getImage();
				} else {
					img = c.getBackgroundImage();
				}
				if (img == null)
					return;
				double wr = (double) c.getBounds().width / (double) img.getBounds().width;
				double hr = (double) c.getBounds().height / (double) img.getBounds().height;
				double r = Math.max(wr, hr);
				int destWidth = (int) (img.getBounds().width * r);
				int destHeight = (int) (img.getBounds().height * r);
				e.gc.drawImage(img, 0, 0, img.getBounds().width, img.getBounds().height, 0, 0, destWidth, destHeight);
			}
		};
		c.addPaintListener(pl);
	}

	public static boolean Width_Height(String path) {
		int width = 0;
		int height = 0;
		try {
			File file1 = new File(path);
			BufferedImage image = ImageIO.read(file1);
			width = image.getWidth();
			height = image.getHeight();
			System.out.println("Image width: " + width + " pixels");
			System.out.println("Image height: " + height + " pixels");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return width <= 150 && height <= 150;
	}

	public static void main(String[] args) {
		File file = new File("login.dat");
		System.out.println(file.getAbsolutePath());
	}


	// 将Object对象序列化为byte[]数组
	public static byte[] serialize(Object obj) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
		objectOutputStream.writeObject(obj);
		return byteArrayOutputStream.toByteArray();
	}

	// 将byte[]数组反序列化为Object对象
	public static Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
		ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
		return objectInputStream.readObject();
	}

	public static byte[] fileToByte(File img) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			BufferedImage bi;
			bi = ImageIO.read(img);
			ImageIO.write(bi, "jpg", baos);
			bytes = baos.toByteArray();
			System.err.println(bytes.length);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			baos.close();
		}
		return bytes;
	}

	public static void ByteToFile(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		BufferedImage bi1 = ImageIO.read(bais);
		try {
			File w2 = new File("Image.jpg");// 可以是jpg,png,gif格式
			ImageIO.write(bi1, "jpg", w2);// 不管输出什么格式图片，此处不需改动
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			bais.close();
		}
	}
}
