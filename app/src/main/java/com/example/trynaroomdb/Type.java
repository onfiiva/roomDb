package com.example.trynaroomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "type")
public class Type {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_type")
    private int id;
    @ColumnInfo(name = "name_type")
    private String name;

    public Type(String name) {
        this.name = name;
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
}
