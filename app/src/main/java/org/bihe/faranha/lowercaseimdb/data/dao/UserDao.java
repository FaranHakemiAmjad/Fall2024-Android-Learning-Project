package org.bihe.faranha.lowercaseimdb.data.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.bihe.faranha.lowercaseimdb.data.objects.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insert(User user);

    @Query("SELECT email FROM User WHERE email = :email")
    String searchDuplicate(String email);

    @Query("SELECT * FROM User WHERE id = :id")
    User findUser(long id);

    @Query("SELECT * FROM User WHERE email = :email")
    User findUser(String email);

}
