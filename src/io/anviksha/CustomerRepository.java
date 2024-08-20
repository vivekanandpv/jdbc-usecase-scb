package io.anviksha;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository {
    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");

        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/jdbc-usecase", "postgres", "postgres");

        if (connection == null) {
            throw new RuntimeException("Could not establish the database connection");
        }

        return connection;
    }

    public static List<Customer> getAll() throws SQLException, ClassNotFoundException {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery("SELECT * FROM customers");
        ) {
            List<Customer> customers = new ArrayList<>();

            while (results.next()) {
                Customer customer = new Customer();

                customer.setId(results.getInt("id"));
                customer.setName(results.getString("name"));
                customer.setEmail(results.getString("email"));
                customer.setContact(results.getLong("contact"));
                customer.setAccountType(results.getString("account_type"));

                customers.add(customer);
            }

            return customers;
        }
    }

    public static Customer getById(int id) throws SQLException, ClassNotFoundException {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement();
                ResultSet results = statement.executeQuery(String.format("SELECT * FROM customers WHERE id = %d", id));
        ) {
            if (results.next()) {
                Customer customer = new Customer();

                customer.setId(results.getInt("id"));
                customer.setName(results.getString("name"));
                customer.setEmail(results.getString("email"));
                customer.setContact(results.getLong("contact"));
                customer.setAccountType(results.getString("account_type"));

                return customer;
            }

            throw new RuntimeException("Customer doesn't exist");
        }
    }

    public static void create(Customer customer) throws SQLException, ClassNotFoundException {
        try (
                Connection connection = getConnection();
                PreparedStatement preparedStatement = getPreparedStatement(connection, customer);
        ) {
            preparedStatement.execute();
        }
    }

    private static PreparedStatement getPreparedStatement(Connection connection, Customer customer) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO public.customers(name, email, contact, account_type) VALUES (?, ?, ?, ?)");

        preparedStatement.setString(1, customer.getName());
        preparedStatement.setString(2, customer.getEmail());
        preparedStatement.setLong(3, customer.getContact());
        preparedStatement.setString(4, customer.getAccountType());
        return preparedStatement;
    }

    public static void deleteById(int id) throws SQLException, ClassNotFoundException {
        try (
                Connection connection = getConnection();
                Statement statement = connection.createStatement()
        ) {
            statement.execute(String.format("DELETE FROM customers WHERE id = %d", id));
        }
    }
}
