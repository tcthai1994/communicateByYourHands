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
import javax.persistence.GeneratedValue;
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
@Table(name = "dictionary")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dictionary.findAll", query = "SELECT d FROM Dictionary d"),
    @NamedQuery(name = "Dictionary.findByInstructionId", query = "SELECT d FROM Dictionary d WHERE d.instructionId = :instructionId"),
    @NamedQuery(name = "Dictionary.findByKeyword", query = "SELECT d FROM Dictionary d WHERE d.keyword = :keyword"),
    @NamedQuery(name = "Dictionary.findByDescription", query = "SELECT d FROM Dictionary d WHERE d.description = :description"),
    @NamedQuery(name = "Dictionary.findByVideoURL", query = "SELECT d FROM Dictionary d WHERE d.videoURL = :videoURL"),
    @NamedQuery(name = "Dictionary.findByStatus", query = "SELECT d FROM Dictionary d WHERE d.status = :status")})
public class Dictionary implements Serializable {
    private static final long serialVersionUID = 1L;
    @GeneratedValue
    @Id
    @Basic(optional = false)
    @Column(name = "instructionId")
    private Integer instructionId;
    @Basic(optional = false)
    @Column(name = "keyword")
    private String keyword;
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @Column(name = "videoURL")
    private String videoURL;
    @Basic(optional = false)
    @Column(name = "status")
    private boolean status;

    public Dictionary() {
    }

    public Dictionary(Integer instructionId) {
        this.instructionId = instructionId;
    }

    public Dictionary(Integer instructionId, String keyword, String videoURL, boolean status) {
        this.instructionId = instructionId;
        this.keyword = keyword;
        this.videoURL = videoURL;
        this.status = status;
    }
    
    public Dictionary(String keyword, String description, String videoURL, boolean status) {
        this.keyword = keyword;
        this.description = description;
        this.videoURL = videoURL;
        this.status = status;
    }

    public Integer getInstructionId() {
        return instructionId;
    }

    public void setInstructionId(Integer instructionId) {
        this.instructionId = instructionId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
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
        hash += (instructionId != null ? instructionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dictionary)) {
            return false;
        }
        Dictionary other = (Dictionary) object;
        if ((this.instructionId == null && other.instructionId != null) || (this.instructionId != null && !this.instructionId.equals(other.instructionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "myo.fpt.sample.entity.Dictionary[ instructionId=" + instructionId + " ]";
    }
    
}
