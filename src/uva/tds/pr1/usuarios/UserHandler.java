package uva.tds.pr1.usuarios;

import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.ws.handler.Handler;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class UserHandler extends DefaultHandler{
	private ArrayList<User> usuarios;
	private ArrayList<Group> grupos;
	
	public UserHandler() {
		super();
		usuarios = new ArrayList<User>();
		grupos = new ArrayList<Group>();
	}
	
	
	public ArrayList<User> getUsuarios() {
		return usuarios;
	}


	public void setUsuarios(ArrayList<User> usuarios) {
		this.usuarios = usuarios;
	}


	public ArrayList<Group> getGrupos() {
		return grupos;
	}


	public void setGrupos(ArrayList<Group> grupos) {
		this.grupos = grupos;
	}
	
	public User getUserByName(String name){
		for (int i=0; i<usuarios.size(); i++)
			if(usuarios.get(i).getName().equals(name)){
				return usuarios.get(i);
			}
		assert false;
		return null;			
	}
	
	public User getUserById(int uId){
		for (int i=0; i<usuarios.size(); i++)
			if(usuarios.get(i).getuID()== uId){
				return usuarios.get(i);
			}
		assert false;
		return null;				
	}
	
	public Group getGroupByName(String name){
		for (int i=0; i<grupos.size(); i++)
			if(grupos.get(i).getName().equals(name)){
				return grupos.get(i);
			}
		assert false;
		return null;				
	}
	
	public Group getGroupById(int gId){
		for (int i=0; i<grupos.size(); i++)
			if(grupos.get(i).getgID()==gId){
				return grupos.get(i);
			}
		assert false;
		return null;			
			
	}
	
	public void createNewUser(String name, int uId, String password, Path pathToHome, String fullName, EnumShell shell,
			Group mainGroup, Group[] secundaryGroups){
		ArrayList<Integer> secundaryGroupsAL = new ArrayList<Integer>();
		for(int i = 0; i<secundaryGroups.length; i++){
			secundaryGroupsAL.add(secundaryGroups[i].getgID());
		}
		
		String pathToHomeSt = pathToHome.toString();
		int mainGroupID =  mainGroup.getgID();
		User user = new User(name, password,pathToHomeSt, uId, shell, fullName, mainGroupID, secundaryGroupsAL);
		
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
					grupoSecundarioID);
			
			usuarios.add(user);
			
			break;

		case "Group":
			break;
		}
		
	}
	
	@Override
	public void endDocument() throws SAXException{
		System.out.println(usuarios);
	}
}
