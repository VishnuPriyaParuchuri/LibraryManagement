package com.library_management.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserServiceDTO {

    private String id;

    @Size(min = 2, max = 30, message = "Username must be between 2 and 50 characters")
    private String userName;

    @Email(message = "Invalid email format")
    private String email;

    private String password;

    private String role;

    private String uuid;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phone;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private String dob;

    private String rollNo;

    private String country;

    private String state;

    private String gender;

    private LocalDateTime createdAt;

    private String createdBy;

    private Boolean isAdmin;

    private LocalDateTime updatedAt;

    private String updatedBy;

    public UserServiceDTO() {

    }

    public UserServiceDTO(String id, String userName, String email, String password, String role, String uuid,
            String phone, String dob, String rollNo, String country, String state, String gender,
            LocalDateTime createdAt,
            String createdBy, LocalDateTime updatedAt, String updatedBy) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.uuid = uuid;
        this.phone = phone;
        this.dob = dob;
        this.rollNo = rollNo;
        this.country = country;
        this.state = state;
        this.gender = gender;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
        this.updatedAt = updatedAt;
        this.updatedBy = updatedBy;
    }

    public UserServiceDTO(String id2, String userName2, String email2, String role2, String country2, String state2,
            String dob2, String phone2, String rollNo2, String gender2, LocalDateTime createdAt2, String createdBy2,
            LocalDateTime updatedAt2, String updatedBy2) {

        this.id = id2;
        this.userName = userName2;
        this.email = email2;
        this.role = role2;
        this.phone = phone2;
        this.dob = dob2;
        this.rollNo = rollNo2;
        this.country = country2;
        this.state = state2;
        this.gender = gender2;
        this.createdAt = createdAt2;
        this.createdBy = createdBy2;
        this.updatedAt = updatedAt2;
        this.updatedBy = updatedBy2;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

}
