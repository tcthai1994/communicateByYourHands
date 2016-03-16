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
@Table(name = "rightSignal", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RightSignal.findAll", query = "SELECT r FROM RightSignal r"),
    @NamedQuery(name = "RightSignal.findByEmgCode", query = "SELECT r FROM RightSignal r WHERE r.emgCode = :emgCode"),
    @NamedQuery(name = "RightSignal.findByMeaningRight", query = "SELECT r FROM RightSignal r WHERE r.meaningRight = :meaningRight"),
    @NamedQuery(name = "RightSignal.findByIsCustom", query = "SELECT r FROM RightSignal r WHERE r.isCustom = :isCustom")})
public class RightSignal implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "emgCode")
    private String emgCode;
    @Basic(optional = false)
    @Column(name = "meaningRight")
    private int meaningRight;
    @Basic(optional = false)
    @Column(name = "isCustom")
    private boolean isCustom;

    public RightSignal() {
    }

    public RightSignal(String emgCode) {
        this.emgCode = emgCode;
    }

    public RightSignal(String emgCode, int meaningRight, boolean isCustom) {
        this.emgCode = emgCode;
        this.meaningRight = meaningRight;
        this.isCustom = isCustom;
    }

    public String getEmgCode() {
        return emgCode;
    }

    public void setEmgCode(String emgCode) {
        this.emgCode = emgCode;
    }

    public int getMeaningRight() {
        return meaningRight;
    }

    public void setMeaningRight(int meaningRight) {
        this.meaningRight = meaningRight;
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
        if (!(object instanceof RightSignal)) {
            return false;
        }
        RightSignal other = (RightSignal) object;
        if ((this.emgCode == null && other.emgCode != null) || (this.emgCode != null && !this.emgCode.equals(other.emgCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.entity.RightSignal[ emgCode=" + emgCode + " ]";
    }
    
}
