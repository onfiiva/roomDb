package com.example.trynaroomdb;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;

public class AccountActivity extends Activity {

TextView accountName, accountBalance;
ImageButton backToMain, exitFromAccount;
SharedPreferences userLogs;
int idUser;
float balance;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_info);

        accountName = findViewById(R.id.account_name);
        accountBalance = findViewById(R.id.account_balance);
        backToMain = findViewById(R.id.back_to_main);
        exitFromAccount = findViewById(R.id.exit_from_account);

        Intent okIntent = getIntent();
        String selected_user = okIntent.getStringExtra("Login");

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        idUser = db.userDao().getId(selected_user);
        List<Card> cards = db.cardDao().getCardByUser(idUser);
        balance = 0;
        for(int i = 0; i < cards.size(); i++){
            balance += cards.get(i).getBalance();
        }
        accountBalance.setText(Float.toString(balance));

        accountName.setText(selected_user);

        userLogs = this.getSharedPreferences(
                "com.example.trynaroomdb", Context.MODE_PRIVATE);

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UserWindow.class);
                intent.putExtra("Login", selected_user);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });
        exitFromAccount.setOnClickListener(new View.OnClickListener() {
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
}
