package com.cms.servlet;

import io.github.cdimascio.dotenv.Dotenv;
import com.cms.dao.DBConnection;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@WebServlet("/StudentFeePaymentServlet")
public class StudentFeePaymentServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private static final Dotenv dotenv = Dotenv.load();
    // Replace these with your Razorpay test API keys
    private static final String RAZORPAY_KEY_ID = dotenv.get("RAZORPAY_KEY_ID");
    private static final String RAZORPAY_KEY_SECRET = dotenv.get("RAZORPAY_KEY_SECRET");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("application/json");
        Gson gson = new Gson();

        if ("getFeeDetails".equals(action)) {
            // Get registrationId from session
            HttpSession session = request.getSession();
            String registrationId = (String) session.getAttribute("registrationId");

            if (registrationId != null && !registrationId.isEmpty()) {
                Map<String, Object> feeDetails = getFeeDetails(registrationId);
                if (feeDetails == null || feeDetails.isEmpty()) {
                    response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "No fee details found for Registration ID: " + registrationId)));
                } else {
                    response.getWriter().write(gson.toJson(Map.of("status", "success", "feeDetails", feeDetails, "razorpayKeyId", RAZORPAY_KEY_ID)));
                }
            } else {
                response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Registration ID not found in session.")));
            }
        } else {
            // Serve the JSP page
            request.getRequestDispatcher("studentFeePayment.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Gson gson = new Gson();
        response.setContentType("application/json");

        String action = request.getParameter("action");

        if ("createOrder".equals(action)) {
            // Create Razorpay order
            String amountStr = request.getParameter("amount");
            BigDecimal amountDue = new BigDecimal(amountStr);

            // Get registrationId from session
            HttpSession session = request.getSession();
            String registrationId = (String) session.getAttribute("registrationId");

            if (registrationId == null || registrationId.isEmpty()) {
                response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Registration ID not found in session.")));
                return;
            }

            int amountInPaise = amountDue.multiply(new BigDecimal(100)).intValue(); // Convert to paise

            try {
                RazorpayClient razorpay = new RazorpayClient(RAZORPAY_KEY_ID, RAZORPAY_KEY_SECRET);

                JSONObject orderRequest = new JSONObject();
                orderRequest.put("amount", amountInPaise); // amount in the smallest currency unit
                orderRequest.put("currency", "INR");
                orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());

                Order order = razorpay.Orders.create(orderRequest);

                // Store order ID in session for verification
                session.setAttribute("razorpayOrderId", order.get("id"));

                // Return order details as JSON
                Map<String, Object> orderData = new HashMap<>();
                orderData.put("orderId", order.get("id"));
                response.getWriter().write(gson.toJson(Map.of("status", "success", "orderData", orderData)));
            } catch (Exception e) {
                e.printStackTrace();
                response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Error creating Razorpay order: " + e.getMessage())));
            }

        } else if ("paymentSuccess".equals(action)) {
            // Handle payment success
            String requestDataJson = request.getReader().lines().collect(Collectors.joining());
            JsonObject requestData = gson.fromJson(requestDataJson, JsonObject.class);

            String razorpayPaymentId = requestData.get("razorpay_payment_id").getAsString();
            String razorpayOrderId = requestData.get("razorpay_order_id").getAsString();
            String razorpaySignature = requestData.get("razorpay_signature").getAsString();
            BigDecimal amountPaid = requestData.get("amount").getAsBigDecimal();

            HttpSession session = request.getSession();
            String registrationId = (String) session.getAttribute("registrationId");
            String sessionOrderId = (String) session.getAttribute("razorpayOrderId");

            if (registrationId == null || registrationId.isEmpty()) {
                response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Registration ID not found in session.")));
                return;
            }

            if (!razorpayOrderId.equals(sessionOrderId)) {
                response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Order ID mismatch.")));
                return;
            }

            // Log the payment details for debugging
            System.out.println("Payment ID: " + razorpayPaymentId);
            System.out.println("Order ID from Razorpay: " + razorpayOrderId);
            System.out.println("Signature: " + razorpaySignature);
            System.out.println("Order ID from session: " + sessionOrderId);
            System.out.println("Registration ID from session: " + registrationId);

            boolean isPaymentValid = verifyPaymentSignature(razorpayPaymentId, razorpayOrderId, razorpaySignature);

            if (isPaymentValid) {
                updateFeePayment(registrationId, amountPaid);

                response.getWriter().write(gson.toJson(Map.of("status", "success", "message", "Payment successful! Your fees have been updated.")));

                session.removeAttribute("razorpayOrderId");
                // Do not remove registrationId if the user remains logged in
            } else {
                response.getWriter().write(gson.toJson(Map.of("status", "error", "message", "Payment verification failed.")));
            }
        }
    }

    private Map<String, Object> getFeeDetails(String registrationId) {
        Map<String, Object> feeDetails = new HashMap<>();
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT registrationId, academicFee, hostelFee, examFee, totalFees, amountPaid, balance FROM fees WHERE registrationId = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, registrationId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    feeDetails.put("registrationId", rs.getString("registrationId"));
                    feeDetails.put("academicFee", rs.getBigDecimal("academicFee"));
                    feeDetails.put("hostelFee", rs.getBigDecimal("hostelFee"));
                    feeDetails.put("examFee", rs.getBigDecimal("examFee"));
                    feeDetails.put("totalFees", rs.getBigDecimal("totalFees"));
                    feeDetails.put("amountPaid", rs.getBigDecimal("amountPaid"));
                    feeDetails.put("balance", rs.getBigDecimal("balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feeDetails;
    }

    private void updateFeePayment(String registrationId, BigDecimal amountPaid) {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE fees SET amountPaid = amountPaid + ? WHERE registrationId = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setBigDecimal(1, amountPaid);
                stmt.setString(2, registrationId);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean verifyPaymentSignature(String paymentId, String orderId, String signature) {
        try {
            JSONObject params = new JSONObject();
            params.put("razorpay_payment_id", paymentId);
            params.put("razorpay_order_id", orderId);
            params.put("razorpay_signature", signature);

            Utils.verifyPaymentSignature(params, RAZORPAY_KEY_SECRET);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}