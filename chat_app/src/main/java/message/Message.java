package message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Message {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private String sender;
	private List<String> recipients;
	private LocalDateTime timestamp;
	private String content;

	public Message(String sender, List<String> recipients, String content) {
		setSender(sender);
		setRecipients(recipients);
		setTimestamp(LocalDateTime.now());
		setContent(content);
	}

	public String getSender() {
		return sender;
	}

	public List<String> getRecipients() {
		return new ArrayList<>(recipients);
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getContent() {
		return content;
	}

	public void setSender(String sender) {
		if (sender == null || sender.isBlank()) {
			System.err.println("Message sender cannot be blank");
			return;
		}

		this.sender = sender;
	}

	public void setRecipients(List<String> recipients) {
		if (recipients == null || recipients.isEmpty()) {
			System.err.println("Message must have at least one recipient");
			return;
		}

		this.recipients = new ArrayList<>(recipients);
	}

	public void setTimestamp(LocalDateTime timestamp) {
		if (timestamp == null) {
			System.err.println("Message timestamp cannot be empty");
			return;
		}

		this.timestamp = timestamp;
	}

	public void setContent(String content) {
		if (content == null || content.isBlank()) {
			System.err.println("Message content cannot be blank");
			return;
		}

		this.content = content;
	}

	// other methods
	public MessageMemento createMemento() {
		return new MessageMemento(content, timestamp);
	}

	@Override
	public String toString() {
		return "[" + timestamp.format(FORMATTER) + "] " + sender + " -> " + String.join(", ", recipients) + ": " + content;
	}
}
