package apps.sharer.network;

public class ClientHandlerFactory {

	private MessageLayer messageLayer;

	public ClientHandlerFactory(MessageLayer messageLayer) {
		this.messageLayer = messageLayer;
	}

	public Thread getClientHandlerThread(Client client) {
		return new Thread(new ClientHandler(client, messageLayer));
	}
}
