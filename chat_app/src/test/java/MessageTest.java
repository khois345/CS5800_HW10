import message.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {
	private final Message message = new Message("Alice", List.of("Bob", "Charlie"), "Hello");

	@Test
	public void getSender() {
		assertEquals("Alice", message.getSender());
	}

	@Test
	public void getRecipients() {
		assertEquals(List.of("Bob", "Charlie"), message.getRecipients());
	}

	@Test
	public void getContent() {
		assertEquals("Hello", message.getContent());
	}

	@Test
	public void timestampSetOnCreation() {
		LocalDateTime before = LocalDateTime.now();
		Message created = new Message("Alice", List.of("Bob"), "Hello");
		LocalDateTime after = LocalDateTime.now();

		assertFalse(created.getTimestamp().isBefore(before));
		assertFalse(created.getTimestamp().isAfter(after));
	}

	@Test
	public void setSenderBlank() {
		message.setSender("");

		assertEquals("Alice", message.getSender());
	}

	@Test
	public void setSenderNull() {
		message.setSender(null);

		assertEquals("Alice", message.getSender());
	}

	@Test
	public void setRecipientsEmpty() {
		message.setRecipients(List.of());

		assertEquals(List.of("Bob", "Charlie"), message.getRecipients());
	}

	@Test
	public void setRecipientsNull() {
		message.setRecipients(null);

		assertEquals(List.of("Bob", "Charlie"), message.getRecipients());
	}

	@Test
	public void setContentBlank() {
		message.setContent("");

		assertEquals("Hello", message.getContent());
	}

	@Test
	public void setContentNull() {
		message.setContent(null);

		assertEquals("Hello", message.getContent());
	}

	@Test
	public void setTimestampNull() {
		LocalDateTime timestamp = LocalDateTime.of(2026, 11, 6, 12, 30, 5);
		message.setTimestamp(timestamp);

		message.setTimestamp(null);

		assertEquals(timestamp, message.getTimestamp());
	}

	@Test
	public void changingSourceListDoesNotChangeRecipients() {
		List<String> recipients = new ArrayList<>(List.of("Bob"));
		Message created = new Message("Alice", recipients, "Hello");

		recipients.add("Charlie");

		assertEquals(List.of("Bob"), created.getRecipients());
	}

	@Test
	public void changingReturnedListDoesNotChangeRecipients() {
		message.getRecipients().add("Dave");

		assertEquals(List.of("Bob", "Charlie"), message.getRecipients());
	}

	@Test
	public void toStringFormat() {
		message.setTimestamp(LocalDateTime.of(2026, 11, 6, 12, 30, 5));

		assertEquals("[2026-11-06 12:30:05] Alice -> Bob, Charlie: Hello", message.toString());
	}
}
