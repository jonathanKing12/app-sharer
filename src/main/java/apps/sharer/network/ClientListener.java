package apps.sharer.network;

public class ClientListener {

	private Server server;
	private ClientHandlerFactory factory;

	public ClientListener(Server server, ClientHandlerFactory clientHandlerFactory) {

		this.server = server;
		this.factory = clientHandlerFactory;
	}

	public void startListening() {
		while (server.isRunning()) {
			try {
				Client client = server.getNextClient();
				Thread thread = factory.getClientHandlerThread(client);
				thread.start();
			} catch (ServerException e) {
				// TODO log error message
			}
		}
	}
}
