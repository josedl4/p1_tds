package uva.tds.pr1.equipo02;

import java.util.ArrayList;


/**
 * Implementación de los usuarios de un sistema Unix.
 * 
 * @author Jose Luis Martín Martín
 * @author Juan Carlos Blázquez Muñoz
 */
public class User{
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
	
	
	/**
	 * Constructor para crear el objeto inicial a partir te los datos obtenidos de una DTD.
	 * 
	 * @param nombre Nombre que tiene el usuario 
	 * @param passwd Contraseña del usuario
	 * @param directorio Directorio del usuario
	 * @param uId Identificador del usuario
	 * @param shell Shell seleccinada por el usuario
	 * @param nombreCompleto Nombre completo del usuario
	 * @param grupoPrincipalID Identificador del grupo Principal
	 * @param grupoSecundarioID Identificadores de los grupos Secundarios
	 * @param mySystem Sistema Referencia al sistema que gestiona a dicho usuario.
	 */
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
	
	
	// Métodos públicos accesibles desde una clase cliente.
	
	/**
	 * Redefine el metodo toString de Object para mostrar el estado de nuestro objeto usuario
	 * con el formato de nuestra DTD.
	 * 
	 * @return xmlStruct String devuelto en el cual tenemos la información sobre el usuario.
	 */
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
	

	/**
	 * Permite obtener el Grupo Principal de un usuario.
	 * 
	 * @return grupoPrincipalID Identificador del grupo Principal.
	 */
	public int getGrupoPrincipalID() {
		return grupoPrincipalID;
	}

	
	/**
	 * Permite modificar el Grupo Principal de un usuario.
	 * 
	 * @param group Grupo Principal nuevo del usuario.
	 */
	// Implementar en el Handler para que pueda ser usado por el cliente
	public void setNewGrupoPrincipal(Group group){
		mySystem.setNewGrupoPrincipal(group, this);
	}

	
	/**
	 * Permite obtener el Identificador de un usuario.
	 * 
	 * @return uId Identificador del usuario.
	 */	
	public int getuId() {
		return uId;
	}

	
	/**
	 * Permite modificar el UID de un usuario.
	 * 
	 * @param uid Identificador nuevo del usuario. 
	 */
	public void setUserID(int uid){
		mySystem.setUserID(uid, this);
	}

	
	/**
	 * Permite obtener el Nombre de un usuario.
	 * 
	 * @return nombre Identificador del usuario.
	 */	
	public String getNombre() {
		return nombre;
	}

	
	/**
	 * Permite modificar el nombre de un usuario.
	 * 
	 * @param nombre Nombre nuevo del usuario. 
	 */	
	public void changeNombre(String nombre) {
		mySystem.changeUserName(nombre, this);
	}
	
	
	/**
	 * Permite obtener la contraseña de un usuario.
	 * 
	 * @return passwd Contraseña del usuario.
	 */	
	public String getPasswd() {
		return passwd;
	}
	
	/**
	 * Permite modificar la contraseña de un usuario.
	 * 
	 * @param passwd Contraseña nueva del usuario. 
	 */	
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
	
	/**
	 * Permite obtener el directorio de un usuario.
	 * 
	 * @return directorio Directorio del usuario.
	 */	
	public String getDirectorio() {
		return directorio;
	}

	
	/**
	 * Permite modificar el directorio de un usuario.
	 * 
	 * @param directorio Directorio nuevo del usuario. 
	 */	
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}
	
	
	/**
	 * Permite obtener el Shell de un usuario.
	 * 
	 * @return shell Shell del usuario.
	 */	
	public EnumShell getShell() {
		return shell;
	}

	
	/**
	 * Permite modificar el Shell de un usuario.
	 * 
	 * @param shell Shell nuevo del usuario. 
	 */	
	public void setShell(EnumShell shell) {
		this.shell = shell;
	}
	
	
	/**
	 * Permite obtener el nombre completo de un usuario.
	 * 
	 * @return nombreCompleto Nombre completo del usuario.
	 */	
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	
	
	/**
	 * Permite modificar el nombre completo de un usuario.
	 * 
	 * @param nombreCompleto Nombre completo nuevo del usuario. 
	 */	
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}
	
	
	// Métodos protegidos para el uso interno del paquete.
		
	/**
	 * Metodo usado por nuesto sistema, comprueba si el grupo que
	 * ser recibe es uno de los grupos secundarios.
	 * 
	 * @param grupo Grupo a consultar. 
	 * @return result Valor de la consulta.
	 */
	protected boolean appear(Group group){
		return grupoSecundario.contains(group);
	}
	
	
	/**
	 * Permite eliminar un Grupo Secundario del sistema.
	 * 
	 * @param grupo Grupo a ser eleminado. 
	 */
	protected void removeGroupSecunday(Group group){
		assert(appear(group));
		grupoSecundario.remove(group);
	}
	
	
	/**
	 * Permite añadir un grupo secundario.
	 * 
	 * @param grupo Grupo a ser añadido. 
	 */	
	protected void addGrupoSecundario(Group group){
		grupoSecundario.add(group);
	}
	
	
	/**
	 * Permite modificar el grupo principal.
	 * 
	 * @param grupoPrincipalID Grupo principal.. 
	 */	
	protected void setGrupoPrincipalID(int grupoPrincipalID) {
		this.grupoPrincipalID = grupoPrincipalID;
	}

	/**
	 * Devuelve al sistema una lista de GIDs de los grupos secundarios de este usuario
	 * 
	 * @return grupoSecundarioID Lista de GIDs de los grupos secundarios
	 */	
	protected ArrayList<Integer> getGrupoSecundarioID() {
		return grupoSecundarioID;
	}
	
	
	/**
	 * Permite modificar el uId de un usuario, por nuestro sistema.
	 * 
	 * @param uId Identificador del usuario. 
	 */	
	protected void setuId(int uId) {
		this.uId = uId;
	}
	
	
	/**
	 * Permite obtener el grupo principal, por nuestro sistema.
	 * 
	 * @return grupoPrincipal Grupo principal.
	 */	
	protected Group getGrupoPrincipal() {
		return grupoPrincipal;
	}

	
	/**
	 * Permite modificar el grupo principal de un usuario, por nuestro sistema.
	 * 
	 * @param grupoPrincipal Grupo principal nuevo del usuario. 
	 */	
	protected void setGrupoPrincipal(Group grupoPrincipal) {
		this.grupoPrincipal = grupoPrincipal;
	}

	
	/**
	 * Devuelve una lista de los grupos secundarios que tiene dicho usuario
	 * 
	 * @return grupoSecundario Lista de grupos secundarios
	 */
	protected ArrayList<Group> getGrupoSecundario() {
		return grupoSecundario;
	}

	
	/**
	 * Permite modificar la lista de grupos secundarios de un usuario, por nuestro sistema.
	 * 
	 * @param grupoSecundario Lista de Grups secundarios del usuario. 
	 */	
	protected void setGrupoSecundario(ArrayList<Group> grupoSecundario) {
		this.grupoSecundario = grupoSecundario;
	}
	
	
	/**
	 * Permite modificar el nombre de un usuario, por nuestro sistema.
	 * 
	 * @param nombreCompleto Nombre nuevo del usuario. 
	 */	
	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
