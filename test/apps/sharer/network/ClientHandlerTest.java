package apps.sharer.network;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClientHandlerTest {

	@Mock
	private Client client;

	@Mock
	private MessageLayer messageLayer;

	@Test
	public void shouldHanldeClient() throws ServerException {
		ClientHandler clientHandler = new ClientHandler(client, messageLayer);
		clientHandler.run();
		verify(messageLayer, only()).continueConversation(client);
		verify(client, times(1)).openConnection();
		verify(client, times(1)).closeConnection();
		verifyNoMoreInteractions(client);

	}
}
