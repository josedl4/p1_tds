package uva.tds.pr1.usuarios;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

public class Group implements Serializable{
	private String nombre;
	private int gID;
	private ArrayList<Integer> idusuarios;
	private ArrayList<User> usuarios = new ArrayList<User>(0);
	private UserHandler mySystem;
		
	protected Group(String nombre, int gID,
			ArrayList<Integer> idusuarios, UserHandler mySystem) {
		this.nombre = nombre;
		this.gID = gID;
		this.idusuarios = idusuarios;
		this.mySystem = mySystem;
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
		user.removeGroupSecunday(this);
		usuarios.remove(user);
	}
	
	public void removeUserFromSystem(User user){
		assert(!appear(user));
		usuarios.remove(user);
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
	}
	
	public int getgID() {
		return gID;
	}

	public void setgID(int gID) {
		this.gID = gID;
	}

	public String getNombre(){
		return nombre;	
	}
	
	
	public ArrayList<User> getUsuarios(){
		return usuarios;
	}

}
