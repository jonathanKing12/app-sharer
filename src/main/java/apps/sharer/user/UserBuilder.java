package apps.sharer.user;

public class UserBuilder {

	private String ipAddress;
	private int portNumber;
	private String username;

	public UserBuilder addUsername(String username) {
		this.username = username;
		return this;

	}

	public UserBuilder addIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;

	}

	public UserBuilder addPortNumber(int portNumber) {
		this.portNumber = portNumber;
		return this;
	}

	public User build() {
		return new User(username, ipAddress, portNumber);
	}
}
