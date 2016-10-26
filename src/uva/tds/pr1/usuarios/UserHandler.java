package uva.tds.pr1.usuarios;

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
import javax.xml.ws.handler.Handler;
import uva.tds.pr1.usuarios.*;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler{
	private ArrayList<User> usuarios;
	private ArrayList<Group> grupos;
	
	public UserHandler() {
		super();
		usuarios = new ArrayList<User>();
		grupos = new ArrayList<Group>();
	}

	// Getters de Usuarios y Grupos
	public User getUserByName(String name){
		for (int i=0; i<usuarios.size(); i++)
			if(usuarios.get(i).getNombre().equals(name)){
				return usuarios.get(i);
			}
		throw new IllegalArgumentException("No se encontro un usuario con ese nombre!!!");
	}
	
	public User getUserById(int uId){
		for (int i=0; i<usuarios.size(); i++)
			if(usuarios.get(i).getuId()== uId){
				return usuarios.get(i);
			}
		throw new IllegalArgumentException("No se encontro un usuario con ese UID!!!");				
	}
	
	public Group getGroupByName(String name){
		for (int i=0; i<grupos.size(); i++)
			if(grupos.get(i).getNombre().equals(name)){
				return grupos.get(i);
			}
		throw new IllegalArgumentException("No se encontro un grupo con ese nombre!!!");				
	}
	
	public Group getGroupById(int gId){
		for (int i=0; i<grupos.size(); i++)
			if(grupos.get(i).getgID()==gId){
				return grupos.get(i);
			}
		throw new IllegalArgumentException("No se encontro un grupo con ese GID!!!");				
	}
	///
	
	// Crear Usuarios y Grupos
	public void createNewUser(String name, int uId, String password, Path pathToHome,
			String fullName, EnumShell shell, Group mainGroup, Group[] secundaryGroups){
		
		for(User u : usuarios){
			if(u.getNombre().equals(name) || u.getuId() == uId)
				throw new IllegalArgumentException();
		}
		
		String pathToHomeSt = pathToHome.toString();
		
		User user = new User(name, password,pathToHomeSt, uId, shell, fullName, 
				mainGroup.getgID(), null, this);
		
		user.setGrupoPrincipal(mainGroup);
		
		ArrayList<Group> secundaryGroupsAL = new ArrayList<Group>();
		
		for(Group g : secundaryGroups){
			if(g.equals(mainGroup))
				throw new IllegalArgumentException("El grupo principal esta en la lista de grupos secundarios");
			secundaryGroupsAL.add(g);
		}
		
		user.setGrupoSecundario(secundaryGroupsAL);
		
		usuarios.add(user);
	}
	
	public void createNewGroup(String name, int gId){
		ArrayList<Integer> idusuarios = new ArrayList<Integer>();
		
		for(Group g: grupos){
			if(g.getNombre().equals(name) || (g.getgID() == gId))
				throw new IllegalArgumentException("El grupo cumple los requisitos de nombre o GID");
		}
		
		Group group = new Group(name, gId, idusuarios, this);
		
		grupos.add(group);
	}
	
	public void addUserToGroup(User user, Group group){
		for(User u : group.getUsuarios()){
			if(u.equals(user))
				throw new IllegalArgumentException("El usuario ya pertenece a este grupo");
		}
		group.addUser(user);
	}
	///

	// Eliminar Usuarios y Grupos
	public void removeUser(User user){
		user.getGrupoPrincipal().removeUserFromSystem(user);
		ArrayList<Group> gruposSecundarios = user.getGrupoSecundario();
		
		for(int i = 0; i < gruposSecundarios.size(); i++)
			gruposSecundarios.get(i).removeUserFromSystem(user);
		
		usuarios.remove(user);
	}
	
	public void removeGroup(Group group){
		assert group.erasable();
		
		for(User u : group.getUsuarios())
			group.removeUserFromGroup(u);
		
		grupos.remove(group);
	}
	///

	public void updateTo(Path file, Path dtd) throws Exception{
		
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
			
			int id = Integer.parseInt(atts.getValue("gid").substring(1,
					atts.getValue("gid").length()));
			
			grupos.add(new Group(atts.getValue("nombre"), id, usuarios, this));
			break;
		}
		
	}
	
	@Override
	public void endDocument() throws SAXException{
		for(int i = 0; i < usuarios.size(); i++){
			User u = usuarios.get(i);
			
			for(int j = 0; j < grupos.size(); j++){
				Group g = grupos.get(j);
				
				if(u.getGrupoPrincipalID() == g.getgID()){
					g.addUser(u);
					u.setGrupoPrincipal(g);
				}
			}
			
			for(int j = 0; j < u.getGrupoSecundarioID().size(); j++){
				int gidSec = u.getGrupoSecundarioID().get(j);
				
				for(int k = 0; k < grupos.size(); k++){
					Group g = grupos.get(k);
					
					if(gidSec == g.getgID()){
						g.addUser(u);
						u.getGrupoSecundario().add(g);
					}
				}
			}
		}
	}
	
	public void warning(SAXParseException e) throws SAXException {
		 System.err.println("Warning: " + e.getMessage());
		 throw new SAXException("Parsing Warning");
	    }
	 
	 public void error(SAXParseException e) throws SAXException {
		 System.err.println("Line " + e.getLineNumber() + ", Column " + e.getColumnNumber() +
		 ". Error: " + e.getMessage());
		 throw new SAXException("Parsing Error");
	 }
	    
	 public void fatalError(SAXParseException e) throws SAXException {
		 System.err.println("Faltal error:" + e.getMessage());
		 throw new SAXException("Parsing fatal error");
	 }
}
