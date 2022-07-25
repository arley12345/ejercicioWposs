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

public class Retiro extends AppCompatActivity {

    EditText Retiro_cedula, Retiro_pin, Retiro_confi_pin, Retiro_monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retiro);

        Retiro_cedula =findViewById(R.id.RetNumeroCedula);
        Retiro_pin = findViewById(R.id.RetPin);
        Retiro_confi_pin = findViewById(R.id.RetPinConfi);
        Retiro_monto = findViewById(R.id.RetMonto);

    }

    public void HacerTRetiro(View view) {
        String retiroCedula = this.Retiro_cedula.getText().toString();
        String retiroPin = this.Retiro_pin.getText().toString();
        String retiroConfPin = this.Retiro_confi_pin.getText().toString();
        String retiroMonto = this.Retiro_monto.getText().toString();


        try {
            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();


            if (!retiroCedula.isEmpty() && !retiroPin.isEmpty() && !retiroConfPin.isEmpty() && !retiroMonto.isEmpty()) {
                Cursor fila = db.rawQuery("select * from crear_cuenta where numero_cedula=\'" + retiroCedula + "\' and pin_cuenta=\'" + retiroPin + "\'", null);

                Cursor fila3 = db.rawQuery("select * from crear_cuenta where numero_cedula=\'" + retiroCedula + "\' and saldo_inicial>\'" + retiroMonto + "\'+2000", null);
                if (fila.moveToFirst()) {
                    if (fila3.moveToFirst()) {
                        // esta query es paara actualizar la base de datos

                        db.execSQL("update crear_cuenta set saldo_inicial=saldo_inicial-" + retiroMonto + "-2000 where numero_cedula=" + retiroCedula + "");
                        Toast.makeText(this, "Retiro realizado exitosamente", Toast.LENGTH_SHORT).show();
                        db.execSQL("update usuario set saldo_corres=saldo_corres+2000 where id_usuario=1 ");
                        Toast.makeText(this, "saldo actualizado", Toast.LENGTH_LONG).show();

                        ContentValues content2 = new ContentValues();
                        long ahora = System.currentTimeMillis();
                        Date fecha = new Date(ahora);
                        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                        String salida = df.format(fecha);

                        String tipo = "retiro";


                        content2.put("fecha_trans", salida);
                        content2.put("tipo_trans", tipo );
                        content2.put("monto_trans", retiroMonto);
                        content2.put("numero_cedula", retiroCedula);


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





