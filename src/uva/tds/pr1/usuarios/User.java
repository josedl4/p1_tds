package uva.tds.pr1.usuarios;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	private String nombre;
	private String passwd;
	private String directorio;
	private int uId;
	private EnumShell shell;
	private String nombreCompleto;
	private Group grupoPrincipal;
	private ArrayList<Group> grupoSecundario = new ArrayList<Group>(0);
	private int grupoPrincipalID;
	private ArrayList<Integer> grupoSecundarioID;
	private UserHandler mySystem;
	
	protected User(String nombre, String passwd, String directorio, int uId, EnumShell shell,
			String nombreCompleto, int grupoPrincipalID, ArrayList<Integer> grupoSecundarioID,
			UserHandler mySystem){
		this.nombre = nombre;
		this.passwd = passwd;
		this.directorio = directorio;
		this.uId = uId;
		this.shell = shell;
		this.nombreCompleto = nombreCompleto;
		this.grupoPrincipalID = grupoPrincipalID;
		this.grupoSecundarioID = grupoSecundarioID;
		this.mySystem = mySystem;
	}
	
	@Override
	public String toString() {
		String xmlStruct = "";
		if(grupoSecundario.size() != 0){
			xmlStruct = "<User nombre='" + nombre + "' passwd='" + passwd +
					"' directorio='" + directorio + "' uid='u" + uId + "' grupoPrincipal='g" 
					+ grupoPrincipalID + "' grupoSecundario='";
			
			for(Group g : grupoSecundario)
				xmlStruct += " g" + g.getgID();
			
			xmlStruct += "'/>";
		} else {
			xmlStruct = "<User nombre='" + nombre + "' passwd='" + passwd +
					"' directorio='" + directorio + "' uid='u" + uId + "' grupoPrincipal='g" 
					+ grupoPrincipalID + "'/>";
		}
		
		return xmlStruct;
	}

	// Hecho - no tocar
	protected boolean appear(Group group){
		return grupoSecundario.contains(group);
	}
	
	// Hecho - no tocar
	protected void removeGroupSecunday(Group group){
		assert(appear(group));
		grupoSecundario.remove(group);
	}
	
	protected void addGrupoSecundario(Group group){
		grupoSecundario.add(group);
	}

	public int getGrupoPrincipalID() {
		return grupoPrincipalID;
	}

	protected void setGrupoPrincipalID(int grupoPrincipalID) {
		this.grupoPrincipalID = grupoPrincipalID;
	}
	
	// Implementar en el Handler para que pueda ser usado por el cliente
	public void setNewGrupoPrincipal(Group group){
		mySystem.setNewGrupoPrincipal(group, this);
	}

	protected ArrayList<Integer> getGrupoSecundarioID() {
		return grupoSecundarioID;
	}

	protected void setGrupoSecundarioID(ArrayList<Integer> grupoSecundarioID) {
		this.grupoSecundarioID = grupoSecundarioID;
	}

	public int getuId() {
		return uId;
	}

	protected void setuId(int uId) {
		this.uId = uId;
	}
	
	public void setUserID(int uid){
		mySystem.setUserID(uid, this);
	}

	protected Group getGrupoPrincipal() {
		return grupoPrincipal;
	}

	protected void setGrupoPrincipal(Group grupoPrincipal) {
		this.grupoPrincipal = grupoPrincipal;
	}

	protected ArrayList<Group> getGrupoSecundario() {
		return grupoSecundario;
	}

	protected void setGrupoSecundario(ArrayList<Group> grupoSecundario) {
		this.grupoSecundario = grupoSecundario;
	}
	
	public String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void changeNombre(String nombre) {
		mySystem.changeUserName(nombre, this);
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getDirectorio() {
		return directorio;
	}

	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}

	public EnumShell getShell() {
		return shell;
	}

	public void setShell(EnumShell shell) {
		this.shell = shell;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
}
