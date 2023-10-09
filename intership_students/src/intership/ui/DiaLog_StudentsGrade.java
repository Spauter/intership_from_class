package intership.ui;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

import intership.Util.LoginHistoryUtil;
import intership.Util.OutPutExcelUtil;
import intership.Util.UI_Util;
import intership.dao.GradeDAO;
import intership.dao.TeacherDAO;
import intership.dao.WatchCourseDao;
import utils.Utils;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.wb.swt.SWTResourceManager;

//dao类：GradeDAO

public class DiaLog_StudentsGrade extends Dialog {

	protected Object result;
	protected Shell shell;
	private Table table;
	private TableColumn tblclmnNewColumn;
	private TableColumn tblclmnNewColumn_1;
	private TableColumn tblclmnNewColumn_2;
	private TableColumn tblclmnNewColumn_3;
	private TableColumn tblclmnNewColumn_4;
	private TableColumn tblclmnNewColumn_5;
	private Text text_1;
	private Text text_2;
	private Text text_3;
	private Text text_4;
	private Text text_5;
	private Text text_6;
	private Text text_8;
	private Text text_7;
	private Text text_9;
	private Text text_10;
	private Combo combo;
	private List<Map<String, Object>> list;
	private boolean update = false;
	GradeDAO gdao = new GradeDAO();
	private String id;
	private String tname;
	TeacherDAO tdao = new TeacherDAO();
	LoginHistoryUtil lh = new LoginHistoryUtil();
	private Button button;
	private boolean canExport = false;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public DiaLog_StudentsGrade(Shell parent, int style) {
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
		shell = new Shell(getParent(), SWT.CLOSE | SWT.MIN | SWT.MAX | SWT.TITLE);
		shell.setImage(SWTResourceManager.getImage(DiaLog_StudentsGrade.class, "/image/HT.ico"));
		shell.setBackgroundImage(null);
		shell.setSize(1273, 814);
		shell.setText("成绩查询");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		UI_Util.centerWindowns(shell);
		id = lh.FileReadOperator();
		tname = (tdao.selectById(id)).get(0).get("T_NAME").toString();
		SashForm sashForm = new SashForm(shell, SWT.VERTICAL);
		
		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm_1 = new SashForm(composite_1, SWT.NONE);

		Composite composite_3 = new Composite(sashForm_1, SWT.NONE);
		composite_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_FOREGROUND));

		combo = new Combo(composite_3, SWT.NONE);
		combo.setLocation(41, 20);
		combo.setSize(129, 25);
		combo.setText("班级");

		table = new Table(composite_3, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLocation(41, 52);
		table.setSize(789, 472);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(121);
		tblclmnNewColumn.setText("学号");

		tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setText("姓名");
		tblclmnNewColumn_1.setWidth(116);

		tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(118);
		tblclmnNewColumn_2.setText("性别");

		tblclmnNewColumn_3 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_3.setWidth(118);
		tblclmnNewColumn_3.setText("联系电话");

		tblclmnNewColumn_4 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_4.setWidth(118);
		tblclmnNewColumn_4.setText("课程名");

		tblclmnNewColumn_5 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_5.setWidth(100);
		tblclmnNewColumn_5.setText("成绩");

		Button button_1 = new Button(composite_3, SWT.NONE);
		button_1.setBounds(190, 19, 68, 25);
		button_1.setText("筛选");

		text_10 = new Text(composite_3, SWT.BORDER);
		text_10.setText("请输入学号");
		text_10.setBounds(278, 20, 124, 23);

		Button btnNewButton = new Button(composite_3, SWT.NONE);
		btnNewButton.setBounds(410, 18, 80, 27);
		btnNewButton.setText("查询");

		button = new Button(composite_3, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				canExport = true;
				showGrade();
			}
		});
		button.setBounds(496, 18, 80, 27);
		button.setText("导出成绩单");

		Composite composite_4 = new Composite(sashForm_1, SWT.NONE);
		composite_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		Label label_3 = new Label(composite_4, SWT.NONE);
		label_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_3.setLocation(37, 134);
		label_3.setSize(89, 17);
		label_3.setText("优秀");

		Label label_4 = new Label(composite_4, SWT.NONE);
		label_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_4.setLocation(37, 186);
		label_4.setSize(89, 17);
		label_4.setText("良好");

		Label label_9 = new Label(composite_4, SWT.NONE);
		label_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_9.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_9.setBounds(37, 249, 61, 17);
		label_9.setText("中等");

		Label label_5 = new Label(composite_4, SWT.NONE);
		label_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_5.setLocation(37, 318);
		label_5.setSize(89, 25);
		label_5.setText("及格");

		Label label_6 = new Label(composite_4, SWT.NONE);
		label_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_6.setLocation(37, 384);
		label_6.setSize(89, 17);
		label_6.setText("不及格");

		Label label_2 = new Label(composite_4, SWT.NONE);
		label_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_2.setLocation(37, 73);
		label_2.setSize(89, 25);
		label_2.setText("平均分");

		text_2 = new Text(composite_4, SWT.NONE);
		text_2.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_2.setLocation(128, 183);
		text_2.setSize(100, 25);
		text_2.setEditable(false);

		text_3 = new Text(composite_4, SWT.NONE);
		text_3.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_3.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_3.setLocation(128, 131);
		text_3.setSize(100, 25);
		text_3.setEditable(false);

		text_4 = new Text(composite_4, SWT.NONE);
		text_4.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_4.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_4.setLocation(128, 315);
		text_4.setSize(100, 25);
		text_4.setEditable(false);

		text_5 = new Text(composite_4, SWT.NONE);
		text_5.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_5.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_5.setLocation(128, 73);
		text_5.setSize(100, 25);
		text_5.setEditable(false);

		text_1 = new Text(composite_4, SWT.NONE);
		text_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_1.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_1.setEditable(false);
		text_1.setLocation(128, 381);
		text_1.setSize(100, 25);

		text_7 = new Text(composite_4, SWT.NONE);
		text_7.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_7.setEditable(false);
		text_7.setBounds(128, 246, 97, 23);
		sashForm_1.setWeights(new int[] { 3, 1 });

		Composite composite_2 = new Composite(sashForm, SWT.BORDER);
		composite_2.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		Label label_8 = new Label(composite_2, SWT.NONE);
		label_8.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_8.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_8.setLocation(222, 10);
		label_8.setSize(64, 28);
		label_8.setText("分数");

		text_6 = new Text(composite_2, SWT.NONE);
		text_6.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_6.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_6.setLocation(92, 7);
		text_6.setSize(100, 25);
		text_6.setEditable(false);

		text_8 = new Text(composite_2, SWT.BORDER);
		text_8.setLocation(292, 7);
		text_8.setSize(100, 25);

		Label label_7 = new Label(composite_2, SWT.NONE);
		label_7.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label_7.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label_7.setLocation(36, 84);
		label_7.setSize(50, 25);
		label_7.setText("姓名");

		Label label = new Label(composite_2, SWT.NONE);
		label.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		label.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		label.setLocation(36, 10);
		label.setSize(50, 25);
		label.setText("学号");

		Button button_2 = new Button(composite_2, SWT.NONE);
		button_2.setBounds(292, 84, 80, 27);
		button_2.setText("确定");

		text_9 = new Text(composite_2, SWT.NONE);
		text_9.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		text_9.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		text_9.setEditable(false);
		text_9.setBounds(92, 81, 100, 25);
		sashForm.setWeights(new int[] { 547, 148 });

		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem tabelItem = (TableItem) e.item;
				System.out.println(tabelItem.getText(0) + "\t" + tabelItem.getText(1));
				text_6.setText(tabelItem.getText(0));
				text_9.setText(tabelItem.getText(1));
				text_8.setText(tabelItem.getText(5));
				String score = text_8.getText();
				update = " ".equals(score) ? true : false;
			}
		});

		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showGrade();
			}
		});

		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String s_id = text_10.getText();
				list = gdao.selectById(s_id);
				if (list == null || list.isEmpty()) {
					Utils.showMsg("暂无查询结果", shell);
					return;
				}
				showGrade();
			}
		});

		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String s_id = text_6.getText();
				String c_name = (tdao.selectById(id)).get(0).get("T_CNAME").toString();
				String t_name = tname;
				String grade = text_8.getText();
				try {
					if (update) {
						gdao.insert(s_id, c_name, t_name, grade);
					} else {
						gdao.update(s_id, c_name, grade);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					Utils.showMsg("录入成绩失败", shell);
				}
				showGrade();
				Count();
			}
		});

		showKids();
		showGrade();
		Count();
	}

	private void showKids() {
		try {
			List<Map<String, Object>> list = new WatchCourseDao().showAllChoosed(tname);
			String[] kids = new String[list.size() + 1];
			kids[0] = "全部";
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> m = list.get(i);
				kids[i + 1] = UI_Util.objectToString(m.get("S_CLASS"));
			}
			combo.removeAll();
			combo.setItems(kids);
			combo.select(0);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), shell);
		}
	}

	public void showGrade() {
		String grade = combo.getText().trim();
		List<Map<String, Object>> list1 = null;
		int index = 0;
		try {
			if ("全部".equals(grade)) {
				list = gdao.showStudentgrade(tname);
			} else {
				list = gdao.select(grade);
			}
			/** 防止DeadCode,完成dao后移除注释 */
			if (list == null || list.isEmpty()) {
				Utils.showMsg("暂无查询结果", shell);
				return;
			}
			table.removeAll();
			TableItem tabelItem = null;
			String[][] data = new String[list.size()][6];
			String path = "";
			// 添加dao后这里不会为空
			if(canExport) {
				FileDialog fd = new FileDialog(shell);
				fd.setFilterExtensions(new String[] { "*.xlsx", "*.xls*" });// 后翔名交置
				path = fd.open();
			}
			for (Map<String, Object> l : list) {
				String s_id = l.get("s_id".toUpperCase()).toString();
				list1 = gdao.selectBySId(s_id);
				String score = list1.size() == 0 ? " " : list1.get(0).get("GRADE").toString();
				tabelItem = new TableItem(table, SWT.NONE);
				tabelItem.setText(new String[] { s_id, UI_Util.objectToString(l.get("s_name".toUpperCase())),
						UI_Util.objectToString(l.get("s_sex".toUpperCase())),
						UI_Util.objectToString(l.get("s_phone_number".toUpperCase())),
						UI_Util.objectToString(l.get("t_cname".toUpperCase())), score, });
				if (canExport) {
					data[index][0] = s_id;
					data[index][1] = UI_Util.objectToString(l.get("s_name".toUpperCase()));
					data[index][2] = UI_Util.objectToString(l.get("s_sex".toUpperCase()));
					data[index][3] = UI_Util.objectToString(l.get("s_phone_number".toUpperCase()));
					data[index][4] = UI_Util.objectToString(l.get("t_cname".toUpperCase()));
					data[index][5] = score;
					index++;
				}
			}
			if (canExport) {
				try {
					OutPutExcelUtil.createExcel("Students", new String[] { "学号", "姓名", "性别", "联系电话", "课程名字", "分数" },
							data, path);
					Utils.showMsg("文件导出成功,位置在:\r\n"+path, shell);
				} catch (Exception e) {
					Utils.showMsg("导出文件失败", shell);
				} finally {
					canExport = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), shell);
		}
	}

	private void Count() {
		List<Map<String, Object>> list1 = gdao.select1(tname);
		int A = 0, B = 0, C = 0, D = 0, E = 0;
		double avg = 0.0;
		if (!(list1.size() == 0)) {
			// 将对应的结果赋值给变量	
			try {
			avg = Double.parseDouble(list1.get(0).get("average_grade".toUpperCase()).toString());
			A = Integer.parseInt(list1.get(0).get("A").toString());
			B = Integer.parseInt(list1.get(0).get("B").toString());
			C = Integer.parseInt(list1.get(0).get("C").toString());
			D = Integer.parseInt(list1.get(0).get("D").toString());
			E = Integer.parseInt(list1.get(0).get("E").toString());
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

		
		text_5.setText(String.format("%.2f", avg));
		text_3.setText(String.valueOf(A));
		text_2.setText(String.valueOf(B));
		text_7.setText(String.valueOf(C));
		text_4.setText(String.valueOf(D));
		text_1.setText(String.valueOf(E));
	}

}
