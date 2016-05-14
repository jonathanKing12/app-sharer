package apps.sharer.message.manager;

import java.util.Map;

import apps.sharer.message.Message;
import apps.sharer.message.MessageType;
import apps.sharer.message.processors.MessageProcessor;

public class MessageManager {

	private Map<MessageType, MessageProcessor> messageProcessors;

	public MessageManager(Map<MessageType, MessageProcessor> messageProcessors) {
		this.messageProcessors = messageProcessors;
	}

	public Message manageMessage(Message message) {
		messageProcessors.get(message.getType());
		return null;
	}
}
