package uva.tds.pr1.usuarios;

import java.nio.file.Path;

/**
 * @author Jose Luis Martín Martín
 * @author Juan Carlos Blázquez Muñoz
 */
public interface UserSystemInterface {
	
	/**
	 * Parsea y carga el contenido de un fichero XML en memoria.
	 * @param pathToXML Ruta del fichero.
	 */
	void loadFrom(Path pathToXML);
	
	/**
	 * Vuelca el contenido en memoria a un fichero XML.
	 * @param pathToXML Ruta del fichero.
	 */
	void updateTo(Path pathToXML);

	/**
	 * Averigua si hay un fichero XML cargado
	 * @return true si lo está, false en caso contrario.
	 */
	boolean isXMLLoaded();

	/**
	 * Permite saber si se ha modificado tras ser cargada.
	 * @return true si lo está, false en caso contrario.
	 */
	boolean isModifiedAfterLoaded();

	/**
	 * Permite crear un Usuario tras la especificacion de sus atributos.
	 * @param nombre 
	 * @param uId identificador del usuario
	 * @param password Contraseña 
	 * @param pathToHome directorio
	 * @param fullName Nombre completo
	 * @param shell
	 * @param mainGroup Identificador del grupo Principal
	 * @param secundaryGroups Identificadores de los grupos Secundarios
	 */
	void createNewUser(String name, int uId, String password, 
			Path pathToHome, String fullName, EnumShell shell, 
			Group mainGroup, Group[] secundaryGroups);
	
	/**
	 * Devuelve un Usuario a partir de su id.
	 * @param uId Identificador del usuario
	 * @return
	 */
	User getUserById(int uId);

	/**
	 * Devuelve un Usuario a partir de su nombre.
	 * @param nombre Nombre del usuario
	 * @return
	 */
	User getUserByName(String name);
	
	/**
	 * Devuelve un Grupo a partir de su id.
	 * @param gId Identificador del grupo
	 * @return
	 */
	Group getGroupById(int gId);
	
	/**
	 * Devuelve un Grupo a partir de su nombre.
	 * @param nombre Nombre del grupo
	 * @return
	 */
	Group getGroupByName(String name);
	
	
	/**
	 * Crea un nuevo Grupo.
	 * @param name Nombre
	 * @param gId Identificador del grupo
	 */
	void createNewGroup(String name, int gId);
	
	
	/**
	 * Añade un Usuario a un grupo.
	 * @param user Usuario
	 * @param group Grupo
	 */
	void addUserToGroup(User user, Group group);
	
	/**
	 * Borra un usuario de un grupo.
	 * @param user Usuario
	 * @param group Grupo
	 */
	void removeUserFromGroup(User user, Group group);
	
	/**
	 * Borra un usuario del sistema.
	 * @param user Usuario
	 */
	void removeUserFromSystem(User user);
	
	/**
	 * Borra un grupo del sistema.
	 * @param group Grupo
	 */
	void removeGroupFromSystem(Group group);
	
}

