package intership.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Shell;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import intership.Util.LoginHistoryUtil;

import intership.Util.UI_Util;
import intership.dao.ChooseCourseDao;
import intership.dao.ImCourseDAO;
import intership.dao.TeacherDAO;
import intership.dao.WatchCourseDao;
import utils.Utils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

public class DiaLog_CourseManage extends Dialog {
	LoginHistoryUtil lh = new LoginHistoryUtil();
	WatchCourseDao wdao = new WatchCourseDao();
	TeacherDAO tdao = new TeacherDAO();
	protected Object result;
	protected Shell shell;
	private String id;
	private String tname;
	private List<Map<String, Object>> list;
	private Table table;
	private Text text;
	private Combo combo;
	private Text text_1;
	private Button button_2;
	private Text text_2;
	private String c_name;
	ChooseCourseDao ccd = new ChooseCourseDao();

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DiaLog_CourseManage(Shell parent, int style) {
		super(parent, style);
		setText("课程管理");
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
		id = lh.FileReadOperator();
		List<Map<String,Object>>l=tdao.selectById(id);
		tname = (l.get(0).get("T_NAME").toString());
		c_name=(l.get(0).get("T_CNAME").toString());
		String c_id=new ImCourseDAO().selectByName(c_name).get(0).get("C_ID").toString();
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE);
		shell.setImage(SWTResourceManager.getImage(DiaLog_CourseManage.class, "/image/OldFu.ico"));
		shell.setBackgroundImage(null);
		shell.setSize(866, 670);
		shell.setText("课表管理");
		UI_Util.centerWindowns(shell);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		list = wdao.showAllChoosed(tname);
		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);

		Composite composite = new Composite(sashForm, SWT.NONE);

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setText("请输入姓名");
		label_1.setBounds(283, 34, 72, 24);

		text = new Text(composite, SWT.BORDER);
		text.setBounds(361, 28, 155, 30);

		Button button = new Button(composite, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String sname = text.getText();
				if (sname.isEmpty() || sname == null) {
					Utils.showMsg("请输名字", shell);
				}
				list = wdao.showStudentID(sname);
				showCourse();
			}
		});
		button.setText("查询");
		button.setBounds(533, 24, 114, 34);

		combo = new Combo(composite, SWT.NONE);
		combo.setBounds(653, 28, 88, 25);
		Button button_1 = new Button(composite, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String Class = combo.getText().trim();
				if ("全部班级".equals(Class)) {
					list = wdao.showAllChoosed(tname);
				} else {
					list = wdao.selectStudentsClass(Class);
				}
				showCourse();
			}
		});
		button_1.setBounds(747, 26, 80, 27);
		button_1.setText("筛选");

		Label label = new Label(composite, SWT.NONE);
		label.setBounds(41, 32, 61, 17);
		label.setText("教师");

		text_1 = new Text(composite, SWT.NONE);
		text_1.setEditable(false);
		text_1.setBounds(108, 32, 155, 23);
		text_1.setText(tname);
		Composite composite_1 = new Composite(sashForm, SWT.NONE);

		table = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(25, 10, 708, 457);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(121);
		tblclmnNewColumn.setText("学号");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(116);
		tblclmnNewColumn_1.setText("姓名");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("班级");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("联系电话");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("备注");

		button_2 = new Button(composite_1, SWT.CHECK);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				int choice = JOptionPane.showConfirmDialog(null, "是否开课？一旦选择无法更改", "提示", JOptionPane.YES_NO_OPTION);
				if (choice == 1) {
					button_2.setSelection(false);
					return;
				} else {
					isOpen();
				}
			}

		});
		button_2.setFont(SWTResourceManager.getFont("Microsoft YaHei UI", 11, SWT.NORMAL));
		button_2.setBounds(703, 505, 129, 39);
		button_2.setText("未开课");

		Composite composite_2 = new Composite(sashForm, SWT.NONE);

		Label button_3 = new Label(composite_2, SWT.NONE);
		button_3.setBounds(10, 10, 66, 23);
		button_3.setText("手动添加");

		text_2 = new Text(composite_2, SWT.BORDER);
		text_2.setBounds(75, 10, 141, 23);

		Button button_4 = new Button(composite_2, SWT.NONE);
		button_4.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String s_id = text_2.getText();
				String t_name = text_1.getText();
				try {
					List<Map<String, Object>> list=new WatchCourseDao().select(s_id);
					if (list.size() == 0) {
						Utils.showMsg("你输入的学号不存在", shell);
						return;
					}
					String s_name = list.get(0).get("S_NAME").toString();
					String s_class = list.get(0).get("S_CLASS").toString();
					int result = JOptionPane.showConfirmDialog(null, "是否添加" + s_class + " " + s_name + " 的学生?", "提示",
							JOptionPane.YES_NO_OPTION);
					if (result == 1) {
						return;
					}
					if (ccd.isSelected(s_id, c_id)) {
						Utils.showMsg("该学生已选此课程", shell);
						return;
					}
					int i = ccd.countChoosed(id);
					if (i >= 2) {
						Utils.showMsg("该学生已经达到最大选课数量", shell);
						return;
					}
					int j = ccd.countChoosed1(t_name);
					if (j >= 10) {
						Utils.showMsg("您的班级已经达到最大选课人数", shell);
						return;
					}
					ccd.insert(s_id, c_id, t_name);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				text_2.setText("");
				showCourse();
			}
		});
		button_4.setBounds(231, 10, 80, 27);
		button_4.setText("查询");
		sashForm.setWeights(new int[] { 77, 554, 77 });

		showTypes();
		showCourse();
	}

	public void showCourse() {
		try {
			TableItem tabelItem = null;
			table.removeAll();
			// 添加dao后这里不会为空
			for (Map<String, Object> l : list) {
				tabelItem = new TableItem(table, SWT.NONE);
				tabelItem.setText(new String[] { l.get("S_ID").toString(), l.get("S_NAME").toString(),
						l.get("S_CLASS").toString(), l.get("s_phone_number".toUpperCase()).toString(), });
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), shell);
			return;
		}
	}

	public void showTypes() {
		combo.removeAll();
		List<Map<String, Object>> list1 = wdao.showAllChoosed(tname);
		String[] kids = new String[list1.size() + 1];
		kids[0] = "全部班级";
		try {
			for (int i = 0; i < list1.size(); i++) {
				Map<String, Object> m = list1.get(i);
				kids[i + 1] = UI_Util.objectToString(m.get("S_CLASS"));
			}
			combo.setItems(kids);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), shell);
		}
	}

	public void isOpen() {
		button_2.setSelection(true);
		button_2.setEnabled(false);
		button_2.setText("已开课");
	}
}
