package org.bihe.faranha.lowercaseimdb.data.objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.HashMap;

@Entity
public class User implements Serializable {

    String username;
    String password;
    String email;
    String gender;
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    long userID;

    public User(String username, String password, String email, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }
}
