package com.example.ejerciciowposs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MenuPrincipal extends AppCompatActivity {

    private int id_usuario=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
    }
// evento on click para pasar de actividad e actividad
    public void Pagos_con_Targeta(View view){

        Intent inte = new Intent(this, PagoConTarjeta.class);
        inte.putExtra("usuario", id_usuario);
        startActivity(inte);
    }

    public void Retiro_Wposs_Bank(View view){
        id_usuario=0;
        Intent inte = new Intent(this, Retiro.class);
        startActivity(inte);
    }

    public void Deposito_Wposs_Bank(View view){
        Intent inte = new Intent(this, Deposito.class);
        startActivity(inte);
    }

    public void Transferencia_Wposs_Bank(View view){

        Intent inte = new Intent(this, Transferencia.class);
        inte.putExtra("usuario", id_usuario);
        startActivity(inte);
    }

    public void Consultas_de_saldo_Wposs_bank(View view){

        Intent inten = new Intent(this, ConsultaSaldo.class);
        startActivity(inten);
    }

    public void Crear_Cuanta_Wposs(View view){

        Intent inte = new Intent(this, CrearCuenta.class);
        inte.putExtra("usuario", id_usuario);
        startActivity(inte);
    }

    public void Historial_de_Transacciones(View view){

        try {
            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();



            Cursor fila = db.rawQuery("select * from transaccion t inner join crear_cuenta c on t.numero_cedula = c.numero_cedula", null);
            if (fila.moveToFirst()) {


                SharedPreferences sharedpreferences = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putInt("id_transaccion", fila.getInt(0));
                editor.putString("fecha_trans", fila.getString(1));
                editor.putString("tipo_trans", fila.getString(2));
                editor.putInt("monto_trans", fila.getInt(3));
                editor.putInt("numero_cedula", fila.getInt(4));

                editor.commit();

                Intent inte = new Intent(this, VisualizarTransaccion.class);
                startActivity(inte);

                Toast.makeText(this, "Bienvenido Hitorial de Transacciones", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(this, "no existe ninguna transaccion", Toast.LENGTH_SHORT).show();
            }



        } catch (Exception ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }



    public void Visualizar_saldo_del_corresponsal(View view){

        try {
            BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
            SQLiteDatabase db = admin.getWritableDatabase();



                Cursor fila = db.rawQuery("select * from usuario where id_usuario=1", null);
                if (fila.moveToFirst()) {


                    SharedPreferences sharedpreferences = getSharedPreferences("sesion_historial", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("id_usuario", fila.getInt(0));
                    editor.putString("nombre_usu", fila.getString(1));
                    editor.putString("apellido_usu", fila.getString(2));
                    editor.putInt("saldo_corres", fila.getInt(3));
                    editor.putString("email_usu", fila.getString(4));
                    editor.putString("password_usu", fila.getString(5));

                    editor.commit();

                    Intent inte = new Intent(this, VisualizarSaldoCorres.class);
                    startActivity(inte);

                    Toast.makeText(this, "Bienvenido a consulta de saldo", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
                }



        } catch (Exception ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

}