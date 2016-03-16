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
@Table(name = "meaningRignt", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeaningRignt.findAll", query = "SELECT m FROM MeaningRignt m"),
    @NamedQuery(name = "MeaningRignt.findByMeaningRight", query = "SELECT m FROM MeaningRignt m WHERE m.meaningRight = :meaningRight")})
public class MeaningRignt implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "meaningRight")
    private Integer meaningRight;

    public MeaningRignt() {
    }

    public MeaningRignt(Integer meaningRight) {
        this.meaningRight = meaningRight;
    }

    public Integer getMeaningRight() {
        return meaningRight;
    }

    public void setMeaningRight(Integer meaningRight) {
        this.meaningRight = meaningRight;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (meaningRight != null ? meaningRight.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeaningRignt)) {
            return false;
        }
        MeaningRignt other = (MeaningRignt) object;
        if ((this.meaningRight == null && other.meaningRight != null) || (this.meaningRight != null && !this.meaningRight.equals(other.meaningRight))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.entity.MeaningRignt[ meaningRight=" + meaningRight + " ]";
    }
    
}
