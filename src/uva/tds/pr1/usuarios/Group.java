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
		if(usuarios.size() != 0){
			xmlStruct = "<Group nombre='" + nombre + "' gid='g"
					+ gID + "' idusuarios='";
			
			for(User u : usuarios)
				xmlStruct += " u" + u.getuId();
			
			xmlStruct += "'/>";
		} else {
			
			xmlStruct = "<Group nombre='" + nombre + "' gid='g"
					+ gID + "'/>";
			
		}
		
		return xmlStruct;
	}
	
	protected boolean appear(User user){
		return usuarios.contains(user);
	}
	
	protected void removeUserFromGroup(User user){
		assert(appear(user));
		user.removeGroupSecunday(this);
		usuarios.remove(user);
	}
	
	protected void removeAllUsers(){
		for(User u : usuarios){
			assert(appear(u));
			u.removeGroupSecunday(this);
		}
		
	}
	
	protected void removeUserFromSystem(User user){
		assert(appear(user));
		usuarios.remove(user);
	}

	protected void setUsuarios(ArrayList<User> usuarios) {
		this.usuarios = usuarios;
	}

	protected boolean erasable() {
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

	protected void setgID(int gID) {
		this.gID = gID;
	}
	
	public void changeGID(int gid){
		mySystem.changeGID(gid, this);
	}

	public String getNombre(){
		return nombre;	
	}
	
	public void changeNombre(String nombre){
		mySystem.changeGroupName(nombre, this);
	}
	
	protected ArrayList<User> getUsuarios(){
		return usuarios;
	}
	
	protected void setNombre(String nombre){
		this.nombre = nombre;
	}

}
