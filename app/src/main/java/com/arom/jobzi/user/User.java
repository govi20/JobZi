package com.arom.jobzi.user;

import android.support.annotation.Nullable;

import com.arom.jobzi.account.AccountType;
import com.arom.jobzi.profile.UserProfile;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 4L;
    
    private String id;
    
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    private AccountType accountType;
    
    private UserProfile userProfile;
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getEmail() {
        return email;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    // Does not parse properly with this.
    @Exclude
    public UserProfile getUserProfile() {
        return userProfile;
    }

    @Exclude
    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }
    
    /**
     * Copies non-object data from <code>user</code> into <code>this</code>.
     *
     * @param user The <code>User</code> object to copy data from.
     */
    public void copyFrom(User user) {
        setUsername(user.getUsername());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setEmail(user.getEmail());
        setAccountType(user.getAccountType());
    }
    
    @Override
    public boolean equals(@Nullable Object obj) {

        if(obj instanceof User) {

            User user = (User) obj;

            return this.getId().equals(user.getId());

        }

        return false;

    }
}

