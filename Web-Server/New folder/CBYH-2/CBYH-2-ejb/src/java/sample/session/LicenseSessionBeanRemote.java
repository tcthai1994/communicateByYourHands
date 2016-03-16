/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Remote;
import sample.entity.License;

/**
 *
 * @author AnhND
 */
@Remote
public interface LicenseSessionBeanRemote {

    List<License> getAllLicense();

    void addNewLicense(License license);

    void updateLicense(int licenseId, License licUpdate);

    boolean DeleteLicense(int licenseId);

    void persist(Object object);
}
