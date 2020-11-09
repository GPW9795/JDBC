package utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils1 {
    /**
     * C3P0数据库连接池
     */

    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");

    public static Connection getConnection() throws SQLException {
        Connection conn = cpds.getConnection();
        return conn;
    }

    /**
     * DBCP
     */
    private static DataSource source;

    static {
        Properties pros = new Properties();
        FileInputStream is = null;
        try {
            is = new FileInputStream(new File("src/dbcp.properties"));
            pros.load(is);
            source = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection1() throws SQLException {
        Connection conn = source.getConnection();
        return conn;
    }

    /**
     * Druid
     */
    private static DataSource source1;

    static {
        Properties properties = new Properties();
        FileInputStream fi = null;
        try {
            fi = new FileInputStream(new File("src/druid.properties"));
            properties.load(fi);
            source1 = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection2() throws SQLException {
        Connection conn = source1.getConnection();
        return conn;
    }

    /**
     * 关闭资源
     */
    public static void closeResource(Connection conn, Statement ps, ResultSet rs) {
        DbUtils.closeQuietly(conn);
        DbUtils.closeQuietly(ps);
        DbUtils.closeQuietly(rs);
    }
}
