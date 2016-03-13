/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Remote;
import sample.entity.Library;

/**
 *
 * @author AnhND
 */
@Remote
public interface LibrarySessionBeanRemote {
    List<Library> getAllLibrary();
    void addNewLibrary(Library lib);
    void updateLibrary(int libraryId, Library libUpdate);
    boolean DeleteLibrary(int libraryId);
    void persist(Object object);
}
