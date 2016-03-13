/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import sample.entity.License;

/**
 *
 * @author AnhND
 */
@Stateless
public class LicenseSessionBean implements LicenseSessionBeanLocal , LicenseSessionBeanRemote{
    @PersistenceContext(unitName = "CBYH-2-ejbPU")
    private EntityManager em;
    
    public List<License> getAllLicense(){
        String jnql = "SELECT a FROM License a";
        Query query = em.createQuery(jnql);
        List<License> listLicense = query.getResultList();
        return listLicense;
    }
    
    public void addNewLicense(License license){
        em.persist(license);
    }
    
    public void updateLicense(int licenseId, License licUpdate){
        License lic = em.find(License.class, licenseId);
        lic.setLicenseName(licUpdate.getLicenseName());
        lic.setStatus(licUpdate.getStatus());
        em.merge(lic);
    }
    
    public boolean DeleteLicense(int licenseId){
        License license = em.find(License.class, licenseId);
        if(license != null ){
            em.remove(license);
            return true;
        }
        return false;
    }

    public void persist(Object object) {
        em.persist(object);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
