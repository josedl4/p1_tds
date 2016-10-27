package uva.tds.pr1.usuarios;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class UserSystemImpl implements UserSystemInterface {
	private UserHandler myHandler;
	private boolean isModify;
	
	static final Path PATH_DTD = new File("usuariosUnix.dtd").toPath();
	
	public UserSystemImpl() {super();}
	
	@Override
	public void loadFrom(Path pathToXML) {
		
		try{
			SAXParserFactory factory = SAXParserFactory.newInstance();
			
			factory.setValidating(true);
			factory.setNamespaceAware(true);;
			
			SAXParser parser = factory.newSAXParser();
			
			DefaultHandler handler = new UserHandler();
			XMLReader reader = parser.getXMLReader();
			//reader.parse(new InputSource(pathToXML.toString()));
			parser.parse(new InputSource(pathToXML.toString()), handler);
			
			myHandler = (UserHandler) handler;
			
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		
		isModify = false;
	}

	@Override
	public void updateTo(Path pathToXML) {
		try{
			myHandler.updateTo(pathToXML, PATH_DTD);
		}catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}
		isModify = false;
		myHandler = null;
	}

	@Override
	public boolean isXMLLoaded() {
		if(myHandler != null)
			return true;
		return false;
	}

	@Override
	public boolean isModifiedAfterLoaded() {
		assert(isXMLLoaded());
		return isModify;
	}

	@Override
	public void createNewUser(String name, int uId, String password, Path pathToHome, String fullName, EnumShell shell,
			Group mainGroup, Group[] secundaryGroups) {
		assert(isXMLLoaded());
		myHandler.createNewUser(name, uId, password, pathToHome, fullName, shell, mainGroup, secundaryGroups);
		isModify = true;
	}

	@Override
	public User getUserById(int uId) {
		assert(isXMLLoaded());
		isModify = true;
		return myHandler.getUserById(uId);
	}

	@Override
	public User getUserByName(String name) {
		assert(isXMLLoaded());
		isModify = true;
		return myHandler.getUserByName(name);
	}

	@Override
	public Group getGroupById(int gId) {
		assert(isXMLLoaded());
		isModify = true;
		return myHandler.getGroupById(gId);
	}

	@Override
	public Group getGroupByName(String name) {
		assert(isXMLLoaded());
		isModify = true;
		return myHandler.getGroupByName(name);
	}

	@Override
	public void createNewGroup(String name, int gId) {
		assert(isXMLLoaded());
		myHandler.createNewGroup(name, gId);
		isModify = true;
	}

	@Override
	public void addUserToGroup(User user, Group group) {
		assert(isXMLLoaded());
		isModify = true;
		myHandler.addUserToGroup(user, group);
		
	}

	@Override
	public void removeUserFromGroup(User user, Group group) {
		assert(isXMLLoaded());
		group.removeUserFromGroup(user);
		isModify = true;
	}

	@Override
	public void removeUserFromSystem(User user) {
		assert(isXMLLoaded());
		myHandler.removeUser(user);
		isModify = true;
	}

	@Override
	public void removeGroupFromSystem(Group group) {
		assert(isXMLLoaded());
		myHandler.removeGroup(group);
		isModify = true;
	}

}
