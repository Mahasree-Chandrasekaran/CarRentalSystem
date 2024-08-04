package Rent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
    public void saveToDatabase() throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Rental (carId, customerId, days) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, car.getCarId());
                stmt.setString(2, customer.getCustomerId());
                stmt.setInt(3, days);
                stmt.executeUpdate();
            }
        }
    }

    // Other methods...
}
