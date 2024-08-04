package Rent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/rentCar")
public class RentCarServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerId = request.getParameter("customerId");
        String carId = request.getParameter("carId");
        int days = Integer.parseInt(request.getParameter("days"));

        try {
            Customer customer = Customer.getCustomerById(customerId);
            Car car = Car.getCarById(carId);

            if (car != null && customer != null && car.isAvailable()) {
                car.updateAvailability(false);
                Rental rental = new Rental(car, customer, days);
                rental.saveToDatabase();
                response.getWriter().write("Car rented successfully.");
            } else {
                response.getWriter().write("Car not available or invalid customer.");
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
