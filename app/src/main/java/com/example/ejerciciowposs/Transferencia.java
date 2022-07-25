package com.example.ejerciciowposs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transferencia extends AppCompatActivity {

    EditText cedula_transfire, pin_cuenta, cedula_cliente, monto_transfiere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transferencia);

        cedula_transfire = findViewById(R.id.trasNumeroCedula);
        pin_cuenta = findViewById(R.id.transPin);
        cedula_cliente = findViewById(R.id.transCedulaCliente);
        monto_transfiere = findViewById(R.id.transMonto);


    }


    public void HacerTransferencia(View view) {

        String cedulaTrans = this.cedula_transfire.getText().toString();
        String pinCuenta = this.pin_cuenta.getText().toString();
        String cedulaClien = this.cedula_cliente.getText().toString();
        String monto = this.monto_transfiere.getText().toString();


        try {
            BasedeDatos admin = new BasedeDatos(this,"banco", null, 2);
            SQLiteDatabase db =  admin.getWritableDatabase();



            if(!cedulaTrans.isEmpty() && !pinCuenta.isEmpty() && !cedulaClien.isEmpty() && !monto.isEmpty()){
                Cursor fila = db.rawQuery("select * from crear_cuenta where numero_cedula=\'"+cedulaTrans+"\' and pin_cuenta=\'"+pinCuenta+"\'",null);
                  Cursor  fila2 = db.rawQuery("select * from crear_cuenta where numero_cedula=\'"+cedulaClien+"\'",null);
                    Cursor fila3 = db.rawQuery("select * from crear_cuenta where numero_cedula=\'"+cedulaTrans+"\' and saldo_inicial>\'"+monto+"\'+1000",null);
                        Cursor fila4 = db.rawQuery("select * from usuario where id_usuario=1",null);
                if (fila.moveToFirst()) {
                   if (fila3.moveToFirst()) {
                       if(fila4.moveToFirst()){


                       db.execSQL("update crear_cuenta set saldo_inicial=saldo_inicial-" + monto + "-1000 where numero_cedula=" + cedulaTrans + "");
                       db.execSQL("update crear_cuenta set saldo_inicial=saldo_inicial+" + monto + " where numero_cedula=" + cedulaClien + "");
                          Toast.makeText(this, "correcto", Toast.LENGTH_SHORT).show();

                           db.execSQL("update usuario set saldo_corres=1000 where id_usuario=1 ");
                           Toast.makeText(this, "saldo actualizado", Toast.LENGTH_LONG).show();

                       ContentValues content2 = new ContentValues();
                       long ahora = System.currentTimeMillis();
                       Date fecha = new Date(ahora);
                       DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                       String salida = df.format(fecha);

                       String tipo = "transferencia";


                       content2.put("fecha_trans", salida);
                       content2.put("tipo_trans", tipo );
                       content2.put("monto_trans", monto);
                       content2.put("numero_cedula", cedulaClien);


                       db.insert("transaccion",null,content2);

                       Toast.makeText(this, "Transaccion realizada", Toast.LENGTH_SHORT).show();



                       if (cedulaClien.equals(cedulaTrans)) {

                           Toast.makeText(this, "No se puede transferir a la misma cuenta", Toast.LENGTH_SHORT).show();

                       } else {
                           Toast.makeText(this, "Datos correctos", Toast.LENGTH_SHORT).show();
                       }
                       } else {
                           Toast.makeText(this, "no se pudo actualizar saldo corresponsal", Toast.LENGTH_SHORT).show();
                       }

                   } else {
                       Toast.makeText(this, "saldo insuficiente", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(this, "el tranferido no exite o pin incorrecto", Toast.LENGTH_SHORT).show();
               }

            }
            else{
                Toast.makeText(this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex){

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


    public void Regresar(View view) {
        Intent inte = new Intent(this, MenuPrincipal.class);
        startActivity(inte);

    }




}