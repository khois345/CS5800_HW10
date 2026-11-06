import message.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageMementoTest {
	private final LocalDateTime timestamp = LocalDateTime.of(2026, 11, 6, 12, 30, 5);
	private final Message message = new Message("Alice", List.of("Bob"), "Hello World!");

	@Test
	public void getContent() {
		assertEquals("Hello World!", message.createMemento().getContent());
	}

	@Test
	public void getTimestamp() {
		message.setTimestamp(timestamp);

		assertEquals(timestamp, message.createMemento().getTimestamp());
	}

	@Test
	public void contentUnchangedWhenMessageChanges() {
		MessageMemento memento = message.createMemento();
		message.setContent("Changed");

		assertEquals("Hello World!", memento.getContent());
	}

	@Test
	public void timestampUnchangedWhenMessageChanges() {
		message.setTimestamp(timestamp);
		MessageMemento memento = message.createMemento();
		message.setTimestamp(timestamp.plusDays(1));

		assertEquals(timestamp, memento.getTimestamp());
	}
}
