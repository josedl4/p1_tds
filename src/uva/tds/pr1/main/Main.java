package uva.tds.pr1.main;

import java.io.File;
import uva.tds.pr1.usuarios.*;

public class Main {

	public static void main(String[] args) {
		UserSystemInterface us = new UserSystemImpl();
		File currentDir = new File(".");
	    File file = new File(currentDir, "/XML/prueba1_correcta.xml");
		File file2 = new File(currentDir, "/XML/prueba1_resultado.xml");
		
		us.loadFrom(file.toPath());
				
		User u = us.getUserById(1);
		
		us.createNewGroup("ElMejorGrupo", 1000);
		Group g = us.getGroupById(1000);
		us.addUserToGroup(u, g);
		Group g1 = us.getGroupById(1);
		Group[] gruposSec = {g1};
		us.createNewUser("Paco", 7, "password", new File("HOME").toPath(), "fullNamePaco", EnumShell.BASH, g, gruposSec);
		User paco = us.getUserByName("Paco");
		System.out.println(paco);
		us.removeUserFromGroup(paco, g1);
		us.removeUserFromSystem(paco);
		us.removeGroupFromSystem(g);
		
		System.out.println(g);
		
		us.updateTo(file2.toPath());
		
		us.loadFrom(file2.toPath());
	}
	
	public static void prueba1Valida() {
		
	}
	
	public static void prueba2Valida() {
		
	}

	public static void prueba3Valida() {
	
	}

}
