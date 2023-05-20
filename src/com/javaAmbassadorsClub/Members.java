package com.javaAmbassadorsClub;

public class Members {
    private int serialNumber;
    private String firstName;
    private String lastName;
    private String userName;
    private String mobile;
    private String email;
    private String date_created;

//Constructor to populate register table
    public Members(int serialNumber, String firstName, String lastName, String userName, String mobile,
                   String email, String date_created) {
        this.serialNumber = serialNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.mobile = mobile;
        this.email = email;
        this.date_created = date_created;
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(int serialNumber) {
        this.serialNumber = serialNumber;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }
}
