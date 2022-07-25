package com.example.ejerciciowposs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import Clases.Cliente;
import Clases.Transaccion;

public class VisCliente extends RecyclerView.Adapter<VisCliente.ViewHolder> implements View.OnClickListener {

    private List<Cliente> cliente;
    private LayoutInflater inflater;
    private  Context context;
    private View.OnClickListener listener;



    public VisCliente(List<Cliente> cliente, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.cliente = cliente;
        this.context = context;
    }



    @NonNull
    @Override
    public VisCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.saldo_cuenta,null);

        return new VisCliente.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull VisCliente.ViewHolder holder, int position) {
        holder.pintarDatos(this.cliente.get(position));


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
        return cliente.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView saldo, nombre;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            nombre = itemView.findViewById(R.id.txtnombre);
            saldo = itemView.findViewById(R.id.txtsaldo);


        }

        public void pintarDatos(final Cliente sald_corres){
            nombre.setText(sald_corres.getNombre());
            saldo.setText("$:"+String.valueOf(sald_corres.getSaldo()));

        }
    }
}