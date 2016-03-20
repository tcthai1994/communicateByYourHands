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
 * @author AnhND
 */
@Entity
@Table(name = "account", catalog = "Myo01", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a"),
    @NamedQuery(name = "Account.findByCustId", query = "SELECT a FROM Account a WHERE a.custId = :custId"),
    @NamedQuery(name = "Account.findByUsername", query = "SELECT a FROM Account a WHERE a.username = :username"),
    @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password"),
    @NamedQuery(name = "Account.findByDetailId", query = "SELECT a FROM Account a WHERE a.detailId = :detailId")})
public class Account implements Serializable {
    @Column(name = "deviceId")
    private String deviceId;
    private static final long serialVersionUID = 1L;
    @GeneratedValue
    @Id
    @Basic(optional = false)
    @Column(name = "custId")
    private Integer custId;
    @Basic(optional = false)
    @Column(name = "username")
    private String username;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "detailId")
    private int detailId;


    public Account() {
    }

    public Account(Integer custId) {
        this.custId = custId;
    }

    public Account(Integer custId, String username, String password, int detailId) {
        this.custId = custId;
        this.username = username;
        this.password = password;
        this.detailId = detailId;
    }
    
    
    
    public Account(String username, String password, int detailId) {
        this.username = username;
        this.password = password;
        this.detailId = detailId;
    }
    
    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public Account(String username, String password, int detailId, String deviceId) {
        this.username = username;
        this.password = password;
        this.detailId = detailId;
        this.deviceId = deviceId;
    }
    
    public Account(String deviceId){
        this.deviceId = deviceId;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDetailId() {
        return detailId;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (custId != null ? custId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.custId == null && other.custId != null) || (this.custId != null && !this.custId.equals(other.custId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sample.entity.Account[ custId=" + custId + " ]";
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    
}
