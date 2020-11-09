package connection;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPTest {
    /**
     * 方式一
     */
    @Test
    public void testGetConnection() throws SQLException {
        BasicDataSource source = new BasicDataSource();
        source.setDriverClassName("com.mysql.cj.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/test");
        source.setUsername("root");
        source.setPassword("gpw710324");
        source.setInitialSize(10);
        Connection conn = source.getConnection();
        System.out.println(conn);
    }

    /**
     * 使用配置文件，推荐
     */
    @Test
    public void testGetConnection1() throws Exception {
        Properties pros = new Properties();
        FileInputStream is = new FileInputStream(new File("src/dbcp.properties"));
        pros.load(is);

        DataSource source = BasicDataSourceFactory.createDataSource(pros);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
