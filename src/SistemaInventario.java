import java.util.ArrayList; 
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;

class Producto{
    private String nombre;
    private int cantidad;
    private double precio;

    public Producto(String nombre, int cantidad, double precio){
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

// Clase principal - Sistema de Gestión de inventario y Ventas
public class SistemaInventario {
    private ArrayList<Producto> inventario;
    private ArrayList<Venta> ventas;
    private Scanner scanner;

    public SistemaInventario(){
        this.inventario = new ArrayList<>();
        this.ventas = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    // Método principal que ejecuta el sistema
    public static void main(String[] args) {
        SistemaInventario sistema = new SistemaInventario();
        sistema.menuPrincipal();
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

            try{
                int opcion = scanner.nextInt();
                scanner.nextLine(); 

                switch (opcion) {
                    case 1:
                        agregarProducto();
                        break;
                    case 2:
                        realizarVenta();
                        break;
                    case 3:
                        generarReporteVentas();
                        break;
                    case 4:
                        generarReporteStock();
                        break;
                    case 5:
                        System.out.println("\n=================================================");
                        System.out.println("   Gracias por usar el sistema. ¡Hasta luego!");
                        System.out.println("=================================================");
                        continuar = false;
                        break;
                    default:
                        System.out.println("\n Opción inválida. Intente nuevamente.");
                } 
            } catch (InputMismatchException e) {
                System.out.println("\n Error: Debe ingresar un número válido.");
                scanner.nextLine();
            }
        }
        scanner.close();
    }}

    // Agrega un nuevo producto al inventario o actualiza la cantidad si ya existe
    // Implementa validaciones según el diagrama de flujo

    private void agregarProducto() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║        AGREGAR NUEVO PRODUCTO              ║");
        System.out.println("╚════════════════════════════════════════════╝");}


        boolean datosValidos = false;
        String nombre = "";
        int cantidad = 0;
        double precio = 0.0;

        // Ciclo de validación según el diagrama de flujo
        while (!datosValidos) {
            try {
                System.out.print("\nIngrese nombre del producto: ");
                nombre = scanner.nextLine().trim();

                System.out.print("Ingrese cantidad disponible: ");
                cantidad = scanner.nextInt();

                System.out.print("Ingrese precio por unidad: $");
                precio = scanner.nextDouble();
                scanner.nextLine(); 

                // Validación de datos según el diagrama de flujo
                if (!nombre.isEmpty() && cantidad > 0 && precio > 0) {
                    datosValidos = true;
                } else {
                    System.out.println("\n Error: Datos inválidos. Intente nuevamente.");
                    System.out.println("   - El nombre no puede estar vacío");
                    System.out.println("   - La cantidad debe ser mayor a 0");
                    System.out.println("   - El precio debe ser mayor a 0");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n Error: Entrada inválida. Use números para cantidad y precio.");
                scanner.nextLine(); // Limpiar buffer
            }
        }

         // Buscar si el producto ya existe en el inventario
        Producto productoExistente = buscarProducto(nombre);

        if (productoExistente != null) {
            // Si existe, actualizar cantidad
            productoExistente.aumentarStock(cantidad);
            System.out.println("\n✓ Cantidad del producto actualizada exitosamente.");
            System.out.println("  Nueva cantidad: " + productoExistente.getCantidad());
        } else {
            // Si no existe, crear nuevo producto
            Producto nuevoProducto = new Producto(nombre, cantidad, precio);
            inventario.add(nuevoProducto);
            System.out.println("\n✓ Producto agregado exitosamente.");
        }
    

    // Realiza una venta, actualiza el stock y registra la transacción
    // Implementa el diagrama de flujo de realizar venta
 private void realizarVenta() {
        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║           REALIZAR VENTA                   ║");
        System.out.println("╚════════════════════════════════════════════╝");

        // Solicitar nombre del producto al usuario
        System.out.print("\nIngrese nombre del producto: ");
        String nombreProducto = scanner.nextLine().trim();

        // Buscar el producto en el inventario
        Producto producto = buscarProducto(nombreProducto);

         // Validar que el producto exista en el inventario
        if (producto == null) {
            System.out.println("\n Error: Producto no encontrado.");
            return;
        }

        try {
            System.out.print("Ingrese cantidad a vender: ");
            int cantidadVender = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            if (cantidadVender <= 0) {
                System.out.println("\n Error: La cantidad debe ser mayor a 0.");
                return;
            }

            // Verificar stock suficiente
            if (producto.getCantidad() < cantidadVender) {
                System.out.println("\n Error: Stock insuficiente.");
                System.out.println("   Stock disponible: " + producto.getCantidad());
                System.out.println("   Cantidad solicitada: " + cantidadVender);
            } else {
                // Calcular total
                double total = cantidadVender * producto.getPrecio();

                // Reducir stock del producto
                producto.reducirStock(cantidadVender);

                // Crear y registrar la venta
                Venta nuevaVenta = new Venta(nombreProducto, cantidadVender, producto.getPrecio());
                ventas.add(nuevaVenta);

                // Mostrar información de la venta
                System.out.println("\n✓ Venta realizada exitosamente!");
                System.out.println("════════════════════════════════════════════");
                System.out.println("Producto: " + nombreProducto);
                System.out.println("Cantidad: " + cantidadVender);
                System.out.println("Precio unitario: $" + String.format("%.2f", producto.getPrecio()));
                System.out.println("────────────────────────────────────────────");
                System.out.println("TOTAL A PAGAR: $" + String.format("%.2f", total));
                System.out.println("════════════════════════════════════════════");
                System.out.println("Stock restante: " + producto.getCantidad());
            }
        } catch (InputMismatchException e) {
            System.out.println("\n Error: Debe ingresar un número válido para la cantidad.");
            scanner.nextLine(); 
        }
    }