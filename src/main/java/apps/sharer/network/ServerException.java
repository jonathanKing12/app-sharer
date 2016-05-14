package apps.sharer.network;

import java.io.IOException;

public class ServerException extends IOException {

	public ServerException(String message, Exception e) {
		super(message, e);
	}

}
