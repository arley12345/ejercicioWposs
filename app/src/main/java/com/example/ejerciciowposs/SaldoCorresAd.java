package com.example.ejerciciowposs;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Clases.Corresponsal;


public class SaldoCorresAd extends RecyclerView.Adapter<SaldoCorresAd.ViewHolder> implements View.OnClickListener {

    private List<Corresponsal> corresponsal;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener listener;



    public SaldoCorresAd(List<Corresponsal> cliente, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.corresponsal = cliente;
        this.context = context;
    }



    @NonNull
    @Override
    public SaldoCorresAd.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.saldo_cuenta,null);

        return new SaldoCorresAd.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pintarDatos(this.corresponsal.get(position));
    }







    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }
    @Override
    public void onClick(View v) {
        if(listener !=  null){
            listener.onClick(v);
        }

    }

    @Override
    public int getItemCount() {
        return corresponsal.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView saldo, nombre;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txtnombre);
            saldo = itemView.findViewById(R.id.txtsaldo);


        }

        public void pintarDatos(final Corresponsal sald_corres){
            nombre.setText(sald_corres.getNombre());
            saldo.setText("$:"+String.valueOf(sald_corres.getSaldo()));

        }
    }
}