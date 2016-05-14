package apps.sharer.network;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientListenerTest {

	private ClientListener clientListener;

	@Mock
	private Server server;

	@Mock
	private Client client;

	@Mock
	private ClientHandlerFactory factory;

	@Mock
	private Thread thread;

	@Before
	public void setup() {
		clientListener = new ClientListener(server, factory);
	}

	@Test
	public void shouldListenToOneClientConnection() throws ServerException {
		when(server.isRunning()).thenReturn(true).thenReturn(false);
		when(server.getNextClient()).thenReturn(client);
		when(factory.getClientHandlerThread(client)).thenReturn(thread);

		clientListener.startListening();

		verify(server, times(1)).getNextClient();
		verify(factory, only()).getClientHandlerThread(client);
		verify(thread, times(1)).start();
	}

	@Test
	public void shouldListenToThreeClientConnection() throws ServerException {
		when(server.isRunning()).thenReturn(true).thenReturn(true).thenReturn(true)
				.thenReturn(false);
		when(server.getNextClient()).thenReturn(client).thenReturn(client).thenReturn(client);
		when(factory.getClientHandlerThread(client)).thenReturn(thread).thenReturn(thread)
				.thenReturn(thread);

		clientListener.startListening();

		verify(server, times(3)).getNextClient();
		verify(factory, times(3)).getClientHandlerThread(client);
		verify(thread, times(3)).start();
	}

	@Test
	public void shouldNotListenToAnyClientConnection() throws ServerException {
		when(server.isRunning()).thenReturn(false);
		when(server.getNextClient()).thenReturn(client).thenReturn(client);

		clientListener.startListening();

		verify(server, never()).getNextClient();
		verify(factory, never()).getClientHandlerThread(Mockito.<Client> any());
	}

}
