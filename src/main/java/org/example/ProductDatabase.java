package org.example;

import java.sql.*;


public class ProductDatabase {
    private final String dbUrl;

    public ProductDatabase(String dbUrl) {
        this.dbUrl = dbUrl;
        createTable();
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "productId INTEGER PRIMARY KEY," +
                "productName TEXT NOT NULL," +
                "productPrice REAL NOT NULL," +
                "productDescription TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertProduct(Product product) {
        String sql = "INSERT INTO products (productName, productPrice, productDescription) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getProductName());
            preparedStatement.setDouble(2, product.getProductPrice().doubleValue());
            preparedStatement.setString(3, product.getProductDescription());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Product getProductById(int productId) {
        String sql = "SELECT * FROM products WHERE productId = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("productId");
                String name = resultSet.getString("productName");
                double price = resultSet.getDouble("productPrice");
                String description = resultSet.getString("productDescription");
                return new Product(name, id, price, description);
            }


            return null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    return null;
    }

    public void removeProduct(String nameOrId) {
        String sql = "DELETE FROM products WHERE productName = ? OR productId = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, nameOrId);
            try {
                int productId = Integer.parseInt(nameOrId);
                preparedStatement.setInt(2, productId);
            } catch (NumberFormatException e) {
                preparedStatement.setNull(2, Types.INTEGER);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}

