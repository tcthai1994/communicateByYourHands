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
@Table(name = "meaningLeft")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeaningLeft.findAll", query = "SELECT m FROM MeaningLeft m"),
    @NamedQuery(name = "MeaningLeft.findByMeaningLeft", query = "SELECT m FROM MeaningLeft m WHERE m.meaningLeft = :meaningLeft")})
public class MeaningLeft implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "meaningLeft")
    private Integer meaningLeft;

    public MeaningLeft() {
    }

    public MeaningLeft(Integer meaningLeft) {
        this.meaningLeft = meaningLeft;
    }

    public Integer getMeaningLeft() {
        return meaningLeft;
    }

    public void setMeaningLeft(Integer meaningLeft) {
        this.meaningLeft = meaningLeft;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meaningLeft != null ? meaningLeft.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeaningLeft)) {
            return false;
        }
        MeaningLeft other = (MeaningLeft) object;
        if ((this.meaningLeft == null && other.meaningLeft != null) || (this.meaningLeft != null && !this.meaningLeft.equals(other.meaningLeft))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.MeaningLeft[ meaningLeft=" + meaningLeft + " ]";
    }
    
}
