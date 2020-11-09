package connection;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class C3P0Test {

    /**
     * 方式一
     * @throws Exception
     */
    @Test
    public void testGetConnection() throws Exception {
        // 获取C3p0
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.cj.jdbc.Driver");
        cpds.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        cpds.setUser("root");
        cpds.setPassword("gpw710324");
        cpds.setInitialPoolSize(10);

        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }

    /**
     * 使用配置文件
     */
    @Test
    public void testGetConnection1() throws Exception {
        // 获取C3p0
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");

        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }

}
