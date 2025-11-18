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
// Getters 
    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }
// Setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

public void reducirStock(int cantidadVendida){
    this.cantidad -=cantidadVendida; 
}

public void aumentarStock(int cantidadVendida){
    this.cantidad +=cantidadVendida;
}

public String toString(){
    return "Producto: " + nombre + 
            "| Cantidad: " + cantidad +
            "| Precio: " +precio;
}

}

class Venta{
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private String fecha;

     // Constructor de Venta - Calcula automáticamente el total
    public Venta(String nombreProducto, int cantidad, double precioUnitario) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = cantidad * precioUnitario;

// Registrar fecha y hora de la venta
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.fecha = LocalDateTime.now().format(formatter);
    }
//Getters
    public String getNombreProducto() {
        return nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public double getTotal() {
        return total;
    }

    public String getFecha() {
        return fecha;
    }

    public String toString(){
       return "Fecha: " + fecha + "\n" +
               "Producto: " + nombreProducto + "\n" +
               "Cantidad: " + cantidad + " | Precio Unit: $" + String.format("%.2f", precioUnitario) + 
               " | Total: $" + String.format("%.2f", total);
    }
    
}


//Clase principal - Sistema de Gestión de inventario y Ventas

public class SistemaInventario {
    private ArrayList<Producto> inventario;
    private ArrayList<Venta> ventas;
    private Scanner scanner;

    public SistemaInventario(){
        this.inventario = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Java Test $$$$$");
    }
}

  // Muestra el menú principal y gestiona las opciones del usuario
    public void menuPrincipal() {
        boolean continuar = true;

        System.out.println("=================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE INVENTARIO Y VENTAS");
        System.out.println("=================================================\n");

        while (continuar) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║           MENÚ PRINCIPAL                   ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║  1. Ingresar nuevo producto                ║");
            System.out.println("║  2. Realizar venta                         ║");
            System.out.println("║  3. Generar reporte de ventas              ║");
            System.out.println("║  4. Generar reporte de stock               ║");
            System.out.println("║  5. Salir                                  ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
        }}
