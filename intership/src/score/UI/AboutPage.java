package score.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class AboutPage extends JFrame {
	private static final long serialVersionUID = -6736454674070258636L;

	/**
	 * 关于）系统(OLD FU)的介绍(图片)
	 */
	public AboutPage() {
		JFrame aboutPage = new JFrame();
		JPanel panel = new JPanel(new BorderLayout());

		JLabel systemName = new JLabel();// 项目名
		panel.add(systemName);
		systemName.setBounds(30, 20, 900, 60);
		systemName.setText("学  生  成  绩  管  理  系  统");
		systemName.setFont(new Font("黑体", Font.BOLD, 40));
		systemName.setForeground(Color.blue);

		JLabel support = new JLabel();// “赞助商”
		panel.add(support);
		support.setBounds(200, 90, 300, 30);
		support.setText("OldFu  友  情  支  持");
		support.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 15));
		support.setForeground(Color.green);

		JLabel team = new JLabel();
		panel.add(team);
		team.setBounds(30, 150, 130, 60);
		team.setText("组名：");
		team.setFont(new Font("黑体", Font.CENTER_BASELINE, 30));

		JLabel teamName = new JLabel();// 小组名
		panel.add(teamName);
		teamName.setBounds(150, 150, 500, 60);
		teamName.setText("秦 王 扫 刘 何");
		teamName.setFont(new Font("宋体", Font.CENTER_BASELINE, 30));
		teamName.setForeground(Color.red);

		JLabel time = new JLabel();
		panel.add(time);
		time.setBounds(430, 300, 60, 60);
		time.setText("日期:");
		time.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));

		JLabel time_F = new JLabel();
		panel.add(time_F);
		time_F.setBounds(495, 300, 200, 60);
		time_F.setText("2023年6月7日");
		time_F.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));
		time_F.setForeground(Color.white);

		JLabel language = new JLabel();
		panel.add(language);
		language.setBounds(30, 250, 90, 35);
		language.setText("语言：");
		language.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));
		language.setForeground(new Color(153, 95, 95));

		JRadioButton Chinese = new JRadioButton();
		panel.add(Chinese);
		Chinese.setText("中文");
		Chinese.setBounds(130, 250, 130, 35);
		Chinese.setBackground(new Color(184, 173, 181));
		Chinese.setSelected(true);

		JRadioButton English = new JRadioButton();
		panel.add(English);
		English.setText("英文");
		English.setBounds(270, 250, 80, 35);
		English.setBackground(new Color(184, 173, 181));

		ButtonGroup languageChoice = new ButtonGroup();
		languageChoice.add(Chinese);
		languageChoice.add(English);

		String urlString = "OldFu.png";// 背景图片
		JLabel label = new JLabel(new ImageIcon(urlString));
		panel.add(label, BorderLayout.CENTER);
		aboutPage.getContentPane().add(panel, BorderLayout.CENTER);
		aboutPage.setSize(937, 531);
		aboutPage.setLocation(300, 200);
		aboutPage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		aboutPage.setTitle("关于系统");
		aboutPage.setVisible(true);
		aboutPage.setResizable(false);

		English.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				English.setSelected(true);// 将以下名字改成英文即可
				aboutPage.setTitle("About this");
				
				systemName.setText("Student Performance Management System");
				systemName.setFont(new Font("Times New Roman", Font.BOLD, 40));
				
				support.setText("Supported By:OldFu");
				support.setFont(new Font("Arial", Font.CENTER_BASELINE, 15));
				
				team.setText("Group Name:");
				team.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
				
				teamName.setText("Mr.Qin beated the group of members");
				teamName.setFont(new Font("Blackadder ITC", Font.CENTER_BASELINE, 20));
				
				language.setText("Language");
				language.setFont(new Font("Times New Roman", Font.CENTER_BASELINE, 20));
				Chinese.setText("Simplified Chinese");
				
				English.setText("English");
				
				time.setText("Date:");
				
				time_F.setText("June,7th,2023");

			}
		});

		Chinese.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Chinese.setSelected(true);
				aboutPage.setTitle("关于系统");
				
				systemName.setText("学  生  成  绩  管  理  系  统");
				systemName.setFont(new Font("黑体", Font.BOLD, 40));
				
				support.setText("OldFu  友  情  支  持");
				support.setFont(new Font("微软雅黑", Font.CENTER_BASELINE, 15));
				
				team.setText("组名：");
				team.setFont(new Font("黑体", Font.CENTER_BASELINE, 30));
				
				teamName.setText("秦 王 扫 刘 何");
				teamName.setFont(new Font("宋体", Font.CENTER_BASELINE, 30));
				
				time.setText("日期:");
				time.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));
				
				time_F.setText("2023年6月7日");
				time_F.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));
				
				language.setText("语言：");
				language.setFont(new Font("宋体", Font.CENTER_BASELINE, 20));
			
				Chinese.setText("中文");
				English.setText("英文");
			}

		});
	}
}
