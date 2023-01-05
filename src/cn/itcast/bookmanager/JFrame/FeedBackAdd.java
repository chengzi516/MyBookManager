package cn.itcast.bookmanager.JFrame;


import cn.itcast.bookmanager.dao.BookDao;
import cn.itcast.bookmanager.dao.BookTypeDao;
import cn.itcast.bookmanager.dao.FeedBackDao;
import cn.itcast.bookmanager.model.Book;
import cn.itcast.bookmanager.model.BookType;
import cn.itcast.bookmanager.model.FeedBack;
import cn.itcast.bookmanager.utils.DbUtil;
import cn.itcast.bookmanager.utils.toolUtil;
import com.mysql.jdbc.Connection;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.ResultSet;

public class FeedBackAdd extends JFrame {
	private JFrame jf;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	FeedBackDao feedBackDao=new FeedBackDao();
	DbUtil dbUtil=new DbUtil();
	public FeedBackAdd(){
		jf=new JFrame("问题反馈");
		jf.setBounds(450, 150, 600, 375);
		jf.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(23, 21, 540, 275);
		jf.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("标题：");
		lblNewLabel.setFont(new Font("幼圆", Font.BOLD, 14));
		lblNewLabel.setBounds(58, 31, 45, 27);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(120, 31, 300, 27);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel label = new JLabel("描述：");
		label.setFont(new Font("幼圆", Font.BOLD, 14));
		label.setBounds(58, 79, 45, 27);
		panel.add(label);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(120, 128, 300, 27);
		panel.add(textField_1);
		
		JLabel label_1 = new JLabel("联系方式：");
		label_1.setFont(new Font("幼圆", Font.BOLD, 14));
		label_1.setBounds(30, 128, 80, 27);
		panel.add(label_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(120, 79, 300, 27);
		panel.add(textField_2);
		
//		JLabel label_2 = new JLabel("库存：");
//		label_2.setFont(new Font("幼圆", Font.BOLD, 14));
//		label_2.setBounds(58, 125, 45, 27);
//		panel.add(label_2);
//
//		textField_3 = new JTextField();
//		textField_3.setColumns(10);
//		textField_3.setBounds(101, 125, 129, 27);
//		panel.add(textField_3);
//
//		JLabel label_3 = new JLabel("价格：");
//		label_3.setFont(new Font("幼圆", Font.BOLD, 14));
//		label_3.setBounds(294, 79, 45, 27);
//		panel.add(label_3);
//
//		textField_4 = new JTextField();
//		textField_4.setColumns(10);
//		textField_4.setBounds(337, 79, 129, 27);
//		panel.add(textField_4);
//
//		JLabel label_4 = new JLabel("类别：");
//		label_4.setFont(new Font("幼圆", Font.BOLD, 14));
//		label_4.setBounds(294, 125, 45, 27);
//		panel.add(label_4);
//
//		JLabel label_5 = new JLabel("描述：");
//		label_5.setFont(new Font("幼圆", Font.BOLD, 14));
//		label_5.setBounds(58, 170, 45, 27);
//		panel.add(label_5);
//
//		textField_6 = new JTextField();
//		textField_6.setColumns(10);
//		textField_6.setBounds(101, 173, 365, 27);
//		panel.add(textField_6);
		
		JButton btnNewButton = new JButton("添加");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String title = textField.getText();
				String describe = textField_1.getText();
				String phone = textField_2.getText();
				if (toolUtil.isEmpty(title) || toolUtil.isEmpty(describe)
						|| toolUtil.isEmpty(phone)) {
					JOptionPane.showMessageDialog(null, "请输入相关内容");
					return;
				}
				FeedBack feedBack = new FeedBack();
				feedBack.setTitle(title);
				feedBack.setDescribe(describe);
				feedBack.setPhone(phone);
				feedBack.setUser_id(LoginFrm.currentUser.getUserId());
				Connection con = null;
				try {
					con = dbUtil.getConnection();
					int i = feedBackDao.addFeedBack(con, feedBack);
					if (i == 1) {
						JOptionPane.showMessageDialog(null, "保存成功");
						reset();
						jf.setVisible(false);
						jf.setResizable(false);
					} else {
						JOptionPane.showMessageDialog(null, "保存失败");
					}
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "系统异常");
				}
			}
		});
		btnNewButton.setFont(new Font("幼圆", Font.BOLD, 14));
		btnNewButton.setBounds(124, 185, 77, 27);
		panel.add(btnNewButton);
		
		JButton button = new JButton("重置");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reset();
			}
		});
		button.setFont(new Font("幼圆", Font.BOLD, 14));
		button.setBounds(329, 185, 77, 27);
		panel.add(button);

		jf.setVisible(true);
		jf.setResizable(true);
	}
	protected void reset() {
		textField.setText("");
		textField_1.setText("");
		textField_2.setText("");
	}
	public static void main(String[] args) {
		try {
			BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.generalNoTranslucencyShadow;
			BeautyEyeLNFHelper.launchBeautyEyeLNF();
		} catch (Exception e) {
			e.printStackTrace();
		}
		//new AdminBookAdd();
	}
}
