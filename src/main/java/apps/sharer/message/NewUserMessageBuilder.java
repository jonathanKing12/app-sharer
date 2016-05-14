package apps.sharer.message;

import apps.sharer.user.UserBuilder;

public class NewUserMessageBuilder {

	private String ipAddress;
	private int portNumber;
	private String username;
	private boolean isLastInConversation;

	public NewUserMessageBuilder addUsername(String username) {
		this.username = username;
		return this;

	}

	public NewUserMessageBuilder addIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;

	}

	public NewUserMessageBuilder addPortNumber(int portNumber) {
		this.portNumber = portNumber;
		return this;
	}

	public NewUserMessageBuilder addIsLastInConversation(boolean isLastInConversation) {
		this.isLastInConversation = isLastInConversation;
		return this;
	}

	public NewUserMessage build() {
		return new NewUserMessage(username, ipAddress, portNumber, isLastInConversation);
	}

}
