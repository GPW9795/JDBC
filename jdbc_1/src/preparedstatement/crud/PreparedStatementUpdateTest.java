package preparedstatement.crud;

import utils.JDBCUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class PreparedStatementUpdateTest {
    // 添加数据
    public void update(String sql, Object... args) throws Exception {
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
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps);
        }
    }


}
