package com.example.trynaroomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface CardDao {
    @Insert
    void insertCard(Card cards);

    @Update
    void updateCard(Card cards);

    @Delete
    void deleteCard(Card cards);

    @Query("SELECT * FROM card WHERE name_card = :name_card")
    Card getCard(String name_card);

    @Query("SELECT * FROM card")
    List<Card> getAllCards();

    @Query("SELECT * FROM card INNER JOIN user ON user_id = id_user WHERE id_user = :id_user")
    List<Card> getCardByUser(int id_user);

    @Query("SELECT * FROM card INNER JOIN user ON user_id = id_user WHERE login_user = :login_user")
    List<Card> getCardByUserLogin(String login_user);

    @Query("SELECT id_card FROM card WHERE name_card = :name_card")
    int getId(String name_card);

    @Query("SELECT name_card FROM card WHERE id_card = :id_card")
    String getName(int id_card);
}
