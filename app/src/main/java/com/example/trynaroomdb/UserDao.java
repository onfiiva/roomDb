package com.example.trynaroomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT * FROM user WHERE login_user = :login_user")
    User getUser(String login_user);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();

    @Query("SELECT EXISTS(SELECT login_user FROM user WHERE login_user = :login_user)")
    boolean isExists(String login_user);

    @Query("SELECT id_user FROM user WHERE login_user = :login_user")
    int getId(String login_user);

    @Query("SELECT login_user FROM user WHERE id_user = :id_user")
    String getLogin(int id_user);
}
