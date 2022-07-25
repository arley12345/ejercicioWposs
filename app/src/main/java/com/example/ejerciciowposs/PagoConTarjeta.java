package com.example.ejerciciowposs;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PagoConTarjeta extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    EditText numeroTarjeta, cvv, fechaExpiracion, nombreCompleto, valorPagar;
    Spinner spiner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago_con_tarjeta);

        numeroTarjeta = findViewById(R.id.txtNumeroTarjeta);
        cvv = findViewById(R.id.txtCVV);
        fechaExpiracion = findViewById(R.id.txtfechaExp);
        nombreCompleto = findViewById(R.id.txtNombreCompleto);
        valorPagar = findViewById(R.id.txtValorPagar);
        spiner = findViewById(R.id.spiner1);




        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numeros, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner.setAdapter(adapter);
        spiner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public void Confirmar(View view) throws ParseException {
        switch (view.getId()){
            case R.id.btnConfirmar:
                String numeroT = numeroTarjeta.getText().toString();
                String cvve = cvv.getText().toString();
                String fecha_exp = fechaExpiracion.getText().toString();
                String valor_pagar = valorPagar.getText().toString();
                String spin = spiner.getTouchables().toString();
                String nombre = nombreCompleto.getText().toString();

                int va = Integer.parseInt(valorPagar.getText().toString().trim());

                if (numeroT.charAt(0)== '3' || numeroT.charAt(0)== '4' || numeroT.charAt(0)== '5' || numeroT.charAt(0)== '6' ) {

                    if (va> 10000) {
                        if (va<1000000) {
                            if (cvve.length()<5){
                                if (numeroT.length()>=15 && numeroT.length()<=16) {

                                    Date fechaactual = new Date(System.currentTimeMillis());
                                    String fechaInicio = fecha_exp;
                                    SimpleDateFormat date = new SimpleDateFormat("yyyy/mm/dd");
                                    Date fechaInicioDate = date.parse(fechaInicio);

                                    if (fechaInicioDate.before(fechaactual)){
                                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                        builder.setTitle("Alerta");
                                        builder.setMessage("La fecha esta expirada");

                                        builder.setPositiveButton("Aceptar", null);

                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }else {


                                        Intent miIntent = new Intent(PagoConTarjeta.this, ConfirmacionPagoTargeta.class);
                                        startActivity(miIntent);

                                        Bundle miBundle = new Bundle();
                                        miBundle.putString("numero_tarjeta", numeroTarjeta.getText().toString());
                                        miBundle.putString("cvv", cvv.getText().toString());
                                        miBundle.putString("fecha_exp", fechaExpiracion.getText().toString());
                                        miBundle.putString("valor_pagar", valorPagar.getText().toString());
                                        miBundle.putString("nombre_completo", nombreCompleto.getText().toString());
                                        miBundle.putString("spinn", spiner.getSelectedItem().toString());

                                        miIntent.putExtras(miBundle);


                                        startActivity(miIntent);
                                    }
                                }else {
                                    Toast.makeText(this, "el numero de tarjeta de credito debe ser entre 15 o 16 dijitos", Toast.LENGTH_SHORT).show();
                                }
                            }else {
                            Toast.makeText(this, "el CVV debe ser menor de 4 dijitos", Toast.LENGTH_SHORT).show();
                         }
                        }else {
                            Toast.makeText(this, "el valor no puede pasar de 1.000.000", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "el valor a pagar debe ser mayor a 10.000", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(this, "lo sentimos no existe esa targeta", Toast.LENGTH_SHORT).show();
                }
                    break;

        }

    }

    public void Regresar(View view) {
        Intent inte = new Intent(this, MenuPrincipal.class);
        startActivity(inte);

    }
}