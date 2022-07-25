package Clases;

public class Cliente {

    public int cedula;
    public String nombre;
    public int pin;
    public int saldo;

    public Cliente() {
    }

    public Cliente(int cedula, String nombre, int pin, int saldo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.pin = pin;
        this.saldo = saldo;
    }

    public Cliente(String nombre, int saldo) {
        this.nombre = nombre;
        this.saldo= saldo;
    }

    public Cliente(int cedula) {
        this.cedula=cedula;
    }

    public int getCedula() {
        return cedula;
    }

    public void setCedula(int cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
}

