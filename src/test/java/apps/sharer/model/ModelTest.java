package apps.sharer.model;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import apps.sharer.user.User;
import apps.sharer.user.UserBuilder;

public class ModelTest {

	private static final String USERNAME1 = "user1";
	private static final String USERNAME2 = "user2";
	private static final String USERNAME3 = "user3";
	private static final String USERNAME4 = "user4";
	private static final String USERNAME5 = "user5";
	private Model model;

	@Before
	public void setUp() {
		model = new Model();
	}

	@Test
	public void shouldAddAndGetOneUser() {
		User userAdded = new UserBuilder().addUsername(USERNAME1).build();
		model.addUser(userAdded);
		User userReceived = model.getUser(USERNAME1);
		assertEquals(userAdded, userReceived);
	}

	@Test
	public void shouldAddTwoUsersAndGetTwoUsers() {
		User userAdded = new UserBuilder().addUsername(USERNAME1).build();
		User userAdded2 = new UserBuilder().addUsername(USERNAME2).build();
		model.addUser(userAdded);
		model.addUser(userAdded2);
		User userReceived = model.getUser(USERNAME1);
		User userReceived2 = model.getUser(USERNAME2);
		assertEquals(userAdded, userReceived);
		assertEquals(userAdded2, userReceived2);
	}

	@Test
	public void shouldGetFirstOfFiveUsers() {
		User user1 = new UserBuilder().addUsername(USERNAME1).build();
		User user2 = new UserBuilder().addUsername(USERNAME2).build();
		User user3 = new UserBuilder().addUsername(USERNAME3).build();
		User user4 = new UserBuilder().addUsername(USERNAME4).build();
		User user5 = new UserBuilder().addUsername(USERNAME5).build();
		model.addUser(user1);
		model.addUser(user2);
		model.addUser(user3);
		model.addUser(user4);
		model.addUser(user5);
		User userReceived = model.getUser(USERNAME1);
		assertEquals(user1, userReceived);
	}

	@Test
	public void shouldGetThirdOfFiveUsers() {
		User user1 = new UserBuilder().addUsername(USERNAME1).build();
		User user2 = new UserBuilder().addUsername(USERNAME2).build();
		User user3 = new UserBuilder().addUsername(USERNAME3).build();
		User user4 = new UserBuilder().addUsername(USERNAME4).build();
		User user5 = new UserBuilder().addUsername(USERNAME5).build();
		model.addUser(user1);
		model.addUser(user2);
		model.addUser(user3);
		model.addUser(user4);
		model.addUser(user5);
		User userReceived = model.getUser(USERNAME3);
		assertEquals(user3, userReceived);
	}

	@Test
	public void shouldGetFifthOfFiveUsers() {
		User user1 = new UserBuilder().addUsername(USERNAME1).build();
		User user2 = new UserBuilder().addUsername(USERNAME2).build();
		User user3 = new UserBuilder().addUsername(USERNAME3).build();
		User user4 = new UserBuilder().addUsername(USERNAME4).build();
		User user5 = new UserBuilder().addUsername(USERNAME5).build();
		model.addUser(user1);
		model.addUser(user2);
		model.addUser(user3);
		model.addUser(user4);
		model.addUser(user5);
		User userReceived = model.getUser(USERNAME5);
		assertEquals(user5, userReceived);
	}

	@Test(expected = ModelException.class)
	public void shouldThrowModelExceptionWhenUserDoesNotExist() {
		User userAdded = new UserBuilder().addUsername(USERNAME1).build();
		model.addUser(userAdded);
		model.getUser(USERNAME2);
	}

	@Test(expected = ModelException.class)
	public void shouldNotGetHostUserWhenGetUserWhenNoUsersAddedToModel() {
		User hostUserAdded = new UserBuilder().addUsername(USERNAME1).build();
		model.setHostUser(hostUserAdded);
		model.getUser(USERNAME1);
	}

	@Test(expected = ModelException.class)
	public void shouldNotGetHostUserWhenGetUserWhenUsersHaveBeenAddedToModel() {
		User hostUserAdded = new UserBuilder().addUsername(USERNAME1).build();
		User user = new UserBuilder().addUsername(USERNAME2).build();
		model.setHostUser(hostUserAdded);
		model.addUser(user);
		model.getUser(USERNAME1);
	}

	@Test
	public void shouldGetHostUserWhenSetBeforeUserAdded() {
		User hostUserAdded = new UserBuilder().addUsername(USERNAME1).build();
		User user = new UserBuilder().addUsername(USERNAME2).build();
		model.setHostUser(hostUserAdded);
		model.addUser(user);
		User hostUserReceived = model.getHostUser();
		assertEquals(hostUserAdded, hostUserReceived);
	}

	@Test
	public void shouldGetHostUserWhenSetAfterUserAdded() {
		User hostUserAdded = new UserBuilder().addUsername(USERNAME1).build();
		User user = new UserBuilder().addUsername(USERNAME2).build();
		model.addUser(user);
		model.setHostUser(hostUserAdded);
		User hostUserReceived = model.getHostUser();
		assertEquals(hostUserAdded, hostUserReceived);
	}
}
