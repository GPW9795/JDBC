package preparedstatement.crud;

import bean.Customer;
import utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.*;

public class CustomerForQuery {

    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth from customer where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);

            resultSet = ps.executeQuery();
            if (resultSet.next()) {

                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);

                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, resultSet);
        }
    }

    public Customer queryForCustomer(String sql, Object... args) {
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
                Customer customer = new Customer();
                for (int i = 0; i < col; i++) {
                    // 列值
                    Object value = rs.getObject(i + 1);
                    // 列名
                    String colName = rsmd.getColumnName(i + 1);

                    Field field = Customer.class.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(customer, value);

                }
                return customer;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);
        }
        return null;
    }
}
