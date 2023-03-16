package com.example.trynaroomdb;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "payment", foreignKeys = {@ForeignKey(entity = Card.class,
        parentColumns = "id_card",
        childColumns = "card_id",
        onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Type.class,
                parentColumns = "id_type",
                childColumns = "type_id",
                onDelete = ForeignKey.CASCADE)})
public class Payment {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_payment")
    private int id;
    @ColumnInfo(name = "name_payment")
    private String name;
    @ColumnInfo(name = "cost_payment")
    private float cost;
    @ColumnInfo(name = "card_id")
    private int card_id;
    @ColumnInfo(name = "type_id")
    private int type_id;

    public Payment(String name, float cost, int card_id, int type_id) {
        this.name = name;
        this.cost = cost;
        this.card_id = card_id;
        this.type_id = type_id;
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

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }
}
