package apps.sharer.message.processors;

import static java.util.Arrays.asList;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import apps.sharer.app.App;
import apps.sharer.message.AppsMessage;
import apps.sharer.message.Message;
import apps.sharer.model.Model;
import apps.sharer.user.User;
import apps.sharer.user.UserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class AppsMessageProcessorTest {

	private static final String USER_NAME = "user1";
	private AppsMessageProcessor processor;
	private AppsMessage appsMessage;
	private List<App> apps;
	private User user;

	@Mock
	private Model model;

	@Before
	public void setUp() {
		apps = new ArrayList<>();
		appsMessage = new AppsMessage(USER_NAME, apps);

		user = new UserBuilder().addUsername(USER_NAME).build();
		when(model.getUser(USER_NAME)).thenReturn(user);

		processor = new AppsMessageProcessor(model);
	}

	@Test
	public void shouldUpdateUserWhenThereIsOneAppInMessage() {
		Message response = processor.processMessage(appsMessage);
		assertNull(response);
	}

	@Test
	public void shouldUpdateUserWhenThereAreManyAppsInMessage() {
		apps.add(new App("app1"));
		processor.processMessage(appsMessage);
		verify(model, only()).getUser(USER_NAME);
		assertEquals(apps, user.getApps());
	}

	@Test
	public void shouldUpdateUserWhenThereAreNoAppsInMessage() {
		apps.addAll(asList(new App("app1"), new App("app2")));

		processor.processMessage(appsMessage);
		verify(model, only()).getUser(USER_NAME);
		assertEquals(apps, user.getApps());
	}
}
