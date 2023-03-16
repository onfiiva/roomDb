package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

public class AdminWindow extends Activity {

    TextView allUsers, allCards, allPayments, allTypes;
    Button adminQuit;
    SharedPreferences userLogs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);
        allUsers = findViewById(R.id.text);
        allCards = findViewById(R.id.text2);
        allPayments = findViewById(R.id.text3);
        allTypes = findViewById(R.id.text4);
        adminQuit = findViewById(R.id.admin_quit);

        fetchUsers();
        fetchCards();
        fetchPayments();
        fetchTypes();

        userLogs = this.getSharedPreferences(
                "com.example.trynaroomdb", Context.MODE_PRIVATE);

        adminQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogs.edit().putString("Login", "").apply();
                userLogs.edit().putString("Password", "").apply();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
    }
    public void fetchUsers(){
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<User> users = db.userDao().getAllUsers();
        allUsers.setText("");
        for(int i = 0; i < users.size(); i++){
            allUsers.append("Id:" + users.get(i).getId() + "; Login: " + users.get(i).getLogin() + "; Password: " + users.get(i).getPassword()+"\n");
        }
    }
    public void fetchCards(){
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<Card> cards = db.cardDao().getAllCards();
        allCards.setText("");
        for(int i = 0; i < cards.size(); i++){
            allCards.append("Id:" + cards.get(i).getId() + "; Name: " + cards.get(i).getName() + "; balance: " + cards.get(i).getBalance()+ "; User: " + cards.get(i).getUser_id()+"\n");
        }
    }
    public void fetchPayments(){
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<Payment> payments = db.paymentDao().getAllPayments();
        allPayments.setText("");
        for(int i = 0; i < payments.size(); i++){
            allPayments.append("Id:" + payments.get(i).getId() + "; Name: " + payments.get(i).getName() + "; cost: " + payments.get(i).getCost()+ "; Card: " + payments.get(i).getCard_id()+"\n");
        }
    }
    public void fetchTypes(){
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        List<Type> types = db.typeDao().getAllTypes();
        allTypes.setText("");
        for(int i = 0; i < types.size(); i++){
            allTypes.append("Id:" + types.get(i).getId() + "; Name: " + types.get(i).getName()+"\n");
        }
    }
}
