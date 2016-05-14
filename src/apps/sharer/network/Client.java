package apps.sharer.network;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import apps.sharer.message.Message;

public class Client {

	private Socket socket;
	private ObjectOutputStream outputStream;
	private ObjectInputStream inputStream;
	private ObjectStreamFactory factory;

	public Client(Socket socket, ObjectStreamFactory factory) {
		this.socket = socket;
		this.factory = factory;
	}

	public void openConnection() throws ServerException {
		try {
			outputStream = factory.getObjectOutputStream(socket);
			inputStream = factory.getObjectInputStream(socket);
		} catch (IOException e) {
			throw new ServerException("failed to open client connection ", e);
		}
	}

	public void sendMessage(Message message) throws ServerException {
		try {
			outputStream.writeObject(message);
		} catch (IOException | NullPointerException e) {
			throw new ServerException("failed to send message to", e);
		}
	}

	public Message receiveMessage() throws ServerException {
		try {
			return (Message) inputStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			throw new ServerException("failed to read message ", e);
		}
	}

	public void closeConnection() {
		close(inputStream);
		close(outputStream);
		close(socket);
	}

	private void close(Closeable closable) {
		try {
			closable.close();
		} catch (IOException e) {
			// TODO log failed to close
		} finally {
			closable = null;
		}
	}
}
