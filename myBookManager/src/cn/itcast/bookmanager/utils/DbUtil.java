package cn.itcast.bookmanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
public class DbUtil {
	private String dbDriver = "com.mysql.cj.jdbc.Driver";
	private String dbUrl = "jdbc:mysql://localhost:3306/bookmanager?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true";
	private String dbUserName = "root";
	private String dbPassword = "password";

	public Connection getConnection()throws Exception{
	    Class.forName(dbDriver);
		Connection con =  DriverManager.getConnection(dbUrl,dbUserName,dbPassword);
		return con;
	}

	public void closeCon (Connection con)throws Exception {
		if(con!=null){
			con.close();
		}
	}

}
