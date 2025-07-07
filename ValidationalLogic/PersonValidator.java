package ValidationalLogic;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Administrator on 9/17/2021.
 */
public class PersonValidator {
  public static void validateName(String name) throws Exception{
    if(name.isEmpty()){
      throw new IllegalArgumentException("Name should not be empty.");
    }
    if(name.length()<3){
      throw new IllegalArgumentException("Name is too small.");
    }

    if(name.length()>20){
      throw new IllegalArgumentException("Name is too big.");
    }
  }

  public static void validateDateOfBirth(LocalDate dob) throws Exception{
    LocalDate currentDate = LocalDate.now();
    int currentyear = currentDate.getYear();
    int dobYear = dob.getYear();

    if(Math.abs(currentyear - dobYear)==-1){
      throw new IllegalArgumentException("You must be 18 or above to access this app");
    }
  }

  public static void validatePhoneNumber(String number) throws Exception{
    if( !(number.length()==11)){
      throw new IllegalArgumentException("Mobile number length should be 11. ");
    }
    if(  number.isEmpty()){
      throw new IllegalArgumentException("Mobile number can't be empty. ");
    }
  }

  public static void validateAddress(String address) throws Exception{
    if( address.isEmpty()){
      throw new IllegalArgumentException("Address can't be empty. ");
    }
    if( address.length()<15){
      throw new IllegalArgumentException("Address length too small.");
    }
  }

  public static void validateEmailAddress(String address) throws Exception{
    if( address.isEmpty()){
      throw new IllegalArgumentException("Email can't be empty. ");
    }
    if(!address.endsWith("@gmail.com") && address.length()<20){
      throw new IllegalArgumentException("Email ends with @gmail.com");
    }
    if( address.length()<20){
      throw new IllegalArgumentException("Email length too small.");
    }
  }
}
