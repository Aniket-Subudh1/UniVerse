package com.cms.servlet;

import com.razorpay.Utils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/paymentSuccess")
public class PaymentSuccessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String razorpayOrderId = req.getParameter("razorpay_order_id");
        String razorpayPaymentId = req.getParameter("razorpay_payment_id");
        String razorpaySignature = req.getParameter("razorpay_signature");

        try {
            Map<String, String> attributes = new HashMap<>();
            attributes.put("razorpay_order_id", razorpayOrderId);
            attributes.put("razorpay_payment_id", razorpayPaymentId);
            attributes.put("razorpay_signature", razorpaySignature);

            Utils.verifyPaymentSignature((JSONObject) attributes, "asqJUTrmLx0aodnuFyCA8pzD");

            req.getRequestDispatcher("paymentSuccess.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Payment verification failed");
        }
    }
}
