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

public class VisualizarSaldoCorres extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_saldo_corres);
        listar_usuarios();
    }


    public void listar_usuarios(){

        List<Cliente> Listclientes = new ArrayList<Cliente>();
        try{

            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();

            SharedPreferences sharedpreferences = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
            int numero_cedula = sharedpreferences.getInt("numero_cedula",-1);

            Cursor fila = db.rawQuery("select * from usuario where id_usuario=1",null);

            if(fila.getCount() != 0){
                while (fila.moveToNext()){
                    Cliente cli = new Cliente(fila.getString(0), fila.getInt(1));
                    cli.setCedula(fila.getInt(0));
                    cli.setNombre(fila.getString(1));
                    cli.setPin(fila.getInt(2));
                    cli.setSaldo(fila.getInt(3));




                    Listclientes.add(cli);
                }

                VisCliente adapter = new VisCliente(Listclientes,this);

                RecyclerView recycler = findViewById(R.id.recyclerView);
                recycler.setHasFixedSize(true);
                recycler.setLayoutManager(new LinearLayoutManager(this));
                recycler.setAdapter(adapter);

                Intent inte = new Intent(this, MainActivity.class);
                adapter.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {


                        inte.putExtra("nombre_cli",Listclientes.get(recycler.getChildAdapterPosition(v)).getNombre());

                        inte.putExtra("saldo_inicial",Listclientes.get(recycler.getChildAdapterPosition(v)).getSaldo());



                        startActivity(inte);
                    }
                });



            }
            else{
                Toast.makeText(this, "no existe cliente", Toast.LENGTH_SHORT).show();
            }
            db.close();

        }catch (Exception ex){
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}