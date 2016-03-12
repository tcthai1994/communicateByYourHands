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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thai
 */
@Entity
@Table(name = "dataContent", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataContent.findAll", query = "SELECT d FROM DataContent d"),
    @NamedQuery(name = "DataContent.findByMeaningCode", query = "SELECT d FROM DataContent d WHERE d.meaningCode = :meaningCode"),
    @NamedQuery(name = "DataContent.findByMeaning", query = "SELECT d FROM DataContent d WHERE d.meaning = :meaning"),
    @NamedQuery(name = "DataContent.findByLibraryId", query = "SELECT d FROM DataContent d WHERE d.libraryId = :libraryId")})
public class DataContent implements Serializable {

    private static final long serialVersionUID = 1L;
    @GeneratedValue
    @Id
    @Basic(optional = false)
    @Column(name = "meaningCode", nullable = false)
    private Integer meaningCode;
    @Basic(optional = false)
    @Column(name = "meaning", nullable = false, length = 50)
    private String meaning;
    @Basic(optional = false)
    @Column(name = "libraryId", nullable = false)
    private int libraryId;

    public DataContent() {
    }

    public DataContent(Integer meaningCode) {
        this.meaningCode = meaningCode;
    }

    public DataContent(Integer meaningCode, String meaning, int libraryId) {
        this.meaningCode = meaningCode;
        this.meaning = meaning;
        this.libraryId = libraryId;
    }

        public DataContent(String meaning, int libraryId) {
        this.meaning = meaning;
        this.libraryId = libraryId;
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

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
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
        if (!(object instanceof DataContent)) {
            return false;
        }
        DataContent other = (DataContent) object;
        if ((this.meaningCode == null && other.meaningCode != null) || (this.meaningCode != null && !this.meaningCode.equals(other.meaningCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.myo.entityBean.DataContent[ meaningCode=" + meaningCode + " ]";
    }

}
