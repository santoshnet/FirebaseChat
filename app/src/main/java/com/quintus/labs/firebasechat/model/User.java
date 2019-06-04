package com.quintus.labs.firebasechat.model;

public class User {
    public String uId, name, email, phone, profileImage, type;

    public User() {

    }


    public User(String name, String email, String phone, String profileImage, String type) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.profileImage = profileImage;
        this.type = type;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


}
