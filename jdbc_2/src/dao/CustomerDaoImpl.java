package dao;

import Bean.Customer;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

public class CustomerDaoImpl extends BaseDao implements CustomerDao {
    @Override
    public void insert(Connection conn, Customer cust) {
        String sql = "insert into customer (name, email, birth) values (?, ?, ?)";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth());
    }

    @Override
    public void deleteById(Connection conn, int id) {
        String sql = "delete from customer where id=?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, int id, Customer cust) {
        String sql = "update customer set name=?, email=?, birth=? where id=?";
        update(conn, sql, cust.getName(), cust.getEmail(), cust.getBirth(), id);
    }

    @Override
    public Customer getCustomerById(Connection conn, int id) {
        String sql = "select id, name, email, birth from customer where id=?";
        Customer customer = getInstance(conn, Customer.class, sql, id);
        return customer;
    }

    @Override
    public List<Customer> getAll(Connection conn) {
        String sql = "select id, name, email, birth from customer";
        List<Customer> list = getForList(conn, Customer.class, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql = "select count(*) from customer";
        return getValue(conn, sql);
    }

    @Override
    public Date getMaxBirth(Connection conn) {
        String sql = "select max(birth) from customer";
        return getValue(conn, sql);
    }
}
