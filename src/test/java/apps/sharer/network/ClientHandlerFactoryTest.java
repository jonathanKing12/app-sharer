package apps.sharer.network;

import static junit.framework.Assert.assertNotSame;
import static org.junit.Assert.assertNotNull;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

public class ClientHandlerFactoryTest {

	private ClientHandlerFactory factory;

	@Mock
	private MessageLayer messageLayer;

	@Mock
	private Client client;

	@Before
	public void setUp() {
		factory = new ClientHandlerFactory(messageLayer);
	}

	@Test
	public void shouldGetOneClienHandlerThreads() {
		Thread thread = factory.getClientHandlerThread(client);
		assertNotNull(thread);
	}

	@Test
	public void shouldGetTwoClienHandlerThreads() {
		Thread thread1 = factory.getClientHandlerThread(client);
		Thread thread2 = factory.getClientHandlerThread(client);
		assertNotNull(thread1);
		assertNotNull(thread2);
		assertNotSame(thread1, thread2);
	}
}
