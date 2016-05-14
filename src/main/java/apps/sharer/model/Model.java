package apps.sharer.model;

import java.util.ArrayList;
import java.util.List;

import apps.sharer.user.User;

public class Model {

	private List<User> users;
	private User hostUser;

	public Model() {
		users = new ArrayList<>();
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void setHostUser(User hostUser) {
		this.hostUser = hostUser;
	}

	public User getHostUser() {
		return hostUser;
	}

	public User getUser(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username)) {
				return user;
			}
		}
		throw new ModelException("The model does not contain user " + username);
	}
}
