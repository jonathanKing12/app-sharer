package apps.sharer.message;

import static apps.sharer.message.MessageType.APPS;

import java.util.List;

import apps.sharer.app.App;

public class AppsMessage implements Message {

	private String userName;
	private List<App> apps;

	public AppsMessage(String userName, List<App> apps) {
		this.userName = userName;
		this.apps = apps;
	}

	@Override
	public boolean isLastInConversation() {
		return true;
	}

	@Override
	public MessageType getType() {
		return APPS;
	}

	public List<App> getApps() {
		return apps;
	}

	public String getUsername() {
		return userName;
	}
}
