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
@Table(name = "library")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Library.findAll", query = "SELECT l FROM Library l"),
    @NamedQuery(name = "Library.findByLibraryId", query = "SELECT l FROM Library l WHERE l.libraryId = :libraryId"),
    @NamedQuery(name = "Library.findByLibraryName", query = "SELECT l FROM Library l WHERE l.libraryName = :libraryName"),
    @NamedQuery(name = "Library.findByStatus", query = "SELECT l FROM Library l WHERE l.status = :status")})
public class Library implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "libraryId")
    private Integer libraryId;
    @Basic(optional = false)
    @Column(name = "libraryName")
    private String libraryName;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;

    public Library() {
    }

    public Library(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public Library(Integer libraryId, String libraryName, boolean status) {
        this.libraryId = libraryId;
        this.libraryName = libraryName;
        this.status = status;
    }

    public Integer getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(Integer libraryId) {
        this.libraryId = libraryId;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
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
        hash += (libraryId != null ? libraryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Library)) {
            return false;
        }
        Library other = (Library) object;
        if ((this.libraryId == null && other.libraryId != null) || (this.libraryId != null && !this.libraryId.equals(other.libraryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.Library[ libraryId=" + libraryId + " ]";
    }
    
}
