package transaction;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class TransactionTest {
    /**
     * 针对user_table的转账问题
     * AA-BB：100
     * update user_table set balance = balance - 100 where user='AA'
     * update user_table set balance = balance + 100 where user='BB'
     */
    public void testUpdate() throws Exception {
        String s1 = "update user_table set balance = balance - 100 where user=?";
        update(s1, "BB");
        String s2 = "update user_table set balance = balance + 100 where user=?";
        update(s2, "CC");
        System.out.println("success");
    }

    public void testTransaction() throws Exception {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(false);
            String s1 = "update user_table set balance = balance - 100 where user=?";
            update(conn, s1, "BB");

//            System.out.println(10 / 0);

            String s2 = "update user_table set balance = balance + 100 where user=?";
            update(conn, s2, "CC");

            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JDBCUtils.closeResource(conn, null, null);
        }

    }

    // 添加数据
    public int update(String sql, Object... args) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 获取连接
            conn = JDBCUtils.getConnection();
            // 预编译sql语句
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 执行语句
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
        return 0;
    }

    // 添加数据
    public int update(Connection conn, String sql, Object... args) throws Exception {
        PreparedStatement ps = null;
        try {
            // 预编译sql语句
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            // 执行语句
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps);
        }
        return 0;
    }
}
