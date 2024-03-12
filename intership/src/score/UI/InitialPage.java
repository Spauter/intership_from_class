package score.UI;

import score.Check.DataValidation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;



/**
 * 以下是关于数据结构的代码实现部分，用Ctrl+鼠标左键跳转
 * <P/>
 * 排序部分
 * <P/>
 * 归并排序{@link #MergeSort(double[])}插入排序{@link #Insertion()}，快速排序{@link #QuickSort(int[], int, int)}，堆排序{@link #HeapSort()};
 * ，选择排序{@link #SelectionSort()}.
 * <P/>
 * 查找功能
 * <P/>
 * 对分查找 ，顺序查找{@link #Sequential(String)}.
 * <P/>
 * ctrl+Q返回上次光标位置
 * 
 * @author Blodc_Spauter
 * 
 */
public class InitialPage extends Students {
	Students stu = new Students("成绩管理系统");
	DataValidation dv = new DataValidation();
	JFrame mainJFrame = new JFrame("学生成绩管理系统");// 构造一个框架

	String content;// 用于文件输入输出流的变量
	String path;// 用于文件路径
	String thisUID;// 用于uid判断
	int count = 0;// 统计现有的学生个数
	static JFrame apffMenu = new JFrame("从文件导入");// Abbreviation:AddPageFromFile
	static JFrame apftMenu = new JFrame("从此处输入");// Abbreviation:AddPageFromThis
	static JFrame squentialPage = new JFrame();// 名字查询界面
	static JFrame csp = new JFrame();// 这是按姓名查询的主界面
	private boolean check_uid = false, check_name = false, check_Math = false, check_English = false,
			check_Computer = false, CanRead = true;// 检查输入是否合法，用来判断能否添加数据，初始值为false
	JTextArea text;// 创建一个多行纯文本域
	JMenuBar mainMenuBar;// 创建一个菜单栏
	JMenu fileMenu, editMenu, countMenu;
	public String[] uidInfos, nameInfos, MathInfos, EnglishInfos, ComputerInfos, avgInfos;// 二组数组列数据

	/**
	 * 初始菜单 五个功能 分别是文件，编辑，统计，排序，帮助
	 * <p/>
	 * 其它窗口 ,{@link #createAddPageFromThis()}.
	 * 
	 * @author Blodc_Spauter
	 * @return show the functions(void)
	 * 
	 */
	public InitialPage() {
		super("成绩管理");
		Container con = mainJFrame.getContentPane();
		text = new JTextArea();
		text.setEditable(false);
		JScrollPane JSPane = new JScrollPane(text);
		con.add(JSPane, BorderLayout.CENTER);
		mainJFrame.setVisible(true);// 设置窗口是否可见y_CLOSE);// 设置关闭窗口时推出JVM
		createMenu(); // 调用自定义的方法创建菜单结构
		mainJFrame.setJMenuBar(mainMenuBar);// 添加菜单到窗口
		mainJFrame.setSize(600, 400);// 设置窗口大小
		mainJFrame.setLocation(500, 300);
		mainJFrame.setResizable(false);
		mainJFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 调用自定义的方法创建菜单结构 五个功能 分别是文件，编辑，统计，排序，帮助。
	 * <P/>
	 * 文件菜单有新建（手动输入数据），打开（从文档导入数据），刷新，打印（控制台），保存，另存为，退出。
	 * <P/>
	 * 编辑有排序和查找两个功能，其中运行查找时需要将数据按学号排序 统计（分数情况） 帮助（关于该项目---瞎编的）。
	 * 
	 * @author Blodc_Spauter
	 * @return void
	 */
	public void createMenu() {
		mainMenuBar = new JMenuBar();// 创建JMemuBar
		fileMenu = new JMenu("文件");// 创建四个JMenu
		editMenu = new JMenu("编辑");
		countMenu = new JMenu("统计");
		JMenu helpMenu = new JMenu("帮助");
		// 创建JMenuItem并添加对应的JMenu中
		mainMenuBar.add(fileMenu);
		// 创建 文件菜单 下面的菜单项
		JMenuItem newItem = new JMenuItem("新建");
		JMenuItem openItem = new JMenuItem("打开");
		JMenuItem refreshItem = new JMenuItem("刷新");
		JMenuItem saveItem = new JMenuItem("保存..");
		JMenuItem saveasItem = new JMenuItem("另存为..");
		JMenuItem printItem = new JMenuItem("打印..");
		JMenuItem exitItem = new JMenuItem("退出");

		// *********在菜单栏中添加项目
		fileMenu.add(newItem);
		fileMenu.add(openItem);
		fileMenu.add(refreshItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveasItem);
		fileMenu.addSeparator();
		fileMenu.add(printItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		// 创建 编辑菜单 下面的菜单项
		mainMenuBar.add(editMenu);
		JMenu sortMenu = new JMenu("排序");
		JMenu byCourse = new JMenu("按课程成绩排序");
		JMenuItem Math = new JMenuItem("数学");
		JMenuItem English = new JMenuItem("英语");
		JMenuItem Computer = new JMenuItem("计算机");
		JMenuItem avgCourse = new JMenuItem("平均成绩");
		JMenuItem normal = new JMenuItem("默认排序");
		JMenu findMenu = new JMenu("查找");
		JMenuItem byName = new JMenuItem("按名字查找");
		JMenuItem byUID = new JMenuItem("按学号查找");
		// *********在菜单栏中添加项目
		editMenu.add(sortMenu);
		sortMenu.add(normal);
		sortMenu.add(byCourse);
		byCourse.add(Math);
		byCourse.add(English);
		byCourse.add(Computer);
		byCourse.add(avgCourse);
		editMenu.addSeparator();
		editMenu.add(findMenu);
		findMenu.add(byUID);
		findMenu.add(byName);

		mainMenuBar.add(countMenu);
		// 创建 统计 下面的菜单项
		JMenuItem byClass = new JMenuItem("按科目统计");

		// *********在菜单栏中添加项目
		countMenu.add(byClass);

		mainMenuBar.add(helpMenu);
		// 创建 帮助菜单 下面的菜单项
		JMenuItem aboutItem = new JMenuItem("关于");
		// *********在菜单栏中添加项目
		helpMenu.add(aboutItem);

		// 由鼠标触发事件
		newItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				createAddPageFromThis();
			}
		});

		openItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				createAddPagefromFile();
			}
		});

		saveItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				File file = new File("Student.dat");// 保存的文件名，与该文件的包名的父目录同级
				if (stu.data[0][0] == null || stu.data.length == 0) {
					JOptionPane.showMessageDialog(saveItem, "空数据无法保存！！", "提示", JOptionPane.WARNING_MESSAGE);
					return;
				} else if (file.exists()) {// 判断文件是否已经存在如果该文件已经存在，先删除该文件
					file.delete();
				}
				FileOutOperator("Student.dat");
				JOptionPane.showMessageDialog(saveItem, "保存成功！！\n" + "保存路径" + file.getAbsolutePath(), "提示",
						JOptionPane.PLAIN_MESSAGE);

			}
		});

		saveasItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					JOptionPane.showMessageDialog(saveItem, "空数据无法保存！！", "提示", JOptionPane.WARNING_MESSAGE);
					return;
				} else {
					saveAsPage();
				}

			}
		});
		refreshItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				toRefresh();

			}
		});
		printItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				JOptionPane.showMessageDialog(printItem, "此操作只能打印所有学生成绩信息\n如果显示结果不同，请点击刷新后再试", "提示", JOptionPane.WARNING_MESSAGE);
				stu.printList();
			}
		});

		exitItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int i = JOptionPane.showConfirmDialog(null, "是否退出系统", "退出确认对话框", JOptionPane.YES_NO_CANCEL_OPTION);
				// 通过对话框中按钮的选择来决定结果，单机yes时，窗口直接消失
				if (i == 0)
					mainJFrame.dispose();

			}
		});

		Math.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}
				Insertion();
				JOptionPane.showMessageDialog(Math, "操作成功！！\n点击确定刷新", "消息提示", JOptionPane.PLAIN_MESSAGE);
				toRefresh();
			}
		});
		English.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}
				SelectionSort();
				for (int i = 0; i < count; i++) {
					stu.data[i][0] = uidInfos[i];
					stu.data[i][1] = nameInfos[i];
					stu.data[i][2] = MathInfos[i];
					stu.data[i][4] = ComputerInfos[i];
					stu.data[i][5] = avgInfos[i];

				}
				JOptionPane.showMessageDialog(Math, "操作成功！！\n点击确定刷新", "消息提示", JOptionPane.PLAIN_MESSAGE);
				toRefresh();
			}
		});

		Computer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}
				HeapSort();
				for (int i = 0; i < count; i++) {
					stu.data[i][0] = uidInfos[i];
					stu.data[i][1] = nameInfos[i];
					stu.data[i][2] = MathInfos[i];
					stu.data[i][3] = EnglishInfos[i];
					stu.data[i][5] = avgInfos[i];
				}
				JOptionPane.showMessageDialog(Math, "操作成功！！\n点击确定刷新", "消息提示", JOptionPane.PLAIN_MESSAGE);
				toRefresh();
			}
		});
		normal.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}
				SearchByUID();
				JOptionPane.showMessageDialog(Math, "操作成功！！\n点击确定刷新", "消息提示", JOptionPane.PLAIN_MESSAGE);
				toRefresh();
			}
		});

		avgCourse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}
				CanRead = true;
				String infos[] = stu.getCols(6).toString().split("\\t");
				double[] base = new double[count];
				if (infos == null || count == 0) {
					return;
				}
				for (int i = 0; i < count; i++) {
					base[i] = Double.parseDouble(infos[i]);
				}
				MergeSort(base);
				for (int i = 0; i < count; i++) {
					stu.data[i][0] = uidInfos[i];
					stu.data[i][1] = nameInfos[i];
					stu.data[i][2] = MathInfos[i];
					stu.data[i][3] = EnglishInfos[i];
					stu.data[i][4] = ComputerInfos[i];
					stu.data[i][5] = base[i];
				}
				JOptionPane.showMessageDialog(Math, "操作成功！！\n点击确定刷新", "消息提示", JOptionPane.PLAIN_MESSAGE);
				toRefresh();
			}
		});

		byUID.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}

				Binary_Before();
			}
		});

		byClass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}
				csp.dispose();
				Count();

			}
		});

		byName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (stu.isEmpty()) {
					return;
				}
				int input = 0;
				while (input == 0) {
					String name = JOptionPane.showInputDialog(byName, "请输入需要查询的名字", "名字查询", JOptionPane.PLAIN_MESSAGE);
					if (name == null) {
						return;
					}
					if (name.isEmpty()) {
						JOptionPane.showMessageDialog(byName, "输入为结果为空,无法查询", "查询提示", JOptionPane.ERROR_MESSAGE);
						continue;
					}
					if (input != -1) {// 判断输入是否为空\
						squentialPage.dispose();
						
							input = Sequential(name);
					} else {

					}
				}
			}
		});

		aboutItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				new AboutPage();
			}
		});
	}

	/**
	 * 刷新功能，其实就是再一次遍历二维数组
	 * 
	 * @author Blodc_Spauter
	 * @return void
	 * 
	 */
	public void toRefresh() {
		getLineData();
		for (int i = 0; i < stu.listLength(); i++) {// 添加前清楚链表所有数据
			stu.deleteIndexNode(i);
		}
		for (int i = 0; i < count; i++) {// 重新写入数据
			String info = stu.turnIntoList(uidInfos[i], nameInfos[i], MathInfos[i], EnglishInfos[i],
					ComputerInfos[i],avgInfos[i]);
			stu.listadd(new Students(info));
		}
		text.setText("");
		text.setVisible(true);
		text.setSize(400, 20 * count);
		if (stu.data[0][0] == null || stu.data[0][0] == "") {
			text.append("\t\t偌大的菜单竟然啥都没有");
			return;
		}
		text.append("序号" + "\t" + "学号" + "\t" + "姓名" + "\t" + "数学" + "\t" + "英语" + "\t" + "计算机" + "\t" + "平均分" + "\n");
		for (int i = 0; i < count; i++) {

			String info = (i + 1) + "\t" + stu.data[i][0] + "\t" + stu.data[i][1] + "\t" + stu.data[i][2] + "\t"
					+ stu.data[i][3] + "\t" + stu.data[i][4] + "\t" + stu.data[i][5];
			text.append(info);
			text.append("\n");
		}
	}

	/**
	 * 用于加載手动输入数据区界面 可以实现数据单个输入修改功能
	 * 
	 * @return void
	 * @warring 数组实际存储内容可能会与菜单栏显示内容不一致，需要点击“刷新按钮”{@link #toRefresh()}
	 */
	public void createAddPageFromThis() {
		apftMenu.setResizable(false);
		apftMenu.setBounds(700, 300, 450, 300);
		apftMenu.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		apftMenu.getContentPane().add(panel, BorderLayout.CENTER);
		apftMenu.setVisible(true);
		// 设置学号标签
		JLabel label_name = new JLabel();
		panel.add(label_name);
		label_name.setText("学号:");
		label_name.setBounds(10, 10, 75, 20);
		//
		JTextField uid = new JTextField();
		panel.add(uid);
		uid.setBounds(90, 10, 100, 20);

		JLabel uidHints = new JLabel();
		panel.add(uidHints);
		uidHints.setBounds(201, 10, 230, 20);
		uidHints.setText("学号必须是四位纯数字，比如0001");

		uid.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				uidHints.setText("学号必须是四位纯数字，比如0001");

			}

			@Override
			public void mousePressed(MouseEvent e) {
				uidHints.setText("");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				String suid = uid.getText();
				check_uid = dv.Check_uid(suid) && stu.HasSame(suid);
				if (check_uid) {
					uidHints.setText("学号设置成功");
				} else {
					uidHints.setText("输入有误");
				}
				if (!stu.HasSame(suid)) {
					uidHints.setText("学号重复！");
					check_uid = false;
				}

			}

		});

		// 设置姓名输入框
		JLabel name = new JLabel();
		panel.add(name);
		name.setText("姓  名:");
		name.setBounds(10, 50, 75, 20);

		JTextField nameText = new JTextField();
		nameText.setBounds(90, 50, 100, 20);
		panel.add(nameText);

		JLabel nameHints = new JLabel();
		panel.add(nameHints);
		nameHints.setText("请输入姓名");
		nameHints.setBounds(201, 50, 230, 20);

		nameText.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				nameHints.setText("请输入姓名");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				nameHints.setText("");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				String sName = nameText.getText();
				check_name = dv.Check_name(sName);
				if (check_name) {
					nameHints.setText("名字设置成功");
				} else {
					nameHints.setText("名字不为空");
				}
			}
		});

//	    	输入数学成绩
		JLabel math = new JLabel();
		math.setText("数学成绩：");
		math.setBounds(10, 90, 75, 20);
		panel.add(math);

		JTextField MathScore = new JTextField();
		MathScore.setBounds(90, 90, 100, 20);
		panel.add(MathScore);

		JLabel MathScoreHints = new JLabel();
		panel.add(MathScoreHints);
		MathScoreHints.setText("数学成绩在0~100之间");
		MathScoreHints.setBounds(201, 90, 230, 20);

		MathScore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				MathScoreHints.setText("数学成绩在0~100之间");

			}

			@Override
			public void mousePressed(MouseEvent e) {
				MathScoreHints.setText("");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				String sMath = MathScore.getText();
				check_Math = dv.Check_score(sMath);
				if (check_Math) {
					MathScoreHints.setText("设置成功");
				} else {
					MathScoreHints.setText("输入有误");
				}
			}
		});

//	    	输入英语成绩
		JLabel English = new JLabel();
		panel.add(English);
		English.setText("英语成绩：");
		English.setBounds(10, 130, 75, 20);

		JTextField EnglishScore = new JTextField();
		panel.add(EnglishScore);
		EnglishScore.setBounds(90, 130, 100, 20);

		JLabel EnglishScoreHints = new JLabel();
		panel.add(EnglishScoreHints);
		EnglishScoreHints.setText("英语成绩在0~100之间");
		EnglishScoreHints.setBounds(201, 130, 230, 20);

		EnglishScore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				EnglishScoreHints.setText("英语成绩在0~100之间");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				EnglishScoreHints.setText("");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				String sEnglish = EnglishScore.getText();
				check_English = dv.Check_score(sEnglish);
				if (check_English) {
					EnglishScoreHints.setText("设置成功");
				} else {
					EnglishScoreHints.setText("输入有误");
				}
			}
		});

//	    	输入计算机成绩
		JLabel Computer = new JLabel();
		panel.add(Computer);
		Computer.setText("计算机成绩：");
		Computer.setBounds(10, 170, 100, 20);

//			计算机成绩输入框
		JTextField ComputerScore = new JTextField();
		panel.add(ComputerScore);
		ComputerScore.setBounds(90, 170, 100, 20);

//			计算机输入提示框

		JLabel ComputerScoreHints = new JLabel();
		panel.add(ComputerScoreHints);
		ComputerScoreHints.setText("计算机成绩在0~100之间");
		ComputerScoreHints.setBounds(201, 170, 230, 20);

//			当鼠标对当前输入框操作时触发发事件
		ComputerScore.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ComputerScoreHints.setText("计算机成绩在0~100之间");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				ComputerScoreHints.setText("");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				String sComputer = ComputerScore.getText();
				check_Computer = dv.Check_score(sComputer);
				if (check_Computer) {
					ComputerScoreHints.setText("设置成功");
				} else {
					ComputerScoreHints.setText("输入有误");
				}
			}
		});

		JButton toAdd = new JButton("添加");
		panel.add(toAdd);
		toAdd.setBounds(50, 220, 60, 30);
		toAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {// 检查输入是否合法
				boolean check = dv.CheckDataEfficiency(uid.getText(), nameText.getText(), MathScore.getText(),
						EnglishScore.getText(), ComputerScore.getText());
				if (check && stu.HasSame(uid.getText())) {
					stu.add(uid.getText());
					stu.add(nameText.getText());
					stu.add(MathScore.getText());
					stu.add(EnglishScore.getText());
					stu.add(ComputerScore.getText());
					int Math = Integer.parseInt(MathScore.getText());
					int English = Integer.parseInt(EnglishScore.getText());
					int Computer = Integer.parseInt(ComputerScore.getText());
					double average = (Math + English + Computer) * 1.0 / 3;
					count++;
					stu.add(String.format("%.2f", average));
					JOptionPane.showMessageDialog(toAdd, "添加成功！！！\n！！！", "消息提示", JOptionPane.PLAIN_MESSAGE);
					uid.setText("");
					uid.setText("");
					nameText.setText("");
					MathScore.setText("");
					EnglishScore.setText("");
					ComputerScore.setText(null);
					uidHints.setText("学号必须是四位纯数字，比如0001");
					nameHints.setText("请输入姓名");
					MathScoreHints.setText("数学成绩在0~100之间");
					EnglishScoreHints.setText("英语成绩在0~100之间");
					ComputerScoreHints.setText("计算机成绩在0~100之间");
					resetCheck();
				} else {
					JOptionPane.showMessageDialog(toAdd, "添加失败！！！\n请检查后点击确定！！！", "消息提示", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		JButton toResetThis = new JButton("重置");
		panel.add(toResetThis);
		toResetThis.setBounds(150, 220, 60, 30);
		toResetThis.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				uid.setText("");
				uid.setText("");
				nameText.setText("");
				MathScore.setText("");
				EnglishScore.setText("");
				ComputerScore.setText(null);
				uidHints.setText("学号必须是四位纯数字，比如0001");
				nameHints.setText("请输入姓名");
				MathScoreHints.setText("数学成绩在0~100之间");
				EnglishScoreHints.setText("英语成绩在0~100之间");
				ComputerScoreHints.setText("计算机成绩在0~100之间");
				resetCheck();
			}
		});

		JButton toChangeFile = new JButton("切换");
		panel.add(toChangeFile);
		toChangeFile.setBounds(240, 220, 60, 30);
		toChangeFile.setVisible(true);
		toChangeFile.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				apftMenu.dispose();
				createAddPagefromFile();

			}
		});

		JButton toExit = new JButton("退出");
		panel.add(toExit);
		toExit.setBounds(340, 220, 60, 30);
		toExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				apftMenu.dispose();
			}
		});

	}

	/**
	 * 此处是加载文件导入界面和文件保存（另存为）界面；作用是批量对二维数组读取和保存. 此处会用到文件输出流（java.io） 实现在
	 * {@link #FileOutOperator(String)},{@link #FileReadOperator(String)}
	 * 
	 * @author Blodc_Spauter
	 * @wbaseing 数组实际存储内容可能会与菜单栏显示内容不一致
	 * @return void
	 */
	public void createAddPagefromFile() {
		JOptionPane.showMessageDialog(null, "请选择文件后缀名为dat的文件", "提示", JOptionPane.PLAIN_MESSAGE);
		JFileChooser open = new JFileChooser(new File("input.dat"));
		open.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		open.showOpenDialog(null);
		open.setFileSelectionMode(JFileChooser.FILES_ONLY);
		try {
			path = open.getSelectedFile().getAbsolutePath();
			if (!dv.Check_File(path)) {
				JOptionPane.showMessageDialog(null, "添加失败！！！\n请检查后点击确定！！！", "消息提示", JOptionPane.ERROR_MESSAGE);
				return;
			}
			FileReadOperator(path);
			JOptionPane.showMessageDialog(null, "操作成功！！\n点击确定刷新", "消息提示", JOptionPane.PLAIN_MESSAGE);
			toRefresh();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(open, "文件未成功读取", "提示", JOptionPane.WARNING_MESSAGE);
			return;
		}

	}

	public void saveAsPage() {
		JFileChooser save = new JFileChooser();
		save.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
		save.showSaveDialog(null);
		save.setFileSelectionMode(JFileChooser.FILES_ONLY);
		try {
			path = save.getSelectedFile().getAbsolutePath();
			JOptionPane.showMessageDialog(null, "文件保存成为/n" + path + "\\output.dat", "提示", JOptionPane.PLAIN_MESSAGE);
			FileOutOperator(path + "\\output.dat");

			toRefresh();
		} catch (NullPointerException e) {
			JOptionPane.showMessageDialog(null, "文件未成功保存！！", "消息提示", JOptionPane.WARNING_MESSAGE);
			return;
		}
	}

	/**
	 * 此处是加载文件导入界面和文件保存（另存为）界面；作用是批量对二维数组读取和保存. 此处会用到文件输出流（java.io） 实现在
	 * {@link #FileOutOperator(String)},{@link #FileReadOperator(String)}
	 * 
	 * @author Blodc_Spauter
	 * @wbaseing 数组实际存储内容可能会与菜单栏显示内容不一致
	 * @return void
	 */

	/**
	 * 重新写的Check_File
	 * 
	 * @param path
	 * @return
	 */
	public boolean check_File(String path) {
		if (path.isEmpty()) {
			return false;
		}
		try {
			File file = new File(path);
			String parentPath = file.getParent();
			File dest = new File(parentPath);
			if (dest.exists() && path.endsWith(".dat")) {
				return true;
			} else {
				return false;

			}
		} catch (NullPointerException npe) {
			return false;
		}
	}

	/**
	 * 文件读取操作，按行读取数据 与 addDataFromFile 一起可以把文档里的数据写入到二维数组中
	 * 
	 * @wbaseing 数组实际存储内容可能会与菜单栏显示内容不一致
	 * @author Blodc_Spauter
	 * @return void
	 */
	public void FileReadOperator(String path) {
		File file = new File(path);
		FileReader iis = null;
		BufferedReader bfr = null;
		try {
			iis = new FileReader(file);
			bfr = new BufferedReader(iis);
			String info = null;
			while ((info = bfr.readLine()) != null) {
				addDataFromFile(info);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "错误/n" + e, "系统错误", JOptionPane.ERROR_MESSAGE);
		}
		if (null != iis) {
			try {
				iis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入数组操作，按行写入 需要过滤不符合条件的数据
	 * 
	 * @wbaseing 数组实际存储内容可能会与菜单栏显示内容不一致
	 * @return void
	 */
	public void addDataFromFile(String info) {
		resetCheck();
		String[] infos = info.split("\\t");
		if (infos == null || infos.length == 0) {
			return;
		}
		if (5 != infos.length) {
			System.out.println("数据不符合要求，添加失败:\t" + info);
			return;
		}
		boolean check = dv.CheckDataEfficiency(infos[0], infos[1], infos[2], infos[3], infos[4])
				&& stu.HasSame(infos[0]);
		if (check) {
			double average = (Integer.parseInt(infos[2]) + Integer.parseInt(infos[3]) + Integer.parseInt(infos[4]))
					* 1.0 / 3;
			stu.add(infos[0]);
			stu.add(infos[1]);
			stu.add(infos[2]);
			stu.add(infos[3]);
			stu.add(infos[4]);
			stu.add(String.format("%.2f", average));
			count++;
		} else {
			if (!(dv.Check_uid(infos[0]))) {
				System.out.println("数据不符合要求，添加失败:\t" + info);
			} else if (!stu.HasSame(infos[0])) {
				System.out.println("重复学号，添加失败:\t" + info);
			} else {
				System.out.println("数据不符合要求，添加失败:\t" + info);
			}
		}
	}

	public void FileOutOperator(String path) {
		File file = new File(path);
		FileWriter out = null;
		try {
			out = new FileWriter(file);
			// 二维数组按行存入到文件中
			for (int i = 0; i < count; i++) {
				for (int j = 0; j < 5; j++) {
					// 将每个元素转换为字符串
					if ((dv.Check_uid(stu.data[i][0].toString()))) {
						content = String.valueOf(stu.data[i][j]) + "";
						out.write(content + "\t");
					} else {
						break;
					}
				}
				out.write("\r\n");
			}
			out.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "错误\n" + e, "系统错误", JOptionPane.ERROR_MESSAGE);

		}

	}

	/** 当添加何修改操作完成后重置这些值的boolean值，避免出现重言式 */
	public void resetCheck() {
		check_uid = false;
		check_name = false;
		check_Math = false;
		check_English = false;
		check_Computer = false;
		thisUID = null;
	}

	/**
	 * 学号查询界面,查询前会对数组以学号进行排序(默认排序) {@link #QuickSort(int[], int, int)}
	 * <p/>
	 * 查询时会使用对分查询方法{@link #Binary(String)}
	 * <p/>
	 * 其实长得跟addPageFromThis差不多的 ,原码见{@link #createAddPageFromThis()}
	 */
	public void searchByUID_Modify_Page() {
		toRefresh();//刷新一下
		JOptionPane.showMessageDialog(apffMenu, "为保证排序结果，在此界面下无法完成部分功能，退出后即可恢复", "提示", JOptionPane.WARNING_MESSAGE);
		fileMenu.setEnabled(false);// 防止打乱学号顺序禁用两功能
		editMenu.setEnabled(false);
		countMenu.setEnabled(false);
		JFrame sbup = new JFrame();// abbr.searchByUIDPage
		sbup.setBounds(400, 300, 450, 300);
		sbup.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		sbup.setTitle("学号查询界面");
		JPanel searchUID_Modify = new JPanel();
		searchUID_Modify.setLayout(null);
		sbup.getContentPane().add(searchUID_Modify, BorderLayout.CENTER);
		sbup.setVisible(true);
		sbup.setResizable(false);

		sbup.addWindowListener(new WindowAdapter() {// 窗口关闭事件，解除fileMenu和editMenu禁用状态

			public void windowClosing(WindowEvent e) {
				super.windowClosing(e);
				fileMenu.setEnabled(true);
				editMenu.setEnabled(true);
				countMenu.setEnabled(true);
			}

		});

		JLabel sUID = new JLabel();
		searchUID_Modify.add(sUID);
		sUID.setText("学号:");
		sUID.setBounds(10, 10, 75, 20);

		JLabel suidHints = new JLabel();
		searchUID_Modify.add(suidHints);
		suidHints.setBounds(201, 10, 230, 20);
		suidHints.setText("学号除自己外不能与其它学号重复");
		suidHints.setVisible(false);

		JTextField suid = new JTextField();
		searchUID_Modify.add(suid);
		suid.setBounds(90, 10, 100, 20);

		JLabel sname = new JLabel();
		searchUID_Modify.add(sname);
		sname.setText("姓  名:");
		sname.setBounds(10, 50, 75, 20);

		JLabel snameHints = new JLabel();
		searchUID_Modify.add(snameHints);
		snameHints.setText("姓名不为空");
		snameHints.setBounds(201, 50, 75, 20);
		snameHints.setVisible(false);

		JTextField snameText = new JTextField();
		searchUID_Modify.add(snameText);
		snameText.setBounds(90, 50, 100, 20);
		snameText.setEditable(false);

		JLabel smath = new JLabel();
		searchUID_Modify.add(smath);
		smath.setText("数学成绩：");
		smath.setBounds(10, 90, 75, 20);

		JLabel sMathScoreHints = new JLabel();
		searchUID_Modify.add(sMathScoreHints);
		sMathScoreHints.setText("数学成绩在0~100分之间");
		sMathScoreHints.setBounds(201, 90, 230, 20);
		sMathScoreHints.setVisible(false);

		JTextField sMathScore = new JTextField();
		sMathScore.setEditable(false);
		sMathScore.setBounds(90, 90, 100, 20);
		searchUID_Modify.add(sMathScore);

		JLabel sEnglish = new JLabel();
		searchUID_Modify.add(sEnglish);
		sEnglish.setText("英语成绩：");
		sEnglish.setBounds(10, 130, 75, 20);

		JLabel sEnglishScoreHints = new JLabel();
		searchUID_Modify.add(sEnglishScoreHints);
		sEnglishScoreHints.setText("英语成绩在0~100分之间");
		sEnglishScoreHints.setBounds(201, 130, 230, 20);

		sEnglishScoreHints.setVisible(false);
		JTextField sEnglishScore = new JTextField();
		searchUID_Modify.add(sEnglishScore);
		sEnglishScore.setBounds(90, 130, 100, 20);
		sEnglishScore.setEditable(false);

		JLabel sComputer = new JLabel();
		searchUID_Modify.add(sComputer);
		sComputer.setText("计算机成绩：");
		sComputer.setBounds(10, 170, 100, 20);

		JLabel sComputerScoreHints = new JLabel();
		searchUID_Modify.add(sComputerScoreHints);
		sComputerScoreHints.setText("计算机成绩在0~100分之间");
		sComputerScoreHints.setBounds(201, 170, 230, 20);

		sComputerScoreHints.setVisible(false);

		JTextField sComputerScore = new JTextField();
		sComputerScore.setBounds(90, 170, 100, 20);
		searchUID_Modify.add(sComputerScore);
		sComputerScore.setEditable(false);

		JButton toModify = new JButton("修改");
		searchUID_Modify.add(toModify);
		toModify.setBounds(40, 220, 60, 30);
		toModify.setForeground(Color.WHITE);
		toModify.setBackground(Color.GRAY);

		JButton cancel = new JButton("取消");
		searchUID_Modify.add(cancel);
		cancel.setBounds(40, 220, 60, 30);
		cancel.setVisible(false);

		JButton enter = new JButton("修改");
		searchUID_Modify.add(enter);
		enter.setBounds(140, 220, 60, 30);
		enter.setVisible(false);

		JButton delete = new JButton("删除");
		searchUID_Modify.add(delete);
		delete.setBounds(140, 220, 60, 30);
		delete.setVisible(true);

		JButton tosearch = new JButton("查询");
		searchUID_Modify.add(tosearch);
		tosearch.setBounds(240, 220, 60, 30);
		tosearch.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("null")
			@Override
			public void mouseReleased(MouseEvent e) {
				String getuid = suid.getText();
				if (getuid == null && getuid.length() == 0) {
					JOptionPane.showMessageDialog(tosearch, "输入为空！！！", "学号查询提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int result = Binary(getuid);
				if (result == -1) {
					JOptionPane.showMessageDialog(tosearch, "未查询到信息", "学号查询提示", JOptionPane.WARNING_MESSAGE);
					snameText.setText("");
					sMathScore.setText("");
					sEnglishScore.setText("");
					sComputerScore.setText(" ");
				} else {
					thisUID = stu.data[result][0].toString();
					snameText.setText(stu.data[result][1].toString());
					sMathScore.setText(stu.data[result][2].toString());
					sEnglishScore.setText(stu.data[result][3].toString());
					sComputerScore.setText(stu.data[result][4].toString());
				}
			}
		});

		JButton toExit = new JButton("退出");
		searchUID_Modify.add(toExit);
		toExit.setBounds(340, 220, 60, 30);
		toExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				fileMenu.setEnabled(true);
				editMenu.setEnabled(true);
				countMenu.setEnabled(true);
				thisUID = null;
				sbup.dispose();
			}
		});

		// 查询——》修改切换操作，就是改变一些组件是否可见，是否可编辑
		toModify.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int choice = JOptionPane.showConfirmDialog(enter, "此操作会修改学生信息\n是否继续？", "提示", JOptionPane.YES_NO_OPTION);
				if (choice == 1) {
					return;
				}
				JOptionPane.showMessageDialog(enter, "修改学生信息需要满足一切条件\n否则无法完成修改", "修改提示", JOptionPane.WARNING_MESSAGE);
				sbup.setTitle("学生信息修改");
				delete.setVisible(false);
				enter.setVisible(true);
				suidHints.setVisible(true);
				snameText.setEditable(true);
				snameHints.setVisible(true);
				sMathScore.setEditable(true);
				sMathScoreHints.setVisible(true);
				sEnglishScore.setEditable(true);
				sEnglishScoreHints.setVisible(true);
				sComputerScore.setEditable(true);
				sComputerScoreHints.setVisible(true);
				toModify.setVisible(false);
				cancel.setVisible(true);
			}
		});

		enter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (thisUID == null || thisUID.length() == 0) {
					JOptionPane.showMessageDialog(enter, "请查询后再修改\n查询仅以学号为准", "修改提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				boolean check = dv.CheckDataEfficiency(suid.getText(), snameText.getText(), sMathScore.getText(),
						sEnglishScore.getText(), sComputerScore.getText());
				if (check) {
					int result = Binary(thisUID);
					if (Binary(suid.getText()) != -1 && !thisUID.equals(suid.getText())) {
						JOptionPane.showMessageDialog(enter, "重复学号,修改失败", "修改提示", JOptionPane.ERROR_MESSAGE);
						return;
					}
					stu.data[result][0] = suid.getText();
					stu.data[result][1] = snameText.getText();
					stu.data[result][2] = sMathScore.getText();
					stu.data[result][3] = sEnglishScore.getText();
					stu.data[result][4] = sComputerScore.getText();
					double average = (Integer.parseInt(stu.data[result][2].toString())
							+ Integer.parseInt(stu.data[result][3].toString())
							+ Integer.parseInt(stu.data[result][4].toString())) * 1.0 / 3;
					stu.data[result][5] = String.format("%.2f", average);
					JOptionPane.showMessageDialog(enter, "修改成功！\n可以通过查询查看修改后结果", "修改提示", JOptionPane.PLAIN_MESSAGE);
					thisUID = null;
				} else {
					JOptionPane.showMessageDialog(enter, "修改失败，请检查后修改", "修改提示", JOptionPane.ERROR_MESSAGE);
				}
				resetCheck();
			}
		});

		cancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				thisUID = null;
				suid.setText("");
				snameText.setText("");
				sMathScore.setText("");
				sEnglishScore.setText("");
				sComputerScore.setText(" ");
				sbup.setTitle("学生信息查询");
				delete.setVisible(true);
				enter.setVisible(false);
				suidHints.setVisible(false);
				snameText.setEditable(false);
				snameHints.setVisible(false);
				sMathScore.setEditable(false);
				sMathScoreHints.setVisible(false);
				sEnglishScore.setEditable(false);
				sEnglishScoreHints.setVisible(false);
				sComputerScore.setEditable(false);
				sComputerScoreHints.setVisible(false);
				cancel.setVisible(false);
				toModify.setVisible(true);

			}
		});

		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (thisUID == null || thisUID.length() == 0) {
					JOptionPane.showMessageDialog(enter, "请查询后再删除\n查询仅以学号为准", "删除提示", JOptionPane.WARNING_MESSAGE);
					return;
				}
				int result = Binary(thisUID);
				int choice = JOptionPane.showConfirmDialog(delete, "是否删除学号为：" + thisUID + "的学生？", "删除提示",
						JOptionPane.YES_NO_OPTION);
				if (choice == 1) {
					return;
				}
				stu.deleteIndexNode(result);
				stu.remove(result + 1);
				JOptionPane.showMessageDialog(enter, "成功删除学号为" + thisUID + "的学生信息", "删除提示", JOptionPane.PLAIN_MESSAGE);
				suid.setText("");
				snameText.setText("");
				sMathScore.setText("");
				sEnglishScore.setText("");
				sComputerScore.setText(" ");
				count--;
				thisUID = null;
			}
		});

	}

	public void getLineData() {
		uidInfos = stu.getCols(1).toString().split("\\t");
		nameInfos = stu.getCols(2).toString().split("\\t");
		MathInfos = stu.getCols(3).toString().split("\\t");
		EnglishInfos = stu.getCols(4).toString().split("\\t");
		ComputerInfos = stu.getCols(5).toString().split("\\t");
		avgInfos = stu.getCols(6).toString().split("\\t");
		CanRead = false;// 只能读取一次,防止数据被覆盖
	}

	public void SearchByUID() {

		CanRead = true;
		getLineData();
		String infos[] = uidInfos;
		int[] base = new int[count];
		if (infos == null || count == 0) {
			return;
		}
		for (int i = 0; i < count; i++) {
			base[i] = Integer.parseInt(infos[i]);
		}
		QuickSort(base, 0, base.length - 1);// 查找uid使用的对分查询，就必须要对学号进行排序
		for (int i = 0; i < count; i++) {
			stu.data[i][0] = uidInfos[i];
			stu.data[i][1] = nameInfos[i];
			stu.data[i][2] = MathInfos[i];
			stu.data[i][3] = EnglishInfos[i];
			stu.data[i][4] = ComputerInfos[i];
			stu.data[i][5] = avgInfos[i];
		}
	}

	/**
	 * 归并排序 包含 {@link #MergeSort(double[])},{@link #merge(double[], int, int, int)};
	 * 
	 * @link #sort(double[] base, int left, int right)}
	 */
	public void MergeSort(double[] base) {
		if (stu.isEmpty()) {
			return;
		}
		if (CanRead) {
			getLineData();
		}
		sort(base, 0, base.length - 1);

	}

	public void sort(double[] base, int left, int right) {
		if (left == right) {
			return;
		}
		int mid = left + ((right - left) >> 1);
		// 对左侧子序列进行递归排序
		sort(base, left, mid);
		// 对右侧子序列进行递归排序
		sort(base, mid + 1, right);
		// 合并
		merge(base, left, mid, right);
	}

	public void merge(double[] base, int left, int mid, int right) {
		double[] temp = new double[right - left + 1];
		String[] tempUId = new String[right - left + 1];
		String[] tempName = new String[right - left + 1];
		String[] tempMath = new String[right - left + 1];
		String[] tempEnglish = new String[right - left + 1];
		String[] tempComputer = new String[right - left + 1];
		int i = 0;
		int p1 = left;
		int p2 = mid + 1;
		// 比较左右两部分的元素，哪个大，把那个元素填入temp中
		while (p1 <= mid && p2 <= right) {
			if (base[p1] > base[p2]) {
				temp[i] = base[p1];
				tempUId[i] = uidInfos[p1];
				tempName[i] = nameInfos[p1];
				tempMath[i] = MathInfos[p1];
				tempEnglish[i] = EnglishInfos[p1];
				tempComputer[i] = ComputerInfos[p1];
				i++;
				p1++;

			} else {
				temp[i] = base[p2];
				tempUId[i] = uidInfos[p2];
				tempName[i] = nameInfos[p2];
				tempMath[i] = MathInfos[p2];
				tempEnglish[i] = EnglishInfos[p2];
				tempComputer[i] = ComputerInfos[p2];
				i++;
				p2++;
			}

		}
		// 上面的循环退出后，把剩余的元素依次填入到temp中
		// 以下两个while只有一个会执行
		while (p1 <= mid) {
			temp[i] = base[p1];
			temp[i] = base[p1];
			tempUId[i] = uidInfos[p1];
			tempName[i] = nameInfos[p1];
			tempMath[i] = MathInfos[p1];
			tempEnglish[i] = EnglishInfos[p1];
			tempComputer[i] = ComputerInfos[p1];
			i++;
			p1++;
		}
		while (p2 <= right) {
			temp[i] = base[p2];
			tempUId[i] = uidInfos[p2];
			tempName[i] = nameInfos[p2];
			tempMath[i] = MathInfos[p2];
			tempEnglish[i] = EnglishInfos[p2];
			tempComputer[i] = ComputerInfos[p2];
			i++;
			p2++;
		}
		// 把最终的排序的结果复制给原数组
		for (i = 0; i < temp.length; i++) {
			base[left + i] = temp[i];
			uidInfos[left + i] = tempUId[i];
			nameInfos[left + i] = tempName[i];
			MathInfos[left + i] = tempMath[i];
			EnglishInfos[left + i] = tempEnglish[i];
			ComputerInfos[left + i] = tempComputer[i];
		}
	}

	/**
	 * 堆排序
	 */
	public void HeapSort() {
		if (stu.isEmpty()) {
			return;
		}
		getLineData();
		int[] base = new int[count];
		for (int i = 0; i < count; i++) {
			base[i] = Integer.parseInt(ComputerInfos[i]);
		}
		sort(base);
		for (int i = 0; i < count; i++) {
			stu.data[i][4] = base[i];
		}
	}

	/**
	 * 堆排序
	 * 
	 * @param base
	 */
	public void sort(int[] base) {
		// 构建堆
		for (int i = base.length / 2 - 1; i >= 0; i--) {
			// 从第一个非叶子结点从下至上，从右至左调整结构
			HeapSort(base, i, base.length);
		}
		// 调整堆结构+交换堆顶元素与末尾元素
		for (int j = base.length - 1; j > 0; j--) {
			swap(base, 0, j);// 将堆顶元素与末尾元素进行交换
			String tempEnglish = EnglishInfos[0];
			EnglishInfos[0] = EnglishInfos[j];
			EnglishInfos[j] = tempEnglish;
			HeapSort(base, 0, j);// 重新对堆进行调整
		}

	}

	/**
	 * 
	 * 堆排序
	 * 
	 * @param base
	 * @param i
	 * @param length
	 */
	public void HeapSort(int[] base, int i, int length) {
		int temp = base[i];// 先取出当前元素i
		String tempUid = uidInfos[i];
		String tempName = nameInfos[i];
		String tempMath = MathInfos[i];
		String tempEnglish = EnglishInfos[i];
		String tempAvg = avgInfos[i];
		for (int k = i * 2 + 1; k < length; k = k * 2 + 1) {// 从i结点的左子结点开始，也就是2i+1处开始
			if (k + 1 < length && base[k] > base[k + 1]) {// 如果左子结点大于右子结点，k指向右子结点
				k++;
			}
			if (base[k] < temp) {// 如果子节点小于父节点，将子节点值赋给父节点（不用进行交换）
				base[i] = base[k];
				uidInfos[i] = uidInfos[k];
				nameInfos[i] = nameInfos[k];
				MathInfos[i] = MathInfos[k];
				EnglishInfos[i] = EnglishInfos[k];
				avgInfos[i] = avgInfos[k];
				i = k;
			} else {
				break;
			}
		}
		base[i] = temp;// 将temp值放到最终的位置
		uidInfos[i] = tempUid;
		nameInfos[i] = tempName;
		MathInfos[i] = tempMath;
		EnglishInfos[i] = tempEnglish;
		avgInfos[i] = tempAvg;
	}

	/**
	 * 交换元素
	 * 
	 * @param base
	 * @param a
	 * 
	 * @param b
	 */
	public void swap(int[] base, int a, int b) {
		int temp = base[a];
		String tempUid = uidInfos[a];
		String tempName = nameInfos[a];
		String tempMath = MathInfos[a];
		String tempAvg = avgInfos[a];

		base[a] = base[b];
		uidInfos[a] = uidInfos[b];
		nameInfos[a] = nameInfos[b];
		MathInfos[a] = MathInfos[b];
		avgInfos[a] = avgInfos[b];

		base[b] = temp;
		uidInfos[b] = tempUid;
		nameInfos[b] = tempName;
		MathInfos[b] = tempMath;
		avgInfos[b] = tempAvg;
	}

	/**
	 * 选择排序；
	 */
	public void SelectionSort() {
		getLineData();
		int[] base = new int[count];
		for (int i = 0; i < count; i++) {
			base[i] = Integer.parseInt(EnglishInfos[i]);
		}
		for (int i = 0; i < base.length; i++) {
			int min = i;
			for (int j = i + 1; j < base.length; j++) {
				if (base[j] > base[min]) {
					min = j;

				}
			}
			if (min != i) {
				swap(base, i, min);
				String tempComputer = ComputerInfos[i];
				ComputerInfos[i] = ComputerInfos[min];
				ComputerInfos[min] = tempComputer;
			}
		}
		for (int i = 0; i < count; i++) {
			stu.data[i][3] = base[i];
		}
	}

	/**
	 * 插入排序
	 */
	public void Insertion() {
		if (stu.isEmpty()) {
			return;
		}
		// 数据转化，分割数据
		String infos[] = stu.getCols(3).toString().split("\\t");
		Object[][] temp = new Object[1][6];
		int[] base = new int[count];
		if (infos == null || count == 0) {
			return;
		}
		// 转化为integer
		for (int i = 0; i < count; i++) {
			base[i] = Integer.parseInt(infos[i]);
		}
		//
		for (int index = 1; index < base.length; index++) {
			// 设置临时值（就是要插入的值）
			int front = index - 1;
			for (int j = 0; j < 6; j++) {
				temp[0][j] = stu.data[index][j];
			}
			int baseTemp = base[index];
			while (front >= 0 && base[index] > base[front]) {
				front--;
			}
			if (front + 1 != index) {
				for (int i = index - 1; i > front; i--) {
					for (int j = 0; j < 6; j++) {
						stu.data[i + 1][j] = stu.data[i][j];
						base[i + 1] = base[i];
					}

				}
				for (int j = 0; j < 6; j++) {
					stu.data[front + 1][j] = temp[0][j];
					base[front + 1] = baseTemp;
				}
			}
		}
	}

	/**
	 * 快速排序
	 * 
	 * @param base
	 * @param left
	 * @param right
	 */
	public void QuickSort(int[] base, int left, int right) {
		if (stu.isEmpty()) {
			return;
		}
		if (left < right) {
			// 定义基准数
			if (CanRead) {
				getLineData();
			}
			int pivotIOD = base[left];
			String pivoUID = uidInfos[left];
			String pivotName = nameInfos[left];
			String pivotMath = MathInfos[left];
			String pivoEnglish = EnglishInfos[left];
			String pivoComputer = ComputerInfos[left];
			String pivoAvg = avgInfos[left];
			// 记录序列的开始
			int begin = left;
			// 记录序列的结束
			int end = right;
			while (right > left) {
				// 当队尾元素小于基准数就将队尾插到队首
				if (base[right] < pivotIOD) {
					base[left] = base[right];
					uidInfos[left] = uidInfos[right];
					nameInfos[left] = nameInfos[right];
					MathInfos[left] = MathInfos[right];
					EnglishInfos[left] = EnglishInfos[right];
					ComputerInfos[left] = ComputerInfos[right];
					avgInfos[left] = avgInfos[right];
					// 并将队首索引后移
					left++;
				} else {
					// 当队尾元素大于基准数就将队尾索引前移
					// 并继续从队尾比较
					right--;
					continue;
				}
				while (right > left) {
					// 当队首元素大于基准数就将队首位置插到队尾
					if (base[left] > pivotIOD) {
						// 将队尾索引前移
						base[right] = base[left];
						uidInfos[right] = uidInfos[left];
						nameInfos[right] = nameInfos[left];
						MathInfos[right] = MathInfos[left];
						EnglishInfos[right] = EnglishInfos[left];
						ComputerInfos[right] = ComputerInfos[left];
						avgInfos[right] = avgInfos[left];
						break;
					} else {
						// 当队首元素小于基准数就将队首索引前移
						// 并继续从队首比较
						left++;
					}
				}
			}
			// 两者重合时，就将基准数插入此位置
			base[left] = pivotIOD;
			uidInfos[left] = pivoUID;
			nameInfos[left] = pivotName;
			MathInfos[left] = pivotMath;
			EnglishInfos[left] = pivoEnglish;
			ComputerInfos[left] = pivoComputer;
			avgInfos[left] = pivoAvg;
			QuickSort(base, begin, left - 1);
			QuickSort(base, left + 1, end);
		}
	}

	public void Binary_Before() {// 提示作用框
		int choice = JOptionPane.showConfirmDialog(null, "此操作会重新对数据进行排序\n是否继续？", "提示", JOptionPane.YES_NO_OPTION);
		if (choice == 1) {
			return;
		}
		SearchByUID();
		searchByUID_Modify_Page();
	}

	/**
	 * 对分查找 找到了返回当前数组行数，找不到返回-1
	 * 
	 * @param string
	 * @return int
	 */
	public int Binary(String string) {
		if (!(dv.Check_uid(string))) {// 检查学号输入是否合法
			return -1;
		}
		int suid = Integer.parseInt(string);// 转化
		String[] infos = stu.getCols(1).toString().split("\\t");
		int[] base = new int[count];
		for (int i = 0; i < count; i++) {
			base[i] = Integer.parseInt(infos[i]);
		}
		int left = 0;
		int right = count - 1;
		do {
			int mid = (left + right) / 2;
			if (suid == base[left]) {// 是否刚好在左边
				return left;
			}
			if (suid == base[mid]) {// 是否刚好在中间
				return mid;
			}
			if (suid == base[right]) {// 是否刚好在右边
				return right;
			}
			if (suid < base[mid]) {// 判断值与中间节点值的大小，如果比中间节点小，搜索范围向左边靠，反之向右边靠
				right = mid;
			} else {
				left = mid;
			}
		} while (right - left > 1);// 两节点相邻时，搜索结束
		return -1;
	}

	public int Sequential(String name){
		squentialPage = new JFrame();
		squentialPage.setBounds(200, 400, 600, 300);
		squentialPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container cs = new Container();
		cs = squentialPage.getContentPane();
		JTextArea text = new JTextArea();
		JScrollPane JSPane = new JScrollPane(text);
		cs.add(JSPane, BorderLayout.CENTER);
		squentialPage.setVisible(true);
		squentialPage.setResizable(false);
		text.setEditable(false);
		String infos[] = stu.getCols(2).toString().split("\\t");
		text.append("\t\t查询到以下结果");
		text.setText("");
		int result = 0;
		text.append("学号" + "\t" + "姓名" + "\t" + "数学" + "\t" + "英语" + "\t" + "计算机" + "\t" + "平均分" + "\n");
		for (int i = 0; i < count; i++) {
			if (name.equals(infos[i])) {
				String info = stu.getRows(i).toString();
				text.append(info);
				text.append("\n");
				result++;
			}
		}
		if (result == 0) {
			JOptionPane.showMessageDialog(null, "未查询到结果", "查询提示", JOptionPane.ERROR_MESSAGE);
			return 0;
		} else {
			JOptionPane.showMessageDialog(null, "共查询到" + result + "个结果", "查询提示", JOptionPane.PLAIN_MESSAGE);
			return result;
		}
	}

	public void Count() {
		if (stu.isEmpty()) {
			return;
		}
		int choice = JOptionPane.showConfirmDialog(null, "此操作会对数据进行多次排序，是否继续？", "操作提示", JOptionPane.YES_NO_OPTION);
		if (choice == 1) {
			return;
		}
		csp = new JFrame();
		csp.setBounds(200, 400, 850, 200);
		csp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container cs = new Container();
		cs = csp.getContentPane();
		JTextArea text = new JTextArea();
		JScrollPane JSPane = new JScrollPane(text);
		cs.add(JSPane, BorderLayout.CENTER);
		csp.setVisible(true);
		csp.setResizable(false);
		text.setEditable(false);
		text.setText("科目" + "\t" + "最高分" + "\t" + "最低分" + "\t" + "平均分" + "\t" + "优秀" + "\t" + "良好" + "\t" + "中等" + "\t"
				+ "及格" + "\t" + "不及格" + "\n");
		for (int i = 3; i < 6; i++) {
			switch (i) {
			case 3:
				Insertion();
				text.append("\n" + "数学" + "\t");
				break;
			case 4:
				SelectionSort();
				text.append("英语" + "\t");
				break;
			case 5:
				HeapSort();
				text.append("计算机" + "\t");
				break;
			}
			int sum = 0;
			double avg = 0.0;
			String infos[] = stu.getCols(i).toString().split("\\t");
			int[] base = new int[count];
			int[] range = new int[count];
			for (int j = 0; j < count; j++) {
				base[j] = Integer.parseUnsignedInt(infos[j]);
				range[j] = base[j] / 10;
				sum = sum + base[j];
			}
			avg = sum * 1.0 / base.length;
			text.append(base[0] + "\t" + base[base.length - 1] + "\t" + String.format("%.2f", avg) + "\t");
			int A = 0, B = 0, C = 0, D = 0, E = 0;
			for (int index = 0; index < range.length; index++) {
				switch (range[index]) {
				case 10:
					A++;
					break;
				case 9:
					B++;
					break;
				case 8:
					B++;
					break;
				case 7:
					C++;
					break;
				case 6:
					D++;
					break;
				default:
					E++;
				}
			}
			text.append(A + "\t" + B + "\t" + C + "\t" + D + "\t" + E + "\t" + "\n\n");
		}
	}
	public static void main(String [] args) {
		new InitialPage();
	}
}
