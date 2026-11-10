import chat.*;
import message.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SearchMessagesByUserTest {
	private final ChatServer server = new ChatServer();
	private final User bob = new User("Bob", server);
	private final Message toBob = new Message("Alice", List.of("Bob"), "Hi Bob");
	private final Message fromBob = new Message("Bob", List.of("Alice"), "Hi Alice");
	private final Message toAnnie = new Message("Alice", List.of("Annie"), "Hi Annie");
	private final Message toBoth = new Message("Alice", List.of("Annie", "Bob"), "Hi both");
	private final List<Message> messages = List.of(toAnnie, toBob, fromBob, toAnnie, toBoth, toAnnie);
	private final searchMessagesByUser iterator = new searchMessagesByUser(messages, bob);

	@Test
	public void iteratesSentAndReceivedInOrder() {
		List<Message> found = new ArrayList<>();
		while (iterator.hasNext()) {
			found.add(iterator.next());
		}

		assertEquals(List.of(toBob, fromBob, toBoth), found);
	}

	@Test
	public void hasNextTrueWhenMatchExists() {
		assertTrue(iterator.hasNext());
	}

	@Test
	public void hasNextDoesNotAdvance() {
		iterator.hasNext();
		iterator.hasNext();

		assertSame(toBob, iterator.next());
	}

	@Test
	public void hasNextFalseWhenNoMatch() {
		searchMessagesByUser noMatch = new searchMessagesByUser(messages, new User("Dave", server));

		assertFalse(noMatch.hasNext());
	}

	@Test
	public void hasNextFalseWhenEmpty() {
		searchMessagesByUser empty = new searchMessagesByUser(List.of(), bob);

		assertFalse(empty.hasNext());
	}

	@Test
	public void hasNextFalseWhenExhausted() {
		iterator.next();
		iterator.next();
		iterator.next();

		assertFalse(iterator.hasNext());
	}

	@Test
	public void hasNextFalseWhenUserNull() {
		searchMessagesByUser noUser = new searchMessagesByUser(messages, null);

		assertFalse(noUser.hasNext());
	}

	@Test
	public void nextWithoutHasNext() {
		iterator.next();

		assertSame(fromBob, iterator.next());
	}

	@Test
	public void nextThrowsWhenExhausted() {
		iterator.next();
		iterator.next();
		iterator.next();

		assertThrows(NoSuchElementException.class, iterator::next);
	}
}
