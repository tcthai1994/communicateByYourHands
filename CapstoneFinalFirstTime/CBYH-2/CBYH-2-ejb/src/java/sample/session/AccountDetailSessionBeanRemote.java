/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.List;
import javax.ejb.Remote;
import sample.dto.AccountManage;
import fpt.myo.entityBean.Account;
import fpt.myo.entityBean.AccountDetail;

/**
 *
 * @author AnhND
 */
@Remote
public interface AccountDetailSessionBeanRemote {

    List<Account> getAllAccount();

    List<AccountDetail> getAllAccountDetail();

    void RegistertoAccount(Account Acc);

    void RegistertoAccountDetail(AccountDetail AccDetail);

    void UpdateAccount(int custId, Account AccUpdate);

    void UpdateAccountDetail(int detailid, AccountDetail AccDetailUpdate);

    int getDetailId();

    Account findByUsername(String username);
    int getCustIdByUsername(String username);
    public AccountDetail getACdetailByDetailId (int detailId);
    boolean checkUsernameExisted(String username);
    boolean checkEmailExisted(String email);
    void updateExpiredDate(int detailId, AccountDetail accDetailUpdate);
    int findDetailIdByUsername(String username);
    void UserUpdateAccountDetail(int detailid, AccountDetail AccDetailUpdate);
    AccountManage getAccMng(String username);
}
