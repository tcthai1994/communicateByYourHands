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
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Thai
 */
@Embeddable
public class NotificationPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "notiId", nullable = false)
    private int notiId;
    @Basic(optional = false)
    @Column(name = "notiDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date notiDate;
    @Basic(optional = false)
    @Column(name = "custId", nullable = false)
    private int custId;

    public NotificationPK() {
    }

    public NotificationPK(int notiId, Date notiDate, int custId) {
        this.notiId = notiId;
        this.notiDate = notiDate;
        this.custId = custId;
    }

    public int getNotiId() {
        return notiId;
    }

    public void setNotiId(int notiId) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) notiId;
        hash += (notiDate != null ? notiDate.hashCode() : 0);
        hash += (int) custId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NotificationPK)) {
            return false;
        }
        NotificationPK other = (NotificationPK) object;
        if (this.notiId != other.notiId) {
            return false;
        }
        if ((this.notiDate == null && other.notiDate != null) || (this.notiDate != null && !this.notiDate.equals(other.notiDate))) {
            return false;
        }
        if (this.custId != other.custId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "fpt.myo.entityBean.NotificationPK[ notiId=" + notiId + ", notiDate=" + notiDate + ", custId=" + custId + " ]";
    }
    
}
