package cn.itcast.bookmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * 看板持久层
 */
public class BoardDao {

    /**
     * 统计书本类型及数量
     *
     * @param con
     * @return
     */
    public List<Map<String, Object>> board(Connection con) {
        //定义返回值
        List<Map<String, Object>> result = new ArrayList<>();
        //定义执行者对象
        PreparedStatement ps = null;
        //定义结果集对象
        ResultSet rs = null;
        try {
            //生成执行者对象
            ps = con.prepareStatement("SELECT b.type_name typeName,COUNT(1) num FROM book a JOIN book_type b ON a.type_id = b.id GROUP BY a.type_id");
            //执行SQL得到结果集对象
            rs = ps.executeQuery();
            //迭代结果集
            while (rs.next()) {
                //定义返回值泛型对象
                Map<String, Object> obj = new HashMap<>();
                //获取书籍类型名称
                obj.put("typeName", rs.getObject("typeName"));
                //获取书籍类型数量
                obj.put("num", rs.getObject("num"));
                //添加至返回值对象中
                result.add(obj);
            }
        } catch (SQLException e) {
            //一般SQL语法异常和表、字段等约束导致的异常会在这里捕获，少数情况会出现事务、锁等导致的异常，查询操作不用担心这些吊毛病
            e.printStackTrace();
        } finally {
            //统一关流
            close(rs, ps, con);
        }
        //通过栈帧的方法返回地址的内存指向将结果返回给调用方
        return result;
    }

    /**
     * 统一关流
     *
     * @param closeables
     */
    private void close(AutoCloseable... closeables) {
        //得到close的stream流
        Arrays.stream(closeables)
                //过滤出不为null的对象
                .filter(Objects::nonNull)
                //遍历close流
                .forEach(closeable -> {
                    try {
                        //关闭流
                        closeable.close();
                    } catch (Exception ignored) {
                    }
                });
    }
}
