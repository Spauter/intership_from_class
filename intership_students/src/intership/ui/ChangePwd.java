package intership.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;


import java.util.List;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.LoginHistoryUtil;
import intership.Util.PwdSafe;
import intership.Util.UI_Util;
import intership.dao.PwdDao;
import utils.Utils;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;

public class ChangePwd {

	protected Shell Login;
	private Text Text;
	private Text pwdText;
	private Label account;
	private Text pwdtext1;
	private String id;
	PwdDao idao = new PwdDao();
	Display display = Display.getDefault();
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			ChangePwd window = new ChangePwd();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/**
	 * Open the window.
	 */
	public void open() {
		
		createContents();
		Login.open();
		Login.layout();
		while (!Login.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		Login = new Shell();
		Login.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		// 窗口小图标
		Login.setImage(SWTResourceManager.getImage(ChangePwd.class, "/image/OldFu.ico"));
		// 设置窗口尺寸
		Login.setSize(423, 362);
		// 窗口标题
		Login.setText("修改您的密码");
		// 窗口居中
		UI_Util.centerWindowns(Login);
		id = new LoginHistoryUtil().FileReadOperator1();
		account = new Label(Login, SWT.NONE);
		account.setBackground(SWTResourceManager.getColor(0, 0, 0));
		account.setForeground(SWTResourceManager.getColor(255, 255, 255));
		account.setFont(SWTResourceManager.getFont("黑体", 12, SWT.NORMAL));
		account.setBounds(73, 88, 61, 17);
		account.setText("原密码");

		Label password = new Label(Login, SWT.NONE);
		password.setForeground(SWTResourceManager.getColor(255, 255, 255));
		password.setBackground(SWTResourceManager.getColor(0, 0, 0));
		password.setFont(SWTResourceManager.getFont("黑体", 12, SWT.NORMAL));
		password.setBounds(73, 149, 61, 17);
		password.setText("新密码");

		Text = new Text(Login, SWT.BORDER | SWT.PASSWORD);
		Text.setBounds(140, 88, 135, 23);

		pwdText = new Text(Login, SWT.BORDER | SWT.PASSWORD);
		pwdText.setBounds(140, 149, 135, 23);

		Button login = new Button(Login, SWT.NONE);
		login.setForeground(SWTResourceManager.getColor(SWT.COLOR_TITLE_FOREGROUND));
		login.setBounds(92, 268, 80, 27);
		login.setText("确定");

		Label Title = new Label(Login, SWT.NONE);
		Title.setForeground(SWTResourceManager.getColor(255, 255, 255));
		Title.setBackground(SWTResourceManager.getColor(0, 0, 0));
		Title.setFont(SWTResourceManager.getFont("楷体", 24, SWT.NORMAL));
		Title.setBounds(62, 28, 247, 42);
		Title.setText("修  改  密  码");

		Button exit = new Button(Login, SWT.NONE);
		exit.setBounds(214, 268, 80, 27);
		exit.setText("退出");

		Label account_1 = new Label(Login, SWT.NONE);
		account_1.setText("确认密码");
		account_1.setForeground(SWTResourceManager.getColor(255, 255, 255));
		account_1.setFont(SWTResourceManager.getFont("黑体", 12, SWT.NORMAL));
		account_1.setBackground(SWTResourceManager.getColor(0, 0, 0));
		account_1.setBounds(49, 206, 74, 17);

		pwdtext1 = new Text(Login, SWT.BORDER | SWT.PASSWORD);
		pwdtext1.setBounds(140, 204, 135, 23);

		Button low = new Button(Login, SWT.NONE);
		low.setForeground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_NORMAL_SHADOW));
		low.setEnabled(false);
		low.setBounds(281, 149, 36, 27);
		low.setText("弱");

		Button mid = new Button(Login, SWT.NONE);
		mid.setEnabled(false);
		mid.setText("中");
		mid.setBounds(323, 149, 36, 27);

		Button heigh = new Button(Login, SWT.NONE);
		heigh.setEnabled(false);
		heigh.setText("强");
		heigh.setBounds(364, 149, 36, 27);

		Label hints = new Label(Login, SWT.NONE);
		hints.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		hints.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		hints.setBounds(281, 206, 119, 17);
		hints.setText("新密码长度在6~16位");

		login.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String originPwd = Text.getText();
				String pwd = pwdText.getText();
				String pwd1 = pwdtext1.getText();
				try {
					Utils.Check( originPwd.isEmpty(), "请输入原密码");
					List<Map<String, Object>> list = idao.selectStu(id,  originPwd);
					if (list.size() == 0) {
						Utils.showMsg("原密码错误！", Login);
						return;
					}
					Utils.Check(pwd.isEmpty(), "请输入新密码");
					Utils.Check(!pwd.equals(pwd1), "输入的密码不一致");
					idao.modifyStu(id, pwd);
				} catch (Exception e1) {
					e1.printStackTrace();
					Utils.showMsg(e1.getMessage(), Login);
					return;
				}
				Utils.showMsg("修改成功", Login);
				Login.dispose();
			}
		});

		pwdText.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent e) {
				String pwd = pwdText.getText();
				try {
					if (pwd.isEmpty()) {
						hints.setText("请输入新密码");
						return;
					}
					if (pwd.length() < 6 || pwd.length() > 16) {
						hints.setText("密码长度在6~16位");
						return;
					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		});

		pwdText.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent e) {
				String pwd = pwdText.getText();
				hints.setText("");
				try {
					if (pwd.isEmpty()) {
						hints.setText("请输入新密码");
						return;
					}
					if (pwd.length() < 6 || pwd.length() > 16) {
						hints.setText("密码长度在6~16位");
						return;
					}
					new PwdSafe();
					int safe = PwdSafe.isPasswordStrong(pwd);
					switch (safe) {
					case 1: {
						low.setBackground(display.getSystemColor(SWT.COLOR_RED));
					}
						break;
					case 2: {
						mid.setBackground(new Color(display, 255, 99, 41) );
					}
						break;
					case 3: {
						mid.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
					}
						break;
					case 4: {
						heigh.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
					}
						break;
					}
				} catch (Exception e1) {

					e1.printStackTrace();
				}
			}
			
			public void mouseEnter(MouseEvent e) {
				hints.setText("");
				low.setBackground(display.getSystemColor(SWT.COLOR_TRANSPARENT));
				mid.setBackground(display.getSystemColor(SWT.COLOR_TRANSPARENT));
				heigh.setBackground(display.getSystemColor(SWT.COLOR_TRANSPARENT));
			}
		});

		exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Login.dispose();
			}
		});
	}
}
