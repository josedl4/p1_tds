package uva.tds.pr1.usuarios;

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
			ArrayList<Integer> usuarios = new ArrayList<Integer>(0);
			
			if(atts.getValue("idusuarios") != null){
				String[] usuariosID = atts.getValue("idusuarios").split(" ");
				
				for(int i = 0; i < usuariosID.length; i++)
					usuarios.add(Integer.parseInt(usuariosID[i].substring(1,
							usuariosID[i].length())));
			}
			
			int id = Integer.parseInt(atts.getValue("gid").substring(1,
					atts.getValue("gid").length()));
			
			grupos.add(new Group(atts.getValue("nombre"), id, usuarios));
			break;
		}
		
	}
	
	@Override
	public void endDocument() throws SAXException{
		System.out.println(usuarios);
		System.out.println(grupos);
		
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
					
					if(gidSec == g.getgID())
						g.addUser(u);
						u.getGrupoSecundario().add(g);
				}
			}
		}
		
		
		for(Group g : grupos) {
			System.out.println(g.toString());
			for(User u : g.getUsuarios())
				System.out.println(u.getNombre());
		}
	}
	
	public void removeUser(User user){
		user.getGrupoPrincipal().removeUserFromGroup(user);
		ArrayList<Group> gruposSecundarios = user.getGrupoSecundario();
		
		for(int i = 0; i < gruposSecundarios.size(); i++)
			gruposSecundarios.get(i).removeUserFromGroup(user);
		
		usuarios.remove(user);
	}
	
	public void removeGroup(Group group){
		assert group.erasable();
		
		for(User u : group.getUsuarios())
			group.removeUserFromGroup(u);
		
		grupos.remove(group);
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
}
