package chat;

import java.util.ArrayList;
import java.util.List;

import message.*;

public class ChatHistory {
	private String owner;
	private List<Message> messages;		// composition

	public ChatHistory(String owner) {
		this.owner = owner;
		this.messages = new ArrayList<>();
	}

	public List<Message> getMessages() {
		return new ArrayList<>(messages);
	}

	// other methods
	public void addMessage(Message message) {
		if (message == null) {
			System.err.println("Message cannot be empty");
			return;
		}

		messages.add(message);
	}

	public Message getLastMessageSent() {
		for (int i = messages.size() - 1; i >= 0; i--) {
			Message message = messages.get(i);
			if (message.getSender().equals(owner)) {
				return message;
			}
		}

		return null;
	}

	public void removeMessage(String sender, MessageMemento memento) {
		throw new UnsupportedOperationException();
	}
}
