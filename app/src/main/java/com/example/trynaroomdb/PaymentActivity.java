package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class PaymentActivity extends Activity {

    TextView namePayment, costPayment, cardNamePayment, cardTypePayment;
    ImageButton exitFromPayment;

    float current_card_balance;
    int current_user_card_id;
    String current_card_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_info_window);

        namePayment = findViewById(R.id.name_payment);
        costPayment = findViewById(R.id.cost_payment);
        cardNamePayment = findViewById(R.id.card_name_payment);
        cardTypePayment = findViewById(R.id.card_type_payment);
        exitFromPayment = findViewById(R.id.exit_from_payment);

        Intent okIntent = getIntent();
        String current_payment_name = okIntent.getStringExtra("name");
        float current_payment_cost = okIntent.getFloatExtra("cost", 0);
        int current_card_payment_id = okIntent.getIntExtra("id_card", 0);

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        current_card_name = db.cardDao().getName(current_card_payment_id);

        namePayment.setText(current_payment_name);
        costPayment.setText(String.valueOf(current_payment_cost));
        cardNamePayment.setText(current_card_name);
        cardTypePayment.setText(db.paymentDao().getType(current_payment_name));

        exitFromPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                current_card_balance = db.cardDao().getCard(current_card_name).getBalance();
                current_user_card_id = db.cardDao().getCard(current_card_name).getUser_id();
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", current_card_name);
                intent.putExtra("balance", current_card_balance);
                intent.putExtra("id_user", current_user_card_id);

                getApplicationContext().startActivity(intent);
            }
        });
    }
}
