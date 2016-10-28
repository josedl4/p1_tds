package uva.tds.pr1.g02;

import java.io.FileOutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Implementación de un manejador para el parser SAX
 * 
 * @author Jose Luis Martín Martín
 * @author Juan Carlos Blázquez Muñoz
 */
public class UserHandler extends DefaultHandler{
	private ArrayList<User> usuarios;
	private ArrayList<Group> grupos;
	
	
	/**
	 * Inicializa un objeto Handler para nuestro sistema
	 * 
	 */
	protected UserHandler() {
		super();
		usuarios = new ArrayList<User>();
		grupos = new ArrayList<Group>();
	}

	
	/**
	 * Permite obtener un usuario por su nombre.
	 * 
	 * @param name Nombre del usuario.
	 * @return u Usuario correspondiente a dicho nombre.
	 * @throws IllegalArgumentException Si no se encuentra un usuario con nombre name.
	 */
	protected User getUserByName(String name){
		for (User u : usuarios){
			if(u.getNombre().equals(name)){
				return u;
			}
		}
		throw new IllegalArgumentException("No se encontro un usuario con ese nombre!!!");
	}
	
	
	/**
	 * Permite obtener un usuario por su Identificador.
	 * 
	 * @param uId Identificador del usuario.
	 * @return Usuario con dicho UID.
	 * @throws IllegalArgumentException Si no se encuentra un usuario con ese UID.
	 */
	protected User getUserById(int uId){
		for (int i=0; i<usuarios.size(); i++)
			if(usuarios.get(i).getuId()== uId){
				return usuarios.get(i);
			}
		throw new IllegalArgumentException("No se encontro un usuario con ese UID!!!");				
	}
	
	
	/**
	 * Permite obtener un grupo por su nombre.
	 * 
	 * @param name Nombre del grupo.
	 * @return Grupo correspondiente a dicho nombre.
	 * @throws IllegalArgumentException Si no se encuentra un grupo con nombre name.
	 */
	protected Group getGroupByName(String name){
		for (int i=0; i<grupos.size(); i++)
			if(grupos.get(i).getNombre().equals(name)){
				return grupos.get(i);
			}
		throw new IllegalArgumentException("No se encontro un grupo con ese nombre!!!");				
	}
	
	
	/**
	 * Permite obtener un grupo por su Identificador.
	 * 
	 * @param gId Identificador del grupo.
	 * @return Grupo correspondiente a dicho GID.
	 * @throws IllegalArgumentException Si no se encuentra un grupo con ese GID.
	 */
	protected Group getGroupById(int gId){
		for (int i=0; i<grupos.size(); i++)
			if(grupos.get(i).getgID()==gId){
				return grupos.get(i);
			}
		throw new IllegalArgumentException("No se encontro un grupo con ese GID!!!");				
	}
	
	
	/**
	 * Permite crear un Usuario tras la especificacion de sus atributos.
	 * 
	 * @param nombre Nombre del usuario
	 * @param uId identificador del usuario.
	 * @param password Contraseña.
	 * @param pathToHome directorio.
	 * @param fullName Nombre completo.
	 * @param shell Shell del usuario.
	 * @param mainGroup Identificador del grupo Principal.
	 * @param secundaryGroups Identificadores de los grupos Secundarios.
	 * @throws IllegalArgumentException Si el grupo principal no esta en el sistema
	 * @throws IllegalArgumentException Si la lista de grupos secundarios contiene grupos repetidos.
	 * @throws IllegalArgumentException Si alguno de los grupos secundarios no esta en el sitema.
	 * @throws IllegalArgumentException Si el grupo principal tambien es un grupo secundario al mismo tiempo
	 */
	protected void createNewUser(String name, int uId, String password, Path pathToHome,
			String fullName, EnumShell shell, Group mainGroup, Group[] secundaryGroups){
		
		for(User u : usuarios){
			if(u.getNombre().equals(name) || u.getuId() == uId)
				throw new IllegalArgumentException();
		}
		
		String pathToHomeSt = pathToHome.toString();
		
		User user = new User(name, password,pathToHomeSt, uId, shell, fullName, 
				mainGroup.getgID(), new ArrayList<Integer>(), this);

		
		ArrayList<Group> secundaryGroupsAL = new ArrayList<Group>();
		
		if(!grupos.contains(mainGroup))
			throw new IllegalArgumentException("El grupo principal no se encuentra en el sistema");
		user.setGrupoPrincipal(mainGroup);
		
		for(Group g : secundaryGroups){
			if(g.equals(mainGroup))
				throw new IllegalArgumentException("El grupo principal, con ID g"+ g.getgID() +", esta en la lista de grupos secundarios");
			if(!grupos.contains(g))
				throw new IllegalArgumentException("El grupo secundario, con ID g"+ g.getgID() +", no se encuentra en el sistema");
			if(secundaryGroupsAL.contains(g))
				throw new IllegalArgumentException("El grupo secundario, con ID g"+ g.getgID() +", del usuario a crear esta repetido");
			g.addUser(user);
			secundaryGroupsAL.add(g);
		}
		
		user.setGrupoSecundario(secundaryGroupsAL);
		mainGroup.addUser(user);
		usuarios.add(user);
	}
	
	
	/**
	 * Permite crear un Grupo tras la especificación de sus atributos.
	 * 
	 * @param name Nombre.
	 * @param gId Identificador del grupo.
	 * @throws IllegalArgumentException Si el grupo no comple los requisitos de nombre y gid unicos.
	 */
	protected void createNewGroup(String name, int gId){
		ArrayList<Integer> idusuarios = new ArrayList<Integer>();
		
		for(Group g: grupos){
			if(g.getNombre().equals(name) || (g.getgID() == gId))
				throw new IllegalArgumentException("El grupo cumple los requisitos de nombre o GID");
		}
		
		Group group = new Group(name, gId, idusuarios, this);
		
		grupos.add(group);
	}
	
	
	/**
	 * Permite añadir un usuario a un Grupo.
	 * 
	 * @param user Usuario que se desea añadir.
	 * @param group Grupo al que deseas añadir el usuario.
	 * @throws IllegalArgumentException Si dicho usuario ya esta en ese grupo.
	 */
	protected void addUserToGroup(User user, Group group){
		assert(usuarios.contains(user));
		assert(grupos.contains(group));
		for(User u : group.getUsuarios()){
			if(u.equals(user))
				throw new IllegalArgumentException("El usuario ya pertenece a este grupo");
		}
		group.addUser(user);
		user.addGrupoSecundario(group);
	}
	
	
	/**
	 * Permite eliminar un usuario del sistema.
	 * 
	 * @param user Usuario que se desea añadir.
	 */
	protected void removeUser(User user){
		assert(usuarios.contains(user));
		assert(usuarios.size() != 1);
		user.getGrupoPrincipal().removeUserFromSystem(user);
		ArrayList<Group> gruposSecundarios = user.getGrupoSecundario();
		
		for(int i = 0; i < gruposSecundarios.size(); i++)
			gruposSecundarios.get(i).removeUserFromSystem(user);
		
		usuarios.remove(user);
	}
	
	
	/**
	 * Permite eliminar un grupo del sistema.
	 * 
	 * @param group Grupo que se desea borrar.
	 */
	protected void removeGroup(Group group){
		assert(grupos.contains(group));
		assert(grupos.size() != 1);
		assert group.erasable();
		
		group.removeAllUsers();
		
		grupos.remove(group);
	}

	
	/**
	 * Cambia el GID de un grupo por otro valido para el sistema
	 * 
	 * @param gid Nuevo GID de nuestro sistema
	 * @param group Grupo al que se le cambiara el GID
	 * @throws IllegalArgumentException Si el nuevo GID de es valido e es el mismo que ya tenia.
	 */
	protected void changeGID(int gid, Group group) {
		assert(grupos.contains(group));
		for(Group g : grupos){
			if(gid == g.getgID())
				throw new IllegalArgumentException("El GID introducido esta en uso o es el mismo!!!");
		}
		group.setgID(gid);
	}

	
	/**
	 * Funcion usada por el grupo para cambiar su nombre por otro.
	 * 
	 * @param nombre
	 * @param group
	 * @throws IllegalArgumentException Si el nombre introducido no es valido o es el mismo 
	 */
	protected void changeGroupName(String nombre, Group group) {
		assert(grupos.contains(group));
		for(Group g : grupos){
			if(g.getNombre().equals(nombre))
				throw new IllegalArgumentException("El nombre introducido esta en uso o es el mismo!!!");
		}
		group.setNombre(nombre);
	}

	
	/**
	 * Funcion usada por el usuario para realizar un cambio de nombre por otro valido
	 * 
	 * @param nombre Nuevo nombre del usuario
	 * @param user Usuario a ser modificado
	 * @throws IllegalArgumentException El nombre no es valido o es el mismo que tiene acutalmente
	 */
	protected void changeUserName(String nombre, User user) {
		assert(usuarios.contains(user));
		for(User u : usuarios){
			if(u.getNombre().equals(nombre))
				throw new IllegalArgumentException("El nombre introducido esta en uso o es el mismo!!!");
		}
		user.setNombre(nombre);
	}

	
	/**
	 * Funcion usada por el usuario para cambiar su UID por otro valido
	 * 
	 * @param uid Nuevo UID del usuario
	 * @param user Usuario a ser modificado
	 * @throws IllegalArgumentException Si el nuevo UID esta en uso o es el mismo que ya tenia
	 */
	protected void setUserID(int uid, User user) {
		assert(usuarios.contains(user));
		for(User u : usuarios){
			if(uid == u.getuId())
				throw new IllegalArgumentException("El UID introducido esta en uso o es el mismo!!!");
		}
		user.setuId(uid);
	}

	
	/**
	 * Funcion empleada por los usuarios para realizar un cambio de grupo principal por uno valido
	 * 
	 * @param group Grupo a establecer como nuevo grupo principal
	 * @param user Usuario en el que se realizara el cambio
	 * @throws IllegalArgumentException Si no es un grupo valido o el usuario ya esta en ese grupo como grupo secundario
	 */
	protected void setNewGrupoPrincipal(Group group, User user) {
		assert(usuarios.contains(user));
		assert(grupos.contains(group));
		if(user.getGrupoSecundario().contains(group))
			throw new IllegalArgumentException("El grupo " + group.getNombre() +
					" es uno de los grupos secundarios y no puede ser añadido como grupo principal");
		user.getGrupoPrincipal().changeMainGroupFromUser(user);
		group.addUser(user);
		user.setGrupoPrincipal(group);
	}
	
	
	/**
	 * Metodo proporciondo al sistema de usuarios para actualizar los cambios
	 * en un fichero XML respecto a una DTD.
	 * 
	 * @param file Path del fichero en el que almacenar los datos.
	 * @param dtd DTD respecto a la cual se validara el XML.
	 * @throws Exception Producidas al gestionar el uso de archivos.
	 */
	protected void updateTo(Path file, Path dtd) throws Exception{
		
		StringWriter sw = new StringWriter();
		XMLOutputFactory xof = XMLOutputFactory.newInstance();

		XMLStreamWriter writer = xof.createXMLStreamWriter(sw);
		writer.writeStartDocument("UTF-8", "1.0");
		writer.writeStartElement("System");
		  
		for(User u : usuarios) {
			writer.writeStartElement("User");
			writer.writeAttribute("nombre", u.getNombre());
			writer.writeAttribute("passwd", u.getPasswd());
			writer.writeAttribute("directorio", u.getDirectorio());
			writer.writeAttribute("uid", "u"+u.getuId());
			writer.writeAttribute("shell", u.getShell().toString());

			if(u.getNombreCompleto() != null)
				writer.writeAttribute("nombreCompleto", u.getNombreCompleto());
			
			writer.writeAttribute("grupoPrincipal", " g" + u.getGrupoPrincipal().getgID());
			
			if(u.getGrupoSecundario().size() != 0){
				String gruposSecundarios = "";
				for(Group g : u.getGrupoSecundario())
					gruposSecundarios += " g" + g.getgID();
				writer.writeAttribute("grupoSecundario", gruposSecundarios);
			}
				
			writer.writeEndElement();
		}
		
		for(Group g : grupos){
			writer.writeStartElement("Group");
			writer.writeAttribute("nombre", g.getNombre());
			
			if(g.getUsuarios().size() != 0){
				String idUsuarios = "";
				for(User u : g.getUsuarios())
					idUsuarios += " u" + u.getuId();
				writer.writeAttribute("idusuarios", idUsuarios);
			}
			
			writer.writeAttribute("gid", "g" + g.getgID());
			
			writer.writeEndElement();
		}
			
		writer.writeEndElement();
			
		writer.flush();
		writer.close();
			
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		transformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, dtd.toString());
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		    
		transformer.transform(new StreamSource(new StringReader(sw.toString())), 
				new StreamResult(new FileOutputStream(file.toString(),false)));
	}
	
	
	/**
	 * Redefinimos el comprotamiento del metodo startElement de DefaultHandler y mediante el cual
	 * leemos y los datos y los pasamos a los objetos de tipo User y Group
	 * 
	 * @param uri
	 * @param localName
	 * @param qName
	 * @param atts
	 * @throws SAXException
	 */
	@Override
	public void startElement(String uri, String localName, String qName, org.xml.sax.Attributes atts)
			throws SAXException {
		
		switch (localName) {
		case "User":
			ArrayList<Integer> grupoSecundarioID = new ArrayList<Integer>(0);
			
			if(atts.getValue("grupoSecundario") != null){
				String[] grupoSec = atts.getValue("grupoSecundario").split(" ");
				
				for(int i = 0; i < grupoSec.length; i++)
					grupoSecundarioID.add(Integer.parseInt(grupoSec[i].substring(1, grupoSec[i].length())));
			}
			
			EnumShell shell;
			
			if(atts.getValue("shell") == null)
				shell = EnumShell.BASH;
			else
				shell = EnumShell.valueOf(atts.getValue("shell"));
			
			int uID = Integer.parseInt(atts.getValue("uid").substring(1,
					atts.getValue("uid").length()));
			
			int gID = Integer.parseInt(atts.getValue("grupoPrincipal").substring(1,
					atts.getValue("grupoPrincipal").length()));
			
			User user = new User(atts.getValue("nombre"),
					atts.getValue("passwd"), atts.getValue("directorio"),
					uID, shell,
					atts.getValue("nombreCompleto"), gID,
					grupoSecundarioID, this);
			
			for(User u : usuarios){
				if(u.getNombre().equals(atts.getValue("nombre")))
						throw new SAXException("Nombre usuario repetido");
			}
			
			usuarios.add(user);
			
			break;

		case "Group":
			ArrayList<Integer> usuarios = new ArrayList<Integer>(0);
			
			if(atts.getValue("idusuarios") != null){
				String[] usuariosID = atts.getValue("idusuarios").split(" ");
				
				for(int i = 0; i < usuariosID.length; i++)
					usuarios.add(Integer.parseInt(usuariosID[i].substring(1,
							usuariosID[i].length())));
			}
			
			for(Group g : grupos){
				if(g.getNombre().equals(atts.getValue("nombre")))
						throw new SAXException("Nombre grupo repetido");
			}
			
			int id = Integer.parseInt(atts.getValue("gid").substring(1,
					atts.getValue("gid").length()));
			
			grupos.add(new Group(atts.getValue("nombre"), id, usuarios, this));
			break;
		}
		
	}
	
	
	/**
	 * Redefinicion del metodo endDocument de DefaultHandler para la gestion de usuarios y grupos
	 * 
	 * @throws SAXException
	 */
	@Override
	public void endDocument() throws SAXException{
		for(User u : usuarios){
			
			for(Group g : grupos){
				
				if(u.getGrupoPrincipalID() == g.getgID()){
					u.setGrupoPrincipal(g);
					g.addUser(u);
				
				}else{
				
					if(u.getGrupoSecundarioID().contains(g.getgID())){
						u.getGrupoSecundario().add(g);
						g.addUser(u);
					}
				}
			}
		}
	}
	
	
	@Override
	public void warning(SAXParseException e) throws SAXException {
		 System.err.println("Warning: " + e.getMessage());
		 throw new SAXException("Parsing Warning");
	}
	
	@Override
	public void error(SAXParseException e) throws SAXException {
		System.err.println("Line " + e.getLineNumber() + ", Column " + e.getColumnNumber() +
		". Error: " + e.getMessage());
		throw new SAXException("Parsing Error");
	}
	    
	@Override
	public void fatalError(SAXParseException e) throws SAXException {
		System.err.println("Faltal error:" + e.getMessage());
		throw new SAXException("Parsing fatal error");
	}
	
}
