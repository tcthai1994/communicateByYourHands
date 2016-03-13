/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import java.util.Date;
import javax.ejb.Remote;
import sample.entity.AccountDetail;
import sample.entity.Recipt;

/**
 *
 * @author AnhND
 */
@Remote
public interface PaymentSessionBeanRemote {
    void addRecipt(Recipt recipt);
    int getLicenseId();
    double getPrice();
    void persist(Object object);
    int findDetailIdByCustId(int custId);
    void updateAfterPayment(int detailId, AccountDetail accDetailUpdate);
    Date findExpiredDateByDetailId(int detailId);
}
