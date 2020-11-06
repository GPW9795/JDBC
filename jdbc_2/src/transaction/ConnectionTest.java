package transaction;

import utils.JDBCUtils;

import java.sql.Connection;

/**
 * 用来测试数据库连接
 */
public class ConnectionTest {
    public static void main(String[] args) throws Exception {
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn);
    }
}
