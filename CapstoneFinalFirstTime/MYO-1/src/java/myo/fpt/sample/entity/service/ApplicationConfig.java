/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myo.fpt.sample.entity.service;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author nguyen
 */
@javax.ws.rs.ApplicationPath("api")
public class ApplicationConfig  extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<Class<?>>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(myo.fpt.sample.entity.service.AccountRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.CustomContentRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.CustomSignalRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.DataContentRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.LeftSignalRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.MeaningLeftRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.MeaningRigntRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.NotiListRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.ReciptRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.RightSignalRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.SysdiagramsRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.TesttableRESTFacade.class);
        resources.add(myo.fpt.sample.entity.service.TrainService.class);
        resources.add(myo.fpt.sample.entity.service.TranslateService.class);
        resources.add(myo.fpt.sample.entity.service.WordSignalRESTFacade.class);
    }
}