package chat;

import java.util.Map;

import message.*;

public class ChatServer {
	private Map<String, User> users;	// aggregation

	public ChatServer() {
	}

	// other methods
	public void registerUser(User user) {
	}

	public void unregisterUser(User user) {
	}

	public boolean sendMessage(Message message) {
		return false;
	}
}
