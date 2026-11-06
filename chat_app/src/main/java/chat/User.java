package chat;

import java.util.List;

import message.*;

public class User {
	private String name;
	private ChatServer server;		// aggregation
	private ChatHistory history;	// composition

	public User(String name, ChatServer server) {
		setName(name);
		this.server = server;
		this.history = new ChatHistory(name);
	}

	public String getName() {
		return name;
	}

	public List<Message> getChatHistory() {
		return history.getMessages();
	}

	// private because the ChatServer tracks users by name
	private void setName(String name) {
		if (name == null || name.isBlank()) {
			System.err.println("User name cannot be blank");
			return;
		}

		this.name = name;
	}

	// other methods
	public void sendMessage(List<String> recipients, String content) {
		if (recipients == null || recipients.isEmpty()) {
			System.err.println("Message must have at least one recipient");
			return;
		}

		if (content == null || content.isBlank()) {
			System.err.println("Message content cannot be blank");
			return;
		}

		Message message = new Message(name, recipients, content);
		if (server.sendMessage(message)) {
			history.addMessage(message);
		}
	}

	public void blockUser(String blockedName) {
		server.blockUser(this, blockedName);
	}

	void receiveMessage(Message message) {
		history.addMessage(message);
	}
}
