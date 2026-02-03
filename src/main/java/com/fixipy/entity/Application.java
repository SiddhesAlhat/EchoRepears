package com.fixipy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.Date;

@Document(collection = "applications")
public class Application {
    
    @Id
    private String id;
    
    @Field("name")
    private String name;
    
    @Field("email")
    private String email;
    
    @Field("phone")
    private String phone;
    
    @Field("role")
    private String role;
    
    @Field("reason")
    private String reason;
    
    @Field("resumePath")
    private String resumePath;
    
    @Field("createdAt")
    private Date createdAt = new Date();
    
    // Constructors
    public Application() {}
    
    public Application(String name, String email, String phone, String role, String reason, String resumePath) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.reason = reason;
        this.resumePath = resumePath;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    
    public String getResumePath() { return resumePath; }
    public void setResumePath(String resumePath) { this.resumePath = resumePath; }
    
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}
