package myo.fpt.sample.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-01T21:35:44")
@StaticMetamodel(Notification.class)
public class Notification_ { 

    public static volatile SingularAttribute<Notification, Integer> custId;
    public static volatile SingularAttribute<Notification, Boolean> isSent;
    public static volatile SingularAttribute<Notification, Integer> notiId;
    public static volatile SingularAttribute<Notification, Date> notiDate;
    public static volatile SingularAttribute<Notification, String> deviceId;
    public static volatile SingularAttribute<Notification, String> notiContent;

}