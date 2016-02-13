/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.entityBean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thai
 */
@Entity
@Table(name = "customSignal", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CustomSignal.findAll", query = "SELECT c FROM CustomSignal c"),
    @NamedQuery(name = "CustomSignal.findByCustomLeft", query = "SELECT c FROM CustomSignal c WHERE c.customSignalPK.customLeft = :customLeft"),
    @NamedQuery(name = "CustomSignal.findByCustomRigh", query = "SELECT c FROM CustomSignal c WHERE c.customSignalPK.customRigh = :customRigh"),
    @NamedQuery(name = "CustomSignal.findByMeaningCode", query = "SELECT c FROM CustomSignal c WHERE c.meaningCode = :meaningCode")})
public class CustomSignal implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CustomSignalPK customSignalPK;
    @Basic(optional = false)
    @Column(name = "meaningCode", nullable = false)
    private int meaningCode;

    public CustomSignal() {
    }

    public CustomSignal(CustomSignalPK customSignalPK) {
        this.customSignalPK = customSignalPK;
    }

    public CustomSignal(CustomSignalPK customSignalPK, int meaningCode) {
        this.customSignalPK = customSignalPK;
        this.meaningCode = meaningCode;
    }

    public CustomSignal(int customLeft, int customRigh) {
        this.customSignalPK = new CustomSignalPK(customLeft, customRigh);
    }

    public CustomSignalPK getCustomSignalPK() {
        return customSignalPK;
    }

    public void setCustomSignalPK(CustomSignalPK customSignalPK) {
        this.customSignalPK = customSignalPK;
    }

    public int getMeaningCode() {
        return meaningCode;
    }

    public void setMeaningCode(int meaningCode) {
        this.meaningCode = meaningCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customSignalPK != null ? customSignalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomSignal)) {
            return false;
        }
        CustomSignal other = (CustomSignal) object;
        if ((this.customSignalPK == null && other.customSignalPK != null) || (this.customSignalPK != null && !this.customSignalPK.equals(other.customSignalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.myo.entityBean.CustomSignal[ customSignalPK=" + customSignalPK + " ]";
    }
    
}
