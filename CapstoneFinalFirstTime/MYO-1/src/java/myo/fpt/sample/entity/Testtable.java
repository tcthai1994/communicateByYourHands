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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nguyen
 */
@Entity
@Table(name = "TESTTABLE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Testtable.findAll", query = "SELECT t FROM Testtable t"),
    @NamedQuery(name = "Testtable.findByIdtest", query = "SELECT t FROM Testtable t WHERE t.idtest = :idtest"),
    @NamedQuery(name = "Testtable.findByNametest", query = "SELECT t FROM Testtable t WHERE t.nametest = :nametest")})
public class Testtable implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "IDTEST")
    private Integer idtest;
    @Size(max = 50)
    @Column(name = "NAMETEST")
    private String nametest;

    public Testtable() {
    }

    public Testtable(Integer idtest) {
        this.idtest = idtest;
    }

    public Integer getIdtest() {
        return idtest;
    }

    public void setIdtest(Integer idtest) {
        this.idtest = idtest;
    }

    public String getNametest() {
        return nametest;
    }

    public void setNametest(String nametest) {
        this.nametest = nametest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtest != null ? idtest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Testtable)) {
            return false;
        }
        Testtable other = (Testtable) object;
        if ((this.idtest == null && other.idtest != null) || (this.idtest != null && !this.idtest.equals(other.idtest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.Testtable[ idtest=" + idtest + " ]";
    }
    
}
