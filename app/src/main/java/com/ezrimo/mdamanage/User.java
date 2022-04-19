package com.ezrimo.mdamanage;

import java.util.Map;

public class User {
    String fullName, email;
    long isAdmin;

    public User(){}


    public User(String fullName, String userEmail, long isAdmin) {
        this.fullName = fullName;
        this.email = userEmail;
        this.isAdmin = isAdmin;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserEmail(String userEmail) {
        this.email = userEmail;
    }

    public long getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(long isAdmin) {
        this.isAdmin = isAdmin;
    }

}
