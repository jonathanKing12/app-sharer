package apps.sharer.message.processors;

import apps.sharer.message.Message;

public interface MessageProcessor {

	Message processMessage(Message message);

}
