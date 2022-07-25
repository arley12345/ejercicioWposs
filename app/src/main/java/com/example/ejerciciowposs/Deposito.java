package com.example.ejerciciowposs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Deposito extends AppCompatActivity {

    EditText depo_cedula_cliente, depo_cedula_persona, monto_deposito;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposito);

        depo_cedula_cliente = findViewById(R.id.DepoCedulaCuenta);
        depo_cedula_persona = findViewById(R.id.DepoCedulaPersoan);
        monto_deposito = findViewById(R.id.DepoMonto);



    }

    public void HacerDeposito(View view) {

        String DepoCedulaCliente = this.depo_cedula_cliente.getText().toString();
        String DepoCedulaPersona = this.depo_cedula_persona.getText().toString();
        String MontoDeposito = this.monto_deposito.getText().toString();


        try {
            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();


            if (!DepoCedulaCliente.isEmpty() && !DepoCedulaPersona.isEmpty() && !MontoDeposito.isEmpty()) {
                Cursor fila = db.rawQuery("select * from crear_cuenta where numero_cedula=\'" + DepoCedulaCliente + "\'", null);

                Cursor fila3 = db.rawQuery("select * from crear_cuenta where numero_cedula=\'" + DepoCedulaCliente + "\' and saldo_inicial>\'" + MontoDeposito + "\'", null);
                if (fila.moveToFirst()) {
                    if (fila3.moveToFirst()) {

// query para el deposito de insertar un dato como actualizarlo
                        db.execSQL("insert into deposito(cedula_deposito,monto_deposito,numero_cedula) values ("+DepoCedulaPersona+", "+MontoDeposito+", "+DepoCedulaCliente+")  ");
                        db.execSQL("update crear_cuenta set saldo_inicial=saldo_inicial+" + MontoDeposito + " where numero_cedula=" + DepoCedulaCliente + "");
                        Toast.makeText(this, "Retiro realizado exitosamente", Toast.LENGTH_SHORT).show();

                        db.execSQL("update usuario set saldo_corres=saldo_corres+2000 where id_usuario=1 ");
                        Toast.makeText(this, "saldo actualizado", Toast.LENGTH_LONG).show();

                        ContentValues content2 = new ContentValues();
                        long ahora = System.currentTimeMillis();
                        Date fecha = new Date(ahora);
                        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        String salida = df.format(fecha);

                        String tipo = "deposito";


                        content2.put("fecha_trans", salida);
                        content2.put("tipo_trans", tipo );
                        content2.put("monto_trans", MontoDeposito);
                        content2.put("numero_cedula", DepoCedulaCliente);


                        db.insert("transaccion",null,content2);

                        Toast.makeText(this, "Transaccion realizada", Toast.LENGTH_SHORT).show();



                    } else {
                        Toast.makeText(this, "saldo insuficiente", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "el tranferido no exite o pin incorrecto", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Por favor ingrese todos los datos", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    public void Regresar(View view) {
        Intent inte = new Intent(this, MenuPrincipal.class);
        startActivity(inte);

    }
}
