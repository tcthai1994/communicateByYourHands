/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyen
 */
@Entity
@Table(name = "customContent")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomContent.findAll", query = "SELECT c FROM CustomContent c"),
    @NamedQuery(name = "CustomContent.findByMeaningCode", query = "SELECT c FROM CustomContent c WHERE c.meaningCode = :meaningCode"),
    @NamedQuery(name = "CustomContent.findByMeaning", query = "SELECT c FROM CustomContent c WHERE c.meaning = :meaning"),
    @NamedQuery(name = "CustomContent.findByCustId", query = "SELECT c FROM CustomContent c WHERE c.custId = :custId"),
    @NamedQuery(name = "CustomContent.findByStatus", query = "SELECT c FROM CustomContent c WHERE c.status = :status")})
public class CustomContent implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "meaningCode")
    private Integer meaningCode;
    @Basic(optional = false)
    @Column(name = "meaning")
    private String meaning;
    @Basic(optional = false)
    @Column(name = "custId")
    private int custId;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;

    public CustomContent() {
    }

    public CustomContent(Integer meaningCode) {
        this.meaningCode = meaningCode;
    }

    public CustomContent(Integer meaningCode, String meaning, int custId, boolean status) {
        this.meaningCode = meaningCode;
        this.meaning = meaning;
        this.custId = custId;
        this.status = status;
    }

    public Integer getMeaningCode() {
        return meaningCode;
    }

    public void setMeaningCode(Integer meaningCode) {
        this.meaningCode = meaningCode;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
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
        hash += (meaningCode != null ? meaningCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomContent)) {
            return false;
        }
        CustomContent other = (CustomContent) object;
        if ((this.meaningCode == null && other.meaningCode != null) || (this.meaningCode != null && !this.meaningCode.equals(other.meaningCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.CustomContent[ meaningCode=" + meaningCode + " ]";
    }
    
}