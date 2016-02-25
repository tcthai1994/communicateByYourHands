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
 * @author Thai
 */
@Embeddable
public class WordSignalPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "meaningLeft")
    private int meaningLeft;
    @Basic(optional = false)
    @Column(name = "meaningRight")
    private int meaningRight;

    public WordSignalPK() {
    }

    public WordSignalPK(int meaningLeft, int meaningRight) {
        this.meaningLeft = meaningLeft;
        this.meaningRight = meaningRight;
    }

    public int getMeaningLeft() {
        return meaningLeft;
    }

    public void setMeaningLeft(int meaningLeft) {
        this.meaningLeft = meaningLeft;
    }

    public int getMeaningRight() {
        return meaningRight;
    }

    public void setMeaningRight(int meaningRight) {
        this.meaningRight = meaningRight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) meaningLeft;
        hash += (int) meaningRight;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WordSignalPK)) {
            return false;
        }
        WordSignalPK other = (WordSignalPK) object;
        if (this.meaningLeft != other.meaningLeft) {
            return false;
        }
        if (this.meaningRight != other.meaningRight) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.myo.entityBean.WordSignalPK[ meaningLeft=" + meaningLeft + ", meaningRight=" + meaningRight + " ]";
    }
    
}
