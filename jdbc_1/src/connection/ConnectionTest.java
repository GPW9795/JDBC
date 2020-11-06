package connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionTest {
    public static void main(String[] args) throws Exception {
        testConnection5();
    }

    /**
     * 连接数据库的方法一
     */
    public static void testConnection1() throws SQLException {
        // 获取Driver实现类对象
        Driver driver = new com.mysql.jdbc.Driver();

        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSl=false";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "gpw710324");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);
    }

    /**
     * 连接数据库的方法二：对一的迭代
     */
    public static void testConnection2() throws ClassNotFoundException, IllegalAccessException, InstantiationException, SQLException {
        // 获取Driver实现类对象：使用反射
        Class clazz = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) clazz.newInstance();

        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSl=false";

        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "gpw710324");

        Connection conn = driver.connect(url, info);

        System.out.println(conn);

    }

    /**
     * 连接数据库的方法三：使用DriverManager替换Driver
     */
    public static void testConnection3() {
        try {
            // 获取Driver实现类对象
            Class clazz = Class.forName("com.mysql.jdbc.Driver");
            Driver driver = (Driver) clazz.newInstance();

            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSl=false";
            String user = "root";
            String password = "gpw710324";
            // 注册驱动
            DriverManager.registerDriver(driver);
            // 获取连接
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接数据库的方法四：基于方式三的优化，可以只是加载驱动，不用显示的注册驱动
     */
    public static void testConnection4() {
        try {
            String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC&characterEncoding=utf8&useUnicode=true&useSSl=false";
            String user = "root";
            String password = "gpw710324";
            // 加载Driver
            Class.forName("com.mysql.jdbc.Driver");
            // 获取连接
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 最终版：将四个信息放到配置文件中，通过读取配置文件的方式获取连接
     */
    public static void testConnection5() throws Exception {
        // 读取配置文件中的四个信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(is);

        String url = pros.getProperty("url");
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String driverClass = pros.getProperty("driverClass");

        Class.forName(driverClass);

        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}
