package com.example.ejerciciowposs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsultaSaldo extends AppCompatActivity {

    EditText consul_cedula, consul_pin, consul_confi_pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_saldo);

        consul_cedula = findViewById(R.id.ConsulCedulaCuenta);
        consul_pin = findViewById(R.id.ConsulPin);
        consul_confi_pin = findViewById(R.id.ConsulConfiPin);

    }

    public void ConsultaSaldo(View view) {

        String ConsulCedula = consul_cedula.getText().toString();
        String ConsulPin = consul_pin.getText().toString();
        String ConsulConfiPin = consul_confi_pin.getText().toString();

        try {
            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();

// se verifica q los campos esten vacios y la query para el listado
            if (!ConsulCedula.isEmpty() && !ConsulPin.isEmpty()) {
                Cursor fila = db.rawQuery("select * from crear_cuenta where numero_cedula="+ConsulCedula+" and pin_cuenta="+ConsulPin+"", null);
                if (fila.moveToFirst()) {

//se obtienen los datos con put
                    SharedPreferences sharedpreferences = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("numero_cedula", fila.getInt(0));
                    editor.putString("nombre_cli", fila.getString(1));
                    editor.putString("pin_cuenta", fila.getString(2));
                    editor.putInt("saldo_inicial", fila.getInt(3));
                    editor.commit();

                    db.execSQL("update usuario set saldo_corres=1000 where id_usuario=1 ");
                    Toast.makeText(this, "saldo actualizado", Toast.LENGTH_LONG).show();


                    Intent inte = new Intent(this, VisualizarSaldo.class);
                    startActivity(inte);

                    Toast.makeText(this, "Bienvenido a consulta de saldo", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(this, "Por favor ingrese su usuario y contraseña", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

    public void Cancelar(View view) {
        Intent inte = new Intent(this, MenuPrincipal.class);
        startActivity(inte);

    }
}



