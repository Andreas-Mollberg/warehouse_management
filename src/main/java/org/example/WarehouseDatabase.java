package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDatabase {
    private final String dbUrl;

    public WarehouseDatabase(String dbUrl) {
        this.dbUrl = dbUrl;
        createTable(dbUrl);
    }

    public void createTable(String dbUrl) {
        String sql = "CREATE TABLE IF NOT EXISTS warehouses (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "warehouseName TEXT NOT NULL)";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.println("Error creating warehouses table: " + e.getMessage());
        }
    }




    public void insertWarehouse(Warehouse warehouse) {
        String sql = "INSERT INTO warehouses (warehouseName) VALUES (?)";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, warehouse.getWarehouseName());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating warehouse failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    warehouse.setWarehouseId(id); // Update the warehouse object with the generated ID
                } else {
                    throw new SQLException("Creating warehouse failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting warehouse: " + e.getMessage());
        }
    }





    public List<Warehouse> getAllWarehouses() {
        List<Warehouse> warehouses = new ArrayList<>();
        String sql = "SELECT * FROM warehouses";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String warehouseName = resultSet.getString("warehouseName");

                // Use id from the database as warehouseId
                warehouses.add(new Warehouse(id, warehouseName));
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving warehouses: " + e.getMessage());
        }

        return warehouses;
    }

    public void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }



    public Warehouse getWarehouseById(int warehouseId) {
        String sql = "SELECT * FROM warehouses WHERE warehouseId = ?";

        try (Connection connection = DriverManager.getConnection(dbUrl);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, warehouseId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String warehouseName = resultSet.getString("warehouseName");

                // Use id from the database as warehouseId
                return new Warehouse(id, warehouseName);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving warehouse: " + e.getMessage());
        }

        return null; // Warehouse not found
    }

}
