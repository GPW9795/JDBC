package preparedstatement.crud.crud;

public class Main {
    public static void main(String[] args) {
        CustomerForQuery customer = new CustomerForQuery();
        customer.testQuery1();
        String sql = "select id, name from customer where id = ?";
        System.out.println(customer.queryForCustomer(sql, 1));
    }

}
