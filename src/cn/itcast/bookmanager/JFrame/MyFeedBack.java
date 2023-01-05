package cn.itcast.bookmanager.JFrame;


import cn.itcast.bookmanager.dao.BorrowDetailDao;
import cn.itcast.bookmanager.dao.FeedBackDao;
import cn.itcast.bookmanager.model.BorrowDetail;
import cn.itcast.bookmanager.utils.DbUtil;
import cn.itcast.bookmanager.utils.toolUtil;
import com.mysql.jdbc.Connection;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.util.Vector;

public class MyFeedBack extends JFrame {
	private JFrame jf;
	private JTable table;
	private DefaultTableModel model;
	DbUtil dbUtil=new DbUtil();
	FeedBackDao feedBackDao=new FeedBackDao();
	public MyFeedBack(){
		jf=new JFrame("我的反馈");
		jf.setBounds(400, 100, 710, 441);
		jf.getContentPane().setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "反馈信息", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(255, 0, 0)));
		panel_1.setBounds(10, 10, 674, 350);
		
		 /*做一个表头栏数据  一位数组
		  * */
		 String[] title={"序号","标题","问题描述","联系方式","提交时间","问题状态"};
		/*具体的各栏行记录 先用空的二位数组占位*/
		 String[][] dates={};
		 /*然后实例化 上面2个控件对象*/
		 model=new DefaultTableModel(dates,title);
		 table=new JTable(model);
		 putDates(LoginFrm.currentUser.getUserId());//获取数据库数据放置table中
		  panel_1.setLayout(null);
		  JScrollPane jscrollpane = new JScrollPane();
		  jscrollpane.setBounds(20, 22, 638, 314);
			jscrollpane.setViewportView(table);
			panel_1.add(jscrollpane);
			jf.getContentPane().add(panel_1);
			
			JLabel lblNewLabel = new JLabel("");
			lblNewLabel.setIcon(new ImageIcon(MyFeedBack.class.getResource("/tupian/adBG2.png")));
			lblNewLabel.setBounds(0, 0, 684, 379);
			jf.getContentPane().add(lblNewLabel);
		
		jf.setVisible(true);
		jf.setResizable(true);
	}
	private void putDates(Integer userid) {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getConnection();
			ResultSet list = feedBackDao.list(con, userid);
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
		}finally{
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
		new MyFeedBack();
	}
}
