package apps.sharer.network;

import java.net.Socket;

public class ClientFactory {

	public Client getClient(Socket socket) {
		ObjectStreamFactory streamFactory = new ObjectStreamFactory();
		return new Client(socket, streamFactory);
	}

}
