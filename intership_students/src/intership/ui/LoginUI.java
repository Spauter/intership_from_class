package intership.ui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

import intership.Util.LoginHistoryUtil;
import intership.dao.LoginDAO;
import utils.Utils;
import javax.swing.JTextField;
/**
 * 感谢OldFu友情提供登录界面
 * @author 32306
 *
 */
public class LoginUI {
	public static List<Map<String, Object>> list;
	LoginHistoryUtil lh = new LoginHistoryUtil();


	public void createWindown() {
		JFrame jf = new JFrame("皇家工学院校园学生管理系统");
		Font font = new Font("仿宋", Font.BOLD, 20);
		Font font2 = new Font("仿宋", Font.BOLD, 60);
		ImageIcon bg = new ImageIcon(".\\images\\1.png");
		JLabel jl = new JLabel(bg);
		jl.setSize(bg.getIconWidth(), bg.getIconHeight());
		jf.getContainerListeners();
		jf.getLayeredPane().add(jl, new Integer(Integer.MIN_VALUE));
		JPanel jp = (JPanel) jf.getContentPane();
		jp.setOpaque(false);
		FlowLayout flowLayout = new FlowLayout();
		jp.setLayout(flowLayout);

		JLabel jl2 = new JLabel("学生管理系统");
		jl2.setForeground(Color.BLACK);
		jl2.setFont(font2);
		jp.add(jl2);
		jp.setLayout(null);
		jl2.setBounds(280, 20, 400, 90);

		JLabel jl5 = new JLabel("用户名:");
		jl5.setForeground(Color.BLACK);
		jp.add(jl5);
		jp.setLayout(null);
		jl5.setBounds(300, 150, 400, 90);

		JLabel jl6 = new JLabel("密码:");
		jl6.setForeground(Color.BLACK);
		jp.add(jl6);
		jp.setLayout(null);
		jl6.setBounds(310, 200, 400, 90);

		JTextField jb = new JTextField();
		jb.setBounds(360, 180, 200, 30);
		jb.setFont(font);
		jp.add(jb);

		JPasswordField jb2 = new JPasswordField();
		jb2.setEchoChar('*');
		jb2.setBounds(360, 230, 200, 30);
		jb2.setFont(font);
		jp.add(jb2);

		JButton jb3 = new JButton("登录");
		jb3.setIcon(new ImageIcon(LoginUI.class.getResource("/org/eclipse/jface/wizard/images/stop.png")));
		jb3.setBounds(400, 270, 113, 40);
		jb3.setFont(font);
		jp.add(jb3);

		JRadioButton jr1 = new JRadioButton("学生");
		jr1.setBounds(610, 180, 50, 30);
		jr1.setBackground(new Color(118, 176, 254));
		JRadioButton jr2 = new JRadioButton("教师");
		jr2.setBounds(610, 230, 50, 30);
		jr2.setBackground(new Color(137, 190, 254));
		JRadioButton jr3 = new JRadioButton("管理员");
		jr3.setHorizontalAlignment(SwingConstants.LEFT);
		jr3.setBounds(610, 280, 80, 30);
		jr3.setBackground(new Color(137, 190, 254));
		ButtonGroup group = new ButtonGroup();
		group.add(jr1);
		group.add(jr2);
		group.add(jr3);
		jp.add(jr1);
		jp.add(jr2);
		jp.add(jr3);

		javax.swing.ImageIcon iconjt = new javax.swing.ImageIcon(".\\images\\jintao.jpg");
		JLabel jl3 = new JLabel(iconjt);
		jl3.setBounds(741, 0, 150, 150);
		iconjt.setImage(iconjt.getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT));

		JLabel label = new JLabel("New label");
		jf.getContentPane().add(label);
		jp.add(jl3);

		JLabel jl4 = new JLabel("遇事不决，且问阿掏");
		jl4.setBounds(365, 510, 300, 40);
		jl4.setFont(font);
		jl4.setForeground(Color.DARK_GRAY);
		jp.add(jl4);

		jf.setSize(900, 600);
		jf.setLocationRelativeTo(null);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(1);
		jf.setVisible(true);

		jb3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = jb.getText();
				char[] password = jb2.getPassword();
				String passwordString = new String(password);
				try {
					if (jr1.isSelected()) {
						list = ld.selectStudent(name, passwordString);
						Utils.Check(isNull(list), "账号名或者密码错误");
						JOptionPane.showMessageDialog(null, "欢迎您:  " + list.get(0).get("S_NAME"), "提示",
								JOptionPane.PLAIN_MESSAGE);
						jf.dispose();
						StudentMainUI student = new StudentMainUI();
						lh.FileOutOperator1(list.get(0).get("S_ID").toString());
						student.open();
					} else if (jr2.isSelected()) {
						list = ld.selectTeacher(name, passwordString);
						Utils.Check(isNull(list), "账号或者密码错误");
						JOptionPane.showMessageDialog(null, "欢迎您:  " + list.get(0).get("T_NAME") + "老师", "提示",
								JOptionPane.PLAIN_MESSAGE);
						lh.FileOutOperator(list.get(0).get("T_ID").toString());
						jf.dispose();
						TeacherMainUI search = new TeacherMainUI();
						search.open();
					} else if (jr3.isSelected()) {
						// 加入dao层的select，返回list值，获得登录人员身份是否相符，如果list为空，返回登录失败
						// 插入
						list=ld.selectAdmin(name, passwordString);
						Utils.Check(isNull(list), "账号或者密码错误");
						jf.dispose();
						lh.FileOutOperator(list.get(0).get("ANAME").toString());
						AdminiMainUI ad = new AdminiMainUI();
						ad.open();
					} else {
						JOptionPane.showMessageDialog(jb3, "未选择身份", "提示", JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(jb3, e1.getMessage(), "提示", JOptionPane.ERROR_MESSAGE);
				}
			}
			
		});
	}
	

	public boolean isNull(List<Map<String, Object>> list) {
		return list == null || list.isEmpty();
	}

	public static void main(String[] args) {
		new LoginUI().createWindown();
	}

	LoginDAO ld = new LoginDAO();
	
}
