import chat.*;
import message.*;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatHistoryTest {
	private final Message sent = new Message("Alice", List.of("Bob"), "Hi Bob");
	private final Message received = new Message("Bob", List.of("Alice"), "Hi Alice");
	private final ChatHistory history = new ChatHistory("Alice");

	@Test
	public void getMessagesEmptyAtStart() {
		assertEquals(List.of(), history.getMessages());
	}

	@Test
	public void addMessage() {
		history.addMessage(sent);

		assertEquals(List.of(sent), history.getMessages());
	}

	@Test
	public void addMessageKeepsOrder() {
		history.addMessage(sent);
		history.addMessage(received);

		assertEquals(List.of(sent, received), history.getMessages());
	}

	@Test
	public void addMessageNull() {
		history.addMessage(null);

		assertEquals(List.of(), history.getMessages());
	}

	@Test
	public void changingReturnedListDoesNotChangeHistory() {
		history.getMessages().add(sent);

		assertEquals(List.of(), history.getMessages());
	}

	@Test
	public void getLastMessageSent() {
		Message latest = new Message("Alice", List.of("Bob"), "Are you there?");
		history.addMessage(sent);
		history.addMessage(latest);

		assertSame(latest, history.getLastMessageSent());
	}

	@Test
	public void getLastMessageSentSkipsReceived() {
		history.addMessage(sent);
		history.addMessage(received);

		assertSame(sent, history.getLastMessageSent());
	}

	@Test
	public void getLastMessageSentNoneSent() {
		history.addMessage(received);

		assertNull(history.getLastMessageSent());
	}

	@Test
	public void removeMessage() {
		history.addMessage(sent);
		history.addMessage(received);
		history.removeMessage("Alice", sent.createMemento());

		assertEquals(List.of(received), history.getMessages());
	}

	@Test
	public void removeMessageNoMatch() {
		Message other = new Message("Alice", List.of("Bob"), "Not in history");
		history.addMessage(sent);
		history.removeMessage("Alice", other.createMemento());

		assertEquals(List.of(sent), history.getMessages());
	}

	@Test
	public void removeMessageDifferentSender() {
		Message sameContent = new Message("Bob", List.of("Alice"), "Hi Bob");
		sameContent.setTimestamp(sent.getTimestamp());
		history.addMessage(sameContent);
		history.removeMessage("Alice", sent.createMemento());

		assertEquals(List.of(sameContent), history.getMessages());
	}

	@Test
	public void removeMessageRemovesLatestMatch() {
		Message repeat = new Message("Alice", List.of("Bob"), "Hi Bob");
		repeat.setTimestamp(sent.getTimestamp());
		history.addMessage(sent);
		history.addMessage(repeat);
		history.removeMessage("Alice", repeat.createMemento());

		assertSame(sent, history.getMessages().get(0));
		assertEquals(1, history.getMessages().size());
	}

	@Test
	public void removeMessageNull() {
		history.addMessage(sent);
		history.removeMessage("Alice", null);

		assertEquals(List.of(sent), history.getMessages());
	}

	@Test
	public void iteratorFindsMessagesByUser() {
		ChatServer server = new ChatServer();
		Message toAnnie = new Message("Alice", List.of("Annie"), "Hi Annie");
		history.addMessage(sent);
		history.addMessage(toAnnie);
		history.addMessage(received);
		Iterator<Message> iterator = history.iterator(new User("Bob", server));

		assertSame(sent, iterator.next());
		assertSame(received, iterator.next());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void iteratorUnaffectedByLaterMessages() {
		history.addMessage(sent);
		Iterator<Message> iterator = history.iterator(new User("Bob", new ChatServer()));
		history.addMessage(received);
		iterator.next();

		assertFalse(iterator.hasNext());
	}
}
