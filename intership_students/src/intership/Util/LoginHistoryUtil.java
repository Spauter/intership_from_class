package intership.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class LoginHistoryUtil {
	public void FileOutOperator(String content) {
		File file = new File("login.dat");
		FileWriter out = null;
		System.out.print(file.getAbsolutePath());
		try {
			out = new FileWriter(file);
			out.write(content);
			out.close();
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "错误\n" + e+"\n无法读出"+file.getAbsolutePath(), "系统错误", JOptionPane.ERROR_MESSAGE);

		}

	}

	public String FileReadOperator() {
		File file = new File("login.dat");
		FileReader iis = null;
		BufferedReader bfr = null;
		String content = "";
		System.out.println(file.getAbsolutePath());
		try {
			iis = new FileReader(file);
			bfr = new BufferedReader(iis);
			String info = null;
			while ((info = bfr.readLine()) != null) {
				content = info;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "错误\n" + e+"\n无法读入"+file.getAbsolutePath(), "系统错误", JOptionPane.ERROR_MESSAGE);
		}
		if (null != iis) {
			try {
				iis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	
	public void FileOutOperator1(String content) {
		File file = new File("login1.dat");
		FileWriter out = null;
		System.out.print(file.getAbsolutePath());
		try {
			out = new FileWriter(file);
			out.write(content);
			out.close();
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "错误\n" + e+"\n无法读出"+file.getAbsolutePath(), "系统错误", JOptionPane.ERROR_MESSAGE);

		}

	}

	public String FileReadOperator1() {
		File file = new File("login1.dat");
		FileReader iis = null;
		BufferedReader bfr = null;
		String content = "";
		System.out.println(file.getAbsolutePath());
		try {
			iis = new FileReader(file);
			bfr = new BufferedReader(iis);
			String info = null;
			while ((info = bfr.readLine()) != null) {
				content = info;
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "错误\n" + e+"\n无法读入"+file.getAbsolutePath(), "系统错误", JOptionPane.ERROR_MESSAGE);
		}
		if (null != iis) {
			try {
				iis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}
	
	public static void main(String[] args) throws IOException {
		File file=new File("login.dat");
		new LoginHistoryUtil().FileOutOperator("THIS FIKLE'S PATH IS :"+ file.getAbsolutePath()+"\n"+file.getCanonicalPath());
		System.out.println();
		System.out.println( new  LoginHistoryUtil().FileReadOperator());
	}

}
