package com.example.trynaroomdb;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.List;
import java.util.Random;

public class HistoryFragment extends Fragment {

    String current_user;
    PieChart pieChart;
    float costTransfer, costCashing, costMarket, costFood, costElectronics,
            finalCostTransfer, finalCostCashing, finalCostMarket, finalCostFood, finalCostElectronics,
    r,g,b;
    View firstView, secondView, thirdView, fourthView, fifthView;
    TextView transferHistory, cashingHistory, marketHistory, foodHistory, electronicsHistory;
    String name;
    int idCard, idType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.history_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pieChart = view.findViewById(R.id.piechart_history);
        firstView = view.findViewById(R.id.view1_history);
        secondView = view.findViewById(R.id.view2_history);
        thirdView = view.findViewById(R.id.view3_history);
        fourthView = view.findViewById(R.id.view4_history);
        fifthView = view.findViewById(R.id.view5_history);
        transferHistory = view.findViewById(R.id.transfer_history);
        cashingHistory = view.findViewById(R.id.cashing_history);
        marketHistory = view.findViewById(R.id.market_history);
        foodHistory = view.findViewById(R.id.food_history);
        electronicsHistory = view.findViewById(R.id.electronics_history);

        UserWindow userWindow = (UserWindow) getActivity();
        current_user = userWindow.getCurrentUser();

        AppDatabase db = AppDatabase.getDbInstance(getContext());
        List<Card> cards = db.cardDao().getCardByUserLogin(current_user);
        for (int i = 0; i < cards.size(); i++){
            idCard = cards.get(i).getId();
            List<Type> types = db.typeDao().getAllTypes();
            for (int j = 1; j <= types.size(); j++){
                idType = j;
                List<Payment> payments = db.paymentDao().getPaymentsByTypeAndCard(idType, idCard);
                for (int k = 0; k < payments.size(); k++){
                    if (db.typeDao().getName(idType).equals("Transfer")){
                        costTransfer += payments.get(k).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Cashing out")){
                        costCashing += payments.get(k).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Market")){
                        costMarket += payments.get(k).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Food")){
                        costFood += payments.get(k).getCost();
                    }
                    if (db.typeDao().getName(idType).equals("Electronics")){
                        costElectronics += payments.get(k).getCost();
                    }
                }

                if (db.typeDao().getName(idType).equals("Transfer")){
                    finalCostTransfer += costTransfer;
                }

                if (db.typeDao().getName(idType).equals("Cashing out")){
                    finalCostCashing += costCashing;
                }

                if (db.typeDao().getName(idType).equals("Market")){
                    finalCostMarket += costMarket;
                }

                if (db.typeDao().getName(idType).equals("Food")){
                    finalCostFood += costFood;
                }

                if (db.typeDao().getName(idType).equals("Electronics")){
                    finalCostElectronics += costElectronics;
                }
            }
        }

        List<Type> types = db.typeDao().getAllTypes();
        for (int i = 1; i <= types.size(); i++){
            idType = i;
            if (db.typeDao().getName(idType).equals("Transfer")){

                int color = randColor();
                firstView.setBackgroundColor(color);
                transferHistory.setText(getResources().getString(R.string.Transfer, String.valueOf(finalCostTransfer)));
                pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), finalCostTransfer, color));
            }
            if (db.typeDao().getName(idType).equals("Cashing out")){

                int color = randColor();
                secondView.setBackgroundColor(color);
                cashingHistory.setText(getResources().getString(R.string.Cashing, String.valueOf(finalCostCashing)));
                pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), finalCostCashing, color));
            }
            if (db.typeDao().getName(idType).equals("Market")){

                int color = randColor();
                thirdView.setBackgroundColor(color);
                marketHistory.setText(getResources().getString(R.string.Market, String.valueOf(finalCostMarket)));
                pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), finalCostMarket, color));
            }
            if (db.typeDao().getName(idType).equals("Food")){

                int color = randColor();
                fourthView.setBackgroundColor(color);
                foodHistory.setText(getResources().getString(R.string.Food, String.valueOf(finalCostFood)));
                pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), finalCostFood, color));
            }
            if (db.typeDao().getName(idType).equals("Electronics")){

                int color = randColor();
                fifthView.setBackgroundColor(color);
                electronicsHistory.setText(getResources().getString(R.string.Electronics, String.valueOf(finalCostElectronics)));
                pieChart.addPieSlice(new PieModel(db.typeDao().getName(idType), finalCostElectronics, color));
            }
        }

        pieChart.startAnimation();
    }
    public int randColor(){
        Random random = new Random();
        float r = random.nextFloat();
        float g = random.nextFloat() / 2f;
        float b = random.nextFloat() / 2f;
        return Color.rgb(r,g,b);
    }
}
