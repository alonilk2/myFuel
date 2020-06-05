package control;

import java.util.ArrayList;
import java.util.List;

import Entity.User;

public class UserController {
	private List<User> connectedUsers;
	public UserController() {
		this.connectedUsers = new ArrayList<User>();
	}
	
	public List<User> getConnectedUsers(){
		return this.connectedUsers;
	}

	public User createNewUserInstance(List<String> retobj) {
		User nuser = new User(retobj.get(3), retobj.get(4), retobj.get(0), retobj.get(1), retobj.get(2));
		connectedUsers.add(nuser);
		System.out.println(connectedUsers);
		return nuser;
	}
	
	public boolean cutUserSession(User user) {
		if(connectedUsers.contains(user)) {
			connectedUsers.remove(user);
			return true;
		}
		else return false;
	}
}
