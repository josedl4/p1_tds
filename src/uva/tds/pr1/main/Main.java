package uva.tds.pr1.main;

import java.io.File;
import uva.tds.pr1.usuarios.*;

public class Main {

	public static void main(String[] args) {
		UserSystemImpl us = new UserSystemImpl();
		File file = new File("Ejemplo1.xml");
		us.loadFrom(file.toPath());
	}

}
