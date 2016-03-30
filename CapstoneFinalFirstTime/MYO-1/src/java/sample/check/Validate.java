/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.check;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import myo.fpt.sample.entity.controller.AccountDetailJpaController;

/**
 *
 * @author AnhND
 */
public class Validate {

    private static String listError;
    private static Pattern pattern;
    private static Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String NUMBER_PATTERN = "\\d{10,12}";
    private static final String DATE_PATTERN
            = "((((19|[2-9]\\d)\\d{2})[\\/\\.-](0[13578]|1[02])[\\/\\.-](0[1-9]|[12]\\d|3[01])\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))"
            + "|(((19|[2-9]\\d)\\d{2})[\\/\\.-](0[13456789]|1[012])[\\/\\.-](0[1-9]|[12]\\d|30)\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))"
            + "|(((19|[2-9]\\d)\\d{2})[\\/\\.-](02)[\\/\\.-](0[1-9]|1\\d|2[0-8])\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))"
            + "|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))[\\/\\.-](02)[\\/\\.-](29)\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]).([0-9][0-9][0-9])))";
    
    private static EntityManagerFactory getEntityManagerFactory() throws NamingException {
        return Persistence.createEntityManagerFactory("MYO-1PU");
    }

    private static AccountDetailJpaController getJpaController() {
        try {
            return new AccountDetailJpaController(getEntityManagerFactory());
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static String validateRegister(String email, String fullname, String username, String password, String re_password, String phone) throws NamingException {
        listError = "";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        System.out.println(phone);
        if (!matcher.matches()) {
            listError += "Email invalid! (me@example.com) \\n";
        }
        if (email.length() > 254 || email.length() < 10) {
            listError += "Email must be 10 - 254 characters \\n";
        }
        if (email == null || email.equals("")) {
            listError += "Email can not be null \\n";
        }
        if (fullname.length() > 50 || fullname.length() < 10) {
            listError += "Full name must be 10 - 50 characters \\n";
        }
        if (fullname == null || fullname.equals("")) {
            listError += "Full name can not be null \\n";
        }
        if (username.length() > 20 || username.length() < 6) {
            listError += "Username must be 6 - 20 characters \\n";
        }
        if (username == null || username.equals("")) {
            listError += "Username can not be null \\n";
        }
        if (password.length() > 12 || password.length() < 6) {
            listError += "Password must be 6 - 12  characters \\n";
        }
        if (password == null || password.equals("")) {
            listError += "Password can not be null \\n";
        }
        if (!phone.matches(NUMBER_PATTERN)) {
            listError += "Phone must be numbers \\n";
        }
        if (!password.equals(re_password)) {
            listError += "Repeat password does not match password \\n";
        }

        boolean checkUsername = getJpaController().checkUsernameExisted(username);
        boolean checkEmail = getJpaController().checkEmailExisted(email);
        if (checkUsername) {
            listError += "User name already exist \\n";
        }
        if (checkEmail) {
            listError += "Email already exist \\n";
        }
        return listError;
    }

    public static String validateLibrary(String libraryName) {
        listError = "";
        if (libraryName.length() > 50 || libraryName.length() < 3) {
            listError += "Library name must be 3 - 50 characters \\n";
        }
        if (libraryName == null || libraryName.equals("")) {
            listError += "Library name can not be null \\n";
        }
        return listError;
    }

    public static String validateDictionary(String keyword, String description, String videoURL) {
        listError = "";
        if (keyword.length() > 20) {
            listError += "Key word must be 1 - 20 characters \\n";
        }
        if (keyword == null || keyword.equals("")) {
            listError += "Key word can not be null \n";
        }
        if (description.length() > 250 || description.length() < 5) {
            listError += "Description must be 5 - 250 characters \\n";
        }
        if (videoURL.length() > 255) {
            listError += "Video URL must be 1 - 255 characters \\n";
        }
        if (videoURL == null || videoURL.equals("")) {
            listError += "Video URL can not be null \\n";
        }
        return listError;
    }

    public static String validateLicense(String licenseName, String price, String description) {
        double d_price = Double.parseDouble(price);
        listError = "";
        if (licenseName.length() > 20 || licenseName.length() < 5) {
            listError += "License name must be 5 - 20 characters \\n";
        }
        if (licenseName == null || licenseName.equals("")) {
            listError += "License name can not be null \\n";
        }
        if (d_price < 0 || d_price > 50) {
            listError += "Price must be 0 - 50 (unit: 1000VND) \\n";
        }
        if (!Pattern.compile(NUMBER_PATTERN).matcher(price).matches()) {
            listError += "Price must be numbers \\n";
        }
        if (description.length() > 250 || description.length() < 6) {
            listError += "Description must be 6 - 250 characters \\n";
        }
        if (description == null || description.equals("")) {
            listError += "Description can not be null \\n";
        }
        return listError;
    }

    public static String validateUpdateUser(String email, String fullname, String phone, String expiredDate, String licenseType) {
        listError = "";
        if (!Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
            listError += "Email invalid! (me@example.com) \\n";
        }
        if (email.length() > 254 || email.length() < 10) {
            listError += "Email must be 10 - 254 characters \\n";
        }
        if (email == null || email.equals("")) {
            listError += "Email can not be null \n";
        }
        if (fullname.length() > 50 || fullname.length() < 10) {
            listError += "Name must be less 10 - 50 characters \\n";
        }
        if (fullname == null || fullname.equals("")) {
            listError += "Full name can not be null \\n";
        }
        if (phone.length() > 12 || phone.length() < 10) {
            listError += "Phone must be 10 - 12 characters \\n";
        }
        if (!Pattern.compile(NUMBER_PATTERN).matcher(phone).matches()) {
            listError += "Phone must be numbers \\n";
        }
//        if(!Pattern.compile(DATE_PATTERN).matcher(expiredDate).matches()){
//            listError += "Date  format must be yyyy[/.-]mm[/.-]dd hh:mm:ss";
//        }
//        if(licenseType.equals("basic") && expiredDate != null && !expiredDate.equals("")){
//            listError += "Basic user can not have expiration date \\n";
//        }
//        if(licenseType.equals("premium") && expiredDate == null && expiredDate.equals("")){
//            listError += "Premium user must have expiration date \\n";
//        }
        return listError;
    }

    public static String validateUpdateLibrary(String libraryName) {
        listError = "";
        if (libraryName.length() > 50 || libraryName.length() < 3) {
            listError += "Library name must be 3 - 50 characters \\n";
        }
        if (libraryName == null || libraryName.equals("")) {
            listError += "Library name can not be null \\n";
        }
        return listError;
    }

    public static String validateUpdateDictionary(String keyword, String description, String videoURL) {
        listError = "";
        if (keyword.length() > 20) {
            listError += "Key word must be 1 - 20 characters \\n";
        }
        if (keyword == null || keyword.equals("")) {
            listError += "Key word can not be null \\n";
        }
        if (description.length() > 250 || description.length() < 5) {
            listError += "Description must be 5 - 250 characters \\n";
        }
        if (videoURL.length() > 255) {
            listError += "Video URL must be 1 - 255 characters \\n";
        }
        if (videoURL == null || videoURL.equals("")) {
            listError += "Video URL can not be null \\n";
        }
        return listError;
    }

    public static String validateUpdateLicense(String licenseName, String price, String description) {
        double d_price = Double.parseDouble(price);
        listError = "";
        if (licenseName.length() > 20 || licenseName.length() < 5) {
            listError += "License name must be 5 - 20 characters \\n";
        }
        if (licenseName == null || licenseName.equals("")) {
            listError += "License name can not be null \\n";
        }
        if (d_price < 0 || d_price > 50) {
            listError += "Price must be 0 - 50 (unit: 1000VND) \\n";
        }
//        if(!Pattern.compile("\\d+").matcher(price).matches()){
//            listError += "Price must be numbers \\n";
//        }
        if (description.length() > 250 || description.length() < 6) {
            listError += "Description must be 6 - 250 characters \\n";
        }
        if (description == null || description.equals("")) {
            listError += "Description can not be null \\n";
        }
        return listError;
    }

    public static String validateUserUpdate(String email, String fullname, String phone, String password, String re_password) {
        listError = "";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            listError += "Email invalid! (me@example.com) \\n";
        }
        if (email.length() > 254 || email.length() < 10) {
            listError += "Email must be 10 - 254 characters \\n";
        }
        if (email == null || email.equals("")) {
            listError += "Email can not be null \\n";
        }
        if (fullname.length() > 50 || fullname.length() < 10) {
            listError += "Full name must be 10 - 50 characters \\n";
        }
        if (fullname == null || fullname.equals("")) {
            listError += "Full name can not be null \\n";
        }
        if (!phone.matches(NUMBER_PATTERN)) {
            listError += "Phone must be numbers and have 10 - 12 numbers \\n";
        }
        if (password != null && re_password != null) {
            if (!password.equals("") && !re_password.equals("")) {
                if (password.length() > 12 || password.length() < 6) {
                    listError += "Password must be 6 - 12  characters \\n";

                }
            }
        }
        if (!password.equals(re_password)) {
            listError += "Repeat password does not match password \\n";
        }
        return listError;
    }
    
    
}
