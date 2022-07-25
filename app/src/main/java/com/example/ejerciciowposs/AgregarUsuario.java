package com.example.ejerciciowposs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AgregarUsuario extends AppCompatActivity {

    private EditText nombre, apellidos, saldo, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);

        nombre = (EditText) findViewById(R.id.txtnombresusu);
        apellidos = (EditText) findViewById(R.id.txtapellidosusu);
        email = (EditText) findViewById(R.id.txtemailusu);
        password = (EditText) findViewById(R.id.txtcontraseñausu);
    }

    public void registra_usuario(View view){


        String nombres = this.nombre.getText().toString();
        String apellidos = this.apellidos.getText().toString();
        String email = this.email.getText().toString();
        String password = this.password.getText().toString();


        //verificar si el los datos no estan vacios
        if(!nombres.isEmpty() && !apellidos.isEmpty()  && !email.isEmpty() && !password.isEmpty()){
            try {
//se abre una conexion a la base de datos
                BasedeDatos admin =  new BasedeDatos(this, "banco", null, 2);
                SQLiteDatabase db = admin.getWritableDatabase();
                double saldo= 0;
                ContentValues usuario = new ContentValues();
//se envian los datos de la tabla con put
                usuario.put("nombre_usu", nombres);
                usuario.put("apellido_usu", apellidos);
                usuario.put("saldo_corres", saldo);
                usuario.put("email_usu", email);
                usuario.put("password_usu", password);


                //Inserción del usuario en BD
                db.insert("usuario", null, usuario);
                db.close();
                Toast.makeText(this, "El usuario ha sido registrado", Toast.LENGTH_LONG).show();


//se envia a la siguiente actividad
                Intent inte = new Intent(this, MainActivity.class);
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
        this.nombre.setText("");
        this.apellidos.setText("");
        this.email.setText("");
        this.password.setText("");
    }
}