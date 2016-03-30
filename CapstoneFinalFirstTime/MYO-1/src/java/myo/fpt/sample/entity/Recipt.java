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
@Table(name = "recipt")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Recipt.findAll", query = "SELECT r FROM Recipt r"),
    @NamedQuery(name = "Recipt.findByReciptId", query = "SELECT r FROM Recipt r WHERE r.reciptId = :reciptId"),
    @NamedQuery(name = "Recipt.findByLicenseId", query = "SELECT r FROM Recipt r WHERE r.licenseId = :licenseId"),
    @NamedQuery(name = "Recipt.findByStartDate", query = "SELECT r FROM Recipt r WHERE r.startDate = :startDate"),
    @NamedQuery(name = "Recipt.findByEndDate", query = "SELECT r FROM Recipt r WHERE r.endDate = :endDate"),
    @NamedQuery(name = "Recipt.findByCustId", query = "SELECT r FROM Recipt r WHERE r.custId = :custId"),
    @NamedQuery(name = "Recipt.findByPrice", query = "SELECT r FROM Recipt r WHERE r.price = :price")})
public class Recipt implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue
    @Id
    @Basic(optional = false)
    @Column(name = "reciptId")
    private Integer reciptId;
    @Column(name = "licenseId")
    private Integer licenseId;
    @Column(name = "startDate")
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "custId")
    private Integer custId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "price")
    private Double price;

    public Recipt() {
    }

    public Recipt(Integer reciptId) {
        this.reciptId = reciptId;
    }
    
    public Recipt(Integer licenseId, Date startDate, Integer custId, double price){
        this.licenseId = licenseId;
        this.startDate = startDate;
        this.custId = custId;
        this.price = price;
    }

    public Integer getReciptId() {
        return reciptId;
    }

    public void setReciptId(Integer reciptId) {
        this.reciptId = reciptId;
    }

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reciptId != null ? reciptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recipt)) {
            return false;
        }
        Recipt other = (Recipt) object;
        if ((this.reciptId == null && other.reciptId != null) || (this.reciptId != null && !this.reciptId.equals(other.reciptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.Recipt[ reciptId=" + reciptId + " ]";
    }
    
}
