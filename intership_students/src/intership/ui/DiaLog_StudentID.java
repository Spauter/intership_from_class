package intership.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.ImageUtil;
import intership.Util.LoginHistoryUtil;
import intership.Util.UI_Util;
import intership.dao.LookStudentIDDao;
import utils.Utils;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class DiaLog_StudentID extends Dialog {
	private List<Map<String, Object>> slist;
	LoginHistoryUtil lh = new LoginHistoryUtil();
	LookStudentIDDao lsdao = new LookStudentIDDao();
	protected Object result;
	protected Shell shell;
	private Text text_name;
	private Text text_sex;
	private Text text_age;
	private Label lblNewLabel_3;
	private Text text_geer;
	private Label lblNewLabel_4;
	private Text text_work;
	private Label lblNewLabel_5;
	private Text text_tel;
	private Label lblNewLabel_6;
	private Text text_id;
	private Text text_class;
	private Label lblNewLabel_8;
	private Label imge;
	private Button button;
	private String id;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DiaLog_StudentID(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		id = lh.FileReadOperator1();
		
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE);
		shell.setBackgroundImage(null);
		shell.setImage(SWTResourceManager.getImage(DiaLog_StudentID.class, "/image/OldFu.ico"));
		shell.setSize(699, 697);
		shell.setText("学生个人信息管理");
		UI_Util.centerWindowns(shell);
		shell.setBackgroundImage(SWTResourceManager.getImage(StudentMainUI.class, "/image/Background.jpg"));
		ImageUtil.fullImg(shell);

		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBounds(71, 329, 54, 24);
		lblNewLabel.setText("姓名：");

		text_name = new Text(shell, SWT.NONE);
		text_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_name.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_name.setEditable(false);
		text_name.setBounds(131, 326, 161, 30);

		Label lblNewLabel_1 = new Label(shell, SWT.NONE);
		lblNewLabel_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_1.setText("性别：");
		lblNewLabel_1.setBounds(71, 399, 54, 24);

		text_sex = new Text(shell, SWT.NONE);
		text_sex.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_sex.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_sex.setEditable(false);
		text_sex.setBounds(131, 396, 161, 30);

		Label lblNewLabel_2 = new Label(shell, SWT.NONE);
		lblNewLabel_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_2.setText("年龄：");
		lblNewLabel_2.setBounds(71, 472, 54, 24);

		text_age = new Text(shell, SWT.NONE);
		text_age.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_age.setEditable(false);
		text_age.setBounds(131, 469, 161, 30);

		lblNewLabel_3 = new Label(shell, SWT.NONE);
		lblNewLabel_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_3.setText("团员：");
		lblNewLabel_3.setBounds(419, 396, 54, 24);

		text_geer = new Text(shell, SWT.BORDER);
		text_geer.setBounds(479, 393, 161, 30);

		lblNewLabel_4 = new Label(shell, SWT.NONE);
		lblNewLabel_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_4.setText("干部职位：");
		lblNewLabel_4.setBounds(384, 460, 95, 24);

		text_work = new Text(shell, SWT.BORDER);
		text_work.setBounds(479, 457, 161, 30);

		lblNewLabel_5 = new Label(shell, SWT.NONE);
		lblNewLabel_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_5.setText("联系电话：");
		lblNewLabel_5.setBounds(401, 539, 78, 24);

		text_tel = new Text(shell, SWT.BORDER);
		text_tel.setBounds(479, 536, 161, 30);

		lblNewLabel_6 = new Label(shell, SWT.NONE);
		lblNewLabel_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_6.setText("学号：");
		lblNewLabel_6.setBounds(71, 539, 54, 24);

		text_id = new Text(shell, SWT.NONE);
		text_id.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_id.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_id.setEditable(false);
		text_id.setBounds(131, 536, 161, 30);

		text_class = new Text(shell, SWT.NONE);
		text_class.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_class.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_class.setEditable(false);
		text_class.setBounds(479, 320, 161, 30);

		lblNewLabel_8 = new Label(shell, SWT.NONE);
		lblNewLabel_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		lblNewLabel_8.setText("班级：");
		lblNewLabel_8.setBounds(419, 323, 54, 24);
		imge = new Label(shell, SWT.NONE);
		imge.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		imge.setBounds(100, 10, 240, 240);
		imge.setImage(SWTResourceManager.getImage(DiaLog_StudentID.class, "/image/zanwu.jpg"));
		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_1.setBounds(10, 125, 61, 17);
		label_1.setText("自定义图像");

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				String id = text_id.getText();
				if (id.isEmpty() || id == null) {
					return;
				}
				JOptionPane.showInternalMessageDialog(null, "请选择最大150*150的图片+", "提示", JOptionPane.WARNING_MESSAGE);
				FileDialog fd = new FileDialog(shell);
				fd.setFilterExtensions(new String[] { "*.jpg", "*png", "*-jpeg", "*.*" });// 后翔名交置
				String path = fd.open();
				System.out.println(path);
				if (!ImageUtil.Width_Height(path)) {
					Utils.showMsg("图片大小不符合要求", shell);
					return;
				}
				try {
					Utils.Check(path == null || path.isEmpty(), "未选择图片");
					File file = new File(path);
					file.getName();
					InputStream in = new FileInputStream(file);
					Image image = new Image(Display.getDefault(), in);
					imge.setImage(image);
					byte[] bytes=ImageUtil.fileToByte(file);
					lsdao.updateImg(id, bytes);
				} catch (Exception e1) {
					e1.printStackTrace();
					Utils.showMsg(e1.getMessage(), shell);
				} 
			}
		});

		label_2.setBounds(151, 278, 102, 17);
		label_2.setText("双击上上传图片");

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		btnNewButton.setBounds(493, 610, 80, 27);
		btnNewButton.setText("退出");

		button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ChangePwd cp = new ChangePwd();
				cp.open();
			}
		});
		button.setBounds(384, 610, 80, 27);
		button.setText("更改密码");

		Button button_1 = new Button(shell, SWT.NONE);
		button_1.setBounds(282, 610, 80, 27);
		button_1.setText("确定修改");

		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String menber = text_geer.getText();
					String work = text_work.getText();
					String tel = text_tel.getText();
					lsdao.update(id, menber, work, tel);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		show();
	}

	public void show() {
		slist = lsdao.select(id);
		text_id.setText(id);
		try {
			text_name.setText(slist.get(0).get("S_NAME").toString());
			text_sex.setText(slist.get(0).get("S_SEX").toString());
			text_class.setText(slist.get(0).get("S_CLASS").toString());
			text_tel.setText(slist.get(0).get("S_PHONE_NUMBER").toString());
			text_geer.setText(slist.get(0).get("S_IS_MEMBER").toString());
			text_work.setText(slist.get(0).get("s_class_position".toUpperCase()).toString());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
}
