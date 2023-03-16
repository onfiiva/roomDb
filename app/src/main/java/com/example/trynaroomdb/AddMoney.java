package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AddMoney extends Activity {

    EditText addMoney;
    Button moneyButton;
    float current_card_balance;
    String current_card_name;
    int current_user_card_id, current_card_id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money);

        addMoney = findViewById(R.id.add_money);
        moneyButton = findViewById(R.id.money_button);

        Intent okIntent = getIntent();
        current_card_name = okIntent.getStringExtra("name");
        current_card_balance = okIntent.getFloatExtra("balance", 0);
        current_user_card_id = okIntent.getIntExtra("id_user", 0);


        moneyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addMoney.getText().toString().equals("")){
                    AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                    current_card_balance += Float.valueOf(addMoney.getText().toString());
                    Card card = db.cardDao().getCard(current_card_name);
                    card.setBalance(current_card_balance);
                    db.cardDao().updateCard(card);
                    Toast.makeText(getApplicationContext(), "Успешное пополнение баланса.", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("name", current_card_name);
                    intent.putExtra("balance", current_card_balance);
                    intent.putExtra("id_user", current_user_card_id);

                    getApplicationContext().startActivity(intent);
                }
            }
        });
    }
}
