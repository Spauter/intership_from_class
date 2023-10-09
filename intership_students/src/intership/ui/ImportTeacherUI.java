package intership.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import intership.Util.AddDataUtil;
import intership.Util.UI_Util;
import intership.dao.ImCourseDAO;
import intership.dao.TeacherDAO;
import utils.Utils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ImportTeacherUI extends Composite {
	AddDataUtil add = new AddDataUtil();
	TeacherDAO tdao = new TeacherDAO();
	ImCourseDAO cdao = new ImCourseDAO();
	private List<Map<String, Object>> list;
	private Table table;
	private Combo combo;
	private String id;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ImportTeacherUI(Composite parent, int style) {
		super(parent, style);

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(49, 91, 717, 418);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("教职工号");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("姓名");

		TableColumn tableColumn_6 = new TableColumn(table, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("性别");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("入职年份");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("职位");

		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("联系电话");

		TableColumn tableColumn_5 = new TableColumn(table, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("任教科目");

		combo = new Combo(this, SWT.NONE);
		combo.setBounds(576, 38, 88, 25);
		combo.setText("全部");

		Label lblkemu = new Label(this, SWT.NONE);
		lblkemu.setBounds(496, 41, 61, 17);
		lblkemu.setText("任教科目");

		Button button_1 = new Button(this, SWT.NONE);
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					String course = combo.getText().trim();
					tdao.update(id, course);
					showTeacher();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem tabelItem = (TableItem) e.item;
				System.out.println(tabelItem.getText(0) + "\t" + tabelItem.getText(1));
				id = tabelItem.getText(0);
			}
		});

		button_1.setBounds(670, 36, 80, 27);
		button_1.setText("确定");

		Button button_2 = new Button(this, SWT.NONE);

		button_2.setBounds(49, 38, 80, 27);
		button_2.setText("上传文件");

		Button button_3 = new Button(this, SWT.NONE);
		button_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					int i=tdao.countSelected(id);
					if(i>0) {
						Utils.showMsg("已经有学生选课，不能删除", getShell());
						return;
					}
					tdao.delete(id);
					showTeacher();
				} catch (Exception e1) {
					e1.printStackTrace();
					Utils.showMsg("删除失败", getShell());
				}
			}
		});
		button_3.setBounds(135, 36, 80, 27);
		button_3.setText("删除");
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell());
				fd.setFilterExtensions(new String[] { "*.xlsx", "*.*" });// 后翔名交置
				String path = fd.open();
				File file = new File(path);
				add.AddTeacherUtil(file);
				showTeacher();
			}
		});
		showTeacher();
		showTypes();
		
	}

	public void showTeacher() {
		try {
			list = tdao.selectAll();
			if (list == null || list.isEmpty()) {
				return;
			}
			Utils.Check(list == null || list.isEmpty(), "暂无数据");
			table.removeAll();
			TableItem tabelItem = null;
			// 添加dao后这里不会为空
			for (Map<String, Object> l : list) {
				tabelItem = new TableItem(table, SWT.NONE);
				tabelItem.setText(new String[] { UI_Util.objectToString(l.get("T_ID")),
						UI_Util.objectToString(l.get("T_NAME")), UI_Util.objectToString(l.get("T_SEX")),
						UI_Util.objectToString(l.get("HIRE_DATE")), UI_Util.objectToString(l.get("POSITION")),
						UI_Util.objectToString(l.get("t_phone_number".toUpperCase())),
						UI_Util.objectToString(l.get("T_CNAME")), });
				System.out.println(UI_Util.objectToString(l.get("T_CNAME")));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), getShell());
		}
	}

	public void showTypes() {
		try {
			List<Map<String, Object>> list = cdao.selectAll();
			String[] kids = new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> m = list.get(i);
				kids[i] = UI_Util.objectToString(m.get("C_NAME"));
			}
			combo.removeAll();
			combo.setItems(kids);
			combo.select(0);
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), getShell());
		}
	}



	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
