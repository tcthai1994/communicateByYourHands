/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.entityBean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author AnhND
 */
@Embeddable
public class CustomSignalPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "customLeft")
    private int customLeft;
    @Basic(optional = false)
    @Column(name = "customRigh")
    private int customRigh;

    public CustomSignalPK() {
    }

    public CustomSignalPK(int customLeft, int customRigh) {
        this.customLeft = customLeft;
        this.customRigh = customRigh;
    }

    public int getCustomLeft() {
        return customLeft;
    }

    public void setCustomLeft(int customLeft) {
        this.customLeft = customLeft;
    }

    public int getCustomRigh() {
        return customRigh;
    }

    public void setCustomRigh(int customRigh) {
        this.customRigh = customRigh;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) customLeft;
        hash += (int) customRigh;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomSignalPK)) {
            return false;
        }
        CustomSignalPK other = (CustomSignalPK) object;
        if (this.customLeft != other.customLeft) {
            return false;
        }
        if (this.customRigh != other.customRigh) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.entity.CustomSignalPK[ customLeft=" + customLeft + ", customRigh=" + customRigh + " ]";
    }
    
}
