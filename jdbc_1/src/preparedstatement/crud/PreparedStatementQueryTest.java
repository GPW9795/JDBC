package preparedstatement.crud;

import utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用PreparedStatement实现针对不同表的通用查询操作
 */
public class PreparedStatementQueryTest<T> {

    /**
     * 针对不同的表的一个对象的通用操作，返回一条记录
     *
     * @param clazz
     * @param sql
     * @param args
     * @return
     */
    public T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            if (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < col; i++) {
                    // 列值
                    Object value = rs.getObject(i + 1);
                    // 列名
                    String colName = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, value);

                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }

    public List<T> getForList(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int col = rsmd.getColumnCount();
            List<T> res = new ArrayList<>();
            while (rs.next()) {
                T t = clazz.newInstance();
                for (int i = 0; i < col; i++) {
                    // 列值
                    Object value = rs.getObject(i + 1);
                    // 列名
                    String colName = rsmd.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, value);

                }
                res.add(t);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}
