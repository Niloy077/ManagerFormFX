package DataBase;

import ValidationalLogic.PersonValidator;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

/**
 * Created by Administrator on 9/17/2021.
 */
public class Person {
  private final UUID id;//UUID = universally unique identifier
  private String firstName,surname,emailAddress,address;
  private LocalDate dateOfBirth;
  private String mobileNumber,pathToProfilePhoto;

  public Person(String firstName, String surname,String mobileNumber,String address, Integer day, Month month,Integer year,String emailAddress,String pathToProfilePicture) throws Exception{
    this.id = UUID.randomUUID();
    this.setFirstName(firstName);
    this.setSurname(surname);
    this.setMobileNumber(mobileNumber);
    this.setAddress(address);
    this.setEmailAddress(emailAddress);
    this.setDateOfBirth(day,month,year);
    this.setEmailAddress(emailAddress);
    this.pathToProfilePhoto(pathToProfilePicture);
  }

  private void pathToProfilePhoto(String pathToProfilePicture) throws Exception{
    PersonValidator.validateProfilePhoto(pathToProfilePhoto);
    this.pathToProfilePhoto = pathToProfilePicture;
  }

  public String getAddress() {
    return address;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setAddress(String address) throws Exception{
    PersonValidator.validateAddress(address);
    this.address = address;
  }

  public void setMobileNumber(String mobileNumber) throws Exception{
    PersonValidator.validatePhoneNumber(mobileNumber);
    this.mobileNumber = mobileNumber;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) throws Exception{
    PersonValidator.validateEmailAddress(emailAddress);
    this.emailAddress = emailAddress;
  }

  public UUID getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getSurname() {
    return surname;
  }

  public void setFirstName(String firstName) throws Exception {
    PersonValidator.validateName(firstName);
    this.firstName = firstName;
  }

  public void setSurname(String surname) throws Exception {
    PersonValidator.validateName(surname);
    this.surname = surname;
  }


  //  public void setGender(String gender) throws Exception{
//    PersonValidator.validateGender(gender);
//    this.gender = gender;
//  }

  public void setDateOfBirth(Integer day, Month month, Integer year) throws Exception{
    LocalDate dateOfBirth = LocalDate.of(year,month,day);
    PersonValidator.validateDateOfBirth(dateOfBirth);
    this.dateOfBirth = dateOfBirth;
  }

  @Override
  public String toString() {
    return this.id.toString() + ": " + this.firstName;
  }
}
