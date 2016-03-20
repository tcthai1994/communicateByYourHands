/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Local;
import fpt.myo.entityBean.Account;
import fpt.myo.entityBean.AccountDetail;

/**
 *
 * @author AnhND
 */
@Local
public interface AccountDetailSessionBeanLocal {

    List<Account> getAllAccount();

    List<AccountDetail> getAllAccountDetail();

    void RegistertoAccount(Account Acc);

    void RegistertoAccountDetail(AccountDetail AccDetail);

    void UpdateAccount(int detailid, Account AccUpdate);

    void UpdateAccountDetail(int detailid, AccountDetail AccDetailUpdate);

    int getDetailId();

    Account findByUsername(String username);

}
