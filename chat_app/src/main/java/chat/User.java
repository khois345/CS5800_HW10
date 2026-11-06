package chat;

import java.util.List;

import message.*;

public class User {
	private String name;
	private ChatServer server;		// aggregation
	private ChatHistory history;	// composition

	public User(String name, ChatServer server) {
	}

	public String getName() {
		throw new UnsupportedOperationException();
	}

	public List<Message> getChatHistory() {
		return null;
	}

	// other methods
	public void sendMessage(List<String> recipients, String content) {
	}

	void receiveMessage(Message message) {
	}
}
