package intership.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.ImageUtil;
import intership.Util.LoginHistoryUtil;
import intership.dao.GradeDAO;
import utils.Utils;

public class StudentMainUI {
	GradeDAO gdao=new GradeDAO();
	private String id;
	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			StudentMainUI window = new StudentMainUI();
//			window.open();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

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
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(StudentMainUI.class, "/image/OldFu.ico"));
		shell.setSize(829, 644);
		shell.setText("学生管理");
		shell.setMaximized(true);
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		id =new LoginHistoryUtil().FileReadOperator1();
		shell.setBackgroundImage(SWTResourceManager.getImage(StudentMainUI.class, "/image/ClassRoom.jpg"));
		ImageUtil.fullImg(shell);


		
		MenuItem menuItem = new MenuItem(menu, SWT.CASCADE);
		menuItem.setImage(SWTResourceManager.getImage(StudentMainUI.class, "/org/eclipse/jface/dialogs/images/popup_menu_disabled.png"));
		menuItem.setText("个人信息管理");
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 DiaLog_StudentID dsd=new  DiaLog_StudentID(shell, SWT.Close | SWT.APPLICATION_MODAL);
				 dsd.open();
			}
			});
		MenuItem menuItem_1 = new MenuItem(menu, SWT.CASCADE);
		menuItem_1.setImage(SWTResourceManager.getImage(StudentMainUI.class, "/org/eclipse/jface/dialogs/images/popup_menu_disabled.png"));
		menuItem_1.setText("选课管理");

		Menu menu_2 = new Menu(menuItem_1);
		menuItem_1.setMenu(menu_2);

		MenuItem choose = new MenuItem(menu_2, SWT.NONE);
		choose.setText("选课");
		choose.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DiaLog_ChooseCourse dsc = new DiaLog_ChooseCourse(shell, SWT.Close | SWT.APPLICATION_MODAL);
				dsc.open();
			}
		});

		MenuItem menuItem_2 = new MenuItem(menu, SWT.CASCADE);
		menuItem_2.setImage(SWTResourceManager.getImage(StudentMainUI.class, "/org/eclipse/jface/dialogs/images/popup_menu.png"));
		menuItem_2.setText("成绩查询");
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				List<Map<String,Object>>list=gdao.selectBySId(id);
				if(list.size()==0) {
					Utils.showMsg("老师未发布成绩，请耐心等待", shell);
					return;
				}
				DiaLog_showStudentGrade dssg=new DiaLog_showStudentGrade(shell, SWT.Close | SWT.APPLICATION_MODAL);
				dssg.open();
			}
		});
	}
	
}
