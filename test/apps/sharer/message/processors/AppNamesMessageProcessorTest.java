package apps.sharer.message.processors;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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
import apps.sharer.message.UserAppNamesMessage;
import apps.sharer.model.Model;
import apps.sharer.user.User;
import apps.sharer.user.UserBuilder;

@RunWith(MockitoJUnitRunner.class)
public class AppNamesMessageProcessorTest {

	private static final String HOST_USERNAME = "hostname1";

	private AppNamesMessageProcessor processor;

	private UserAppNamesMessage message;

	@Mock
	private Model model;

	private User hostUser;

	private App app1;
	private App app2;

	private List<App> hostApps;

	private ArrayList<String> names;

	@Before
	public void setup() {
		hostApps = new ArrayList<>();
		app1 = new App("app1");
		app2 = new App("app2");

		hostUser = new UserBuilder().addUsername(HOST_USERNAME).build();
		hostUser.setApps(hostApps);

		when(model.getHostUser()).thenReturn(hostUser);
		processor = new AppNamesMessageProcessor(model);

		names = new ArrayList<String>();
		message = new UserAppNamesMessage(names);
	}

	@Test
	public void shouldReturnAppMessageAfterProcessingAppNameMessage() {
		Message response = processMessage(message);
		assertNotNull(response);
	}

	@Test
	public void shouldGetNoAppsWhenHostAndUserHaveSameApps() {
		AppsMessage response = processMessage(message);
		assertThat(response.getApps().isEmpty(), is(true));
	}

	@Test
	public void shouldGetOneApp() {
		hostApps.add(app1);

		AppsMessage response = processMessage(message);
		List<App> apps = response.getApps();
		assertThat(apps.size(), is(1));

		assertThat(apps, hasItem(app1));
	}

	@Test
	public void shouldGetTwoApps() {
		hostApps.addAll(asList(app1, app2));

		AppsMessage response = processMessage(message);
		List<App> apps = response.getApps();
		assertThat(apps.size(), is(2));

		assertThat(apps, hasItems(app1, app2));
	}

	@Test
	public void shouldGetLastAppUserDoesNotHave() {
		names.add("app1");
		hostApps.addAll(asList(app1, app2));

		AppsMessage response = processMessage(message);
		List<App> apps = response.getApps();
		assertThat(apps.size(), is(1));

		assertThat(apps, hasItem(app2));
	}

	@Test
	public void shouldGetFirstAppUserDoesNotHave() {
		names.add("app2");
		hostApps.addAll(asList(app1, app2));

		AppsMessage response = processMessage(message);
		List<App> apps = response.getApps();
		assertThat(apps.size(), is(1));

		assertThat(apps, hasItem(app1));
	}

	@Test
	public void shouldGetNoApps() {
		names.add("app1");
		names.add("app2");
		hostApps.addAll(asList(app1, app2));

		AppsMessage response = processMessage(message);
		List<App> apps = response.getApps();
		assertThat(apps.size(), is(0));
	}

	@Test
	public void shouldGetManyAppsUserDoesNotHave() {
		names.addAll(asList("app0", "ap1.5", "ap1.7"));
		hostApps.addAll(asList(new App("app0"), app1, new App("ap1.5"), new App("ap1.7"), app2));

		AppsMessage response = processMessage(message);
		List<App> apps = response.getApps();
		assertThat(apps.size(), is(2));
		assertThat(apps, hasItems(app1, app2));
	}

	@Test
	public void shouldGetHostNameWhenCreateAppsMessage() {
		AppsMessage response = processMessage(message);
		assertThat(response.getUsername(), is(HOST_USERNAME));
	}

	private <T> T processMessage(UserAppNamesMessage message) {
		return (T) processor.processMessage(message);
	}
}
