package Rent;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class Car {
    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;

    public Car(String carId, String brand, String model, double basePricePerDay) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true;
    }
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return basePricePerDay * rentalDays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }

public void saveToDatabase() throws SQLException {
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "INSERT INTO Car (carId, brand, model, basePricePerDay, isAvailable) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, carId);
            stmt.setString(2, brand);
            stmt.setString(3, model);
            stmt.setDouble(4, basePricePerDay);
            stmt.setBoolean(5, isAvailable);
            stmt.executeUpdate();
        }
    }
}
public static Car getCarById(String carId) throws SQLException {
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "SELECT * FROM Car WHERE carId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, carId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Car(
                        rs.getString("carId"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getDouble("basePricePerDay")
                    );
                }
            }
        }
    }
    return null;
}

public void updateAvailability(boolean availability) throws SQLException {
    try (Connection connection = DatabaseConnection.getConnection()) {
        String query = "UPDATE Car SET isAvailable = ? WHERE carId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setBoolean(1, availability);
            stmt.setString(2, carId);
            stmt.executeUpdate();
        }
    }
}

// Other methods...
}
