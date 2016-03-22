/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Remote;
import sample.entity.Dictionary;

/**
 *
 * @author AnhND
 */
@Remote
public interface SearchSessionBeanRemote {
    List<Dictionary> searchByText(String keyword);
    void persist(Object object);
}