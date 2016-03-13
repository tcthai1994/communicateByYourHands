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
import sample.session.AccountDetailSessionBeanRemote;
/**
 *
 * @author AnhND
 */
public class Validate {

    private static List<String> listError;
    private static Pattern pattern;
    private static Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String NUMBER_PATTERN = "[-+]?\\d*\\.?\\d+";
    private static final String DATE_PATTERN 
            = "/^((((19|[2-9]\\d)\\d{2})[\\/\\.-](0[13578]|1[02])[\\/\\.-](0[1-9]|[12]\\d|3[01])\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))"
            + "|(((19|[2-9]\\d)\\d{2})[\\/\\.-](0[13456789]|1[012])[\\/\\.-](0[1-9]|[12]\\d|30)\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))"
            + "|(((19|[2-9]\\d)\\d{2})[\\/\\.-](02)[\\/\\.-](0[1-9]|1\\d|2[0-8])\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]))"
            + "|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))[\\/\\.-](02)[\\/\\.-](29)\\s(0[0-9]|1[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]).([0-9][0-9][0-9])))$/g";

    public static List<String> validateRegister(String email, String fullname, String username, String password, String re_password, String phone) throws NamingException {
        listError = new ArrayList<String>();
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        if(!matcher.matches()){
            listError.add("Email invalid! (me@example.com)");
        }
        if (email.length() > 254 || email.length() < 10) {
            listError.add("Email must be 10 - 254 characters");
        } 
        if(email == null || email == ""){
            listError.add("Email can not be null");
        }
        if (fullname.length() > 50 || fullname.length() < 10) {
            listError.add("Full name must be 10 - 50 characters");
        }
        if (fullname == null || fullname == ""){
            listError.add("Full name can not be null");
        }
        if (username.length() > 20 || username.length() < 9){
            listError.add("Username must be 9 - 20 characters");
        }
        if (username == null || username == ""){
            listError.add("Username can not be null");
        }
        if (password.length() > 12 || password.length() < 6){
            listError.add("Password must be 6 - 12  characters");
        }
        if (password == null || password == ""){
            listError.add("Password can not be null");
        }
        if (phone.length() > 12 || phone.length() < 10){
            listError.add("Phone must be 10 - 12 characters");
        }
        if (!Pattern.compile(NUMBER_PATTERN).matcher(phone).matches()){
            listError.add("Phone must be numbers");
        }
        if(password != re_password){
            listError.add("Repeat password does not match password");
        }
        Context context = new InitialContext();
        Object obj = context.lookup("UAJNDI");
        AccountDetailSessionBeanRemote beanRemote = (AccountDetailSessionBeanRemote) obj;
        boolean checkUsername = beanRemote.checkUsernameExisted(username);
        boolean checkEmail = beanRemote.checkEmailExisted(email);
        if(checkUsername){
            listError.add("User name already exist");
        }
        if(checkEmail){
            listError.add("Email already exist");
        }
        return listError;
    }
    
    public static List<String> validateLibrary(String libraryName){
        listError = new ArrayList<String>();
        if(libraryName.length() > 50 || libraryName.length() < 3){
            listError.add("Library name must be 3 - 50 characters");
        }
        if(libraryName == null || libraryName == ""){
            listError.add("Library name can not be null");
        }
        return listError;
    }
    
    public static List<String> validateDictionary(String keyword, String description, String videoURL){
        listError = new ArrayList<String>();
        if(keyword.length() > 20){
            listError.add("Key word must be 1 - 20 characters");
        }
        if(keyword == null || keyword == ""){
            listError.add("Key word can not be null");
        }
        if(description.length() > 250 || description.length() < 5){
            listError.add("Description must be 5 - 250 characters");
        }
        if(videoURL.length() > 255){
            listError.add("Video URL must be 1 - 255 characters");
        }
        if(videoURL == null || videoURL == ""){
            listError.add("Video URL can not be null");
        }
        return listError;
    }
    
    public static List<String> validateLicense(String licenseName, String price, String description){
        double d_price = Double.parseDouble(price);
        listError = new ArrayList<String>();
        if(licenseName.length() > 20 || licenseName.length() < 6){
            listError.add("License name must be 6 - 20 characters");
        }
        if(licenseName == null || licenseName == ""){
            listError.add("License name can not be null");
        }
        if(d_price < 0 || d_price > 50){
            listError.add("Price must be 0 - 50 (unit: 1000VND)");
        }
        if(!Pattern.compile(NUMBER_PATTERN).matcher(price).matches()){
            listError.add("Price must be numbers");
        }
        if(description.length() > 250 || description.length() < 6){
            listError.add("Description must be 6 - 250 characters");
        }
        if(description == null || description == ""){
            listError.add("Description can not be null");
        }
        return listError;
    }
    
    public static List<String> validateUpdateUser(String email, String fullname, String phone, String expiredDate, String licenseType){
        listError = new ArrayList<String>();
        if(!Pattern.compile(EMAIL_PATTERN).matcher(email).matches()){
            listError.add("Email invalid! (me@example.com)");
        }
        if (email.length() > 254 || email.length() < 10) {
            listError.add("Email must be 10 - 254 characters");
        } 
        if(email == null || email == ""){
            listError.add("Email can not be null");
        }
        if (fullname.length() > 50 || fullname.length() < 10) {
            listError.add("Name must be less 10 - 50 characters");
        }
        if (fullname == null || fullname == ""){
            listError.add("Full name can not be null");
        }
        if (phone.length() > 12 || phone.length() < 10){
            listError.add("Phone must be 10 - 12 characters");
        }
        if (!Pattern.compile(NUMBER_PATTERN).matcher(phone).matches()){
            listError.add("Phone must be numbers");
        }
        if(!Pattern.compile(DATE_PATTERN).matcher(expiredDate).matches()){
            listError.add("Date  format must be yyyy[/.-]mm[/.-]dd hh:mm:ss");
        }
        if(licenseType == "basic" && expiredDate != null && expiredDate != ""){
            listError.add("Basic user can not have expiration date");
        }
        if(licenseType == "premium" && expiredDate == null && expiredDate == ""){
            listError.add("Premium user must have expiration date");
        }
        return listError;
    }
    
    public static List<String> validateUpdateLibrary(String libraryName){
        listError = new ArrayList<String>();
        if(libraryName.length() > 50 || libraryName.length() < 3){
            listError.add("Library name must be 3 - 50 characters");
        }
        if(libraryName == null || libraryName == ""){
            listError.add("Library name can not be null");
        }
        return listError;
    }
    
    public static List<String> validateUpdateDictionary(String keyword, String description, String videoURL){
        listError = new ArrayList<String>();
        if(keyword.length() > 20){
            listError.add("Key word must be 1 - 20 characters");
        }
        if(keyword == null || keyword == ""){
            listError.add("Key word can not be null");
        }
        if(description.length() > 250 || description.length() < 5){
            listError.add("Description must be 5 - 250 characters");
        }
        if(videoURL.length() > 255){
            listError.add("Video URL must be 1 - 255 characters");
        }
        if(videoURL == null || videoURL == ""){
            listError.add("Video URL can not be null");
        }
        return listError;
    }
    
    public static List<String> validateUpdateLicense(String licenseName, String price, String description){
        double d_price = Double.parseDouble(price);
        listError = new ArrayList<String>();
        if(licenseName.length() > 20 || licenseName.length() < 6){
            listError.add("License name must be 6 - 20 characters");
        }
        if(licenseName == null || licenseName == ""){
            listError.add("License name can not be null");
        }
        if(d_price < 0 || d_price > 50){
            listError.add("Price must be 0 - 50 (unit: 1000VND)");
        }
        if(!Pattern.compile(NUMBER_PATTERN).matcher(price).matches()){
            listError.add("Price must be numbers");
        }
        if(description.length() > 250 || description.length() < 6){
            listError.add("Description must be 6 - 250 characters");
        }
        if(description == null || description == ""){
            listError.add("Description can not be null");
        }
        return listError;
    }
}
