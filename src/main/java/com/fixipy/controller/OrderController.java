package com.fixipy.controller;

import com.fixipy.dto.BookingDetails;
import com.fixipy.entity.Order;
import com.fixipy.service.OrderService;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder() {
        try {
            Map<String, Object> order = orderService.createRazorpayOrder(50000); // 500 INR in paise
            return ResponseEntity.ok(order);
        } catch (RazorpayException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify-payment")
    public ResponseEntity<?> verifyPayment(@RequestBody Map<String, Object> requestBody) {
        try {
            String razorpayOrderId = (String) requestBody.get("razorpay_order_id");
            String razorpayPaymentId = (String) requestBody.get("razorpay_payment_id");
            String razorpaySignature = (String) requestBody.get("razorpay_signature");
            
            Map<String, Object> bookingMap = (Map<String, Object>) requestBody.get("booking_details");
            BookingDetails bookingDetails = new BookingDetails();
            bookingDetails.setCustomerName((String) bookingMap.get("customerName"));
            bookingDetails.setPhone((String) bookingMap.get("phone"));
            bookingDetails.setDevice((String) bookingMap.get("device"));
            bookingDetails.setIssue((String) bookingMap.get("issue"));
            bookingDetails.setAddress((String) bookingMap.get("address"));
            bookingDetails.setAmount(Double.valueOf(bookingMap.get("amount").toString()));
            
            Map<String, Object> locationMap = (Map<String, Object>) bookingMap.get("location");
            if (locationMap != null) {
                BookingDetails.Location location = new BookingDetails.Location();
                location.setLat((String) locationMap.get("lat"));
                location.setLongitude((String) locationMap.get("long"));
                bookingDetails.setLocation(location);
            }

            boolean isValid = orderService.verifyPayment(razorpayOrderId, razorpayPaymentId, razorpaySignature, bookingDetails);
            
            if (isValid) {
                return ResponseEntity.ok(Map.of("status", "success"));
            } else {
                return ResponseEntity.status(400).body(Map.of("status", "failure"));
            }
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }
}
