/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Local;
import sample.entity.Dictionary;

/**
 *
 * @author AnhND
 */
@Local
public interface SearchSessionBeanLocal {
    List<Dictionary> searchByText(String keyword);
    void persist(Object object);
}
