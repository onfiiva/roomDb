package com.example.trynaroomdb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BalanceFragment extends Fragment {

    RecyclerView recyclerView;
    TextView toAccount;
    String current_user;
    ImageButton addCard;
    int idUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                              Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_balance, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycler);
        toAccount = view.findViewById(R.id.to_account);
        addCard = view.findViewById(R.id.add_card);

        UserWindow userWindow = (UserWindow) getActivity();
        current_user = userWindow.getCurrentUser();

        toAccount.setText(current_user + " >");

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        AppDatabase db = AppDatabase.getDbInstance(getContext());
        idUser = db.userDao().getId(current_user);
        List<Card> cards = db.cardDao().getCardByUser(idUser);
        CardAdapter cardAdapter = new CardAdapter(getContext(), cards);
        recyclerView.setAdapter(cardAdapter);

        toAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AccountActivity.class);
                intent.putExtra("Login", current_user);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddCard.class);
                intent.putExtra("Login", current_user);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
            }
        });
    }
}
