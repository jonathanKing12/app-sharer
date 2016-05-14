package apps.sharer.message.processors;

import apps.sharer.message.EnableMessage;
import apps.sharer.message.Message;
import apps.sharer.model.Model;
import apps.sharer.user.User;

public class EnabledMessageProcessor implements MessageProcessor {

	private final Model model;

	public EnabledMessageProcessor(final Model model) {
		this.model = model;
	}

	@Override
	public Message processMessage(final Message message) {
		EnableMessage enableMessage = (EnableMessage) message;
		User user = model.getUser(enableMessage.getUsername());
		user.setUserEnabledHost(true);
		return null;
	}
}
