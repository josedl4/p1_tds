package uva.tds.pr1.usuarios;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable{
	private String nombre;
	private int gID;
	private ArrayList<Integer> idusuarios;
	private ArrayList<User> usuarios;
	
	public Group(String nombre, int gID,
			ArrayList<Integer>idusuarios) {
		this.nombre = nombre;
		this.gID = gID;
		this.idusuarios = idusuarios;
	}
	
	@Override
	public String toString(){
		String xmlStruct = "";
		if(idusuarios.size() != 0){
			xmlStruct = "<Group nombre='" + nombre + "' gid='g"
					+ gID + "' idusuarios='";
			
			for(int n : idusuarios)
				xmlStruct += " u" + n;
			
			xmlStruct += "'/>";
		} else {
			
			xmlStruct = "<Group nombre='" + nombre + "' gid='g"
					+ gID + "'/>";
			
		}
		
		return xmlStruct;
	}

}
