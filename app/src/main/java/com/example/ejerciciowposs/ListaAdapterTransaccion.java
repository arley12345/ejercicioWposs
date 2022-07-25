package com.example.ejerciciowposs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


import Clases.Transaccion;

public class ListaAdapterTransaccion  extends RecyclerView.Adapter<ListaAdapterTransaccion.ViewHolder> implements View.OnClickListener {

    private List<Transaccion> transaccion;
    private LayoutInflater inflater;
    private Context context;
    private View.OnClickListener listener;


    public ListaAdapterTransaccion(List<Transaccion> transaccion, Context context) {
        this.inflater = LayoutInflater.from(context);
        this.transaccion = transaccion;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_transacciones, null);

        return new ListaAdapterTransaccion.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.pintarDatos(this.transaccion.get(position));
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }

    }

    @Override
    public int getItemCount() {
        return transaccion.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView fecha, tipo, monto, cedula;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            fecha = itemView.findViewById(R.id.txtfechaTransaccion);
            tipo = itemView.findViewById(R.id.txttipotransaccion);
            monto = itemView.findViewById(R.id.txtmontotransaccion);
            cedula = itemView.findViewById(R.id.txtcedulatransaccion);

        }

        public void pintarDatos(final Transaccion list_transacciones) {
           fecha.setText(list_transacciones.getFecha());
           tipo.setText(list_transacciones.getTipoTrans());
           monto.setText("valor:"+String.valueOf(list_transacciones.getMonto()));
           cedula.setText(String.valueOf(list_transacciones.getNumero()));

        }
    }

}
