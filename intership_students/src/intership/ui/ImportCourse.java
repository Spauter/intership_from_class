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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import intership.Util.AddDataUtil;
import intership.Util.UI_Util;
import intership.dao.LessonDAO;
import utils.Utils;

import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class ImportCourse extends Composite {
	AddDataUtil add = new AddDataUtil();
	LessonDAO ldao = new LessonDAO();
	private List<Map<String, Object>> list;
	private Table table;
	private Text text_id;
	private Text text_name;
	private String c_id;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public ImportCourse(Composite parent, int style) {
		super(parent, style);
		table = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(197, 33, 425, 285);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		TableColumn tableColumn = new TableColumn(table, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("课程编号");

		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.NONE);
		tblclmnNewColumn.setWidth(100);
		tblclmnNewColumn.setText("课程名称");

		TableColumn tableColumn_1 = new TableColumn(table, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("是否开课");

		Label label = new Label(this, SWT.NONE);
		label.setBounds(164, 367, 61, 17);
		label.setText("课程编号");

		Label label_1 = new Label(this, SWT.NONE);
		label_1.setBounds(164, 431, 61, 17);
		label_1.setText("课程名称");

		Label label_2 = new Label(this, SWT.NONE);
		label_2.setBounds(345, 367, 61, 17);
		label_2.setText("是否开课");

		text_id = new Text(this, SWT.BORDER);
		text_id.setBounds(229, 361, 91, 23);

		text_name = new Text(this, SWT.BORDER);
		text_name.setBounds(231, 431, 89, 23);

		Combo combo = new Combo(this, SWT.NONE);
		combo.setBounds(412, 364, 80, 25);
		combo.setItems(new String[] {"是","否"});

		Button button = new Button(this, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String id=text_id.getText();
				String name=text_name.getText();
				String isOpen=combo.getText().trim();
				try {
				ldao.insert(id, name, isOpen);
				showCourse();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		button.setBounds(326, 429, 80, 27);
		button.setText("确定添加");

		Button button_1 = new Button(this, SWT.NONE);

		button_1.setBounds(412, 429, 80, 27);
		button_1.setText("上传文件");
		
		Button button_2 = new Button(this, SWT.NONE);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					ldao.delete(c_id);
					showCourse();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		button_2.setBounds(517, 361, 80, 27);
		button_2.setText("删除");

		button_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog fd = new FileDialog(getShell());
				fd.setFilterExtensions(new String[] { "*.xlsx", "*.*" });// 后翔名交置
				String path = fd.open();
				File file = new File(path);
				add.AddCourseUtil(file);
				showCourse();
			}
		});
		
		table.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem tabelItem = (TableItem) e.item;
				System.out.println(tabelItem.getText(0) + "\t" + tabelItem.getText(1));
				c_id=tabelItem.getText(0);
			}
		});
		
		showCourse();
	}

	public void showCourse() {
		try {
			list = ldao.selectAll();
			if (list == null || list.isEmpty()) {
				return;
			}
			table.removeAll();
			TableItem tabelItem = null;
			// 添加dao后这里不会为空
			for (Map<String, Object> l : list) {
				tabelItem = new TableItem(table, SWT.NONE);
				tabelItem.setText(new String[] { UI_Util.objectToString(l.get("C_ID")),
						UI_Util.objectToString(l.get("C_NAME")), UI_Util.objectToString(l.get("IS_COURSE_OPEN")),

				});
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utils.showMsg(e.getMessage(), getShell());
			return;
		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
