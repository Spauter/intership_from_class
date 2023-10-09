package intership.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.ImageUtil;
import intership.Util.LoginHistoryUtil;
import intership.Util.UI_Util;
import intership.dao.LookTeacherIDDao;
import utils.BizException;
import utils.Utils;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

public class DiaLog_TeacherID {
	private List<Map<String, Object>> list;
	LookTeacherIDDao ltdao = new LookTeacherIDDao();
	LoginHistoryUtil lh = new LoginHistoryUtil();
	protected Shell shell;
	private Text text_id;
	private Text text_name;
	private Text text_sex;
	private Text text_tel;
	private Text text_time;
	private Text text_profession;
	private String id;
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			DiaLog_TeacherID window = new DiaLog_TeacherID();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		id=lh.FileReadOperator();
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(DiaLog_TeacherID.class, "/image/OldFu.ico"));
		shell.setSize(600, 600);
		shell.setText("教职工信息表");
		UI_Util.centerWindowns(shell);
		shell.setBackgroundImage(SWTResourceManager.getImage(StudentMainUI.class, "/image/Background.jpg"));
		ImageUtil.fullImg(shell);
		
		Label lblNewLabel = new Label(shell, SWT.NONE);
		lblNewLabel.setImage(SWTResourceManager.getImage(DiaLog_TeacherID.class, "/image/zanwu.jpg"));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		lblNewLabel.setBounds(21, 66, 198, 217);

		Label label = new Label(shell, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		label.setBounds(60, 33, 61, 17);
		label.setText("教职工图像");

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setBounds(276, 66, 61, 17);
		label_1.setText("教职工号");
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		text_id = new Text(shell, SWT.NONE);
		text_id.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_id.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_id.setEditable(false);
		text_id.setBounds(341, 66, 150, 23);

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_2.setBounds(276, 128, 61, 17);
		label_2.setText("教师姓名");
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		text_name = new Text(shell, SWT.NONE);
		text_name.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_name.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_name.setEditable(false);
		text_name.setBounds(341, 125, 150, 23);

		Label label_3 = new Label(shell, SWT.NONE);
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_3.setBounds(276, 184, 61, 17);
		label_3.setText("性别");
		label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		text_sex = new Text(shell, SWT.NONE);
		text_sex.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_sex.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_sex.setEditable(false);
		text_sex.setBounds(341, 181, 150, 23);

		Label label_4 = new Label(shell, SWT.NONE);
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_4.setBounds(276, 239, 61, 17);
		label_4.setText("联系电话");
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		text_tel = new Text(shell, SWT.NONE);
		text_tel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_tel.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_tel.setBounds(341, 239, 97, 23);

		Label label_5 = new Label(shell, SWT.NONE);
		label_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_5.setBounds(276, 304, 61, 17);
		label_5.setText("入职时间");
		label_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		text_time = new Text(shell, SWT.NONE);
		text_time.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_time.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_time.setEditable(false);
		text_time.setBounds(341, 304, 150, 23);

		Label label_6 = new Label(shell, SWT.NONE);
		label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_6.setBounds(276, 369, 61, 17);
		label_6.setText("职位");
		label_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));

		text_profession = new Text(shell, SWT.NONE);
		text_profession.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_profession.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		text_profession.setBounds(341, 369, 72, 23);

		Label label_7 = new Label(shell, SWT.NONE);
		label_7.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_7.setBounds(60, 304, 61, 17);
		label_7.setText("双击上传图片");
		label_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		
		Label pro_hints = new Label(shell, SWT.NONE);
		pro_hints.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		pro_hints.setBounds(466, 369, 88, 17);
	
		
		Label tel_hints = new Label(shell, SWT.NONE);
		tel_hints.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		tel_hints.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		tel_hints.setBounds(452, 239, 102, 17);
		
		
		text_tel.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent e) {
				String tel=text_tel.getText();
				String id=text_id.getText();
				try {
					ltdao.updateTel(id, tel);
					tel_hints.setText("");
				}catch(Exception e1) {
					e1.printStackTrace();
					tel_hints.setText("修改失败");
					
				}
				
			}
		});
			
		text_profession.addMouseTrackListener(new MouseTrackAdapter() {
			public void mouseExit(MouseEvent e) {
				String id=text_id.getText();
				String work=text_profession.getText();
				if(work.isEmpty()||work==null) {
					pro_hints.setText("修改失败");
					return;
				}
				try {
					ltdao.updatePro(id, work);
					pro_hints.setText("");
				}catch(Exception e1) {
					e1.printStackTrace();
					pro_hints.setText("修改失败");
					
				}
				
			}
		});
		
		label_7.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDoubleClick(MouseEvent e) {
				String id=text_id.getText();
				if(id.isEmpty()||id==null){
					return;
				}
				JOptionPane.showInternalMessageDialog(null, "请选择最大150*150的图片+","提示",JOptionPane.WARNING_MESSAGE);
				FileDialog fd = new FileDialog(shell);
				fd.setFilterExtensions(new String[] { "*.jpg", "*png", "*-jpeg", "*.*" });// 后翔名交置
				String path = fd.open();
				System.out.println(path);
				if(!ImageUtil.Width_Height(path)) {
					Utils.showMsg("图片大小不符合要求", shell);
					return;
				}
				try {
					Utils.Check(path == null || path.isEmpty(), "未选择图片");
					File file = new File(path);
					file.getName();
					InputStream in = new FileInputStream(file);
					Image image = new Image(Display.getDefault(), in);
					lblNewLabel.setImage(image);
				} catch (BizException e1) {
					e1.printStackTrace(); 
					Utils.showMsg(e1.getMessage(), shell);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
					Utils.showMsg(e1.getMessage(), shell);
				}
			}
		});

		
		Button exit = new Button(shell, SWT.NONE);
		exit.setImage(SWTResourceManager.getImage(DiaLog_TeacherID.class, "/org/eclipse/jface/wizard/images/stop.png"));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_TRANSPARENT));
		exit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		exit.setBounds(276, 429, 80, 27);
		exit.setText("返回");
		

		showInfo();
		
	}

	public void showInfo() {
		// 获得登录人员姓名
		try {
			list = ltdao.select(id);
			text_id.setText(list.get(0).get("T_ID").toString());
			text_name.setText(list.get(0).get("T_NAME").toString());
			text_sex.setText(list.get(0).get("T_SEX").toString());
			text_tel.setText(list.get(0).get("T_PHONE_NUMBER").toString());
			text_profession.setText(list.get(0).get("POSITION").toString());
			text_time.setText(list.get(0).get("HIRE_DATE").toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
