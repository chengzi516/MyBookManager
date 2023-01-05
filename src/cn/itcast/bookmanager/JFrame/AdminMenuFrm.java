package cn.itcast.bookmanager.JFrame;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import cn.itcast.bookmanager.dao.BookTypeDao;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import com.mysql.jdbc.Connection;
import cn.itcast.bookmanager.model.BookType;
import cn.itcast.bookmanager.utils.DbUtil;
import cn.itcast.bookmanager.utils.toolUtil;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.Font;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import java.awt.Color;
import javax.swing.ImageIcon;

public class AdminMenuFrm extends JFrame {
	private JFrame jf;
	private JTextField textField;
	private JButton btnNewButton;
	private JTextArea textArea;
	DbUtil dbUtil=new DbUtil();
	BookTypeDao bookTypeDao=new BookTypeDao();
	public AdminMenuFrm(){
		jf=new JFrame("管理员界面");
		jf.setBounds(400, 100, 600, 429);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		
		JLabel label = new JLabel();
		label.setFont(new Font("幼圆", Font.BOLD, 14));
		label.setText("类别说明：");
		label.setBounds(112, 107, 75, 26);
		jf.getContentPane().add(label);
		
		JLabel label_1 = new JLabel();
		label_1.setFont(new Font("幼圆", Font.BOLD, 14));
		label_1.setText("类别名称：");
		label_1.setBounds(112, 38, 75, 26);
		jf.getContentPane().add(label_1);
		
		textField = new JTextField();
		textField.setBounds(197, 38, 241, 26);
		jf.getContentPane().add(textField);
		
		btnNewButton = new JButton("添加");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String typeName = textField.getText();
				String typeRemark = textArea.getText();
				if (toolUtil.isEmpty(typeName) || toolUtil.isEmpty(typeRemark)) {
					JOptionPane.showMessageDialog(null, "请输入相关信息");
					return;
				}
				BookType bookType = new BookType();
				bookType.setTypeName(typeName);
				bookType.setRemark(typeRemark);
				Connection con = null;
				try {
					con = dbUtil.getConnection();
					int i = bookTypeDao.add(con, bookType);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "添加成功");
						reset();
					}else if(i == 2){
						JOptionPane.showMessageDialog(null, "添加失败,类别已存在");
					} else {
						JOptionPane.showMessageDialog(null, "添加失败");
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}finally{
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton.setBounds(182, 281, 80, 26);
		jf.getContentPane().add(btnNewButton);
		
		JButton button = new JButton("重置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		button.setFont(new Font("幼圆", Font.BOLD, 14));
		button.setBounds(360, 281, 80, 26);
		jf.getContentPane().add(button);
		
		textArea = new JTextArea();
		textArea.setColumns(20);
		textArea.setRows(5);
		textArea.setBackground(Color.WHITE);
		textArea.setBounds(197, 109, 241, 132);
		jf.getContentPane().add(textArea);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AdminMenuFrm.class.getResource("/tupian/adBG2.png")));
		lblNewLabel.setBounds(0, 0, 584, 370);
		jf.getContentPane().add(lblNewLabel);
		
		JMenuBar menuBar = new JMenuBar();
		jf.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("类别管理");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("类别添加");
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("类别修改");
		mntmNewMenuItem_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBTypeEdit();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_2 = new JMenu("书籍管理");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("书籍添加");
		mntmNewMenuItem_2.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBookAdd();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("书籍修改");
		mntmNewMenuItem_3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBookEdit();
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
		JMenu menu1 = new JMenu("用户管理");
		menuBar.add(menu1);
		
		JMenuItem mntmNewMenuItem_4 = new JMenuItem("用户信息");
		mntmNewMenuItem_4.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminUserInfo();
			}
		});
		menu1.add(mntmNewMenuItem_4);
		
		JMenuItem mntmNewMenuItem_5 = new JMenuItem("借阅信息");
		mntmNewMenuItem_5.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminBorrowInfo();
			}
		});
		menu1.add(mntmNewMenuItem_5);
		
		JMenu mnNewMenu_1 = new JMenu("退出系统");
		mnNewMenu_1.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				JOptionPane.showMessageDialog(null, "欢迎再次使用");
				jf.dispose();
			}
		});

		JMenu mnNewMenu_3 = new JMenu("反馈问题处理");
		mnNewMenu_3.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new FeedBackEdit();
			}
		});
		menuBar.add(mnNewMenu_3);
		
		JMenu statistAnalysis = new JMenu("统计分析");
		statistAnalysis.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminStatistAnalysis();
			}
		});
		menuBar.add(statistAnalysis);

		JMenu marketTrends = new JMenu("市场趋势");
		marketTrends.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminMarketTrends();
			}
		});
		menuBar.add(marketTrends);

		JMenu userPortrait = new JMenu("用户画像");
		userPortrait.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminUserPortrait();
			}
		});
		menuBar.add(userPortrait);
	
		
		
		
		jf.setVisible(true);
		jf.setResizable(true);
	}
	protected void reset() {
		textField.setText("");
		textArea.setText("");
	}
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new AdminMenuFrm();
	}
}
