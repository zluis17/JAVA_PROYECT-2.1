import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
 
class Producto {
    private String nombre;
    private int cantidad;
    private double precio;
 
    public Producto(String nombre, int cantidad, double precio){
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }
 
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
 
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }
 
    public void reducirStock(int cantidadVendida){
        this.cantidad -= cantidadVendida;
    }
 
    public void aumentarStock(int cantidadNueva){
        this.cantidad += cantidadNueva;
    }
 
    public String toString(){
        return "Producto: " + nombre +
               " | Cantidad: " + cantidad +
               " | Precio: $" + String.format("%.2f", precio);
    }
}
 
class Venta {
    private String nombreProducto;
    private int cantidad;
    private double precioUnitario;
    private double total;
    private String fecha;
 
    public Venta(String nombreProducto, int cantidad, double precioUnitario) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.total = cantidad * precioUnitario;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.fecha = LocalDateTime.now().format(formatter);
    }
 
    public double getTotal() { return total; }
 
    public String toString(){
        return "Fecha: " + fecha + "\n" +
               "Producto: " + nombreProducto + "\n" +
               "Cantidad: " + cantidad +
               " | Precio Unit: $" + String.format("%.2f", precioUnitario) +
               " | Total: $" + String.format("%.2f", total);
    }
}
 
public class SistemaInventario {
    private ArrayList<Producto> inventario;
    private ArrayList<Venta> ventas;
    private Scanner scanner;
 
    public SistemaInventario(){
        this.inventario = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }
 
    public static void main(String[] args) {
        SistemaInventario sistema = new SistemaInventario();
        sistema.menuPrincipal();
    }
 
    public void menuPrincipal() {
        boolean continuar = true;
 
        System.out.println("=================================================");
        System.out.println("   SISTEMA DE GESTIÓN DE INVENTARIO Y VENTAS");
        System.out.println("=================================================\n");
 
        while (continuar) {
            System.out.println("\n╔════════════════════════════════════════════╗");
            System.out.println("║           MENÚ PRINCIPAL                   ║");
            System.out.println("╠════════════════════════════════════════════╣");
            System.out.println("║ 1. Ingresar nuevo producto                 ║");
            System.out.println("║ 2. Realizar venta                          ║");
            System.out.println("║ 3. Generar reporte de ventas               ║");
            System.out.println("║ 4. Generar reporte de stock                ║");
            System.out.println("║ 5. Salir                                   ║");
            System.out.println("╚════════════════════════════════════════════╝");
            System.out.print("Seleccione una opción: ");
 
            try {
                int opcion = scanner.nextInt();
                scanner.nextLine();
 
                switch (opcion) {
                    case 1: agregarProducto(); break;
                    case 2: realizarVenta(); break;
                    case 3: generarReporteVentas(); break;
                    case 4: generarReporteStock(); break;
                    case 5:
                        System.out.println("\nGracias por usar el sistema. ¡Hasta luego!");
                        continuar = false;
                        break;
                    default:
                        System.out.println("\nOpción inválida.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nError: debe ingresar un número válido.");
                scanner.nextLine();
            }
        }
    }
 
    private void agregarProducto() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        AGREGAR NUEVO PRODUCTO              ║");
        System.out.println("╚════════════════════════════════════════════╝");
 
        boolean datosValidos = false;
        String nombre = "";
        int cantidad = 0;
        double precio = 0.0;
 
        while (!datosValidos) {
            try {
                System.out.print("\nIngrese nombre del producto: ");
                nombre = scanner.nextLine().trim();
 
                System.out.print("Ingrese cantidad disponible: ");
                cantidad = scanner.nextInt();
 
                System.out.print("Ingrese precio por unidad: $");
                precio = scanner.nextDouble();
                scanner.nextLine();
 
                if (!nombre.isEmpty() && cantidad > 0 && precio > 0) {
                    datosValidos = true;
                } else {
                    System.out.println("\nError: Datos inválidos.");
                }
 
            } catch (InputMismatchException e) {
                System.out.println("\nError: Entrada inválida.");
                scanner.nextLine();
            }
        }
 
        Producto existente = buscarProducto(nombre);
        if (existente != null) {
            existente.aumentarStock(cantidad);
            System.out.println("\nCantidad actualizada. Nueva cantidad: " + existente.getCantidad());
        } else {
            inventario.add(new Producto(nombre, cantidad, precio));
            System.out.println("\nProducto agregado exitosamente.");
        }
    }
 
    private void realizarVenta() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║           REALIZAR VENTA                   ║");
        System.out.println("╚════════════════════════════════════════════╝");
 
        System.out.print("\nIngrese nombre del producto: ");
        String nombre = scanner.nextLine().trim();
 
        Producto producto = buscarProducto(nombre);
        if (producto == null) {
            System.out.println("\nError: Producto no encontrado.");
            return;
        }
 
        try {
            System.out.print("Ingrese cantidad a vender: ");
            int cant = scanner.nextInt();
            scanner.nextLine();
 
            if (cant <= 0) {
                System.out.println("\nError: cantidad inválida.");
                return;
            }
 
            if (producto.getCantidad() < cant) {
                System.out.println("\nError: stock insuficiente.");
                return;
            }
 
            producto.reducirStock(cant);
            Venta venta = new Venta(nombre, cant, producto.getPrecio());
            ventas.add(venta);
 
            System.out.println("\n✓ Venta realizada con éxito.");
            System.out.println(venta);
 
        } catch (InputMismatchException e) {
            System.out.println("\nError: debe ingresar un número válido.");
            scanner.nextLine();
        }
    }
 
    private void generarReporteVentas() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         REPORTE DE VENTAS                  ║");
        System.out.println("╚════════════════════════════════════════════╝");
 
        if (ventas.isEmpty()) {
            System.out.println("\nNo hay ventas registradas.");
            return;
        }
 
        double total = 0;
        int n = 1;
 
        for (Venta v : ventas) {
            System.out.println("\n--- Venta #" + n + " ---");
            System.out.println(v.toString());
            total += v.getTotal();
            n++;
        }
 
        System.out.println("\nTOTAL DE INGRESOS: $" + String.format("%.2f", total));
    }
 
    private void generarReporteStock() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║         REPORTE DE STOCK                   ║");
        System.out.println("╚════════════════════════════════════════════╝");
 
        if (inventario.isEmpty()) {
            System.out.println("\nInventario vacío.");
            return;
        }
 
        double totalValor = 0;
 
        for (Producto p : inventario) {
            System.out.println("\n" + p);
            totalValor += p.getCantidad() * p.getPrecio();
        }
 
        System.out.println("\nVALOR TOTAL DEL INVENTARIO: $" + String.format("%.2f", totalValor));
    }
 
    private Producto buscarProducto(String nombre) {
        for (Producto p : inventario) {
            if (p.getNombre().equalsIgnoreCase(nombre)) {
                return p;
            }
        }
        return null;
    }
}