package com.example.ejerciciowposs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BasedeDatos extends SQLiteOpenHelper {
    public BasedeDatos(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//creacion de tablas
        db.execSQL("create table crear_cuenta (" +
                "numero_cedula integer primary key," +
                "nombre_cli text," +
                "pin_cuenta integer," +
                "saldo_inicial integer)");


          db.execSQL("create table usuario (" +
                "id_usuario integer primary key," +
                "nombre_usu text, apellido_usu text," +
                "saldo_corres integer," +
                "email_usu text unique, password_usu text)");

        db.execSQL("create table pagos_con_targeta (" +
                "id_pagos integer primary key," +
                "id_usuario numeric(16)," +
                "fecha_expiracion date, " +
                "cvv_targeta numeric(4)," +
                "nombre_cliente text," +
                "valor_pagar double, " +
                "numero_cuotas numeric(2))");

        db.execSQL("create table deposito (" +
                "id_deposito integer primary key," +
                "cedula_deposito integer, " +
                "monto_deposito integer," +
                "numero_cedula integer," +
                " foreign key (numero_cedula) references crear_cuenta (numero_cedula))");

        db.execSQL("create table transaccion (" +
                "id_transaccion integer primary key," +
                "fecha_trans date," +
                "tipo_trans text," +
                "monto_trans integer," +
                "numero_cedula numeric(17))");







    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
