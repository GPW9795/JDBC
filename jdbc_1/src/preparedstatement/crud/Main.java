package preparedstatement.crud;

import bean.Customer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
//        CustomerForQuery customer = new CustomerForQuery();
//        customer.testQuery1();
//        String sql = "select id, name from customer where id = ?";
//        System.out.println(customer.queryForCustomer(sql, 1));

        PreparedStatementQueryTest<Customer> pre = new PreparedStatementQueryTest<>();
        String sql = "select id, name, email, birth from customer";
        pre.getForList(Customer.class, sql).forEach(
                System.out::println
        );
    }

}
