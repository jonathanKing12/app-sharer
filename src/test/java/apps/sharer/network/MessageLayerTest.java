package apps.sharer.network;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import apps.sharer.message.Message;
import apps.sharer.message.manager.MessageManager;

@RunWith(MockitoJUnitRunner.class)
public class MessageLayerTest {

	private MessageLayer messageLayer;

	@Mock
	private Message middleMessage;

	@Mock
	private Message endMessage;

	@Mock
	private Client client;

	@Mock
	private MessageManager messageManager;

	@Before
	public void setup() {
		when(middleMessage.isLastInConversation()).thenReturn(false);
		when(endMessage.isLastInConversation()).thenReturn(true);
		messageLayer = new MessageLayer(messageManager);
	}

	@Test
	public void sendMessageTest() throws ServerException {
		messageLayer.startConversation(client, endMessage);
		verify(client, times(1)).sendMessage(endMessage);
		verify(client, never()).receiveMessage();
		verify(messageManager, never()).manageMessage(Mockito.<Message> anyObject());
	}

	@Test
	public void sendAndReceiveMessageTest() throws ServerException {
		when(client.receiveMessage()).thenReturn(endMessage);
		messageLayer.startConversation(client, middleMessage);

		verify(client, times(1)).sendMessage(middleMessage);
		verify(client, times(1)).receiveMessage();
		verify(messageManager, times(1)).manageMessage(endMessage);
	}

	@Test
	public void sendReceiveAndSendAgainMessagesTest() throws ServerException {
		when(client.receiveMessage()).thenReturn(middleMessage);
		when(messageManager.manageMessage(middleMessage)).thenReturn(endMessage);

		messageLayer.startConversation(client, middleMessage);

		verify(client, times(1)).sendMessage(middleMessage);
		verify(client, times(1)).receiveMessage();
		verify(messageManager, times(1)).manageMessage(middleMessage);
		verify(client, times(1)).sendMessage(endMessage);
	}

	@Test
	public void startConversationReceivingLastMessageTest() throws ServerException {
		when(client.receiveMessage()).thenReturn(middleMessage).thenReturn(middleMessage)
				.thenReturn(endMessage);

		when(messageManager.manageMessage(middleMessage)).thenReturn(middleMessage).thenReturn(
				middleMessage);

		messageLayer.startConversation(client, middleMessage);

		verify(client, times(3)).sendMessage(middleMessage);
		verify(client, times(3)).receiveMessage();
		verify(messageManager, times(2)).manageMessage(middleMessage);
		verify(messageManager, times(1)).manageMessage(endMessage);
		verify(client, never()).sendMessage(endMessage);
	}

	@Test
	public void startConversationSendLastMessageTest() throws ServerException {
		when(client.receiveMessage()).thenReturn(middleMessage).thenReturn(middleMessage)
				.thenReturn(middleMessage);

		when(messageManager.manageMessage(middleMessage)).thenReturn(middleMessage)
				.thenReturn(middleMessage).thenReturn(endMessage);

		messageLayer.startConversation(client, middleMessage);

		verify(client, times(3)).sendMessage(middleMessage);
		verify(client, times(3)).receiveMessage();
		verify(messageManager, times(3)).manageMessage(middleMessage);
		verify(client, times(1)).sendMessage(endMessage);
	}

	@Test
	public void receiveMessageTest() throws ServerException {
		messageLayer.continueConversation(client);
		verify(client, times(1)).receiveMessage();
		verify(client, never()).sendMessage(Mockito.<Message> anyObject());
	}

	@Test
	public void receiveAndSendMessageTest() throws ServerException {
		when(client.receiveMessage()).thenReturn(middleMessage);
		when(messageManager.manageMessage(middleMessage)).thenReturn(endMessage);

		messageLayer.continueConversation(client);

		verify(client, times(1)).receiveMessage();
		verify(messageManager, times(1)).manageMessage(middleMessage);
		verify(client, times(1)).sendMessage(endMessage);
		verify(client, never()).sendMessage(middleMessage);
	}

	@Test
	public void receiveSendAndReceiveAgainMessagesTest() throws ServerException {
		when(client.receiveMessage()).thenReturn(middleMessage).thenReturn(endMessage);
		when(messageManager.manageMessage(middleMessage)).thenReturn(middleMessage);

		messageLayer.continueConversation(client);

		verify(client, times(2)).receiveMessage();
		verify(messageManager, times(1)).manageMessage(middleMessage);
		verify(client, times(1)).sendMessage(middleMessage);
		verify(messageManager, times(1)).manageMessage(endMessage);
		verify(client, never()).sendMessage(endMessage);
	}

	@Test
	public void continueConversationRecevieLastMessageTest() throws ServerException {
		when(client.receiveMessage()).thenReturn(middleMessage).thenReturn(middleMessage)
				.thenReturn(middleMessage).thenReturn(endMessage);

		when(messageManager.manageMessage(middleMessage)).thenReturn(middleMessage)
				.thenReturn(middleMessage).thenReturn(middleMessage);

		messageLayer.continueConversation(client);

		verify(client, times(3)).sendMessage(middleMessage);
		verify(client, times(4)).receiveMessage();
		verify(messageManager, times(3)).manageMessage(middleMessage);
		verify(messageManager, times(1)).manageMessage(endMessage);
		verify(client, never()).sendMessage(endMessage);

	}
}
