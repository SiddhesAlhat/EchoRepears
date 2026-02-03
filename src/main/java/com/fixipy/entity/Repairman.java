package com.fixipy.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.util.List;

@Document(collection = "repairmen")
public class Repairman {
    
    @Id
    private String id;
    
    @Field("name")
    private String name;
    
    @Field("phone")
    private String phone;
    
    @Field("email")
    private String email;
    
    @Field("aadhar")
    private String aadhar;
    
    @Field("experience")
    private String experience;
    
    @Field("skills")
    private List<String> skills;
    
    @Field("address")
    private String address;
    
    // Constructors
    public Repairman() {}
    
    public Repairman(String name, String phone, String email, String aadhar, String experience, List<String> skills, String address) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.aadhar = aadhar;
        this.experience = experience;
        this.skills = skills;
        this.address = address;
    }
    
    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getAadhar() { return aadhar; }
    public void setAadhar(String aadhar) { this.aadhar = aadhar; }
    
    public String getExperience() { return experience; }
    public void setExperience(String experience) { this.experience = experience; }
    
    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }
    
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
