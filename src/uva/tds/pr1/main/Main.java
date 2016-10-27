package uva.tds.pr1.main;

import java.io.File;
import uva.tds.pr1.usuarios.*;

public class Main {

	public static void main(String[] args) {
		prueba1Valida();
	}
	
	public static void prueba1Valida() {
		
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
		
	}

	public static void prueba3Valida() {
	
	}

}
