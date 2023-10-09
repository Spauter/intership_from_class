package intership.ui;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;

import intership.Util.ImageUtil;
import intership.Util.UI_Util;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;

public class AdminiMainUI {
	Display display = new Display();
	protected Shell shell;

	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
//		try {
//			AdminiMainUI window = new AdminiMainUI();
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
		shell.setImage(SWTResourceManager.getImage(AdminiMainUI.class, "/image/HT.ico"));
		shell.setSize(1150, 717);
		shell.setText("管理员欢迎您");
		shell.setMaximized(false);
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		UI_Util.centerWindowns(shell);
		MenuItem menuItem = new MenuItem(menu, SWT.NONE);
		menuItem.setText("录入学生信息");

		MenuItem menuItem_1 = new MenuItem(menu, SWT.NONE);
		menuItem_1.setText("录入教师信息");

		MenuItem menuItem_2 = new MenuItem(menu, SWT.NONE);
		menuItem_2.setText("录入课程信息");

		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);

		TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
		tabItem.setImage(SWTResourceManager.getImage(AdminiMainUI.class, "/org/eclipse/jface/fieldassist/images/info_ovr.png"));
		tabItem.setText("首页");

		Canvas canvas = new Canvas(tabFolder, SWT.NONE);
		canvas.setBackgroundImage(SWTResourceManager.getImage(AdminiMainUI.class, "/image/database.jpg"));
		tabItem.setControl(canvas);
		ImageUtil.fullImg(shell);
		canvas.setLayout(null);

		TabItem tabItem_1 = new TabItem(tabFolder, SWT.NONE);
		tabItem_1.setImage(SWTResourceManager.getImage(AdminiMainUI.class, "/org/eclipse/jface/contentassist/images/content_assist_cue.png"));
		tabItem_1.setText("录入学生信息");
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openTab(tabFolder, "录入学生信息", new ImportStudentsUI(tabFolder, SWT.NONE), tabItem_1);
			}
		});

		TabItem tabItem_2 = new TabItem(tabFolder, SWT.NONE);
		tabItem_2.setImage(SWTResourceManager.getImage(AdminiMainUI.class, "/org/eclipse/jface/contentassist/images/content_assist_cue.png"));
		tabItem_2.setText("录入教师信息");
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openTab(tabFolder, "录入教师信息", new ImportTeacherUI(tabFolder, SWT.NONE), tabItem_2);
			}
		});

		TabItem tabItem_3 = new TabItem(tabFolder, SWT.NONE);
		tabItem_3.setImage(SWTResourceManager.getImage(AdminiMainUI.class, "/org/eclipse/jface/contentassist/images/content_assist_cue.png"));
		tabItem_3.setText("录入课程信息");
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				openTab(tabFolder, "录入课程信息", new ImportCourse(tabFolder, SWT.NONE), tabItem_3);
			}
		});

		ImageLoader imageLoader = new ImageLoader();
		final ImageData[] imageDatas = imageLoader.load(".\\images\\HuTao.gif");
		final Image image = new Image(display, imageDatas[0].width, imageDatas[0].height);
		canvas.addPaintListener(new PaintListener() {
			public void paintControl(PaintEvent e) {
				e.gc.drawImage(image, shell.getSize().x - imageDatas[0].width,
						shell.getSize().y - imageDatas[0].height - imageDatas[0].height / 5);
			}
		});

		final GC gc = new GC(image);
		final Thread thread = new Thread() {
			int frameIndex = 0;

			public void run() {
				while (!isInterrupted()) {
					frameIndex %= imageDatas.length;
					final ImageData frameData = imageDatas[frameIndex];
					display.asyncExec(new Runnable() {
						public void run() {
							Image frame = new Image(display, frameData);
							gc.drawImage(frame, frameData.x, frameData.y);
							frame.dispose();
							canvas.redraw();
						}
					});
					try {
						Thread.sleep(imageDatas[frameIndex].delayTime * 10);
					} catch (InterruptedException e) {
						return;
					}
					frameIndex += 1;
				}
			}
		};

		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				thread.interrupt();
			}
		});
		thread.start();

	}

	public void openTab(TabFolder tabFolder, String name, Control control, TabItem tabItem) {
		control.setLocation(tabFolder.getSize().x - control.getSize().x, tabFolder.getSize().y - control.getSize().y);
		int i = 0;
		int index = tabFolder.getItemCount();
		for (; i < index; i++) {
			TabItem item = tabFolder.getItem(i);
			if (item.getText().equals(name)) {
				break;
			}
			if (i == tabFolder.getItemCount()) {
				TabItem tabItem1 = new TabItem(tabFolder, SWT.NONE);
				tabItem1.setText(name);
			}
			if (control != null) {
				tabItem.setControl(control);
			}
		}
		tabFolder.setSelection(i);
	}
	
}
