package com.example.trynaroomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "card", foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "id_user",
        childColumns = "user_id",
        onDelete = ForeignKey.CASCADE)
})
public class Card {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_card")
    private int id;
    @ColumnInfo(name = "name_card")
    private String name;
    @ColumnInfo(name = "balance_card")
    private float balance;
    @ColumnInfo(name = "user_id")
    private int user_id;

    public Card(String name, float balance, int user_id) {
        this.name = name;
        this.balance = balance;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
