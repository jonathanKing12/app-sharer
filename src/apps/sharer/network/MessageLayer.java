package apps.sharer.network;

import apps.sharer.message.Message;
import apps.sharer.message.manager.MessageManager;

public class MessageLayer {

	private MessageManager messageManager;

	public MessageLayer(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public void startConversation(Client client, Message sendMessage) throws ServerException {
		client.sendMessage(sendMessage);

		if (!sendMessage.isLastInConversation()) {
			continueConversation(client);
		}
	}

	public void continueConversation(Client client) throws ServerException {
		Message responseMessage = null;
		do {
			Message receivedMessage = client.receiveMessage();
			responseMessage = messageManager.manageMessage(receivedMessage);

			if (responseMessage == null) {
				return;
			}

			client.sendMessage(responseMessage);

		} while (!responseMessage.isLastInConversation());
	}
}
