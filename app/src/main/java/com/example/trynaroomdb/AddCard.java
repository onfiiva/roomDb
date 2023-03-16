package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class AddCard extends Activity {

    EditText cardName;

    Button addCard;

    float balance;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_card);

        cardName = findViewById(R.id.add_card_name);
        addCard = findViewById(R.id.insert_card);

        Intent okIntent = getIntent();
        String current_user = okIntent.getStringExtra("Login");
        Toast.makeText(getApplicationContext(), current_user, Toast.LENGTH_LONG).show();

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardName.getText().toString().equals("")){
                    AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                    balance = 0;
                    id = db.userDao().getId(current_user);
                    Card card = new Card(cardName.getText().toString(), balance, id);
                    db.cardDao().insertCard(card);

                    Intent intent = new Intent(getApplicationContext(), UserWindow.class);
                    intent.putExtra("Login", current_user);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Введите данные.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
