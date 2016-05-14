package apps.sharer.message.processors;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import apps.sharer.message.EnableMessage;
import apps.sharer.message.Message;
import apps.sharer.model.Model;
import apps.sharer.user.User;
import apps.sharer.user.UserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class EnabledMessageProcessorTest {

	private static final String ENABLED_USERNAME = "enabledUsername";

	private MessageProcessor processor;
	private EnableMessage message;
	private User enabeledUser;

	@Mock
	private Model model;

	@Before
	public void setUp() {

		enabeledUser = new UserBuilder().addUsername(ENABLED_USERNAME).build();
		when(model.getUser(ENABLED_USERNAME)).thenReturn(enabeledUser);

		processor = new EnabledMessageProcessor(model);
		message = new EnableMessage(ENABLED_USERNAME);
	}

	@Test
	public void shouldReturnNullAfterProcessedMessageTest() {
		Message response = processor.processMessage(message);
		assertNull(response);
	}

	@Test
	public void shouldUpdateUserWhenModelHasOneUser() {
		processor.processMessage(message);
		assertTrue(enabeledUser.isEnabled());
		verify(model, only()).getUser(ENABLED_USERNAME);
	}
}
