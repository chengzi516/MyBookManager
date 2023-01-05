package cn.itcast.bookmanager.JFrame;


import cn.itcast.bookmanager.dao.BookDao;
import cn.itcast.bookmanager.dao.FeedBackDao;
import cn.itcast.bookmanager.model.Book;
import cn.itcast.bookmanager.model.BookType;
import cn.itcast.bookmanager.utils.DbUtil;
import cn.itcast.bookmanager.utils.toolUtil;
import com.mysql.jdbc.Connection;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Vector;

public class FeedBackEdit extends JFrame {
	private JFrame jf;
	private JTextField textField;
	private JTable table;
	private DefaultTableModel model;
	DbUtil dbUtil=new DbUtil();
	FeedBackDao feedBackDao=new FeedBackDao();
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	public FeedBackEdit(){
		jf=new JFrame("问题反馈");
		jf.setBounds(400, 50, 600, 672);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "问题反馈处理", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_1.setBounds(20, 105, 541, 195);


		JMenuBar menuBar = new JMenuBar();
		jf.setJMenuBar(menuBar);

		JMenu mnNewMenu = new JMenu("类别管理");
		menuBar.add(mnNewMenu);

		JMenuItem mntmNewMenuItem = new JMenuItem("类别添加");
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				jf.dispose();
				new AdminMenuFrm();
			}
		});
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
		menuBar.add(mnNewMenu_1);

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
		
		 /*做一个表头栏数据  一位数组
		  * */
		String[] title={"序号","标题","问题描述","联系方式","提交时间","问题状态"};
		/*具体的各栏行记录 先用空的二位数组占位*/
		 String[][] dates={};
		 /*然后实例化 上面2个控件对象*/
		 model=new DefaultTableModel(dates,title);
		 table=new JTable(model);
		 putDates();//获取数据库数据放置table中
		  panel_1.setLayout(null);
		  JScrollPane jscrollpane = new JScrollPane();
		  jscrollpane.setBounds(20, 22, 496, 154);
			jscrollpane.setViewportView(table);
			panel_1.add(jscrollpane);
			jf.getContentPane().add(panel_1);
		jf.getContentPane().add(panel_1);
		
		table.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent evt) {
				tableMousePressed(evt);
			}
		});
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(20, 310, 541, 292);
		jf.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		JLabel label = new JLabel("编号：");
		label.setFont(new Font("幼圆", Font.BOLD, 14));
		label.setBounds(58, 10, 45, 27);
		panel_2.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(101, 10, 129, 27);
		textField_1.setEnabled(false);
		panel_2.add(textField_1);
		
		JLabel label_1 = new JLabel("标题：");
		label_1.setFont(new Font("幼圆", Font.BOLD, 14));
		label_1.setBounds(294, 10, 45, 27);
		panel_2.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(338, 10, 128, 27);
		textField_2.setEnabled(false);
		panel_2.add(textField_2);
		
		JLabel label_2 = new JLabel("问题描述：");
		label_2.setFont(new Font("幼圆", Font.BOLD, 14));
		label_2.setBounds(28, 58, 80, 27);
		panel_2.add(label_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(101, 58, 129, 27);
		textField_3.setEnabled(false);
		panel_2.add(textField_3);
		
		JLabel label_3 = new JLabel("联系方式：");
		label_3.setFont(new Font("幼圆", Font.BOLD, 14));
		label_3.setBounds(28, 104, 80, 27);
		panel_2.add(label_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(101, 104, 129, 27);
		textField_4.setEnabled(false);
		panel_2.add(textField_4);
		
		JLabel label_4 = new JLabel("提交时间：");
		label_4.setFont(new Font("幼圆", Font.BOLD, 14));
		label_4.setBounds(264, 58, 80, 27);
		panel_2.add(label_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(337, 58, 129, 27);
		textField_5.setEnabled(false);
		panel_2.add(textField_5);
		
		JLabel label_5 = new JLabel("问题状态：");
		label_5.setFont(new Font("幼圆", Font.BOLD, 14));
		label_5.setBounds(264, 104, 80, 27);
		panel_2.add(label_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(337, 104, 129, 27);
		textField_6.setEnabled(false);
		panel_2.add(textField_6);


		JButton btnNewButton_1 = new JButton("处理问题");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = textField_1.getText();
				String status = textField_6.getText();
				Connection con = null;
				try {
					if ("已解决".equals(status)) {
						JOptionPane.showMessageDialog(null, "问题已处理");
						return;
					}
					con = dbUtil.getConnection();
					int i = feedBackDao.updateFeedBack(con, Integer.parseInt(id));
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "保存成功");
					} else {
						JOptionPane.showMessageDialog(null, "保存失败");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "系统异常");
				}finally{
					try {
						dbUtil.closeCon(con);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

				putDates();
			}
		});
		btnNewButton_1.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton_1.setBounds(304, 235, 93, 35);
		panel_2.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(FeedBackEdit.class.getResource("/tupian/adBG3.png")));
		lblNewLabel.setBounds(0, 0, 584, 613);
		jf.getContentPane().add(lblNewLabel);
		
		

		jf.setVisible(true);
		jf.setResizable(true);
	}
	//点击表格获取数据
	/**
	 * @param evt
	 */
	protected void tableMousePressed(MouseEvent evt) {
		int row = table.getSelectedRow();
		Integer userId = Integer.parseInt(table.getValueAt(row, 0).toString());
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet list = feedBackDao.list2(con, userId);
			if (list.next()) {
				textField_1.setText(list.getString("id"));
				textField_2.setText(list.getString("title"));
				textField_3.setText(list.getString("describe"));
				textField_4.setText(list.getString("phone"));
				textField_5.setText(toolUtil.getDateByTime(list.getDate("createtime")));
				int status = list.getInt("status");
				textField_6.setText(status == 0 ? "待处理":"已解决");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	private void putDates() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet list = feedBackDao.list(con, 0);
			while (list.next()) {
				Vector rowData = new Vector();
				rowData.add(list.getString("id"));
				rowData.add(list.getString("title"));
				rowData.add(list.getString("describe"));
				rowData.add(list.getString("phone"));
				rowData .add(toolUtil.getDateByTime(list.getDate("createtime")));
				int status = list.getInt("status");
				if (status == 0) {
					rowData.add("待处理");
				} else {
					rowData.add("已解决");
				}
				model.addRow(rowData);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new AdminBookEdit();
	}
}
