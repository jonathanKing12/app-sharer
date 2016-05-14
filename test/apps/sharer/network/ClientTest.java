package apps.sharer.network;

import static junit.framework.Assert.assertSame;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import apps.sharer.message.Message;

//@RunWith(MockitoJUnitRunner.class)
@RunWith(PowerMockRunner.class)
@PrepareForTest({ Client.class })
public class ClientTest {

	private Client client;

	@Mock
	private Socket socket;

	@Mock
	private ObjectInputStream inputStream;

	@Mock
	private ObjectOutputStream outputStream;

	@Mock
	private ObjectStreamFactory factory;

	@Mock
	private Message message;

	@Before
	public void setUp() throws Exception {
		when(factory.getObjectInputStream(socket)).thenReturn(inputStream);
		when(factory.getObjectOutputStream(socket)).thenReturn(outputStream);
		client = new Client(socket, factory);
	}

	@Test
	public void shouldOpenConnection() throws IOException {
		client.openConnection();

		verify(factory, times(1)).getObjectInputStream(socket);
		verify(factory, times(1)).getObjectOutputStream(socket);
		verifyZeroInteractions(factory);
		verifyZeroInteractions(inputStream);
		verifyZeroInteractions(outputStream);
	}

	@Test
	public void shouldSendMessage() throws IOException {
		client.openConnection();
		client.sendMessage(message);

		verify(outputStream, only()).writeObject(message);
		verifyZeroInteractions(inputStream);
	}

	@Test
	public void shouldReceiveMessage() throws IOException, ClassNotFoundException {
		when(inputStream.readObject()).thenReturn(message);

		client.openConnection();
		Message message = client.receiveMessage();

		assertSame(message, this.message);
		verify(inputStream, only()).readObject();
		verifyZeroInteractions(outputStream);
	}

	@Test
	public void testCloseConnection() throws IOException {
		client.openConnection();
		client.closeConnection();
		verify(inputStream, only()).close();
		verify(outputStream, only()).close();
		verify(socket, only()).close();
	}
}
