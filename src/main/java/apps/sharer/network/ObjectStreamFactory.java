package apps.sharer.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectStreamFactory {

	public ObjectInputStream getObjectInputStream(Socket socket) throws IOException {
		return new ObjectInputStream(socket.getInputStream());
	}

	public ObjectOutputStream getObjectOutputStream(Socket socket) throws IOException {
		return new ObjectOutputStream(socket.getOutputStream());
	}
}
