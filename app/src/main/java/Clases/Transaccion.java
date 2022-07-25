package Clases;

public class Transaccion {

    public int idTransaccion;
    public String fecha;
    public String tipoTrans;
    public int monto;
    public int numero;

    public Transaccion() {
    }

    public Transaccion(int idTransaccion, String fecha, String tipoTrans, int monto, int numero) {
        this.idTransaccion = idTransaccion;
        this.fecha = fecha;
        this.tipoTrans = tipoTrans;
        this.monto = monto;
        this.numero = numero;
    }

    public Transaccion(String fecha, String tipoTrans, int monto) {

        this.fecha = fecha;
        this.tipoTrans = tipoTrans;
        this.monto = monto;

    }

    public Transaccion(String fecha) {
        this.fecha = fecha;
    }


    public int getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(int idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTipoTrans() {
        return tipoTrans;
    }

    public void setTipoTrans(String tipoTrans) {
        this.tipoTrans = tipoTrans;
    }

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }
}
