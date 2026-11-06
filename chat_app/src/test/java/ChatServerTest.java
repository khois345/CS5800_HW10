import chat.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatServerTest {
	private final ChatServer server = new ChatServer();
	private final User alice = new User("Alice", server);
	private final User bob = new User("Bob", server);

	@BeforeEach
	public void registerUsers() {
		server.registerUser(alice);
		server.registerUser(bob);
	}

	@Test
	public void registerUser() {
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(1, bob.getChatHistory().size());
	}

	@Test
	public void registerUserNull() {
		server.registerUser(null);
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(1, bob.getChatHistory().size());
	}

	@Test
	public void registerUserNoName() {
		server.registerUser(new User("", server));
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(1, bob.getChatHistory().size());
	}

	@Test
	public void registerUserDuplicateName() {
		User otherBob = new User("Bob", server);
		server.registerUser(otherBob);
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(1, bob.getChatHistory().size());
		assertEquals(List.of(), otherBob.getChatHistory());
	}

	@Test
	public void unregisterUserStopsReceiving() {
		server.unregisterUser(bob);
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(List.of(), bob.getChatHistory());
	}

	@Test
	public void unregisterUserStopsSending() {
		server.unregisterUser(alice);
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(List.of(), alice.getChatHistory());
		assertEquals(List.of(), bob.getChatHistory());
	}

	@Test
	public void unregisterUserNull() {
		server.unregisterUser(null);
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(1, bob.getChatHistory().size());
	}

	@Test
	public void unregisterUserDifferentUserSameName() {
		server.unregisterUser(new User("Bob", server));
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(1, bob.getChatHistory().size());
	}

	@Test
	public void sendMessageSkipsUnknownRecipient() {
		alice.sendMessage(List.of("Ghost", "Bob"), "Hi");

		assertEquals(1, alice.getChatHistory().size());
		assertEquals(1, bob.getChatHistory().size());
	}

	@Test
	public void sendMessageOnlyUnknownRecipients() {
		alice.sendMessage(List.of("Ghost"), "Hi");

		assertEquals(List.of(), alice.getChatHistory());
	}

	@Test
	public void sendMessageDuplicateRecipientDeliveredOnce() {
		alice.sendMessage(List.of("Bob", "Bob"), "Hi Bob");

		assertEquals(1, bob.getChatHistory().size());
	}

	@Test
	public void sendMessageToSelfNotDelivered() {
		alice.sendMessage(List.of("Alice"), "Hi me");

		assertEquals(List.of(), alice.getChatHistory());
	}
}
