import chat.*;
import message.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
	private final ChatServer server = new ChatServer();
	private final User alice = new User("Alice", server);
	private final User bob = new User("Bob", server);
	private final User annie = new User("Annie", server);

	@BeforeEach
	public void registerUsers() {
		server.registerUser(alice);
		server.registerUser(bob);
		server.registerUser(annie);
	}

	@Test
	public void getName() {
		assertEquals("Alice", alice.getName());
	}

	@Test
	public void nameBlank() {
		assertNull(new User("", server).getName());
	}

	@Test
	public void nameNull() {
		assertNull(new User(null, server).getName());
	}

	@Test
	public void chatHistoryEmptyAtStart() {
		assertEquals(List.of(), alice.getChatHistory());
	}

	@Test
	public void sendMessageAddsToSenderHistory() {
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		Message sent = alice.getChatHistory().get(0);
		assertEquals("Alice", sent.getSender());
		assertEquals(List.of("Bob"), sent.getRecipients());
		assertEquals("Hi Bob", sent.getContent());
	}

	@Test
	public void sendMessageDeliversToRecipient() {
		alice.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals("Hi Bob", bob.getChatHistory().get(0).getContent());
		assertEquals(List.of(), annie.getChatHistory());
	}

	@Test
	public void sendMessageDeliversToMultipleRecipients() {
		alice.sendMessage(List.of("Bob", "Annie"), "Hi both");

		assertEquals("Hi both", bob.getChatHistory().get(0).getContent());
		assertEquals("Hi both", annie.getChatHistory().get(0).getContent());
	}

	@Test
	public void sendMessageBlankContent() {
		alice.sendMessage(List.of("Bob"), "");

		assertEquals(List.of(), alice.getChatHistory());
		assertEquals(List.of(), bob.getChatHistory());
	}

	@Test
	public void sendMessageNullContent() {
		alice.sendMessage(List.of("Bob"), null);

		assertEquals(List.of(), alice.getChatHistory());
		assertEquals(List.of(), bob.getChatHistory());
	}

	@Test
	public void sendMessageEmptyRecipients() {
		alice.sendMessage(List.of(), "Hi");

		assertEquals(List.of(), alice.getChatHistory());
	}

	@Test
	public void sendMessageNullRecipients() {
		alice.sendMessage(null, "Hi");

		assertEquals(List.of(), alice.getChatHistory());
	}

	@Test
	public void sendMessageNotRegistered() {
		User dave = new User("Dave", server);
		dave.sendMessage(List.of("Bob"), "Hi Bob");

		assertEquals(List.of(), dave.getChatHistory());
		assertEquals(List.of(), bob.getChatHistory());
	}
}
