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
@Table(name = "NotiList", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NotiList.findAll", query = "SELECT n FROM NotiList n"),
    @NamedQuery(name = "NotiList.findByNotiId", query = "SELECT n FROM NotiList n WHERE n.notiId = :notiId"),
    @NamedQuery(name = "NotiList.findByNotiContent", query = "SELECT n FROM NotiList n WHERE n.notiContent = :notiContent")})
public class NotiList implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "notiId", nullable = false)
    private Integer notiId;
    @Basic(optional = false)
    @Column(name = "notiContent", nullable = false, length = 250)
    private String notiContent;

    public NotiList() {
    }

    public NotiList(Integer notiId) {
        this.notiId = notiId;
    }

    public NotiList(Integer notiId, String notiContent) {
        this.notiId = notiId;
        this.notiContent = notiContent;
    }

    public Integer getNotiId() {
        return notiId;
    }

    public void setNotiId(Integer notiId) {
        this.notiId = notiId;
    }

    public String getNotiContent() {
        return notiContent;
    }

    public void setNotiContent(String notiContent) {
        this.notiContent = notiContent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (notiId != null ? notiId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotiList)) {
            return false;
        }
        NotiList other = (NotiList) object;
        if ((this.notiId == null && other.notiId != null) || (this.notiId != null && !this.notiId.equals(other.notiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.myo.entityBean.NotiList[ notiId=" + notiId + " ]";
    }
    
}
