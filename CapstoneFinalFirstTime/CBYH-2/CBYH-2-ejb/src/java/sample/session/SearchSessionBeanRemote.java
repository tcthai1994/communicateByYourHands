/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Remote;
import fpt.myo.entityBean.Dictionary;

/**
 *
 * @author AnhND
 */
@Remote
public interface SearchSessionBeanRemote {
    List<Dictionary> searchByText(String keyword);
    List<Dictionary> getAllDictionary();
    void persist(Object object);
}
