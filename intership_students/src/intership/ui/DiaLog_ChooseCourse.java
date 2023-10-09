package intership.ui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.LoginHistoryUtil;
import intership.Util.UI_Util;
import intership.dao.ChooseCourseDao;
import utils.Utils;

import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class DiaLog_ChooseCourse extends Dialog {
	ChooseCourseDao cdaos = new ChooseCourseDao();
	LoginHistoryUtil lh = new LoginHistoryUtil();
	protected Shell shell;
	protected Object result;
	private List<Map<String, Object>> list;
	private List<Map<String, Object>> list1;
	private Table table;
	private Table table_1;
	private Combo combo;
	private String id;
	private String c_id;
	private String t_name;
	private String c_id1;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public DiaLog_ChooseCourse(Shell parent, int style) {
		super(parent, style);
		setText("课程管理");
	}

	/**
	 * Open the window.
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
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setImage(SWTResourceManager.getImage(DiaLog_ChooseCourse.class, "/image/OldFu.ico"));
		shell.setBackgroundImage(null);
		shell.setSize(1081, 613);
		shell.setText("课程信息表");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		id = lh.FileReadOperator1();
		UI_Util.centerWindowns(shell);
		SashForm sashForm = new SashForm(shell, SWT.NONE);


		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		lblNewLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		lblNewLabel.setText("课程：");
		lblNewLabel.setBounds(68, 76, 47, 24);

		combo = new Combo(composite_1, SWT.BORDER);
		combo.setBounds(121, 76, 158, 30);

		Button button = new Button(composite_1, SWT.CENTER);
		button.setAlignment(SWT.LEFT);
		button.setImage(SWTResourceManager.getImage(DiaLog_ChooseCourse.class, "/icons/full/help.png"));
		button.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String name = combo.getText().trim();
				try {
					list = cdaos.select(name);
					showCourses();
				} catch (Exception e1) {
					Utils.showMsg(e1.getMessage(), shell);
					e1.printStackTrace();
				}
				
			}
		});
		button.setText("查询");
		button.setBounds(285, 75, 114, 25);

		Button button_2 = new Button(composite_1, SWT.NONE);
		button_2.setImage(SWTResourceManager.getImage(DiaLog_ChooseCourse.class, "/org/eclipse/jface/text/source/projection/images/collapsed.png"));
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int result=JOptionPane.showConfirmDialog(null, "是否选择这门课程？","提示",JOptionPane.YES_NO_OPTION);
					if(result==1) {
						return;
					}
					if(cdaos.isSelected(id, c_id)) {
						Utils.showMsg("无法重复选择同一门课程", shell);
						return;
					}
					int i=cdaos.countChoosed(id);
					if(i>=2) {
						Utils.showMsg("已经达到最大选课数量", shell);
						return;
					}
					int j=cdaos.countChoosed1(t_name);
					if(j>=10) {
						Utils.showMsg("该班级已经达到最大选课人数", shell);
					}
					cdaos.insert(id, c_id, t_name);
					showChoose();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		button_2.setText("确定选课");
		button_2.setBounds(460, 75, 114, 24);

		Button button_1 = new Button(composite_1, SWT.NONE);
		button_1.setImage(SWTResourceManager.getImage(DiaLog_ChooseCourse.class, "/org/eclipse/jface/dialogs/images/message_error.png"));
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		button_1.setText("退出");
		button_1.setBounds(599, 75, 114, 24);

		table = new Table(composite_1, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);
		table.setBounds(29, 109, 704, 402);

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(153);
		tblclmnNewColumn.setText("课程编号");

		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_1.setWidth(133);
		tblclmnNewColumn_1.setText("课程名称");

		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn_2.setWidth(144);
		tblclmnNewColumn_2.setText("任教老师");

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(127);
		tableColumn.setText("开课状态");

		Composite composite = new Composite(sashForm, SWT.NONE);
		composite.setBackground(SWTResourceManager.getColor(SWT.COLOR_BLACK));

		table_1 = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setBounds(34, 107, 222, 116);
		table_1.setHeaderVisible(true);
		table_1.setLinesVisible(true);

		TableColumn tableColumn_1 = new TableColumn(table_1, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("已选课程");

		TableColumn tableColumn_2 = new TableColumn(table_1, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("任课老师");

		Button button_3 = new Button(composite, SWT.NONE);
		button_3.setImage(SWTResourceManager.getImage(DiaLog_ChooseCourse.class, "/icons/full/message_error.png"));
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int result=JOptionPane.showConfirmDialog(null, "是否退课","提示",JOptionPane.YES_NO_OPTION);
					if(result==1) {
						return;
					}
					//TODO
					/** 加入开课限制*/
					cdaos.delete(c_id1);
					showChoose();
				}
				catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		button_3.setBounds(88, 268, 80, 27);
		button_3.setText("退课");
		sashForm.setWeights(new int[] {790, 272});

		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem tabelItem = (TableItem) e.item;
				System.out.println(tabelItem.getText(0) + "\t" + tabelItem.getText(1));
				c_id = tabelItem.getText(0);
				t_name = tabelItem.getText(2);
			}
		});

		table_1.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem tabelItem = (TableItem) e.item;
				System.out.println(tabelItem.getText(0) + "\t" + tabelItem.getText(1));
				c_id1 = tabelItem.getText(0);
			}
		});
		
		showChoose();
		showCourses();
		showTypes();
	}

	public void showCourses() {
		try {
			table.removeAll();
			String name = combo.getText().trim();
			if ("全部".equals(name)) {
				list = cdaos.selectAll();
			} else {
				list = cdaos.select(name);
			}
			if (list == null || list.isEmpty()) {
				return;
			}
			TableItem tabelItem = null;
			// 添加dao后这里不会为空
			for (Map<String, Object> l : list) {
				tabelItem = new TableItem(table, SWT.NONE);
				tabelItem.setText(new String[] { 
						UI_Util.objectToString(l.get("C_ID")),
						UI_Util.objectToString(l.get("C_NAME")), 
						UI_Util.objectToString(l.get("T_NAME")),
						UI_Util.objectToString(l.get("IS_COURSE_OPEN")), });
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showChoose() {
		table_1.removeAll();
		list1 = cdaos.selectChoose(id);
		if (list1 == null || list1.isEmpty()) {
			return;
		}
		try {
			TableItem tabelItem = null;
			// 添加dao后这里不会为空
			for (Map<String, Object> l : list1) {
				tabelItem = new TableItem(table_1, SWT.NONE);
				tabelItem.setText(new String[] {
						UI_Util.objectToString(l.get("C_ID")),
						UI_Util.objectToString(l.get("T_NAME")),
	
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showTypes() {
		try {
			List<Map<String, Object>> list = cdaos.selectAll();
			String[] kids = new String[list.size() + 1];
			kids[0] = "全部";
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> m = list.get(i);
				kids[i + 1] = UI_Util.objectToString(m.get("T_NAME"));
			}
			combo.removeAll();
			combo.setItems(kids);
			combo.select(0);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), shell);
		}
	}

}
