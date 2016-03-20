/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.session;

import javax.ejb.Remote;
import fpt.myo.entityBean.Account;
import fpt.myo.entityBean.Device;

/**
 *
 * @author AnhND
 */
@Remote
public interface DeviceSessionBeanRemote {
    void addDevice(Device device);
    int getCustIdByUsername(String username);
    void addDeviceIdToAccount(int custId, Account accountUpdate);
    boolean checkLogin(String username, String password);
    boolean isActive(int detailId);
    int getDetailIdByUsername(String username);
    boolean isDeviceIdExist(String deviceId);
    void updateRegistrationId(String deviceId, String regId);
}
