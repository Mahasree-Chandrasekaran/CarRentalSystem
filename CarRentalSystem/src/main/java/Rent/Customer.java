package Rent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

public void saveToDatabase() throws SQLException {
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "INSERT INTO Customer (customerId, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerId);
            stmt.setString(2, name);
            stmt.executeUpdate();
        }
    }
}

public static Customer getCustomerById(String customerId) throws SQLException {
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "SELECT * FROM Customer WHERE customerId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(
                        rs.getString("customerId"),
                        rs.getString("name")
                    );
                }
            }
        }
    }
    return null;
}

// Other methods...
}