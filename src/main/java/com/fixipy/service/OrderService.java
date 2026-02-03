package com.fixipy.service;

import com.fixipy.dto.BookingDetails;
import com.fixipy.repository.OrderRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Value("${razorpay.key.id}")
    private String razorpayKeyId;

    @Value("${razorpay.key.secret}")
    private String razorpayKeySecret;

    @Value("${twilio.sid}")
    private String twilioSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.whatsapp.from}")
    private String twilioWhatsAppFrom;

    @Value("${admin.whatsapp.number}")
    private String adminWhatsAppNumber;

    public Map<String, Object> createRazorpayOrder(int amount) throws RazorpayException {
        RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
        
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", amount);
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", "order_rcptid_" + System.currentTimeMillis());

        com.razorpay.Order order = razorpayClient.orders.create(orderRequest);
        
        Map<String, Object> response = new HashMap<>();
        response.put("id", order.get("id"));
        response.put("amount", order.get("amount"));
        response.put("currency", order.get("currency"));
        response.put("receipt", order.get("receipt"));
        
        return response;
    }

    public boolean verifyPayment(String razorpayOrderId, String razorpayPaymentId, 
                                String razorpaySignature, BookingDetails bookingDetails) {
        try {
            // Verify signature
            String payload = razorpayOrderId + "|" + razorpayPaymentId;
            String expectedSignature = generateSignature(payload, razorpayKeySecret);
            
            if (!expectedSignature.equals(razorpaySignature)) {
                return false;
            }

            // Save order to database
            com.fixipy.entity.Order newOrder = new com.fixipy.entity.Order();
            newOrder.setCustomerName(bookingDetails.getCustomerName());
            newOrder.setPhone(bookingDetails.getPhone());
            newOrder.setDevice(bookingDetails.getDevice());
            newOrder.setIssue(bookingDetails.getIssue());
            newOrder.setAddress(bookingDetails.getAddress());
            
            if (bookingDetails.getLocation() != null) {
                com.fixipy.entity.Order.Location location = new com.fixipy.entity.Order.Location(
                    bookingDetails.getLocation().getLat(),
                    bookingDetails.getLocation().getLongitude()
                );
                newOrder.setLocation(location);
            }
            
            newOrder.setAmount(bookingDetails.getAmount());
            newOrder.setOrderId(razorpayOrderId);
            newOrder.setPaymentId(razorpayPaymentId);
            newOrder.setStatus("Paid");
            
            orderRepository.save(newOrder);

            // Send WhatsApp notification
            sendWhatsAppNotification(bookingDetails);
            
            return true;
        } catch (Exception e) {
            System.err.println("Payment verification failed: " + e.getMessage());
            return false;
        }
    }

    private String generateSignature(String payload, String secret) {
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] signatureBytes = sha256_HMAC.doFinal(payload.getBytes());
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate signature", e);
        }
    }

    private void sendWhatsAppNotification(BookingDetails bookingDetails) {
        try {
            Twilio.init(twilioSid, twilioAuthToken);
            
            String messageBody = String.format(
                "🚀 *New Fixipy Order!*\n👤 Name: %s\n📱 Phone: %s\n🔧 Device: %s\n💰 Status: PAID",
                bookingDetails.getCustomerName(),
                bookingDetails.getPhone(),
                bookingDetails.getDevice()
            );
            
            Message message = Message.creator(
                new PhoneNumber("whatsapp:" + adminWhatsAppNumber),
                new PhoneNumber("whatsapp:" + twilioWhatsAppFrom),
                messageBody
            ).create();
            
            System.out.println("WhatsApp sent: " + message.getSid());
        } catch (Exception e) {
            System.err.println("WhatsApp failed: " + e.getMessage());
        }
    }
}
