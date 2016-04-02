package myo.fpt.sample.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2016-04-01T21:35:44")
@StaticMetamodel(AccountDetail.class)
public class AccountDetail_ { 

    public static volatile SingularAttribute<AccountDetail, String> phone;
    public static volatile SingularAttribute<AccountDetail, Date> expiredDate;
    public static volatile SingularAttribute<AccountDetail, String> email;
    public static volatile SingularAttribute<AccountDetail, Boolean> status;
    public static volatile SingularAttribute<AccountDetail, Boolean> isStaff;
    public static volatile SingularAttribute<AccountDetail, String> fullname;
    public static volatile SingularAttribute<AccountDetail, String> licenseType;
    public static volatile SingularAttribute<AccountDetail, Integer> detailId;

}