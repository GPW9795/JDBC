package connection;

import preparedstatement.crud.PreparedStatementUpdateTest;

public class Main {
    public static void main(String[] args) throws Exception {
        String sql = "UPDATE user_table SET user=? WHERE id=1";
        String sql1 = "DELETE FROM user_table WHERE id=?";
        PreparedStatementUpdateTest psu = new PreparedStatementUpdateTest();
        psu.update(sql1, 1);
    }
}
