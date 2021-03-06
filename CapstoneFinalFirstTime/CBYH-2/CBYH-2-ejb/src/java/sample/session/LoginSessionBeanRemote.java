/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Remote;
import fpt.myo.entityBean.AccountDetail;

/**
 *
 * @author AnhND
 */
@Remote
public interface LoginSessionBeanRemote {
    boolean checkLogin(String username, String password);
    boolean isAdmin(int detailId);
    List<AccountDetail> getAllAccountDetail();
    int getDetailIdByUsername(String username);
    boolean isActive(int detailId);
    void persist(Object object);
}
