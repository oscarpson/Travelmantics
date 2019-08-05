package com.oscar.Travelmantics.model;

/**
 * The type Admin model.
 */
public class AdminModel {
    /**
     * The Email.
     */
    String email;
    /**
     * The Phone number.
     */
    String phoneNumber;
    /**
     * The Display name.
     */
    String displayName;

    /**
     * Instantiates a new Admin model.
     */
    public AdminModel() {
    }

    /**
     * Instantiates a new Admin model.
     *
     * @param email       the email
     * @param phoneNumber the phone number
     * @param displayName the display name
     */
    public AdminModel(String email, String phoneNumber,String displayName) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.displayName= displayName;
    }

}
