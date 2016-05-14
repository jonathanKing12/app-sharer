package apps.sharer.message.processors;

import apps.sharer.message.Message;
import apps.sharer.message.NewUserMessage;
import apps.sharer.message.NewUserMessageBuilder;
import apps.sharer.model.Model;
import apps.sharer.user.User;
import apps.sharer.user.UserBuilder;

public class NewUserMessageProcessor implements MessageProcessor {

	private final Model model;

	public NewUserMessageProcessor(final Model model) {
		this.model = model;
	}

	@Override
	public Message processMessage(final Message message) {
		NewUserMessage newUserMessage = (NewUserMessage) message;
		addNewUserToModel(newUserMessage);

		if (!newUserMessage.isLastInConversation()) {
			return createNewUserMessageWithHostUserDetails();
		}
		return null;
	}

	private void addNewUserToModel(final NewUserMessage newUserMessage) {
		User user = createUser(newUserMessage);
		model.addUser(user);
	}

	private User createUser(final NewUserMessage message) {
		String username = message.getUsername();
		String ipAddress = message.getIpAddress();
		int portNumber = message.getPortNumber();

		return new UserBuilder().addUsername(username).addIpAddress(ipAddress)
				.addPortNumber(portNumber).build();
	}

	private NewUserMessage createNewUserMessageWithHostUserDetails() {
		User user = model.getHostUser();
		String username = user.getUsername();
		String ipAddress = user.getIpAddress();
		int portNumber = user.getPortNumber();

		return new NewUserMessageBuilder().addUsername(username).addIpAddress(ipAddress)
				.addPortNumber(portNumber).build();
	}
}
