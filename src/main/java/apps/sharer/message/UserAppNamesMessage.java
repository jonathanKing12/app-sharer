package apps.sharer.message;

import static apps.sharer.message.MessageType.APPS_NAME;

import java.util.List;

public class UserAppNamesMessage implements Message {

	private List<String> names;

	public UserAppNamesMessage(List<String> names) {
		this.names = names;
	}

	@Override
	public boolean isLastInConversation() {
		return false;
	}

	@Override
	public MessageType getType() {
		return APPS_NAME;
	}

	public List<String> getNames() {
		return names;
	}
}
