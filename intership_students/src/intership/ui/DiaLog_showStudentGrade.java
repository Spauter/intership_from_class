package intership.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.LoginHistoryUtil;
import intership.Util.UI_Util;
import intership.dao.GradeDAO;
import utils.Utils;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DiaLog_showStudentGrade extends Dialog {
	GradeDAO gdao=new GradeDAO();
	protected Object result;
	protected Shell shell;
	private Text text;
	private String id;
	private Combo combo;
	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DiaLog_showStudentGrade(Shell parent, int style) {
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
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		shell.setBackgroundImage(null);
		shell.setImage(SWTResourceManager.getImage(DiaLog_showStudentGrade.class, "/image/OldFu.ico"));
		shell.setSize(371, 328);
		shell.setText("信息查询");
		UI_Util.centerWindowns(shell);
		id=new LoginHistoryUtil().FileReadOperator1();
		combo = new Combo(shell, SWT.NONE);
		combo.setBounds(172, 37, 114, 25);

		
		Label label = new Label(shell, SWT.NONE);
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label.setBounds(46, 40, 120, 24);
		label.setText("选择你要查看的科目：");

		text = new Text(shell, SWT.NONE);
		text.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text.setEditable(false);
		text.setBounds(172, 133, 120, 30);

		Label label_1 = new Label(shell, SWT.NONE);
		label_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_1.setBounds(106, 136, 60, 24);
		label_1.setText("您的成绩：");

		Button btnNewButton = new Button(shell, SWT.NONE);
		btnNewButton.setImage(SWTResourceManager.getImage(DiaLog_showStudentGrade.class, "/icons/progress/ani/3.png"));
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String cname=combo.getText().trim();
				String grade=UI_Util.objectToString(gdao.showGrade(cname).get(0).get("GRADE"));
				text.setText(grade);
			}
		});
		btnNewButton.setBounds(117, 202, 114, 34);
		btnNewButton.setText("查看");

		Label label_2 = new Label(shell, SWT.NONE);
		label_2.setImage(SWTResourceManager.getImage(DiaLog_StudentID.class, "/com/img/jiji.png"));
		label_2.setBounds(727, 0, 102, 154);
		
		showCourses();
	}
	
	public void showCourses() {
		combo.removeAll();
		List<Map<String, Object>> list1 = gdao.selectBySId(id);
		String[] kids = new String[list1.size()];
		try {
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> m = list1.get(i);
				kids[i] = UI_Util.objectToString(m.get("C_NAME"));
			}
			combo.setItems(kids);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(),shell);
		}
	}
}
