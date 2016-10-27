package uva.tds.pr1.usuarios;

import java.io.Serializable;
import java.util.ArrayList;

import javax.jws.soap.SOAPBinding.Use;

/**
 * Clase que representa a los grupos.
 * @author Jose Luis Martín Martín
 * @author Juan Carlos Blázquez Muñoz
 */
public class Group implements Serializable{
	private String nombre;
	private int gID;
	private ArrayList<Integer> idusuarios;
	private ArrayList<User> usuarios = new ArrayList<User>(0);
	private UserHandler mySystem;
		
	/**
	 * Permite crear un grupo tras la especificacion de sus atributos.
	 * @param nombre Nombre del grupo
	 * @param gID Identificador del grupo
	 * @param idusuarios Identificadores de los usuarios del grupo 
	 * @param mySystem Sistema
	 */
	protected Group(String nombre, int gID,
			ArrayList<Integer> idusuarios, UserHandler mySystem) {
		this.nombre = nombre;
		this.gID = gID;
		this.idusuarios = idusuarios;
		this.mySystem = mySystem;
	}
	
	/**
	 * Métodos públicos para ser usados desde una clase cliente.
	 */
	
	/**
	 * ##################################################################
	 * @return xmlStruct 
	 */
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
	
	
	/**
	 * Permite añadir un usuario a un grupo.
	 * @param user Usuario que se va a añadir.
	 */
	public void addUser(User user) {
		assert(!usuarios.contains(user));
		usuarios.add(user);
	}
	
	/**
	 * Permite obtener el ID de un grupo.
	 * @return gID Identificador del grupo.
	 */
	public int getgID() {
		return gID;
	}
	
	/**
	 * Permite modificar el GID de un grupo.
	 * @param gid Identificador del grupo.
	 */
	public void changeGID(int gid){
		mySystem.changeGID(gid, this);
	}

	/**
	 * Permite obtener el nombre de un grupo.
	 * @return nombre Nombre del grupo.
	 */
	public String getNombre(){
		return nombre;	
	}
	
	/**
	 * Permite modificar el nombre del grupo.
	 * @param nombre Nombre nuevo del grupo que se desea modificar.
	 */
	public void changeNombre(String nombre){
		mySystem.changeGroupName(nombre, this);
	}
	
	/**
	 * Métodos protegidos para el uso interno del paquete.
	 */
	
	/**
	 * Permite saber si un usuario se encuentra en el sistema.
	 * @param user Usuario que se va a comprobar.
	 * @return boolean.
	 */
	protected boolean appear(User user){
		return usuarios.contains(user);
	}
	
	/**
	 * Permite borrar un usuario de un grupo.
	 * @param user Usuario que se va a borrar.
	 */
	protected void removeUserFromGroup(User user){
		assert(appear(user));
		user.removeGroupSecunday(this);
		usuarios.remove(user);
	}
	
	/**
	 * Permite borrar todos los usuarios de un grupo.
	 */
	protected void removeAllUsers(){
		for(User u : usuarios){
			assert(appear(u));
			u.removeGroupSecunday(this);
		}		
	}
	
	/**
	 * Permite borrar un usuario del sistema.
	 * @param user Usuario que se va a borrar.
	 */
	protected void removeUserFromSystem(User user){
		assert(appear(user));
		usuarios.remove(user);
	}

	/**
	 * Permite ##################################################################
	 * @param usuarios
	 */
	protected void setUsuarios(ArrayList<User> usuarios) {
		this.usuarios = usuarios;
	}

	/**
	 * Permite saber si un grupo se puede borrar.
	 * @return boolean.
	 */
	protected boolean erasable() {
		for(User u : usuarios){
			if(u.getGrupoPrincipal().equals(this))
				return false;
		}
		return true;
	}
	
	/**
	 * Permite modificar el valor del gID de un grupo.
	 * @param gID Identificador que se va a modificar.
	 */
	protected void setgID(int gID) {
		this.gID = gID;
	}
	

	/**
	 * Permite ##################################################################
	 * @return usuarios
	 */
	protected ArrayList<User> getUsuarios(){
		return usuarios;
	}
	
	/**
	 * Permite modificar el nombre del grupo.
	 * @param nombre Nombre nuevo del grupo que se desea modificar.
	 */
	protected void setNombre(String nombre){
		this.nombre = nombre;
	}	

}
