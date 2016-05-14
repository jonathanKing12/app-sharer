package apps.sharer.message;

import static apps.sharer.message.MessageType.NEW_USER;

public class NewUserMessage implements Message {

	private String ipAddress;
	private int portnumber;
	private String username;
	private boolean isLastInConversation;

	public NewUserMessage(String username, String ipAddress, int portnumber,
			boolean isLastInConversation) {
		this.ipAddress = ipAddress;
		this.portnumber = portnumber;
		this.username = username;
		this.isLastInConversation = isLastInConversation;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public int getPortNumber() {
		return portnumber;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public boolean isLastInConversation() {
		return isLastInConversation;
	}

	@Override
	public MessageType getType() {
		return NEW_USER;
	}
}
