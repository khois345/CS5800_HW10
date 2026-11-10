package chat;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import message.*;

public class searchMessagesByUser implements Iterator<Message> {
	private List<Message> messages;
	private User user;
	private int index;

	public searchMessagesByUser(List<Message> messages, User user) {
		if (messages == null || user == null) {
			System.err.println("Messages and user cannot be empty");
			messages = List.of();
		}

		this.messages = messages;
		this.user = user;
		this.index = 0;
	}

	// other methods
	@Override
	public boolean hasNext() {
		// only return messages that are sent by the user or received by the user
		while (index < messages.size()) {
			Message message = messages.get(index);
			if (message.getSender().equals(user.getName()) || message.getRecipients().contains(user.getName())) {
				return true;
			}

			index++;
		}

		return false;
	}

	@Override
	public Message next() {
		if (!hasNext()) {
			throw new NoSuchElementException("No more messages found");
		}

		return messages.get(index++);
	}
}
