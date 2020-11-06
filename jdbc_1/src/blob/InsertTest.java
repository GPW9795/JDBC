package blob;

import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 使用PreparedStatement实现批量数据的操作，主要是批量插入
 */
public class InsertTest {

    /**
     * 批量插入方式1
     */
    public void testInsert1() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();

            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 20000; i++) {
                ps.setString(1, "good_" + i);
                ps.execute();
            }

            long end = System.currentTimeMillis();
            System.out.println("Time: " + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    /**
     * 批量插入方式2
     */
    public void testInsert2() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();

            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);

            for (int i = 1; i <= 1000000; i++) {
                ps.setString(1, "good_" + i);
                // 1、攒"sql"
                ps.addBatch();
                if (i % 500 == 0) {
                    // 2、执行
                    ps.executeBatch();
                    // 3、清空
                    ps.clearBatch();
                }
            }

            long end = System.currentTimeMillis();
            System.out.println("Time: " + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }

    /**
     * 批量插入方式3
     */
    public void testInsert3() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();

            conn = JDBCUtils.getConnection();
            String sql = "insert into goods (name) values (?)";
            ps = conn.prepareStatement(sql);

            // 设置不允许自动提交数据
            conn.setAutoCommit(false);
            for (int i = 1; i <= 1000000; i++) {
                ps.setString(1, "good_" + i);
                // 1、攒"sql"
                ps.addBatch();
                if (i % 500 == 0) {
                    // 2、执行
                    ps.executeBatch();
                    // 3、清空
                    ps.clearBatch();
                }
            }
            conn.commit();

            long end = System.currentTimeMillis();
            System.out.println("Time: " + (end - start));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }
}
