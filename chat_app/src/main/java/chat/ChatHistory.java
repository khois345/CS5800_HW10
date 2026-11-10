package chat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import message.*;

public class ChatHistory implements IterableByUser {
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
		if (memento == null) {
			System.err.println("Memento cannot be empty");
			return;
		}

		for (int i = messages.size() - 1; i >= 0; i--) {
			Message message = messages.get(i);
			if (message.getSender().equals(sender)
					&& message.getContent().equals(memento.getContent())
					&& message.getTimestamp().equals(memento.getTimestamp())) {
				messages.remove(i);
				return;
			}
		}
	}

	@Override
	public Iterator<Message> iterator(User userToSearchWith) {
		List<Message> messages = getMessages();
		return new searchMessagesByUser(messages, userToSearchWith);
	}
}
