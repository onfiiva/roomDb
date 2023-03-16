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

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.WaitViewHolder> {

    public static List<Payment> Payments;
    private Context context;

    public PaymentAdapter(Context context, List<Payment> waitPayments) {
        this.context = context;
        this.Payments = waitPayments;
    }

    @NonNull
    @Override
    public PaymentAdapter.WaitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.payment_item, parent, false);
        return new WaitViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.WaitViewHolder holder, int position) {
        holder.bind(Payments.get(position));
    }

    @Override
    public int getItemCount() {
        return Payments.size();
    }

    public class WaitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView namePayment;
        private TextView costPayment;

        public WaitViewHolder(@NonNull View itemView) {
            super(itemView);
            namePayment = itemView.findViewById(R.id.payment_name_item);
            costPayment = itemView.findViewById(R.id.payment_cost_item);
            itemView.setOnClickListener(this);
        }

        public void bind(Payment waitPayments){
            namePayment.setText(waitPayments.getName());
            costPayment.setText(String.valueOf(waitPayments.getCost()));
        }

        @Override
        public void onClick(View view) {
            switchActivities(((TextView)view.findViewById(R.id.payment_name_item)).getText().toString());
        }

        private void switchActivities(String name){
            Payment payment = Payments.stream().filter(payment1 -> payment1.getName().equals(name)).findFirst().orElse(null);
            Intent intent = new Intent(context, PaymentActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("name", payment.getName());
            intent.putExtra("cost", payment.getCost());
            intent.putExtra("id_card", payment.getCard_id());
            intent.putExtra("id", payment.getId());
            context.startActivity(intent);
        }
    }
}
