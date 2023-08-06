package com.example.earning_app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.earning_app.Models.TrModel;
import com.example.earning_app.R;
import com.example.earning_app.databinding.ItemRedeemBinding;

import java.util.ArrayList;

public class TrAdapter extends RecyclerView.Adapter<TrAdapter.viewHolder>{

    Context context;
    ArrayList<TrModel>list;

    public TrAdapter(Context context, ArrayList<TrModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_redeem , parent , false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        final TrModel model = list.get(position);

        String status = model.getStatus();
        String coin = model.getCoin();
        String method = model.getPaymentMethod();

        int currentCoin = Integer.parseInt(coin);

      //  double earn = currentCoin * 0.02;

        double earn = currentCoin;

        holder.binding.paymentTitle.setText(model.getPaymentMethod());
        holder.binding.paymentDetails.setText(model.getPaymentDetails());
        holder.binding.paymentDate.setText(model.getDate());

        holder.binding.earn.setText("( " + "Rs.  " + earn + " )");

        if (status.equals("false")){

            holder.binding.btnStatus.setText("Pendding");

        }
        else {

            holder.binding.btnStatus.setText("Success");
            holder.binding.btnStatus.setBackgroundResource(R.drawable.btn_status);

        }

        if (method.equals("Paytm")){
            holder.binding.paymentMethodLogo.setImageResource(R.drawable.paytm);
        } else if (method.equals("Paypal")) {
            holder.binding.paymentMethodLogo.setImageResource(R.drawable.paypal);
        } else if (method.equals("Amazon Gift")) {
            holder.binding.paymentMethodLogo.setImageResource(R.drawable.amazon);
        } else if (method.equals("Google Play Gift")) {
            holder.binding.paymentMethodLogo.setImageResource(R.drawable.google_play);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        ItemRedeemBinding binding;
        public viewHolder(@NonNull View itemView) {
            super(itemView);

            binding = ItemRedeemBinding.bind(itemView);

        }
    }

}
