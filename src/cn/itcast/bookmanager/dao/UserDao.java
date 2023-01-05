package cn.itcast.bookmanager.dao;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import cn.itcast.bookmanager.model.User;
import cn.itcast.bookmanager.utils.toolUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDao {
	
	public User login(Connection con,User user)throws Exception {
		User resultUser = null;
		String sql = "select * from user where username=? and password=? and role = ?";
	    PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1,user.getUserName());
		pstmt.setString(2,user.getPassword());
		pstmt.setInt(3,user.getRole());
	    ResultSet rs = pstmt.executeQuery();
	    if(rs.next()){
	    	resultUser = new User();
	    	resultUser.setUserId(rs.getInt("id"));
	    	resultUser.setUserName(rs.getString("username"));
	    	resultUser.setSex(rs.getString("sex"));
	    	resultUser.setPhone(rs.getString("phone"));
	    }
		return resultUser;
	}
	
	public int addUser(Connection con,User user) throws Exception{
		//查询注册用户名是否存在
		String sql = "select * from user where userName=? ";
	    PreparedStatement pstmt = (PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1,user.getUserName());
	    ResultSet rs = pstmt.executeQuery();
	    if(rs.next()){
	    	return 2;
	    }
	    
	    sql="insert into user (username,password,role,sex,phone) values (?,?,?,?,?)";
	    PreparedStatement pstmt2=(PreparedStatement) con.prepareStatement(sql);
		pstmt2.setString(1, user.getUserName());
		pstmt2.setString(2, user.getPassword());
		pstmt2.setInt(3, user.getRole());
		pstmt2.setString(4,user.getSex());
		pstmt2.setString(5,user.getPhone());
		return pstmt2.executeUpdate();
	}
	
	
	public ResultSet list(Connection con,User user)throws Exception{
		StringBuffer sb=new StringBuffer("select * from user where role = 1");
		if(!toolUtil.isEmpty(user.getUserName())){
			sb.append(" and username like '%"+user.getUserName()+"%'");
		}
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
	
	public int update(Connection con,User user)throws Exception{
		String sql="update user set username=?,password=?,sex=?,phone=? where id=?";
		PreparedStatement pstmt=(PreparedStatement) con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getSex());
		pstmt.setString(4, user.getPhone());
		pstmt.setInt(5, user.getUserId());
		return pstmt.executeUpdate();
	}
    public List<Map<String, Object>> findUserPortrait(Connection connection, int id) throws SQLException {
        PreparedStatement ps =  (PreparedStatement) connection.prepareStatement("SELECT d.type_name typeName,COUNT(1) num FROM borrowdetail a JOIN `user` b ON a.user_id = b.id JOIN book c ON a.book_id = c.id JOIN book_type d ON c.type_id = d.id WHERE a.user_id = ? GROUP BY d.type_name");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        List<Map<String, Object>> result = new ArrayList<>();
        while (rs.next()) {
            Map<String, Object> obj = new HashMap<>();
            obj.put("typeName", rs.getString("typeName"));
            obj.put("num",rs.getObject("num"));
            result.add(obj);
        }
        return result;
    }
}
