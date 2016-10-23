package uva.tds.pr1.usuarios;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;


public class UserSystemImpl implements UserSystemInterface {
	private UserHandler myHandler;
	private boolean isModify;
	
	public UserSystemImpl() {super();}
	
	@Override
	public void loadFrom(Path pathToXML) {
		FileReader input;
		InputSource source;
		XMLReader myXML;
		
		try{
			input = new FileReader(pathToXML.toFile());
			source = new InputSource(input);
			myXML = XMLReaderFactory.createXMLReader();
			
			UserHandler handler = new UserHandler();
			
			myXML.setContentHandler(handler);
			myXML.parse(source);
			
		} catch (Exception e) {
			System.err.println(e);
		}
		
		isModify = false;
	}

	@Override
	public void updateTo(Path pathToXML) {
		// TODO Auto-generated method stub
		
		
		isModify = false;
	}

	@Override
	public boolean isXMLLoaded() {
		if(myHandler != null)
			return true;
		return false;
	}

	@Override
	public boolean isModifiedAfterLoaded() {
		
		return isModify;
	}

	@Override
	public void createNewUser(String name, int uId, String password, Path pathToHome, String fullName, EnumShell shell,
			Group mainGroup, Group[] secundaryGroups) {
		// TODO Auto-generated method stub
		
		isModify = true;
	}

	@Override
	public User getUserById(int uId) {
		// TODO Auto-generated method stub
		
		isModify = true;
		return null;
	}

	@Override
	public User getUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Group getGroupById(int gId) {
		// TODO Auto-generated method stub
		
		isModify = true;
		return null;
	}

	@Override
	public Group getGroupByName(String name) {
		// TODO Auto-generated method stub
		
		isModify = true;
		return null;
	}

	@Override
	public void createNewGroup(String name, int gId) {
		// TODO Auto-generated method stub
		
		isModify = true;
	}

	@Override
	public void addUserToGroup(User user, Group group) {
		
		// TODO Auto-generated method stub
		
		isModify = true;
	}

	@Override
	public void removeUserFromGroup(User user, Group group) {
		
		group.removeUserFromGroup(user);
		isModify = true;
	}

	@Override
	public void removeUserFromSystem(User user) {

		myHandler.removeUser(user);
		isModify = true;
	}

	@Override
	public void removeGroupFromSystem(Group group) {
		assert(!group.getUsuarios().isEmpty());
		myHandler.removeGroup(group);
		isModify = true;
	}

}
