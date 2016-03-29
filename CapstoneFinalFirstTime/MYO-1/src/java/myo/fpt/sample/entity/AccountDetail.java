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
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyen
 */
@Entity
@Table(name = "accountDetail")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AccountDetail.findAll", query = "SELECT a FROM AccountDetail a"),
    @NamedQuery(name = "AccountDetail.findByDetailId", query = "SELECT a FROM AccountDetail a WHERE a.detailId = :detailId"),
    @NamedQuery(name = "AccountDetail.findByEmail", query = "SELECT a FROM AccountDetail a WHERE a.email = :email"),
    @NamedQuery(name = "AccountDetail.findByFullname", query = "SELECT a FROM AccountDetail a WHERE a.fullname = :fullname"),
    @NamedQuery(name = "AccountDetail.findByPhone", query = "SELECT a FROM AccountDetail a WHERE a.phone = :phone"),
    @NamedQuery(name = "AccountDetail.findByIsStaff", query = "SELECT a FROM AccountDetail a WHERE a.isStaff = :isStaff"),
    @NamedQuery(name = "AccountDetail.findByLicenseType", query = "SELECT a FROM AccountDetail a WHERE a.licenseType = :licenseType"),
    @NamedQuery(name = "AccountDetail.findByExpiredDate", query = "SELECT a FROM AccountDetail a WHERE a.expiredDate = :expiredDate"),
    @NamedQuery(name = "AccountDetail.findByStatus", query = "SELECT a FROM AccountDetail a WHERE a.status = :status")})
public class AccountDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "detailId")
    private Integer detailId;
    @Basic(optional = false)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "phone")
    private String phone;
    @Basic(optional = false)
    @Column(name = "isStaff")
    private boolean isStaff;
    @Basic(optional = false)
    @Column(name = "licenseType")
    private String licenseType;
    @Column(name = "expiredDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredDate;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;

    public AccountDetail() {
    }

    public AccountDetail(Integer detailId) {
        this.detailId = detailId;
    }

    public AccountDetail(Integer detailId, String email, String fullname, boolean isStaff, String licenseType, boolean status) {
        this.detailId = detailId;
        this.email = email;
        this.fullname = fullname;
        this.isStaff = isStaff;
        this.licenseType = licenseType;
        this.status = status;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
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

    public boolean getIsStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
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
        hash += (detailId != null ? detailId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AccountDetail)) {
            return false;
        }
        AccountDetail other = (AccountDetail) object;
        if ((this.detailId == null && other.detailId != null) || (this.detailId != null && !this.detailId.equals(other.detailId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.AccountDetail[ detailId=" + detailId + " ]";
    }
    
}
