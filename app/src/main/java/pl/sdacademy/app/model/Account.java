package pl.sdacademy.app.model;

import android.text.TextUtils;

import java.io.Serializable;

public class Account implements Serializable {

    private String firstName;

    private String lastName;

    private String birthDate;

    private Gender gender;

    private String street;

    private String streetNumber;

    private String postalCode;

    private String city;

    private String country;

    private String email;

    private String phoneNumber;

    private String password;

    private boolean newsletter;

    public boolean isValid() {
        return !TextUtils.isEmpty(firstName)
                && !TextUtils.isEmpty(lastName)
                && !TextUtils.isEmpty(birthDate)
                && !TextUtils.isEmpty(street)
                && !TextUtils.isEmpty(streetNumber)
                && !TextUtils.isEmpty(postalCode)
                && !TextUtils.isEmpty(city)
                && !TextUtils.isEmpty(country)
                && !TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(phoneNumber)
                && !TextUtils.isEmpty(password);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postaCode) {
        this.postalCode = postaCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isNewsletter() {
        return newsletter;
    }

    public void setNewsletter(boolean newsletter) {
        this.newsletter = newsletter;
    }
}
