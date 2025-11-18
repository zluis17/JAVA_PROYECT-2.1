import java.util.ArrayList; 
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

class Producto{
    private String nombre;
    private int cantidad;
    private double precio;

    public Producto(String  nombre, int cantidad, double precio){
    this.nombre = nombre;
    this.cantidad = cantidad;
    this.precio = precio;
}

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    

}








public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Java Test $$$$$");
    }
}
