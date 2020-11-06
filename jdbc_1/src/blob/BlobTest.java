package blob;

import bean.Customer;
import utils.JDBCUtils;

import java.io.*;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 * 测试使用PreparedStatement操作blob类型的数据
 */
public class BlobTest {
    /**
     * 向数据表中插入blob类型的数据
     */
    public void testInsert() throws Exception {
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into customer (name, email, birth, photo) values (?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setObject(1, "gpw2");
        ps.setObject(2, "gpw@bupt.edu.cn");
        ps.setObject(3, "1997-09-09");
        FileInputStream is = new FileInputStream(new File("gpw.jpg"));
        ps.setBlob(4, is);

        ps.execute();
        is.close();
        JDBCUtils.closeResource(conn, ps);
    }

    /**
     * 查询数据表中的blob数据
     */
    public void testQuery() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        InputStream is = null;
        FileOutputStream fos = null;
        try{
            conn = JDBCUtils.getConnection();
            String sql = "select id, name, email, birth, photo from customer where id=?";
            ps = conn.prepareStatement(sql);

            ps.setInt(1, 4);
            rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);

                Customer cus = new Customer(id, name, email, birth);
                System.out.println(cus);

                Blob photo = rs.getBlob(5);
                is = photo.getBinaryStream();
                fos = new FileOutputStream("gpw.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(conn, ps, rs);
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
