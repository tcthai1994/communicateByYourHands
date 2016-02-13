/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.entityBean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thai
 */
@Entity
@Table(name = "account", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByCustId", query = "SELECT a FROM Account a WHERE a.custId = :custId"),
    @NamedQuery(name = "Account.findByUsername", query = "SELECT a FROM Account a WHERE a.username = :username"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email"),
    @NamedQuery(name = "Account.findByFullname", query = "SELECT a FROM Account a WHERE a.fullname = :fullname"),
    @NamedQuery(name = "Account.findByPhone", query = "SELECT a FROM Account a WHERE a.phone = :phone"),
    @NamedQuery(name = "Account.findByIsAdmin", query = "SELECT a FROM Account a WHERE a.isAdmin = :isAdmin"),
    @NamedQuery(name = "Account.findByLicenseType", query = "SELECT a FROM Account a WHERE a.licenseType = :licenseType"),
    @NamedQuery(name = "Account.findByExpiredDate", query = "SELECT a FROM Account a WHERE a.expiredDate = :expiredDate"),
    @NamedQuery(name = "Account.findByStatus", query = "SELECT a FROM Account a WHERE a.status = :status")})
public class Account implements Serializable {
    @Basic(optional = false)
    @Column(name = "detailId")
    private int detailId;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "custId", nullable = false)
    private Integer custId;
    @Basic(optional = false)
    @Column(name = "username", nullable = false, length = 250)
    private String username;
    @Basic(optional = false)
    @Column(name = "password", nullable = false, length = 2147483647)
    private String password;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 250)
    private String email;
    @Basic(optional = false)
    @Column(name = "fullname", nullable = false, length = 250)
    private String fullname;
    @Column(name = "phone", length = 11)
    private String phone;
    @Basic(optional = false)
    @Column(name = "isAdmin", nullable = false)
    private boolean isAdmin;
    @Basic(optional = false)
    @Column(name = "licenseType", nullable = false, length = 20)
    private String licenseType;
    @Column(name = "expiredDate")
    @Temporal(TemporalType.DATE)
    private Date expiredDate;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private boolean status;

    public Account() {
    }

    public Account(Integer custId) {
        this.custId = custId;
    }

    public Account(Integer custId, String username, String password, String email, String fullname, boolean isAdmin, String licenseType, boolean status) {
        this.custId = custId;
        this.username = username;
        this.password = password;
        this.email = email;
        this.fullname = fullname;
        this.isAdmin = isAdmin;
        this.licenseType = licenseType;
        this.status = status;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public void setLicenseType(String licenseType) {
        this.licenseType = licenseType;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
        return "fpt.myo.entityBean.Account[ custId=" + custId + " ]";
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }
    
}
