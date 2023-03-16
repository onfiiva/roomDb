package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;
import java.util.Random;

public class CardDiagram extends Activity {

    ImageButton exitFromCardDiagram;
    String current_user;
    PieChart pieChart;
    float costTransfer, costCashing, costMarket, costFood, costElectronics;
    View firstView, secondView, thirdView, fourthView, fifthView;
    TextView transferCard, cashingCard, marketCard, foodCard, electronicsCard;

    int idCard, idType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diagram_card);

        exitFromCardDiagram = findViewById(R.id.exit_from_card_diagram);
        pieChart = findViewById(R.id.piechart);
        firstView = findViewById(R.id.view1_card);
        secondView = findViewById(R.id.view2_card);
        thirdView = findViewById(R.id.view3_card);
        fourthView = findViewById(R.id.view4_card);
        fifthView = findViewById(R.id.view5_card);
        transferCard = findViewById(R.id.transfer_card);
        cashingCard = findViewById(R.id.cashing_card);
        marketCard = findViewById(R.id.market_card);
        foodCard = findViewById(R.id.food_card);
        electronicsCard = findViewById(R.id.electronics_card);

        Intent okIntent = getIntent();
        current_user = okIntent.getStringExtra("Login");
        String current_card_name = okIntent.getStringExtra("name");
        float current_card_balance = okIntent.getFloatExtra("balance", 0);
        int current_user_card_id = okIntent.getIntExtra("id_user", 0);

        AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
        idCard = db.cardDao().getId(current_card_name);

        List<Type> types = db.typeDao().getAllTypes();
        for (int i = 1; i <= types.size(); i++){
            idType = i;
            if (db.paymentDao().getPaymentsByTypeAndCard(idType, idCard) != null){
                List<Payment> costList = db.paymentDao().getPaymentsByTypeAndCard(idType, idCard);
                for(int j = 0; j < costList.size(); j++){
                    if (db.typeDao().getName(idType).equals("Transfer")){
                        costTransfer += costList.get(j).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Cashing out")){
                        costCashing += costList.get(j).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Market")){
                        costMarket += costList.get(j).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Food")){
                        costFood += costList.get(j).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Electronics")){
                        costElectronics += costList.get(j).getCost();
                    }
                }

                Random random = new Random();

                float r = random.nextFloat();
                float g = random.nextFloat() / 2f;
                float b = random.nextFloat() / 2f;



                if (db.typeDao().getName(idType).equals("Transfer")){
                    firstView.setBackgroundColor(Color.rgb(r, g, b));
                    transferCard.append(String.valueOf(costTransfer));

                    pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), costTransfer, Color.rgb(r, g, b)));
                }

                if (db.typeDao().getName(idType).equals("Cashing out")){
                    secondView.setBackgroundColor(Color.rgb(r, g, b));
                    cashingCard.append(String.valueOf(costCashing));

                    pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), costCashing, Color.rgb(r, g, b)));
                }

                if (db.typeDao().getName(idType).equals("Market")){
                    thirdView.setBackgroundColor(Color.rgb(r, g, b));
                    marketCard.append(String.valueOf(costMarket));

                    pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), costMarket, Color.rgb(r, g, b)));
                }

                if (db.typeDao().getName(idType).equals("Food")){
                    fourthView.setBackgroundColor(Color.rgb(r, g, b));
                    foodCard.append(String.valueOf(costFood));

                    pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), costFood, Color.rgb(r, g, b)));
                }

                if (db.typeDao().getName(idType).equals("Electronics")){
                    fifthView.setBackgroundColor(Color.rgb(r, g, b));
                    electronicsCard.append(String.valueOf(costElectronics));

                    pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), costElectronics, Color.rgb(r, g, b)));
                }
            }
        }

        pieChart.startAnimation();


        exitFromCardDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase db = AppDatabase.getDbInstance(getApplicationContext());
                current_user = db.userDao().getLogin(current_user_card_id);
                Intent intent = new Intent(getApplicationContext(), CardActivity.class);
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
