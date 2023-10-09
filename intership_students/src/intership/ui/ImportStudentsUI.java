package intership.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import intership.Util.AddDataUtil;
import intership.Util.UI_Util;
import intership.dao.ImStudentDAO;
import utils.Utils;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ImportStudentsUI extends Composite {
	AddDataUtil addstu = new AddDataUtil();
	ImStudentDAO sdao = new ImStudentDAO();
	private List<Map<String, Object>> list;
	private Table table;
	private Text text_class;
	private Text text_tel;
	private Text text_name;
	private Text text_id;
	private Combo combo, combo_1;
	@SuppressWarnings("deprecation")
	private int year = new Date().getYear() + 1900;
	@SuppressWarnings("deprecation")
	private int month = new Date().getMonth() + 1;
	private int term1 = month < 8 ? 1 : 0;
	private String id;
	
	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ImportStudentsUI(Composite parent, int style) {
		super(parent, style);

		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(44, 110, 725, 409);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(128);
		tableColumn.setText("学号");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("姓名");

		TableColumn tableColumn_2 = new TableColumn(table, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("性别");

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("所在班级");

		TableColumn tableColumn_3 = new TableColumn(table, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("年级");

		TableColumn tableColumn_4 = new TableColumn(table, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("联系电话");

		combo = new Combo(this, SWT.NONE);
		combo.setBounds(44, 73, 88, 25);
		combo.setText("全部");

		Button btnNewButton = new Button(this, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showStudents();
			}
		});
		btnNewButton.setBounds(138, 71, 80, 27);
		btnNewButton.setText("筛选");

		Button label = new Button(this, SWT.NONE);

		label.setBounds(573, 67, 98, 34);
		label.setText("上传文件");

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(787, 130, 61, 17);
		label_1.setText("学号");

		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(787, 185, 61, 17);
		label_2.setText("姓名");

		Label label_3 = new Label(this, SWT.NONE);
		label_3.setBounds(787, 241, 61, 17);
		label_3.setText("性别");

		Label label_4 = new Label(this, SWT.NONE);
		label_4.setBounds(787, 306, 61, 17);
		label_4.setText("所在班级");

		Label label_5 = new Label(this, SWT.NONE);
		label_5.setBounds(787, 359, 61, 17);
		label_5.setText("入学年份");

		Label label_6 = new Label(this, SWT.NONE);
		label_6.setBounds(787, 426, 61, 17);
		label_6.setText("联系电话");

		text_class = new Text(this, SWT.BORDER);
		text_class.setBounds(854, 306, 88, 23);

		Combo combo2 = new Combo(this, SWT.NONE);
		combo2.setBounds(854, 359, 88, 25);
		combo2.setItems(new String[] { (year - term1) + "", (year - 1 - term1) + "", (year - 2 - term1) + "",
				(year - 3 - term1) + "" });

		text_tel = new Text(this, SWT.BORDER);
		text_tel.setBounds(854, 426, 88, 23);

		text_name = new Text(this, SWT.BORDER);
		text_name.setBounds(854, 182, 88, 23);

		text_id = new Text(this, SWT.BORDER);
		text_id.setBounds(854, 130, 88, 23);

		Button button_1 = new Button(this, SWT.NONE);
		button_1.setBounds(845, 492, 80, 27);
		button_1.setText("添加");

		combo_1 = new Combo(this, SWT.NONE);
		combo_1.setBounds(854, 241, 88, 25);
		combo_1.setItems(new String[] { "男", "女 " });
		
		Button button = new Button(this, SWT.NONE);
		button.setBounds(472, 73, 80, 27);
		button.setText("删除");

		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
				int i=sdao.countSelected(id);
				if(i>0) {
					Utils.showMsg("该学生已经选课,无法删除", getShell());
					return;
				}
				sdao.delete(id);
				showStudents();
				}catch(Exception e1) {
					e1.printStackTrace();
					Utils.showMsg("删除失败", getShell());
				}
				
			}
		});
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem tabelItem = (TableItem) e.item;
				System.out.println(tabelItem.getText(0) + "\t" + tabelItem.getText(1));
				id=tabelItem.getText(0);
			}
		});
		
		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
				String id=text_id.getText();
				String name = text_name.getText();
				String sex=combo_1.getText().trim();
				String sclass = text_class.getText();
				String date=combo2.getText().trim();
				String tel=text_tel.getText();
				showStudents();
				sdao.insert(id, name, sclass, tel, date, sex);
				Utils.showMsg("添加成功", getShell());
				}catch(Exception e1) {
					Utils.showMsg("添加失败", getShell());
					e1.printStackTrace();
				}
			}
			
		});

		label.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell());
				fd.setFilterExtensions(new String[] { "*.xlsx", "*.*" });// 后翔名交置
				String path = fd.open();
				File file = new File(path);
				addstu.AddStudentUtil(file);
				showStudents();
			}
		});
		showKids();
		showStudents();
	}

	public void showStudents() {
		try {
			list = sdao.selectAll();
			if (list == null || list.isEmpty()) {
				return;
			}
			String sclass = combo.getText().trim();
			List<Map<String, Object>> list = null;

			if ("全部".equals(sclass)) {
				list = sdao.selectAll();

			} else {
				list = sdao.searchStudentsBy(sclass);
			}
			Utils.Check(list == null || list.isEmpty(), "暂无数据");
			table.removeAll();
			table.removeAll();
			TableItem tabelItem = null;
			// 添加dao后这里不会为空
			for (Map<String, Object> l : list) {
				String sclass1=UI_Util.objectToString(l.get("S_CLASS"));
				tabelItem = new TableItem(table, SWT.NONE);
				tabelItem.setText(new String[] { 
						UI_Util.objectToString(l.get("S_ID")),
						UI_Util.objectToString(l.get("S_NAME")),
						UI_Util.objectToString(l.get("S_SEX")),
						sclass1,
						sclass1.substring(2, 4),
						UI_Util.objectToString(l.get("S_PHONE_NUMBER")),

				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), getShell());
		}
	}

	public void showKids() {
		try {
			List<Map<String, Object>> list = sdao.selectAll();
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
			Utils.showMsg(e.getMessage(), getShell());
		}

	}


	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
