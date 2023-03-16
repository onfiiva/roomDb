package com.example.trynaroomdb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.logging.FileHandler;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.WaitViewHolder> {

    public static List<Card> Cards;
    private Context context;

    public CardAdapter(Context context, List<Card> waitCards) {
        this.context = context;
        this.Cards = waitCards;
    }

    @NonNull
    @Override
    public CardAdapter.WaitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.card_item, parent, false);
        return new WaitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.WaitViewHolder holder, int position) {
        holder.bind(Cards.get(position));
    }

    @Override
    public int getItemCount() {
        return Cards.size();
    }

    public class WaitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView nameCard;
        private TextView balanceCard;

        public WaitViewHolder(@NonNull View itemView) {
            super(itemView);
            nameCard = itemView.findViewById(R.id.card_name);
            balanceCard = itemView.findViewById(R.id.card_balance);
            itemView.setOnClickListener(this);
        }

        public void bind(Card waitCards){
            nameCard.setText(waitCards.getName());
            balanceCard.setText(String.valueOf(waitCards.getBalance()));
        }

        @Override
        public void onClick(View view) {
            switchActivities(((TextView)view.findViewById(R.id.card_name)).getText().toString());
        }

        private void switchActivities(String name){
            Card card = Cards.stream().filter(card1 -> card1.getName().equals(name)).findFirst().orElse(null);
            Intent intent = new Intent(context, CardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name", card.getName());
            intent.putExtra("balance", card.getBalance());
            intent.putExtra("id_user", card.getUser_id());
            intent.putExtra("id", card.getId());
            context.startActivity(intent);
        }
    }
}
