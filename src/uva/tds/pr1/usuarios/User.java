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
	
	public String toString() {
		String xmlStruct = "";
		if(grupoSecundarioID.size() != 0){
			xmlStruct = "<User nombre='" + nombre + "' passwd='" + passwd +
					"' directorio='" + directorio + "' uid='u" + uId + "' grupoPrincipal='g" 
					+ grupoPrincipalID + "' grupoSecundario='";
			
			for(int n : grupoSecundarioID)
				xmlStruct += " g" + n;
			
			xmlStruct += "'/>";
		} else {
			xmlStruct = "<User nombre='" + nombre + "' passwd='" + passwd +
					"' directorio='" + directorio + "' uid='u" + uId + "' grupoPrincipal='g" 
					+ grupoPrincipalID + "'/>";
		}
		
		return xmlStruct;
	}

	public boolean appear(Group group){
		return grupoSecundario.contains(group);
	}
	
	public void removeGroupSecunday(Group group){
		assert(appear(group));
		grupoSecundario.remove(group);
	}

	public int getGrupoPrincipalID() {
		return grupoPrincipalID;
	}

	public void setGrupoPrincipalID(int grupoPrincipalID) {
		this.grupoPrincipalID = grupoPrincipalID;
	}

	public ArrayList<Integer> getGrupoSecundarioID() {
		return grupoSecundarioID;
	}

	public void setGrupoSecundarioID(ArrayList<Integer> grupoSecundarioID) {
		this.grupoSecundarioID = grupoSecundarioID;
	}

	public int getuId() {
		return uId;
	}

	public void setuId(int uId) {
		this.uId = uId;
	}

	public Group getGrupoPrincipal() {
		return grupoPrincipal;
	}

	public void setGrupoPrincipal(Group grupoPrincipal) {
		this.grupoPrincipal = grupoPrincipal;
	}

	public ArrayList<Group> getGrupoSecundario() {
		return grupoSecundario;
	}

	public void setGrupoSecundario(ArrayList<Group> grupoSecundario) {
		this.grupoSecundario = grupoSecundario;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPasswd() {
		return passwd;
	}

	public String getDirectorio() {
		return directorio;
	}

	public EnumShell getShell() {
		return shell;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
}
