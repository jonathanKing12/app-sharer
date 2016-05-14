package apps.sharer.network;

public class ClientHandler implements Runnable {

	private Client client;
	private MessageLayer messageLayer;

	public ClientHandler(Client client, MessageLayer messageLayer) {
		this.client = client;
		this.messageLayer = messageLayer;
	}

	@Override
	public void run() {
		try {
			client.openConnection();
			messageLayer.continueConversation(client);
			client.closeConnection();
		} catch (ServerException e) {
			// TODO LOG error
		}
	}
}
