/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import myo.fpt.sample.entity.AccountDetail;

/**
 *
 * @author nguyen
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByCustId", query = "SELECT a FROM Account a WHERE a.custId = :custId"),
    @NamedQuery(name = "Account.findByUsername", query = "SELECT a FROM Account a WHERE a.username = :username"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByDetailId", query = "SELECT a FROM Account a WHERE a.detailId = :detailId"),
    @NamedQuery(name = "Account.findByDeviceId", query = "SELECT a FROM Account a WHERE a.deviceId = :deviceId")})
public class Account implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "isStaff")
    private boolean isStaff;
    @Column(name = "expiredDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "licenseType")
    private String licenseType;
    private static final long serialVersionUID = 1L;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Basic(optional = false)
    @Column(name = "custId")
    private Integer custId;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "detailId")
    private int detailId;
    @Column(name = "deviceId")
    private String deviceId;
    
    

    public Account() {
    }

    public Account(Integer custId) {
        this.custId = custId;
    }

    public Account(Integer custId, String username, String password, int detailId) {
        this.custId = custId;
        this.username = username;
        this.password = password;
        this.detailId = detailId;
    }
    
    public Account(String username, String password, int detailId) {
        this.username = username;
        this.password = password;
        this.detailId = detailId;
    }
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Account(String username, String password, int detailId, boolean isStaff, Date expiredDate, String licenseType, String deviceId) {
        this.username = username;
        this.password = password;
        this.detailId = detailId;
        this.isStaff = isStaff;
        this.expiredDate = expiredDate;
        this.licenseType = licenseType;
        this.deviceId = deviceId;
    }
    
    public Account(String deviceId){
        this.deviceId = deviceId;
    }
    
    public Account(boolean isStaff, Date expiredDate, String licenseType){
        this.isStaff = isStaff;
        this.expiredDate = expiredDate;
        this.licenseType = licenseType;
    }
    
    public Account(String licenseType, Date expiredDate){
        this.licenseType = licenseType;
        this.expiredDate = expiredDate;
    }
    
//    public Account(String username, AccountDetail accountDetail){
//        this.username = username;
//        this.accountDetail = accountDetail;
//    }
//    
//    public Account(String username, String password, AccountDetail accountDetail){
//        this.username = username;
//        this.password = password;
//        this.accountDetail = accountDetail;
//    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custId != null ? custId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.custId == null && other.custId != null) || (this.custId != null && !this.custId.equals(other.custId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.Account[ custId=" + custId + " ]";
    }

    public boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }
    
}
