package uva.tds.pr1.main;

import java.io.File;

import uva.tds.pr1.equipo02.*;

/**
 * Nuestra clase Main para cargar un archivo xml que almacenará la información de usuarios 
 * del sistema según una DTD definida. 
 * 
 * Hace uso de los servicios de la implementación y pruebas.
 * 
 * Ejerce el papel de clase cliente que usa nuestras clases desarrolladas en el paquete uva.tds.pr1.usuarios
 * 
 * @author Jose Luis Martín Martín
 * @author Juan Carlos Blázquez Muñoz
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("Pruebas de nuestro Sistema de usuarios:");
		// Pruebas de validacion de DTD y con operaciones correctas
		prueba1Valida();
		
		prueba2Valida();
		
		
		// Prueba de validacion de DTD pero con operaciones incorrectas que generen excepciones
		prueba3ErrorOperaciones();
		
		
		// Pruebas de xml no validos
		prueba4XMLNoValido();
		
		prueba5XMLNoValido();
		
		prueba6XMLNoValido();
		
		prueba7XMLNoValido();
	}

	public static void prueba1Valida() {
		System.out.println("\n\t Prueba 1");
		
		// Creamos instancia del Sistema de usuarios para realizar nuestras pruebas
		UserSystemInterface us = new UserSystemImpl();
		// Creamos objetos File donde gestionaremos los distintos archivos XML
		File currentDir = new File(".");
	    File fileIn = new File(currentDir, "/XML/prueba1_correcta.xml");
		File fileOut = new File(currentDir, "/XML/prueba1_resultado.xml");
		
		// Cargamos contenido del primer fichero XML como contenido del sistema
		us.loadFrom(fileIn.toPath());
		
		// Cargamos en referencias locales el user 1 y group 1 del sistema
		User u1 = us.getUserById(1);
		Group g1 = us.getGroupById(1);
		Group[] gruposSec = {g1};	// Creamos un array de grupos secuandarios formados por el grupo 1
		
		// Creamos un nuevo grupo y le añadimos el user 1 que tendra este grupo como otro secundario mas
		us.createNewGroup("ElMejorGrupo", 1000);
		Group g1000 = us.getGroupById(1000);
		us.addUserToGroup(u1, g1000);
		
		// Creamos un nuevo usuario y tendra como grupo principal el recien creado y como secundario en grupo 1
		us.createNewUser("Paco", 7, "password", new File("HOME").toPath(),
				"fullNamePaco", EnumShell.BASH, g1000, gruposSec);
		User paco = us.getUserByName("Paco");
		
		// Borramos al usuario "Paco" de su grupo secundario sin problemas
		us.removeUserFromGroup(paco, g1);

		// Borrar usuario recientemente creado
		us.removeUserFromSystem(paco);
		
		// Borrar el grupo con ID = g1000 creado anteriormente
		us.removeGroupFromSystem(g1000);
		
		us.createNewGroup("NewGroup", 23);
		Group g23 = us.getGroupById(23);
		User u2 = us.getUserById(2);
		u2.setNewGrupoPrincipal(g23);
		User u4 = us.getUserById(4);
		u4.setNewGrupoPrincipal(g23);
		User u6 = us.getUserById(6);
		us.removeUserFromSystem(u6);
		
		//us.removeGroupFromSystem(us.getGroupById(2));
		
		// Comprobamos el estado de nuestra instancia sistema respecto a la información del XML
		System.out.println("Resultado esperado <<true>> ya que se ha modificado el XML --> " 
				+us.isModifiedAfterLoaded());
		
		// Cargamos los cambios realizados en el fichero destino
		us.updateTo(fileOut.toPath());
		
		// Comprobamos que ya no esta cagado ningun sistema
		System.out.println("Resultado esperado <<false>> ya que actualmente no hay datos cagados del sistema --> " 
				+ us.isXMLLoaded());
		
		// Comprobamos que cargar al cargar de nuevo el fichero escrito anteriormente este nuevo 
		// fichero continua conservando su valided.
		us.loadFrom(fileOut.toPath());

		// Comprobamos que al estar recien cargado el estado del sistema no se ha modificado respecto al XML
		System.out.println("Resultado esperado <<false>> ya que no se ha modificado el XML --> " 
				+us.isModifiedAfterLoaded());
	}
	
	public static void prueba2Valida() {
		System.out.println("\n\t Prueba 2");
		
		// Creamos instancia del Sistema de usuarios para realizar nuestras pruebas
		UserSystemInterface us = new UserSystemImpl();
		// Creamos objetos File donde gestionaremos los distintos archivos XML
		File currentDir = new File(".");
	    File fileIn = new File(currentDir, "/XML/prueba1_correcta.xml");
		File fileOut = new File(currentDir, "/XML/prueba2_resultado.xml");
		
		// Cargamos contenido del primer fichero XML como contenido del sistema
		us.loadFrom(fileIn.toPath());
		
		User u4 = us.getUserById(4);
		@SuppressWarnings("unused")
		Group g3 = us.getGroupById(3);
		
		u4.changeNombre("Sin Nombre");
		u4.setUserID(44);
		
		us.removeUserFromSystem(us.getUserById(2));
		us.removeUserFromSystem(us.getUserById(3));
		us.removeUserFromSystem(us.getUserById(5));
		us.removeUserFromSystem(us.getUserById(6));
		
		us.getUserById(1).setNombreCompleto(null);
		u4.setShell(EnumShell.ZSH);
		System.out.println("Cambios realizados sin problemas");
				
		// Cargamos los cambios realizados en el fichero destino
		us.updateTo(fileOut.toPath());
		
		System.out.println("Fichero generado valida de nuevo al ser cargado por el sistema");
		
		//Cargamos de nuevo el fichero guardado para comprobar que cumple con la DTD
		us.loadFrom(fileOut.toPath());
	}

	public static void prueba3ErrorOperaciones() {
		System.out.println("\n\t Prueba 3");
		
		// Creamos instancia del Sistema de usuarios para realizar nuestras pruebas
		UserSystemInterface us = new UserSystemImpl();
		// Creamos objetos File donde gestionaremos los distintos archivos XML
		File currentDir = new File(".");
	    File fileIn = new File(currentDir, "/XML/prueba1_correcta.xml");
		File fileOut = new File(currentDir, "/XML/prueba3_resultado.xml");
		
		// Cargamos contenido del primer fichero XML como contenido del sistema
		us.loadFrom(fileIn.toPath());
		
		us.removeUserFromSystem(us.getUserById(1));
		us.removeUserFromSystem(us.getUserById(2));
		us.removeUserFromSystem(us.getUserById(3));
		us.removeUserFromSystem(us.getUserById(4));
		
		User u5 = us.getUserById(5);
		us.removeUserFromSystem(u5);
		
		try{us.removeUserFromSystem(us.getUserById(6));} catch (Error e) {
			System.out.println("Se produce el error esperado al borrar todos los usuarios: "+e);
		}
		
		//Se añade correctamente el usuario u6 al grupo g4
		us.addUserToGroup(us.getUserById(6), us.getGroupById(4));
		
		try{us.addUserToGroup(u5, us.getGroupById(4));} catch (Error e) {
			System.out.println("Se prodruce el error esperado al intentar "
					+ "añadir a un usuario ya borrado a un grupo: "+e); 
		}
		
		Group g3 = us.getGroupById(3);
		us.removeGroupFromSystem(g3);
		
		try{us.getUserById(6).setNewGrupoPrincipal(g3);} catch (Error e) {
			System.out.println("Error esperado, se intenta cambiar a un usuario al un grupo que "
					+ "ya no esta en el sistema: "+e);
		}
		
		try {us.getGroupById(4).changeGID(1);} catch (Exception e) {
			System.out.println("Excepcion esperada, el nuevo GID ya esta en uso: "+ e);
		}
		
		try {us.addUserToGroup(us.getUserById(6), us.getGroupById(2));} catch (Exception e) {
			System.out.println("Excepcion esperada, el usuario u6 ya tiene el grupo g2 como principal: "+ e);
		}
		
		try {us.addUserToGroup(us.getUserById(6), us.getGroupById(4));} catch (Exception e) {
			System.out.println("Excepcion esperada, el usuario u6 ya tiene el grupo g4 como secundario: "+ e);
		}
		
		// Cargamos los cambios realizados en el fichero destino con las modificaciones correctas
		us.updateTo(fileOut.toPath());
		
		// Comprobamos que el XML generado es valido
		us.loadFrom(fileOut.toPath());
	}
	
	private static void prueba4XMLNoValido() {
		System.out.println("\n\t Prueba 4");
		
		// Creamos instancia del Sistema de usuarios para realizar nuestras pruebas
		UserSystemInterface us = new UserSystemImpl();
		// Creamos objetos File donde gestionaremos los distintos archivos XML
		File currentDir = new File(".");
	    File fileIn = new File(currentDir, "/XML/prueba4_noValida.xml");
		
		// Cargamos contenido del primer fichero XML como contenido del sistema
		try{us.loadFrom(fileIn.toPath());} catch (Throwable e) {
			System.out.println("Error esperado, XML no valido respecto a DTD: " + e);
		}
	}

	private static void prueba5XMLNoValido() {
		System.out.println("\n\t Prueba 5");
		
		// Creamos instancia del Sistema de usuarios para realizar nuestras pruebas
		UserSystemInterface us = new UserSystemImpl();
		// Creamos objetos File donde gestionaremos los distintos archivos XML
		File currentDir = new File(".");
	    File fileIn = new File(currentDir, "/XML/prueba5_noValida.xml");
		
		// Cargamos contenido del primer fichero XML como contenido del sistema
		try{us.loadFrom(fileIn.toPath());} catch (Throwable e) {
			System.out.println("Error esperado, XML no valido respecto a DTD: " + e);
		}
	}
	
	private static void prueba6XMLNoValido() {
		System.out.println("\n\t Prueba 6");
		
		// Creamos instancia del Sistema de usuarios para realizar nuestras pruebas
		UserSystemInterface us = new UserSystemImpl();
		// Creamos objetos File donde gestionaremos los distintos archivos XML
		File currentDir = new File(".");
	    File fileIn = new File(currentDir, "/XML/prueba6_noValida.xml");
		
		// Cargamos contenido del primer fichero XML como contenido del sistema
		try{us.loadFrom(fileIn.toPath());} catch (Throwable e) {
			System.out.println("Error esperado, XML no valido respecto a DTD: " + e);
		}
	}
	
	private static void prueba7XMLNoValido() {
		System.out.println("\n\t Prueba 7");
		
		// Creamos instancia del Sistema de usuarios para realizar nuestras pruebas
		UserSystemInterface us = new UserSystemImpl();
		// Creamos objetos File donde gestionaremos los distintos archivos XML
		File currentDir = new File(".");
	    File fileIn = new File(currentDir, "/XML/prueba7_noValida.xml");
		
		// Cargamos contenido del primer fichero XML como contenido del sistema
		try{us.loadFrom(fileIn.toPath());} catch (Throwable e) {
			System.out.println("Error esperado, XML no valido respecto a DTD: " + e);
		}
	}
}
