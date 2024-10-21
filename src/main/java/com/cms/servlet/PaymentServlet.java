package com.cms.servlet;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;

@WebServlet("/createOrder")
public class PaymentServlet extends HttpServlet {
    private RazorpayClient razorpay;

    @Override
    public void init() throws ServletException {
        try {
            // Initialize Razorpay client with your API key and secret
            razorpay = new RazorpayClient("rzp_test_E96YctEGlH3EcE", "asqJUTrmLx0aodnuFyCA8pzD");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // Get the amount from the request (sent from the form)
            String amountParam = req.getParameter("amount");
            int amount = Integer.parseInt(amountParam) * 100; // Convert to paise

            // Create the order request object
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", amount); // Amount in paise
            orderRequest.put("currency", "INR");
            orderRequest.put("receipt", "txn_12345");

            // Create the order in Razorpay
            Order order = razorpay.Orders.create(orderRequest);

            // Pass the order ID and amount to the JSP page
            req.setAttribute("orderId", order.get("id"));
            req.setAttribute("amount", amountParam); // Pass amount to JSP
            req.getRequestDispatcher("paymentForm.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Order creation failed");
        }
    }
}
