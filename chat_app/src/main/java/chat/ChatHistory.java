package chat;

import java.util.List;

import message.*;

public class ChatHistory {
	private String owner;
	private List<Message> messages;

	public ChatHistory(String owner) {
	}

	public List<Message> getMessages() {
		return null;
	}

	// other methods
	public void addMessage(Message message) {
	}

	public Message getLastMessageSent() {
		throw new UnsupportedOperationException();
	}
}
