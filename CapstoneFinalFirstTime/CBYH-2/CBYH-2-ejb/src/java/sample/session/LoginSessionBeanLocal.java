/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Local;
import fpt.myo.entityBean.AccountDetail;

/**
 *
 * @author AnhND
 */
@Local
public interface LoginSessionBeanLocal {
    boolean checkLogin(String username, String password);
    boolean isAdmin(int detailId);
    List<AccountDetail> getAllAccountDetail();
    int getDetailIdByUsername(String username);
    void persist(Object object);
}
