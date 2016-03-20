/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.entityBean;

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
 * @author AnhND
 */
@Entity
@Table(name = "leftSignal", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LeftSignal.findAll", query = "SELECT l FROM LeftSignal l"),
    @NamedQuery(name = "LeftSignal.findByEmgCode", query = "SELECT l FROM LeftSignal l WHERE l.emgCode = :emgCode"),
    @NamedQuery(name = "LeftSignal.findByMeaningLeft", query = "SELECT l FROM LeftSignal l WHERE l.meaningLeft = :meaningLeft"),
    @NamedQuery(name = "LeftSignal.findByIsCustom", query = "SELECT l FROM LeftSignal l WHERE l.isCustom = :isCustom")})
public class LeftSignal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "emgCode")
    private String emgCode;
    @Basic(optional = false)
    @Column(name = "meaningLeft")
    private int meaningLeft;
    @Basic(optional = false)
    @Column(name = "isCustom")
    private boolean isCustom;

    public LeftSignal() {
    }

    public LeftSignal(String emgCode) {
        this.emgCode = emgCode;
    }

    public LeftSignal(String emgCode, int meaningLeft, boolean isCustom) {
        this.emgCode = emgCode;
        this.meaningLeft = meaningLeft;
        this.isCustom = isCustom;
    }

    public String getEmgCode() {
        return emgCode;
    }

    public void setEmgCode(String emgCode) {
        this.emgCode = emgCode;
    }

    public int getMeaningLeft() {
        return meaningLeft;
    }

    public void setMeaningLeft(int meaningLeft) {
        this.meaningLeft = meaningLeft;
    }

    public boolean getIsCustom() {
        return isCustom;
    }

    public void setIsCustom(boolean isCustom) {
        this.isCustom = isCustom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emgCode != null ? emgCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LeftSignal)) {
            return false;
        }
        LeftSignal other = (LeftSignal) object;
        if ((this.emgCode == null && other.emgCode != null) || (this.emgCode != null && !this.emgCode.equals(other.emgCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.entity.LeftSignal[ emgCode=" + emgCode + " ]";
    }
    
}
