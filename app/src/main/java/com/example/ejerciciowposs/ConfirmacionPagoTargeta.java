package com.example.ejerciciowposs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ConfirmacionPagoTargeta extends AppCompatActivity {

    TextView numero_tarjeta, nombre_Completo, valor_pagar, n_cuotas, tipo_tarjeta, fecha, cvv ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_pago_targeta);

        numero_tarjeta = findViewById(R.id.txtNumeroTarj);
        nombre_Completo = findViewById(R.id.txtnombreCom);
        valor_pagar = findViewById(R.id.txtvalorPa);
        n_cuotas = findViewById(R.id.txtSpinerCou);
        tipo_tarjeta = findViewById(R.id.txtTipoTarjeta);
        fecha = findViewById(R.id.txtfecha);
        cvv = findViewById(R.id.txt_cvv);


        Bundle miBundle = this.getIntent().getExtras();

        if (miBundle!=null){
            String terjeta = miBundle.getString("numero_tarjeta");
            String nombre = miBundle.getString("nombre_completo");
            String valor = miBundle.getString("valor_pagar");
            String ncuotas = miBundle.getString("spinn");
            String fecha_ex = miBundle.getString("fecha_exp");
            String c_vv = miBundle.getString("cvv");

            numero_tarjeta.setText(terjeta);
            nombre_Completo.setText(nombre);
            valor_pagar.setText(valor);
            n_cuotas.setText(ncuotas);
            fecha.setText(fecha_ex);
            cvv.setText(c_vv);
//se verifica los caracteres del Textvew
            if (terjeta.charAt(0)== '3'){
                tipo_tarjeta.setText("Tarjeta American Express");
            }
            if (terjeta.charAt(0)== '4'){
                tipo_tarjeta.setText("Tarjeta VISA");
            }
            if (terjeta.charAt(0)== '5'){
                tipo_tarjeta.setText("Tarjeta MasterCard");
            }

            if (terjeta.charAt(0)== '6'){
                tipo_tarjeta.setText("Tarjeta UnionPay");
            }
        }
    }

// se crea el alerta de exito
    public void Aceptar(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta");
        builder.setMessage("TRANSACCION EXITOSA");

        builder.setPositiveButton("Aceptar", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        InsertarTransaccion();
                        Intent miIntent = new Intent(ConfirmacionPagoTargeta.this, PagoConTarjeta.class);
                        startActivity(miIntent);
                    }
                }

        );

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void InsertarTransaccion(){
        BasedeDatos admin = new BasedeDatos(this, "banco", null, 2);
        SQLiteDatabase db = admin.getWritableDatabase();


        try{
            ContentValues content = new ContentValues();

            content.put("id_usuario", String.valueOf(numero_tarjeta));
            content.put("fecha_expiracion", String.valueOf(fecha));
            content.put("cvv_targeta", String.valueOf(cvv));
            content.put("nombre_cliente", String.valueOf(nombre_Completo));
            content.put("valor_pagar", String.valueOf(valor_pagar));
            content.put("numero_cuotas", String.valueOf(n_cuotas));

            String p_terjeta = numero_tarjeta.getText().toString();
            String p_nombre = nombre_Completo.getText().toString();
            String p_valor = valor_pagar.getText().toString();
            String p_ncuotas = n_cuotas.getText().toString();
            String p_c_vv = cvv.getText().toString();
            String p_fecha_ex = fecha.getText().toString();





            db.execSQL("insert into pagos_con_targeta(id_usuario,fecha_expiracion,cvv_targeta,nombre_cliente,valor_pagar,numero_cuotas) values("+p_terjeta+", '"+p_fecha_ex+"', "+p_c_vv+", '"+p_nombre+"', "+p_valor+", "+p_ncuotas+") ");
            Toast.makeText(this, "el pago con targeta se ah registrado exitosamente", Toast.LENGTH_LONG).show();
            db.execSQL("update usuario set saldo_corres=saldo_corres+"+valor_pagar+" where id_usuario=1 ",null);
            Toast.makeText(this, "saldo actualizado", Toast.LENGTH_LONG).show();




            ContentValues content2 = new ContentValues();
            long ahora = System.currentTimeMillis();
            Date fecha = new Date(ahora);
            DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            String salida = df.format(fecha);

            String tipo = "pago con tarjeta";


            content2.put("fecha_trans", salida);
            content2.put("tipo_trans", tipo );
            content2.put("monto_trans", p_valor);
            content2.put("numero_cedula", p_terjeta);


            db.insert("transaccion",null,content2);

            Toast.makeText(this, "Transaccion realizada", Toast.LENGTH_SHORT).show();




        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();

        }


    }

    public void Cancelar(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alerta");
        builder.setMessage("TRANSACCION RECHAZADA");

        builder.setPositiveButton("Salir", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent miIntent = new Intent(ConfirmacionPagoTargeta.this, MenuPrincipal.class);
                        startActivity(miIntent);
                    }
                }

        );

        AlertDialog dialog = builder.create();
        dialog.show();


    }
}