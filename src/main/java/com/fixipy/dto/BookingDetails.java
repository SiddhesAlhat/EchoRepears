package com.fixipy.dto;

public class BookingDetails {
    private String customerName;
    private String phone;
    private String device;
    private String issue;
    private String address;
    private Location location;
    private Double amount;

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
    public BookingDetails() {}

    // Getters and Setters
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
}
