import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Sultana")
public class Sultana extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Sultana() {
        super();
    }

    // Handles HTTP GET request (redirects to the HTML form)
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("index.html");
    }

    // Handles HTTP POST request (processes form submission)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String socketType = request.getParameter("socketType");
        String quantityStr = request.getParameter("quantity");
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Validate quantity
        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityStr);
            if (quantity < 1) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            out.println("<html>");
            out.println("<head><title>Error</title></head>");
            out.println("<body>");
            out.println("<h2 style='color: red;'>Invalid quantity. Please enter a valid number greater than 0.</h2>");
            out.println("</body>");
            out.println("</html>");
            return;
        }

        // Calculate price based on socket type and quantity
        double pricePerSocket;
        switch (socketType) {
            case "Type A": pricePerSocket = 10.0; break;
            case "Type B": pricePerSocket = 15.0; break;
            case "Type C": pricePerSocket = 20.0; break;
            default: pricePerSocket = 0.0; break;
        }
        double totalPrice = quantity * pricePerSocket;

        // Output the confirmation response
        out.println("<html>");
        out.println("<head><title>Quote Request Submitted</title></head>");
        out.println("<body style='font-family: Arial, sans-serif;'>");
        out.println("<h2 style='color: orange;'>Quote Request Submitted</h2>");
        out.println("<p>Thank you, <strong>" + name + "</strong>. Here is your quote:</p>");
        out.println("<p>Socket Type: <strong>" + socketType + "</strong></p>");
        out.println("<p>Quantity: <strong>" + quantity + "</strong></p>");
        out.println("<p>Total Price: <strong>$" + totalPrice + "</strong></p>");
        out.println("<p>A detailed quote will be sent to <a href='mailto:" + email + "'>" + email + "</a>.</p>");
        out.println("</body>");
        out.println("</html>");
    }
}