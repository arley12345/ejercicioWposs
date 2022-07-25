package com.example.ejerciciowposs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Clases.Cliente;
import Clases.Transaccion;

public class VisualizarTransaccion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_transaccion);
        listar_transacciones();
    }


    public void listar_transacciones(){

        List<Transaccion> ListTransaccion = new ArrayList<Transaccion>();
        try{

            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();

            SharedPreferences sharedpreferences = getSharedPreferences("sesion_historial", Context.MODE_PRIVATE);
            int id_transaccion = sharedpreferences.getInt("id_transaccion",-1);

            Cursor fila = db.rawQuery("select * from transaccion",null);

            if(fila.getCount() != 0){
                while (fila.moveToNext()){
                    Transaccion trans = new Transaccion();
                    trans.setFecha(fila.getString(1));
                    trans.setTipoTrans(fila.getString(2));
                    trans.setMonto(fila.getInt(3));
                    trans.setNumero(fila.getInt(4));




                    ListTransaccion.add(trans);
                }

                ListaAdapterTransaccion adapter = new ListaAdapterTransaccion(ListTransaccion,this);

                RecyclerView recycler = findViewById(R.id.recyclerView_transacciones);
                recycler.setHasFixedSize(true);
                recycler.setLayoutManager(new LinearLayoutManager(this));
                recycler.setAdapter(adapter);

                Intent inte = new Intent(this, MainActivity.class);




            }
            else{
                Toast.makeText(this, "no existen transacciones", Toast.LENGTH_SHORT).show();
            }
            db.close();

        }catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}