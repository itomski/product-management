package de.lubowiecki.productmanagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    private final String URL = "jdbc:sqlite:products.db";

    private final String TABLE = "products";

    public ProductRepository() throws SQLException {
        createTable();
    }

    public boolean save(Product product) throws SQLException {

        final String SQL = "INSERT INTO " + TABLE + " (id, name, description, quantity, price) VALUES(null, ?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(URL);
            PreparedStatement stmt = connection.prepareStatement(SQL)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getQuantity());
            stmt.setDouble(4, product.getPrice());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Product> find() throws SQLException {

        final String SQL = "SELECT * FROM " + TABLE;

        List<Product> products = new ArrayList<>();

        try(Connection connection = DriverManager.getConnection(URL);
            Statement stmt = connection.createStatement()) {
            if(stmt.execute(SQL)) {
                ResultSet results = stmt.getResultSet();
                while(results.next()) {
                    //0: id=1, name=Tasse, etc
                    products.add(create(results)); // Wandelt Zeilen aus DB in Produkt-Objekte um
                }
            }
        }
        return products;
    }

    private Product create(ResultSet results) throws SQLException {
        Product product = new Product();
        product.setId(results.getInt("id"));
        product.setName(results.getString("name"));
        product.setDescription(results.getString("description"));
        product.setQuantity(results.getInt("quantity"));
        product.setPrice(results.getDouble("price"));
        return product;
    }

    private void createTable() throws SQLException {

        final String SQL = "CREATE TABLE IF NOT EXISTS " + TABLE + " (" +
                "id INTEGER, " +
                "name TEXT, " +
                "description TEXT, " +
                "quantity INTEGER, " +
                "price REAL, " +
                "PRIMARY KEY (id AUTOINCREMENT))";

        try(Connection connection = DriverManager.getConnection(URL);
            Statement stmt = connection.createStatement()) {
            stmt.execute(SQL);
        }
    }
}
