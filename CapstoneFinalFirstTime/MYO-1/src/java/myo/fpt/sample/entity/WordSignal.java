/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity;

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
 * @author nguyen
 */
@Entity
@Table(name = "wordSignal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WordSignal.findAll", query = "SELECT w FROM WordSignal w"),
    @NamedQuery(name = "WordSignal.findByMeaningLeft", query = "SELECT w FROM WordSignal w WHERE w.wordSignalPK.meaningLeft = :meaningLeft"),
    @NamedQuery(name = "WordSignal.findByMeaningRight", query = "SELECT w FROM WordSignal w WHERE w.wordSignalPK.meaningRight = :meaningRight"),
    @NamedQuery(name = "WordSignal.findByMeaningCode", query = "SELECT w FROM WordSignal w WHERE w.meaningCode = :meaningCode")})
public class WordSignal implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WordSignalPK wordSignalPK;
    @Basic(optional = false)
    @Column(name = "meaningCode")
    private int meaningCode;

    public WordSignal() {
    }

    public WordSignal(WordSignalPK wordSignalPK) {
        this.wordSignalPK = wordSignalPK;
    }

    public WordSignal(WordSignalPK wordSignalPK, int meaningCode) {
        this.wordSignalPK = wordSignalPK;
        this.meaningCode = meaningCode;
    }

    public WordSignal(int meaningLeft, int meaningRight) {
        this.wordSignalPK = new WordSignalPK(meaningLeft, meaningRight);
    }

    public WordSignalPK getWordSignalPK() {
        return wordSignalPK;
    }

    public void setWordSignalPK(WordSignalPK wordSignalPK) {
        this.wordSignalPK = wordSignalPK;
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
        hash += (wordSignalPK != null ? wordSignalPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WordSignal)) {
            return false;
        }
        WordSignal other = (WordSignal) object;
        if ((this.wordSignalPK == null && other.wordSignalPK != null) || (this.wordSignalPK != null && !this.wordSignalPK.equals(other.wordSignalPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "bien no thanh j son di" + this.meaningCode;
    }
    
}
