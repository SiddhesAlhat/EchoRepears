package com.fixipy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

@Document(collection = "orders")
public class Order {
    
    @Id
    private String id;
    
    @Field("customerName")
    private String customerName;
    
    @Field("phone")
    private String phone;
    
    @Field("device")
    private String device;
    
    @Field("issue")
    private String issue;
    
    @Field("address")
    private String address;
    
    @Field("location")
    private Location location;
    
    @Field("amount")
    private Double amount;
    
    @Field("orderId")
    private String orderId;
    
    @Field("paymentId")
    private String paymentId;
    
    @Field("status")
    private String status = "Pending";
    
    @Field("createdAt")
    private Date createdAt = new Date();
    
    // Inner class for location
    public static class Location {
        private String lat;
        private String longitude;
        
        public Location() {}
        
        public Location(String lat, String longitude) {
            this.lat = lat;
            this.longitude = longitude;
        }
        
        public String getLat() { return lat; }
        public void setLat(String lat) { this.lat = lat; }
        public String getLongitude() { return longitude; }
        public void setLongitude(String longitude) { this.longitude = longitude; }
    }
    
    // Constructors
    public Order() {}
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getDevice() { return device; }
    public void setDevice(String device) { this.device = device; }
    
    public String getIssue() { return issue; }
    public void setIssue(String issue) { this.issue = issue; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
