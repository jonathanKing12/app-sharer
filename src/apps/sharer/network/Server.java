package apps.sharer.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

public class Server {

	private ServerSocketFactory factory;
	private boolean isRunning;
	private ServerSocket serverSocket;
	private ClientFactory clientFactory;

	public Server(ServerSocketFactory serverSocketFactory, ClientFactory clientFactory) {
		this.factory = serverSocketFactory;
		this.clientFactory = clientFactory;
	}

	public void start() throws ServerException {
		try {
			serverSocket = factory.createServerSocket(0);
			isRunning = true;
		} catch (IOException e) {
			throw new ServerException("failed to start server", e);
		}
	}

	public boolean isRunning() {
		return isRunning;
	}

	public Client getNextClient() throws ServerException {
		Socket socket;
		try {
			socket = serverSocket.accept();
		} catch (NullPointerException | IOException e) {
			throw new ServerException("failed to get next client", e);
		}
		return clientFactory.getClient(socket);
	}

	public void stop() {
		try {
			serverSocket.close();
		} catch (IOException e) {
			// TODO log error message
		} finally {
			isRunning = false;
			serverSocket = null;
		}
	}
}
