package apps.sharer.network;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.net.ServerSocketFactory;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ServerTest {

	private Server server;

	@Mock
	private ServerSocketFactory factory;

	@Mock
	private ClientFactory clientFactory;

	@Mock
	private ServerSocket serverSocket;

	@Mock
	private Client mockedClient1;

	@Mock
	private Client mockedClient2;

	@Before
	public void setup() {
		server = new Server(factory, clientFactory);
	}

	@Test
	public void ShouldBeRunningWhenStarted() throws IOException {
		server.start();
		assertTrue(server.isRunning());
		verify(factory, only()).createServerSocket(0);
	}

	@Test
	public void ShouldNotBeRunningWhenNotStarted() throws IOException {
		assertFalse(server.isRunning());
		verifyZeroInteractions(factory);
	}

	@Test
	public void ShouldAcceptOneClient() throws IOException {
		when(factory.createServerSocket(0)).thenReturn(serverSocket);
		when(clientFactory.getClient(Mockito.<Socket> any())).thenReturn(mockedClient1);
		server.start();
		Client client = server.getNextClient();

		verify(factory, only()).createServerSocket(0);
		verify(serverSocket, only()).accept();
		assertSame(client, mockedClient1);
	}

	@Test
	public void ShouldAcceptTwoClients() throws IOException {
		when(factory.createServerSocket(0)).thenReturn(serverSocket);
		when(clientFactory.getClient(Mockito.<Socket> any())).thenReturn(mockedClient1).thenReturn(
				mockedClient2);

		server.start();
		Client client1 = server.getNextClient();
		Client client2 = server.getNextClient();

		verify(factory, only()).createServerSocket(0);
		verify(serverSocket, times(2)).accept();
		verifyNoMoreInteractions(serverSocket);
		assertSame(client1, mockedClient1);
		assertSame(client2, mockedClient2);
	}

	@Test
	public void shouldNotBeRunningWhenStopped() throws IOException {
		when(factory.createServerSocket(0)).thenReturn(serverSocket);
		server.start();
		server.stop();
		assertFalse(server.isRunning());
		verify(serverSocket, times(1)).close();
	}

	@Test(expected = ServerException.class)
	public void shouldNotAcceptClientWhenServerSocketIsNull() throws IOException {
		server.getNextClient();
	}

	@Test(expected = ServerException.class)
	public void shouldNotAcceptClientWhenIOExceptionThrown() throws IOException {
		when(factory.createServerSocket(0)).thenReturn(serverSocket);
		when(serverSocket.accept()).thenThrow(new IOException());
		server.start();
		server.getNextClient();
	}

	@Test(expected = ServerException.class)
	public void shouldNotStartServerWhenIOExceptionThrown() throws IOException {
		when(factory.createServerSocket(0)).thenThrow(new IOException());
		server.start();
	}

	@Test
	public void shouldStopServerWhenIOExceptionThrown() throws IOException {
		when(factory.createServerSocket(0)).thenReturn(serverSocket);
		doThrow(new IOException()).when(serverSocket).close();
		server.start();
		server.stop();
		assertFalse(server.isRunning());
	}
}
