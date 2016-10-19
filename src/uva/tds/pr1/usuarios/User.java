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
	private ArrayList<Group> grupoSecundario;
	private int grupoPrincipalID;
	private ArrayList<Integer> grupoSecundarioID;
	
	public User(String nombre, String passwd, String directorio, int uId, EnumShell shell,
			String nombreCompleto, int grupoPrincipalID, ArrayList<Integer> grupoSecundarioID){
		this.nombre = nombre;
		this.passwd = passwd;
		this.directorio = directorio;
		this.uId = uId;
		this.shell = shell;
		this.nombreCompleto = nombreCompleto;
		this.grupoPrincipalID = grupoPrincipalID;
		this.grupoSecundarioID = grupoSecundarioID;
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
	
	
	
}
