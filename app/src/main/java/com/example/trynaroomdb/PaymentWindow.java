package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PaymentWindow extends Activity {
    EditText paymentName, paymentMoney, paymentType;
    Button paymentButton;

    String current_card_name;
    float current_card_balance;
    int current_card_user_id, id;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_window);

        paymentName = findViewById(R.id.payment_name);
        paymentMoney = findViewById(R.id.payment_money);
        paymentButton = findViewById(R.id.payment_button);
        paymentType = findViewById(R.id.payment_type);

        Intent okIntent = getIntent();
        current_card_name = okIntent.getStringExtra("name");
        current_card_balance = okIntent.getFloatExtra("balance", 0);
        current_card_user_id = okIntent.getIntExtra("id_user", 0);

        paymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!paymentName.getText().toString().equals("")
                        && !paymentMoney.getText().toString().equals("")
                && !paymentType.getText().toString().equals("")){
                    AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                    if (db.typeDao().getType(paymentType.getText().toString()) != null){
                        if (current_card_balance >= Float.valueOf(paymentMoney.getText().toString())){
                            id = db.cardDao().getId(current_card_name);
                            Payment payment = new Payment(paymentName.getText().toString(), Float.valueOf(paymentMoney.getText().toString()), id, Integer.valueOf(db.typeDao().getId(paymentType.getText().toString())));
                            db.paymentDao().insertPayment(payment);

                            current_card_balance -= Float.valueOf(paymentMoney.getText().toString());
                            Card card = db.cardDao().getCard(current_card_name);
                            card.setBalance(current_card_balance);
                            db.cardDao().updateCard(card);

                            Toast.makeText(getApplicationContext(), "Успешное проведение платежа.", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getApplicationContext(), CardActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("name", current_card_name);
                            intent.putExtra("balance", current_card_balance);
                            intent.putExtra("id_user", current_card_user_id);

                            getApplicationContext().startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), "Недостаточно средств.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Введите существующий тип.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "Введите данные.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
