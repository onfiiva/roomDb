package com.example.trynaroomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TypeDao {
    @Insert
    void insertType(Type type);

    @Update
    void updateType(Type type);

    @Delete
    void deleteType(Type type);

    @Query("SELECT * FROM type WHERE name_type = :name_type")
    Type getType(String name_type);

    @Query("SELECT * FROM type")
    List<Type> getAllTypes();

    @Query("SELECT id_type FROM type WHERE name_type = :name_type")
    int getId(String name_type);

    @Query("SELECT name_type FROM type WHERE id_type = :id_type")
    String getName(int id_type);
}
