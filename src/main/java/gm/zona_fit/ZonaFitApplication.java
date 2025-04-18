package gm.zona_fit;

import gm.zona_fit.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import gm.zona_fit.modelo.Cliente;
import java.util.List;

import java.util.Scanner;

/**
 * Obtiene datos de tipo entidad
 */
//Comentando, desactivamos la activación con Spring
//@SpringBootApplication //<-- Importante ver como Spring ya la maneja como aplicación
public class ZonaFitApplication implements CommandLineRunner {
	//@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaFitApplication.class);

	String nl = System.lineSeparator(); //Obtenemos el salto de linea
	public static void main(String[] args) {

		logger.info("Inicializando el servicio\n --------------------------------------");

		//Levanta la fabrica de Spring
		SpringApplication.run(ZonaFitApplication.class, args);
		//System.out.println("Zona Fit");
		logger.info("Finalizando el servicio \n");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaFitApp();
	}

	private void zonaFitApp(){
		Scanner scanner = new Scanner(System.in);
		logger.info("*** App ZonaFit ***\n");
		var salir = false;
		var consola = new Scanner(System.in);

		while(!salir){
		var opcion = mostrarMenu(consola);
		logger.info("*** " + opcion + " ***\n");
		salir = ejecutarOpciones(consola, opcion);
		logger.info(nl);
		}
	}

	// Se puede mandar la variable de opcion como parámetro y tendríamos que referenciar en el bucle
	private int mostrarMenu(Scanner scanner) {
		logger.info("""
				\n *** Menu App ZonaFit ***
				1. Listar Clientes
				2. Buscar Cliente
				3. Agregar Cliente
				4. Modificar Cliente
				5. Eliminar Cliente
				6. Salir
				Elige una opción plox: 
				""");
		//Scanner scanner = new Scanner(System.in);
		//int opcion = scanner.nextInt();
		//return opcion;
		return Integer.parseInt(scanner.nextLine()); // más easy por que desde que aqui se puede tomar opción nos ahorrae 3 lines de código jiji
	}

	private boolean ejecutarOpciones(Scanner scanner, int opcion) {
		var salir = false;
		switch(opcion){
			case 1 -> {
				logger.info("*** Listar Clientes" + nl);
				List<Cliente> clientes = clienteServicio.listarCliente();
				clientes.forEach(cliente -> logger.info(cliente.toString() + nl));
			}
			case 2 -> {
				logger.info("*** Buscar Cliente" + nl);
				logger.info("Ingrese id: " + nl);
				Cliente cliente = clienteServicio.buscar1ClientePorId(Integer.parseInt(scanner.nextLine()));
				if (cliente != null) {
					logger.info(cliente.toString());
				} else {
					logger.info("Cliente no encontrado");
				}
			}
			case 3 -> {
				logger.info("*** Agregar Cliente" + nl);
				Cliente cliente = new Cliente();
				logger.info("Ingrese nombre: " + nl);
				cliente.setNombre(scanner.nextLine());
				logger.info("Ingrese apellido: " + nl);
				cliente.setApellido(scanner.nextLine());
				logger.info("Ingrese membresia: " + nl);
				cliente.setMembresia(Integer.parseInt(scanner.nextLine()));
				clienteServicio.guardarCliente(cliente);
			}
			case 4 -> {
				logger.info("*** Modificar Cliente" + nl);
				logger.info("Ingrese id del cliente a modificar: " + nl);
				Cliente cliente = clienteServicio.buscar1ClientePorId(Integer.parseInt(scanner.nextLine()));
				if(cliente != null){
					logger.info("Modifica los datos del cliente");
					logger.info("Nombre del cliente: ");
					cliente.setNombre(scanner.nextLine());
					logger.info("Apellido del cliente: ");
					cliente.setApellido(scanner.nextLine());
					logger.info("Membresia del cliente: ");
					cliente.setMembresia(Integer.parseInt(scanner.nextLine()));
					clienteServicio.guardarCliente(cliente);
					logger.info("Cliente modificado");
				}else
					logger.info("Cliente no encontrado");
			}
			case 5 -> {
				logger.info("*** Eliminar Cliente" + nl);
				logger.info("Ingrese id del cliente a eliminar: " + nl);
				Cliente cliente = clienteServicio.buscar1ClientePorId(Integer.parseInt(scanner.nextLine()));
				if(cliente != null){
					clienteServicio.eliminarCliente(cliente);
					logger.info(cliente.toString()+nl);
					logger.info("Cliente eliminado");
				}
			}

			case 6 ->{
				logger.info("*** Salir del servicio" + nl);
				salir = true;
			}
			default -> logger.info("Opcion no valida intente de nuevo");

		}
		return salir;
	}
}
