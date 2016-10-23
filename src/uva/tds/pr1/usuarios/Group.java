package uva.tds.pr1.usuarios;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

public class Group implements Serializable{
	private String nombre;
	private int gID;
	private ArrayList<Integer> idusuarios;
	private ArrayList<User> usuarios = new ArrayList<User>(0);
	
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
	
	public boolean appear(User user){
		return usuarios.contains(user);
	}
	
	public void removeUserFromGroup(User user){
		assert(!appear(user));
		idusuarios.remove(user.getuId());
		usuarios.remove(user);
	}

	public ArrayList<User> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ArrayList<User> usuarios) {
		this.usuarios = usuarios;
	}

	public boolean erasable() {
		for(User u : usuarios){
			if(u.getGrupoPrincipal().equals(this))
				return false;
		}
		return true;
	}
	
	public void addUser(User user) {
		assert(!usuarios.contains(user));
		
		usuarios.add(user);
		if(!idusuarios.contains(user.getuId()))
			idusuarios.add(user.getuId());
	}
	
	public int getgID() {
		return gID;
	}

	public void setgID(int gID) {
		this.gID = gID;
	}

	public String getName(){
		return nombre;	
	}
	
	
	public ArrayList<User> getUsuarios(){
		return usuarios;
	}
	
	}

}
