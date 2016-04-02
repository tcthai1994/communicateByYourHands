/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.dto;

import java.util.Date;

/**
 *
 * @author AnhND
 */
public class AccountManage {

    private int detailId;
    private String username;
    private String password;
    private String email;
    private String fullname;
    private String phone;
    private boolean isStaff;
    private String licenseType;
    private String expiredDate;
    private boolean status;

    public AccountManage() {
    }

    public AccountManage(String username, int detailId, String email, String fullname, String phone, boolean isStaff, String licenseType, String expiredDate, boolean status) {
        this.username = username;
        this.detailId = detailId;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.isStaff = isStaff;
        this.licenseType = licenseType;
        this.expiredDate = expiredDate;
        this.status = status;
    }
    
    public AccountManage(String username, String password, int detailId, String email, String fullname, String phone, String licenseType, String expiredDate){
        this.username = username;
        this.password = password;
        this.detailId = detailId;
        this.email = email;
        this.fullname = fullname;
        this.phone = phone;
        this.licenseType = licenseType;
        this.expiredDate = expiredDate;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the fullname
     */
    public String getFullname() {
        return fullname;
    }

    /**
     * @param fullname the fullname to set
     */
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the isStaff
     */
    public boolean isIsStaff() {
        return isStaff;
    }

    /**
     * @param isStaff the isStaff to set
     */
    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    /**
     * @return the licenseType
     */
    public String getLicenseType() {
        return licenseType;
    }

    /**
     * @param licenseType the licenseType to set
     */
    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    /**
     * @return the expiredDate
     */
    public String getExpiredDate() {
        return expiredDate;
    }

    /**
     * @param expiredDate the expiredDate to set
     */
    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
    }

    /**
     * @return the status
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }

    /**
     * @return the detailId
     */
    public int getDetailId() {
        return detailId;
    }

    /**
     * @param detailId the detailId to set
     */
    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
