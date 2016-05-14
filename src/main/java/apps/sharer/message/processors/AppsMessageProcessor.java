package apps.sharer.message.processors;

import apps.sharer.message.AppsMessage;
import apps.sharer.message.Message;
import apps.sharer.model.Model;
import apps.sharer.user.User;

public class AppsMessageProcessor implements MessageProcessor {

	private final Model model;

	public AppsMessageProcessor(final Model model) {
		this.model = model;
	}

	@Override
	public Message processMessage(final Message message) {
		AppsMessage appsMessage = (AppsMessage) message;
		String username = appsMessage.getUsername();
		User user = model.getUser(username);
		user.setApps(appsMessage.getApps());
		return null;
	}

}
