package apps.sharer.message.processors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import apps.sharer.message.Message;
import apps.sharer.message.NewUserMessage;
import apps.sharer.message.NewUserMessageBuilder;
import apps.sharer.model.Model;
import apps.sharer.user.User;
import apps.sharer.user.UserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class NewUserMessageProcessorTest {

	private static final String USERNAME = "username1";
	private static final String IP_ADDRESS = "192.168.0.12";
	private static final int PORT_NUMBER = 10;
	private static final String HOST_USERNAME = "host username";

	private NewUserMessageProcessor processor;
	private NewUserMessage newUserMessage;

	@Mock
	private Model model;

	@Before
	public void setUp() {
		createNewUserMessageThatIsNotLastInConversation();
		processor = new NewUserMessageProcessor(model);
	}

	private NewUserMessage createNewUserMessageThatIsNotLastInConversation() {
		return new NewUserMessageBuilder().addUsername(USERNAME).addIpAddress(IP_ADDRESS)
				.addPortNumber(PORT_NUMBER).build();
	}

	private NewUserMessage createNewUserMessageThatIsLastInConversation() {
		return new NewUserMessageBuilder().addUsername(USERNAME).addIpAddress(IP_ADDRESS)
				.addPortNumber(PORT_NUMBER).addIsLastInConversation(true).build();
	}

	@Test
	public void shouldCreateUserWhenProcessNewUserMessageTest() {
		newUserMessage = createNewUserMessageThatIsLastInConversation();
		processor.processMessage(newUserMessage);

		ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);
		verify(model, times(1)).addUser(argument.capture());

		User user = argument.getValue();
		assertEquals(IP_ADDRESS, user.getIpAddress());
		assertEquals(PORT_NUMBER, user.getPortNumber());
		assertEquals(USERNAME, user.getUsername());
	}

	@Test
	public void shouldReturnNewUserMessageWhenProcessNewUserMessageThatIsNotLastInConversation() {
		newUserMessage = createNewUserMessageThatIsNotLastInConversation();
		User hostUser = new UserBuilder().addUsername(HOST_USERNAME).build();
		when(model.getHostUser()).thenReturn(hostUser);

		Message response = processor.processMessage(newUserMessage);

		NewUserMessage newUserMessage = (NewUserMessage) response;
		assertEquals(HOST_USERNAME, newUserMessage.getUsername());
		verify(model, times(1)).getHostUser();
	}

	@Test
	public void shouldReturnNullAfterProcessingNewUserMessageTest() {
		newUserMessage = createNewUserMessageThatIsLastInConversation();
		Message message = processor.processMessage(newUserMessage);
		assertNull(message);
	}
}
