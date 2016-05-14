package apps.sharer.message.processors;

import static apps.sharer.message.MessageType.APPS;
import static apps.sharer.message.MessageType.ENABLE_USER;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import apps.sharer.message.EnableMessage;
import apps.sharer.message.MessageType;
import apps.sharer.message.manager.MessageManager;

@RunWith(MockitoJUnitRunner.class)
public class MessageProcessorTest {

	private MessageManager messageManager;

	@Spy
	private HashMap<MessageType, MessageProcessor> messageProcessors;

	@Mock
	private EnabledMessageProcessor enabledMessageProcessor;

	@Mock
	private AppsMessageProcessor appsMessageProcessor;

	@Before
	public void setUp() {
		messageManager = new MessageManager(messageProcessors);
	}

	@Test
	public void shouldSelectEnabledMessageProcessorToProcessEnabledMessage() {
		messageProcessors.put(ENABLE_USER, enabledMessageProcessor);
		messageManager.manageMessage(new EnableMessage("user1"));
		verify(messageProcessors, times(1)).get(ENABLE_USER);
	}

	@Test
	public void shouldNotProcessEnabledMessagesWithAppsMessageProcessor() {
		messageProcessors.put(ENABLE_USER, enabledMessageProcessor);
		messageProcessors.put(APPS, appsMessageProcessor);
		messageManager.manageMessage(new EnableMessage("user1"));

		verify(messageProcessors, times(1)).get(ENABLE_USER);
		verifyNoMoreInteractions(appsMessageProcessor);
	}
}
