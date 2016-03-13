/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author AnhND
 */
@Entity
@Table(name = "license", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "License.findAll", query = "SELECT l FROM License l"),
    @NamedQuery(name = "License.findByLicenseId", query = "SELECT l FROM License l WHERE l.licenseId = :licenseId"),
    @NamedQuery(name = "License.findByLicenseName", query = "SELECT l FROM License l WHERE l.licenseName = :licenseName"),
    @NamedQuery(name = "License.findByPrice", query = "SELECT l FROM License l WHERE l.price = :price"),
    @NamedQuery(name = "License.findByDescription", query = "SELECT l FROM License l WHERE l.description = :description"),
    @NamedQuery(name = "License.findByStatus", query = "SELECT l FROM License l WHERE l.status = :status")})
public class License implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue
    @Id
    @Basic(optional = false)
    @Column(name = "licenseId")
    private Integer licenseId;
    @Basic(optional = false)
    @Column(name = "licenseName")
    private String licenseName;
    @Basic(optional = false)
    @Column(name = "price")
    private double price;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;

    public License() {
    }

    public License(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public License(Integer licenseId, String licenseName, double price, String description, boolean status) {
        this.licenseId = licenseId;
        this.licenseName = licenseName;
        this.price = price;
        this.description = description;
        this.status = status;
    }
    
    public License(String licenseName, double price, String description, boolean status) {
        this.licenseName = licenseName;
        this.price = price;
        this.description = description;
        this.status = status;
    }

    public Integer getLicenseId() {
        return licenseId;
    }

    public void setLicenseId(Integer licenseId) {
        this.licenseId = licenseId;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        hash += (licenseId != null ? licenseId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof License)) {
            return false;
        }
        License other = (License) object;
        if ((this.licenseId == null && other.licenseId != null) || (this.licenseId != null && !this.licenseId.equals(other.licenseId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.entity.License[ licenseId=" + licenseId + " ]";
    }
    
}
