/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpt.myo.entityBean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Thai
 */
@Entity
@Table(name = "notification", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notification.findAll", query = "SELECT n FROM Notification n"),
    @NamedQuery(name = "Notification.findByNotiId", query = "SELECT n FROM Notification n WHERE n.notificationPK.notiId = :notiId"),
    @NamedQuery(name = "Notification.findByNotiDate", query = "SELECT n FROM Notification n WHERE n.notificationPK.notiDate = :notiDate"),
    @NamedQuery(name = "Notification.findByCustId", query = "SELECT n FROM Notification n WHERE n.notificationPK.custId = :custId")})
public class Notification implements Serializable {
    @Id
    @Basic(optional = false)
    @Column(name = "notiId")
    private Integer notiId;
    @Basic(optional = false)
    @Column(name = "notiDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date notiDate;
    @Basic(optional = false)
    @Column(name = "custId")
    private int custId;
    @Basic(optional = false)
    @Column(name = "isSent")
    private boolean isSent;
    @Basic(optional = false)
    @Column(name = "notiContent")
    private String notiContent;
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NotificationPK notificationPK;

    public Notification() {
    }

    public Notification(NotificationPK notificationPK) {
        this.notificationPK = notificationPK;
    }

    public Notification(int notiId, Date notiDate, int custId) {
        this.notificationPK = new NotificationPK(notiId, notiDate, custId);
    }

    public NotificationPK getNotificationPK() {
        return notificationPK;
    }

    public void setNotificationPK(NotificationPK notificationPK) {
        this.notificationPK = notificationPK;
    }

//    @Override
//    public int hashCode() {
//        int hash = 0;
//        hash += (notificationPK != null ? notificationPK.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object object) {
//        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Notification)) {
//            return false;
//        }
//        Notification other = (Notification) object;
//        if ((this.notificationPK == null && other.notificationPK != null) || (this.notificationPK != null && !this.notificationPK.equals(other.notificationPK))) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "fpt.myo.entityBean.Notification[ notificationPK=" + notificationPK + " ]";
//    }

    public Notification(Integer notiId) {
        this.notiId = notiId;
    }

    public Notification(Integer notiId, Date notiDate, int custId, boolean isSent, String notiContent) {
        this.notiId = notiId;
        this.notiDate = notiDate;
        this.custId = custId;
        this.isSent = isSent;
        this.notiContent = notiContent;
    }

    public Integer getNotiId() {
        return notiId;
    }

    public void setNotiId(Integer notiId) {
        this.notiId = notiId;
    }

    public Date getNotiDate() {
        return notiDate;
    }

    public void setNotiDate(Date notiDate) {
        this.notiDate = notiDate;
    }

    public int getCustId() {
        return custId;
    }

    public void setCustId(int custId) {
        this.custId = custId;
    }

    public boolean getIsSent() {
        return isSent;
    }

    public void setIsSent(boolean isSent) {
        this.isSent = isSent;
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
        if (!(object instanceof Notification)) {
            return false;
        }
        Notification other = (Notification) object;
        if ((this.notiId == null && other.notiId != null) || (this.notiId != null && !this.notiId.equals(other.notiId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.myo.entityBean.Notification[ notiId=" + notiId + " ]";
    }
    
}
