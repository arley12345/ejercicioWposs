package com.example.ejerciciowposs;

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

public class MainActivity extends AppCompatActivity {

    private EditText usuario, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usuario = (EditText) findViewById(R.id.txtusuario);
        password = (EditText) findViewById(R.id.txtpassword);

    }

    public void iniciar_sesion(View view){


        String usuario = this.usuario.getText().toString();
        String password = this.password.getText().toString();

        try {
            BasedeDatos admin = new BasedeDatos(this,"banco", null, 2);
            SQLiteDatabase db =  admin.getWritableDatabase();


// se crea la query para ver si el correcto el ingreso al menu
            if(!usuario.isEmpty() && !password.isEmpty()){
                Cursor fila = db.rawQuery("select * from usuario where email_usu=\'"+usuario+"\' and password_usu=\'"+password+"\'",null);
                if(fila.moveToFirst()) {



                    SharedPreferences sharedpreferences = getSharedPreferences("sesion_usuario", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putInt("id_usuario", fila.getInt(0));
                    editor.putString("nombre_usu", fila.getString(1));

                    editor.putInt("saldo_usu", fila.getInt(3));
                    editor.commit();

                    Intent inte = new Intent(this, MenuPrincipal.class);
                    startActivity(inte);

                    Toast.makeText(this, "Bienvenido", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(this, "Usuario y/o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }

            }
            else{
                Toast.makeText(this, "Por favor ingrese su usuario y contraseña", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception ex){

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }


    public void Agregar_usuario(View view) {

        Intent inte = new Intent(this, AgregarUsuario.class);
        startActivity(inte);

    }
}