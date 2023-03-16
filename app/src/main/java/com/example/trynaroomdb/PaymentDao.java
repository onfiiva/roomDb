package com.example.trynaroomdb;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PaymentDao {
    @Insert
    void insertPayment(Payment payment);

    @Update
    void updatePayment(Payment payment);

    @Delete
    void deletePayment(Payment payment);

    @Query("SELECT * FROM payment WHERE name_payment = :name_payment")
    Payment getPayment(String name_payment);

    @Query("SELECT * FROM payment")
    List<Payment> getAllPayments();

    @Query("SELECT name_type FROM payment INNER JOIN type ON type_id = id_type WHERE name_payment = :name_payment")
    String getType(String name_payment);

    @Query("SELECT * FROM payment INNER JOIN card ON card_id = id_card WHERE id_card = :id_card")
    List<Payment> getPaymentsByCard(int id_card);

    @Query("SELECT * FROM payment INNER JOIN card ON card_id = id_card WHERE user_id = :user_id")
    List<Payment> getPaymentsByUser(int user_id);

    @Query("SELECT * FROM payment INNER JOIN type ON type_id = id_type WHERE name_type = :name_type")
    List<Payment> getPaymentsByType(String name_type);

    @Query("SELECT * FROM payment INNER JOIN type ON type_id = id_type INNER JOIN card ON card_id = id_card WHERE type_id = :type_id AND card_id = :card_id")
    List<Payment> getPaymentsByTypeAndCard(int type_id, int card_id);
}
