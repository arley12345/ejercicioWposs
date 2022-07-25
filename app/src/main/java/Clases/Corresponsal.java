package Clases;

public class Corresponsal {

    public int idusuario;
    public String nombre;
    public String apellido;
    public int saldo;
    public String email;


    public Corresponsal() {
    }

    public Corresponsal(int idusuario, String nombre, String apellido, int saldo, String email) {
        this.idusuario = idusuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.saldo = saldo;
        this.email = email;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
