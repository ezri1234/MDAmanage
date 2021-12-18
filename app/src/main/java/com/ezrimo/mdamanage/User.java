package com.ezrimo.mdamanage;

public class User {
    String fullName, userEmail;
    long isAdmin, isUser;

    public User(){}

    public User(String fullName, String userEmail, long isAdmin) {
        this.fullName = fullName;
        this.userEmail = userEmail;
        this.isAdmin = isAdmin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(long isAdmin) {
        this.isAdmin = isAdmin;
    }

}
