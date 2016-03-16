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
public interface DictionarySessionBeanRemote {
    List<Dictionary> getAllDictionary();
    void addNewDictionary(Dictionary dic);
    void updateDictionary(int dictionaryId, Dictionary dicUpdate);
    boolean deleteDictionary(int dictionaryId);
}
