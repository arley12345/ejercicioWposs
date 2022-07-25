package com.example.ejerciciowposs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Clases.Cliente;

public class VisualizarSaldo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_saldo);
        listar_usuarios();
    }


    public void listar_usuarios(){

        List<Cliente> Listclientes = new ArrayList<Cliente>();
        try{

            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();

            SharedPreferences sharedpreferences = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
            int numero_cedula = sharedpreferences.getInt("numero_cedula",-1);

            Cursor fila = db.rawQuery("select * from crear_cuenta where numero_cedula="+numero_cedula+"",null);

            if(fila.getCount() != 0){
                while (fila.moveToNext()){
                    Cliente cli = new Cliente(fila.getString(1), fila.getInt(2));
                    cli.setCedula(fila.getInt(0));
                    cli.setNombre(fila.getString(1));
                    cli.setPin(fila.getInt(2));
                    cli.setSaldo(fila.getInt(3));

                    db.execSQL("update usuario set saldo_corres=saldo_corres+1000 where id_usuario=1 ");
                    Toast.makeText(this, "saldo actualizado", Toast.LENGTH_LONG).show();

                    ContentValues content2 = new ContentValues();
                    long ahora = System.currentTimeMillis();
                    Date fecha = new Date(ahora);
                    DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                    String salida = df.format(fecha);

                    String tipo = "consulta saldo";
                    int monto = 0;


                    content2.put("fecha_trans", salida);
                    content2.put("tipo_trans", tipo );
                    content2.put("monto_trans", monto);
                    content2.put("numero_cedula", numero_cedula);


                    db.insert("transaccion",null,content2);

                    Toast.makeText(this, "Transaccion realizada", Toast.LENGTH_SHORT).show();





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