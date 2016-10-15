package uva.tds.pr1.usuarios;

import java.nio.file.Path;

public interface UserSystemInterface {
	void loadFrom(Path pathToXML);
	
	void updateTo(Path pathToXML);

	boolean isXMLLoaded();

	boolean isModifiedAfterLoaded();

	void createNewUser(String name, int uId, String password, 
			Path pathToHome, String fullName, EnumShell shell, 
			Group mainGroup, Group[] secundaryGroups);
	
	User getUserById(int uId);

	User getUserByName(String name);
	
	Group getGroupById(int gId);
	
	Group getGroupByName(String name);
	
	void createNewGroup(String name, int gId);
	
	void addUserToGroup(User user, Group group);
	
	void removeUserFromGroup(User user, Group group);
	
	void removeUserFromSystem(User user);
	
	void removeGroupFromSystem(Group group);
	
}

