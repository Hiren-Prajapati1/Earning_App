package com.example.earning_app.Models;


public class UserModel {

    private String name , mobileNumber , email , profile , referCode;
    private int coins , spins;

    public UserModel(String name, String mobileNumber, String email, String profile, String referCode, int coins, int spins) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.profile = profile;
        this.referCode = referCode;
        this.coins = coins;
        this.spins = spins;
    }

    public UserModel() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getReferCode() {
        return referCode;
    }

    public void setReferCode(String referCode) {
        this.referCode = referCode;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getSpins() {
        return spins;
    }

    public void setSpins(int spins) {
        this.spins = spins;
    }
}
