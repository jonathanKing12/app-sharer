package apps.sharer.message;

import static apps.sharer.message.MessageType.ENABLE_USER;

public class EnableMessage implements Message {

	private String username;

	public EnableMessage(String username) {
		this.username = username;
	}

	@Override
	public boolean isLastInConversation() {
		return false;
	}

	@Override
	public MessageType getType() {
		return ENABLE_USER;
	}

	public String getUsername() {
		return username;
	}

}
