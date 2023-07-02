package marketplace;

import java.util.ArrayList;
import java.util.Scanner;

public class Marketplace {
    private ArrayList<Producto> productos;
    private ArrayList<Vendedor> vendedores;
    private ArrayList<Comprador> compradores;
    private Vendedor vendedorActual;
    private Comprador compradorActual;

    public Marketplace() {
        productos = new ArrayList<>();
        vendedores = new ArrayList<>();
        compradores = new ArrayList<>();
        vendedorActual = null;
        compradorActual = null;
    }

    public void iniciarSesionVendedor(String nombreUsuario, String contraseña) {
        for (Vendedor vendedor : vendedores) {
            if (vendedor.getNombreUsuario().equals(nombreUsuario) && vendedor.getContraseña().equals(contraseña)) {
                vendedorActual = vendedor;
                System.out.println("Inicio de sesión exitoso como vendedor.");
                return;
            }
        }
        System.out.println("Nombre de usuario o contraseña incorrectos.");
    }

    public void iniciarSesionComprador(String nombreUsuario, String contraseña) {
        for (Comprador comprador : compradores) {
            if (comprador.getNombreUsuario().equals(nombreUsuario) && comprador.getContraseña().equals(contraseña)) {
                compradorActual = comprador;
                System.out.println("Inicio de sesión exitoso como comprador.");
                return;
            }
        }
        System.out.println("Nombre de usuario o contraseña incorrectos.");
    }

    public void cargarProducto(String nombre, double precio, int cantidad) {
        if (vendedorActual != null) {
            Producto producto = new Producto(nombre, precio, cantidad, vendedorActual);
            productos.add(producto);
            vendedorActual.agregarProducto(producto);
            System.out.println("Producto cargado exitosamente.");
        } else {
            System.out.println("Debe iniciar sesión como vendedor para cargar un producto.");
        }
    }

    public void seleccionarProductos() {
        if (compradorActual != null) {
            System.out.println("Seleccione los productos que desea comprar:");
            for (int i = 0; i < productos.size(); i++) {
                Producto producto = productos.get(i);
                System.out.println((i + 1) + ") " + producto.getNombre() + " - Precio: " + producto.getPrecio());
            }

            try (Scanner scanner = new Scanner(System.in)) {
				System.out.print("Ingrese el número del producto: ");
				int numeroProducto = scanner.nextInt();

				if (numeroProducto >= 1 && numeroProducto <= productos.size()) {
				    Producto productoSeleccionado = productos.get(numeroProducto - 1);
				    compradorActual.agregarProducto(productoSeleccionado);
				    System.out.println("Producto seleccionado: " + productoSeleccionado.getNombre());
				} else {
				    System.out.println("Número de producto inválido.");
				}
			}
        } else {
            System.out.println("Debe iniciar sesión como comprador para seleccionar productos.");
        }
    }

    public void mostrarTodosLosProductos() {
        System.out.println("Lista de todos los productos:");
        for (Producto producto : productos) {
            System.out.println("Nombre: " + producto.getNombre() + " - Precio: " + producto.getPrecio() + " - Vendedor: " + producto.getVendedor().getNombreUsuario());
        }
    }

    public static void main(String[] args) {
        Marketplace marketplace = new Marketplace();

        Vendedor vendedor1 = new Vendedor("juan1", "admin1");
        Vendedor vendedor2 = new Vendedor("said2", "admin2");
        Comprador comprador1 = new Comprador("gabriel", "admin1");
        Comprador comprador2 = new Comprador("karla", "admin2");

        marketplace.vendedores.add(vendedor1);
        marketplace.vendedores.add(vendedor2);
        marketplace.compradores.add(comprador1);
        marketplace.compradores.add(comprador2);

        try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
			    System.out.println("\nMenú:");
			    System.out.println("1) Inicio de sesion como vendedor");
			    System.out.println("2) Inicio de sesion como comprador");
			    System.out.println("3) Cargar un producto");
			    System.out.println("4) Seleccionar productos");
			    System.out.println("5) Mostrar todos los productos");
			    System.out.println("6) Salir");
			    System.out.print("Seleccione una opcion: ");

			    int opcion = scanner.nextInt();

			    switch (opcion) {
			        case 1:
			            System.out.print("Ingrese el nombre de usuario del vendedor: ");
			            String usuarioVendedor = scanner.next();
			            System.out.print("Ingrese la contraseña del vendedor: ");
			            String contraseñaVendedor = scanner.next();
			            marketplace.iniciarSesionVendedor(usuarioVendedor, contraseñaVendedor);
			            break;
			        case 2:
			            System.out.print("Ingrese el nombre de usuario del comprador: ");
			            String usuarioComprador = scanner.next();
			            System.out.print("Ingrese la contraseña del comprador: ");
			            String contraseñaComprador = scanner.next();
			            marketplace.iniciarSesionComprador(usuarioComprador, contraseñaComprador);
			            break;
			        case 3:
			            scanner.nextLine(); // Limpiar el buffer
			            System.out.print("Ingrese el nombre del producto: ");
			            String nombreProducto = scanner.nextLine();
			            System.out.print("Ingrese el precio del producto: ");
			            double precioProducto = scanner.nextDouble();
			            System.out.print("Ingrese la cantidad disponible del producto: ");
			            int cantidadProducto = scanner.nextInt();
			            marketplace.cargarProducto(nombreProducto, precioProducto, cantidadProducto);
			            break;
			        case 4:
			            marketplace.seleccionarProductos();
			            break;
			        case 5:
			            marketplace.mostrarTodosLosProductos();
			            break;
			        case 6:
			            System.out.println("¡Hasta luego!");
			            System.exit(0);
			        default:
			            System.out.println("Opción inválida.");
			    }
			}
		}
    }
}

class Producto {
    private String nombre;
    private double precio;
    private int cantidad;
    private Vendedor vendedor;

    public Producto(String nombre, double precio, int cantidad, Vendedor vendedor) {
        this.nombre = nombre;
        this.precio = precio;
        this.setCantidad(cantidad);
        this.vendedor = vendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}

class Vendedor {
    private String nombreUsuario;
    private String contraseña;
    private ArrayList<Producto> productos;

    public Vendedor(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.productos = new ArrayList<>();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }
}

class Comprador {
    private String nombreUsuario;
    private String contraseña;
    private ArrayList<Producto> productosSeleccionados;

    public Comprador(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.productosSeleccionados = new ArrayList<>();
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void agregarProducto(Producto producto) {
        productosSeleccionados.add(producto);
    }
}
