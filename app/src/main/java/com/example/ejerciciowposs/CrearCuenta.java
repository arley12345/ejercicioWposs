package com.example.ejerciciowposs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearCuenta extends AppCompatActivity {

    EditText numeroCedula, nombreCiente, pinCuenta, saldoInicial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        numeroCedula = findViewById(R.id.txtcedulaCliente);
        nombreCiente = findViewById(R.id.txtnombre);
        pinCuenta =findViewById(R.id.txtPin);
        saldoInicial =findViewById(R.id.txtsaldo_cliente);

    }

    public void Crear_cuentaWposs(View view) {
        String cedula = this.numeroCedula.getText().toString();
        String nombre = this.nombreCiente.getText().toString();
        String pin = this.pinCuenta.getText().toString();
        String saldo = this.saldoInicial.getText().toString();


        if(!cedula.isEmpty() && !nombre.isEmpty() && !pin.isEmpty() && !saldo.isEmpty()){
            try {

                BasedeDatos admin =  new BasedeDatos(this, "banco", null, 2);
                SQLiteDatabase db = admin.getWritableDatabase();



                db.execSQL("update usuario set saldo_corres=10000 where id_usuario=1 ");
                Toast.makeText(this, "saldo actualizado", Toast.LENGTH_LONG).show();


                ContentValues content = new ContentValues();




                content.put("numero_cedula", cedula);
                content.put("nombre_cli", nombre);
                content.put("pin_cuenta", pin);
                content.put("saldo_inicial", saldo);



                db.insert("crear_cuenta", null, content);
               Toast.makeText(this, "La Cuenta ah sido Creada", Toast.LENGTH_LONG).show();



                ContentValues content2 = new ContentValues();
                long ahora = System.currentTimeMillis();
                Date fecha = new Date(ahora);
                DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
                String salida = df.format(fecha);

                String tipo = "creacion cuenta";
                int monto = 0;

                   content2.put("fecha_trans", salida);
                   content2.put("tipo_trans", tipo );
                   content2.put("monto_trans", monto);
                   content2.put("numero_cedula", cedula);


                    db.insert("transaccion",null,content2);

                Toast.makeText(this, "Transaccion realizada", Toast.LENGTH_SHORT).show();




                Intent inte = new Intent(this, MenuPrincipal.class);
                startActivity(inte);
                limpiarFormulario();
            }catch (Exception ex){

                Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();

            }


        }
        else{
            Toast.makeText(this, "Por favor ingrese todos los datos del usuario", Toast.LENGTH_SHORT).show();
        }


    }

    public void limpiarFormulario(){
        this.numeroCedula.setText("");
        this.nombreCiente.setText("");
        this.pinCuenta.setText("");
        this.saldoInicial.setText("");
    }

    public void Regresar(View view) {
        Intent inte = new Intent(this, MenuPrincipal.class);
        startActivity(inte);

    }
}