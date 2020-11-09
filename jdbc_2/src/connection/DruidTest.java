package connection;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.util.Properties;

public class DruidTest {
    @Test
    public void getConnection() throws Exception {
        Properties properties = new Properties();
        FileInputStream fi = new FileInputStream(new File("src/druid.properties"));
        properties.load(fi);

        DataSource source = DruidDataSourceFactory.createDataSource(properties);
        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
