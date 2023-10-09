package intership.Util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Shell;

public class UI_Util {
	// 由于当前Util已经封装，所以把登录成功管理员的信息放在了这里
	public List<Map<String, Object>> adminLogin = null;
	public static float totalPrice;
	public static float actualPrice;
	
	public static void centerWindowns(Shell shell) {
		// 获取屏幕的大小
		Dimension demo = Toolkit.getDefaultToolkit().getScreenSize();
		shell.setLocation((demo.width - shell.getSize().x) / 2, (demo.height - shell.getSize().y) / 2);
	}
	
	public static String objectToString(Object obj) {
		if(obj==null) {
			return "";
		}else {
			return obj.toString();
		}
	}

	
	public static String orderId() {
		Date date=new Date();
		return "OldFu"+new SimpleDateFormat("yyyyMMddhhmmss").format(date);
	}
	
	
	public static String menberDate() {
		Date date=new Date();
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}
	
	
	
	/**
	 *  字符串转化为16进制字符串
	 * @param s
	 * @return
	 */
	public static String stringToHexString(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}
	
	/**
	 * 16进制字符串转化为字符串
	 * @param s
	 * @return
	 */
	public static String hexStringToString(String s) {
		if (s == null || s.equals("")) {
			return null;
		}
		s = s.replace(" ", "");
		byte[] baKeyword = new byte[s.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(s.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			s = new String(baKeyword, "gbk");
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return s;
	}
}
