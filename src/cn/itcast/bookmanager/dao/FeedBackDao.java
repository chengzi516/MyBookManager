package cn.itcast.bookmanager.dao;

import cn.itcast.bookmanager.model.BorrowDetail;
import cn.itcast.bookmanager.model.FeedBack;
import cn.itcast.bookmanager.model.User;
import cn.itcast.bookmanager.utils.toolUtil;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import java.sql.ResultSet;

public class FeedBackDao {
	
	public int addFeedBack(Connection con, FeedBack feedBack) throws Exception{

	    String sql="insert into feedback (title,`describe`,phone,user_id) values (?,?,?,?)";
	    PreparedStatement pstmt2=(PreparedStatement) con.prepareStatement(sql);
		pstmt2.setString(1, feedBack.getTitle());
		pstmt2.setString(2, feedBack.getDescribe());
		pstmt2.setString(3, feedBack.getPhone());
		pstmt2.setInt(4, feedBack.getUser_id());
		return pstmt2.executeUpdate();
	}
	
	
	public ResultSet list(Connection con,Integer userid)throws Exception{
		StringBuffer sb= null;
		if(userid == 0){
			sb=new StringBuffer("select * from feedback");
		}else{
			sb=new StringBuffer("select * from feedback where user_id = "+userid+"");
		}

		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}

	public ResultSet list2(Connection con,Integer id)throws Exception{
		StringBuffer sb = new StringBuffer("select * from feedback where id = "+id+"");
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}

	public int updateFeedBack(Connection con, Integer userid)throws Exception {
		String sql = "update feedback set status = 1  where id = ?";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setInt(1, userid);
		return pstmt.executeUpdate();
	}

}
