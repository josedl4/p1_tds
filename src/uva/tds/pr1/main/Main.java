package uva.tds.pr1.main;

import java.io.File;
import uva.tds.pr1.usuarios.*;

public class Main {

	public static void main(String[] args) {
		UserSystemImpl us = new UserSystemImpl();
		File file = new File("Ejemplo1.xml");
		File file2 = new File("Prueba.xml");
		us.loadFrom(file.toPath());
		
		System.out.println("p1");
		
		us.updateTo(file2.toPath());
	}

}
