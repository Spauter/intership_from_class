package intership.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.ImageUtil;
import intership.Util.LoginHistoryUtil;
import intership.Util.UI_Util;
import intership.dao.TeacherDAO;
import utils.Utils;

import org.eclipse.swt.widgets.Menu;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class TeacherMainUI {
	protected Shell shell;
	private String id;
	TeacherDAO tdao=new TeacherDAO();
//	public static void main(String[] args) {
//		try {
//			TeacherMainUI window = new TeacherMainUI();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

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

	protected void createContents() {
		Display display = Display.getDefault();
		shell = new Shell(display, SWT.SHELL_TRIM | SWT.PRIMARY_MODAL);
		shell.setImage(SWTResourceManager.getImage(TeacherMainUI.class, "/image/HT.ico"));
		shell.setSize(1280,800);
		shell.setMaximized(true);
		shell.setBackgroundImage(SWTResourceManager.getImage(StudentMainUI.class, "/image/students.jpg"));
		ImageUtil.fullImg(shell);
		shell.setText("教师，欢迎您");
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		UI_Util.centerWindowns(shell);
		shell.setMaximized(true);
		id=new LoginHistoryUtil().FileReadOperator();
		MenuItem mntmNewSubmenu = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu.setImage(SWTResourceManager.getImage(TeacherMainUI.class, "/icons/full/popup_menu_disabled.png"));
		mntmNewSubmenu.setText("个人信息");
		mntmNewSubmenu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DiaLog_TeacherID dt = new DiaLog_TeacherID();
				dt.open();
			}
		});

		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setImage(SWTResourceManager.getImage(TeacherMainUI.class, "/org/eclipse/jface/dialogs/images/popup_menu_disabled.png"));
		menuItem.setText("课表信息");
		menuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DiaLog_CourseManage dc = new DiaLog_CourseManage(shell, SWT.Close | SWT.APPLICATION_MODAL);
				try {
					List<Map<String,Object>>list=tdao.selectById(id);
					
					list.get(0).get("T_CNAME");
					dc.open();
				}catch(Exception e1) {
					e1.printStackTrace();
					Utils.showMsg("您暂无课程，请与管理员联系", shell);
				}
				
			}
		});
		MenuItem mntmNewSubmenu_1 = new MenuItem(menu, SWT.CASCADE);
		mntmNewSubmenu_1.setImage(SWTResourceManager.getImage(TeacherMainUI.class, "/org/eclipse/jface/dialogs/images/popup_menu_disabled.png"));
		mntmNewSubmenu_1.setText("学生成绩");
		mntmNewSubmenu_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DiaLog_StudentsGrade dsg = new DiaLog_StudentsGrade(shell, SWT.Close | SWT.APPLICATION_MODAL);
				dsg.open();
			}
		});

	}
}
