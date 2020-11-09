package transaction;

import utils.JDBCUtils;
import utils.JDBCUtils1;

import java.sql.Connection;

/**
 * 用来测试数据库连接
 */
public class ConnectionTest {
    public static void main(String[] args) throws Exception {
        Connection conn = JDBCUtils1.getConnection2();
        System.out.println(conn);
    }
}
