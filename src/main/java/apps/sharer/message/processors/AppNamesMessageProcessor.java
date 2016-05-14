package apps.sharer.message.processors;

import java.util.ArrayList;
import java.util.List;

import apps.sharer.app.App;
import apps.sharer.message.AppsMessage;
import apps.sharer.message.Message;
import apps.sharer.message.UserAppNamesMessage;
import apps.sharer.model.Model;
import apps.sharer.user.User;

public class AppNamesMessageProcessor implements MessageProcessor {

	private final Model model;

	public AppNamesMessageProcessor(final Model model) {
		this.model = model;
	}

	@Override
	public Message processMessage(final Message message) {
		UserAppNamesMessage userAppNamesMessage = (UserAppNamesMessage) message;
		List<App> apps = getAppsOwnedByHostThatAreNotOwnedByUser(userAppNamesMessage);
		return createAppsMessage(apps);
	}

	private List<App> getAppsOwnedByHostThatAreNotOwnedByUser(final UserAppNamesMessage message) {

		List<String> appNamesOwnedByUser = message.getNames();
		List<App> appsOwnedByHost = getHostApps();

		List<App> appsNotOwnedByUser = new ArrayList<>();
		for (App app : appsOwnedByHost) {
			if (!isAppOwnedByUser(app, appNamesOwnedByUser)) {
				appsNotOwnedByUser.add(app);
			}
		}
		return appsNotOwnedByUser;
	}

	private boolean isAppOwnedByUser(final App app, final List<String> appNamesOwnedByUser) {
		return appNamesOwnedByUser.contains(app.getName());
	}

	private List<App> getHostApps() {
		User host = model.getHostUser();
		return host.getApps();
	}

	private AppsMessage createAppsMessage(final List<App> apps) {
		String userName = getHostUserName();
		return new AppsMessage(userName, apps);
	}

	private String getHostUserName() {
		User host = model.getHostUser();
		return host.getUsername();
	}
}
