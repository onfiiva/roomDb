package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardActivity extends Activity {

    RecyclerView recyclerView;

    TextView nameCard, balanceCard;
    ImageButton addMoney, transferMoney, exitFromCard, graphicButtonCard;
    Button deleteCard;
    String current_user;
    int idUser, idCard;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_info_window);
        nameCard = findViewById(R.id.name_card);
        balanceCard = findViewById(R.id.balance_card);
        addMoney = findViewById(R.id.add_money_button);
        transferMoney = findViewById(R.id.transfer_button);
        exitFromCard = findViewById(R.id.exit_from_card);
        deleteCard = findViewById(R.id.delete_card);
        graphicButtonCard = findViewById(R.id.graphic_button_card);

        Intent okIntent = getIntent();
        String current_card_name = okIntent.getStringExtra("name");
        float current_card_balance = okIntent.getFloatExtra("balance", 0);
        int current_user_card_id = okIntent.getIntExtra("id_user", 0);

        recyclerView = findViewById(R.id.recycler_payment);
        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        idCard = db.cardDao().getId(current_card_name);
        List<Payment> payments = db.paymentDao().getPaymentsByCard(idCard);
        PaymentAdapter paymentAdapter = new PaymentAdapter(getApplicationContext(), payments);
        recyclerView.setAdapter(paymentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nameCard.setText(current_card_name);
        balanceCard.setText(String.valueOf(current_card_balance));

        addMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMoney.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", current_card_name);
                intent.putExtra("balance", current_card_balance);
                intent.putExtra("id_user", current_user_card_id);
                getApplicationContext().startActivity(intent);
            }
        });

        transferMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentWindow.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", current_card_name);
                intent.putExtra("balance", current_card_balance);
                intent.putExtra("id_user", current_user_card_id);
                getApplicationContext().startActivity(intent);
            }
        });

        exitFromCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                current_user = db.userDao().getLogin(current_user_card_id);
                Intent intent = new Intent(getApplicationContext(), UserWindow.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Login", current_user);

                getApplicationContext().startActivity(intent);
            }
        });

        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                Card card = db.cardDao().getCard(current_card_name);
                db.cardDao().deleteCard(card);

                Toast.makeText(getApplicationContext(), "Успешное удаление карты.", Toast.LENGTH_SHORT).show();

                current_user = db.userDao().getLogin(current_user_card_id);
                Intent intent = new Intent(getApplicationContext(), UserWindow.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Login", current_user);

                getApplicationContext().startActivity(intent);
            }
        });
        graphicButtonCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                current_user = db.userDao().getLogin(current_user_card_id);
                Intent intent = new Intent(getApplicationContext(), CardDiagram.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Login", current_user);
                intent.putExtra("name", current_card_name);
                intent.putExtra("balance", current_card_balance);
                intent.putExtra("id_user", current_user_card_id);

                getApplicationContext().startActivity(intent);
            }
        });
    }
}
