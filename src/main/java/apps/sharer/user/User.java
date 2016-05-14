package apps.sharer.user;

import static java.util.Collections.unmodifiableList;

import java.util.List;

import apps.sharer.app.App;

public class User {

	private String ipAddress;
	private int portNumber;
	private String username;
	private List<App> apps;
	private boolean isEnabled;

	User(String username, String ipAddress, int portNumber) {
		this.ipAddress = ipAddress;
		this.portNumber = portNumber;
		this.username = username;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPortNumber() {
		return portNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUserEnabledHost(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public List<App> getApps() {
		return unmodifiableList(apps);
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	public boolean isEnabled() {
		return isEnabled;
	}
}
