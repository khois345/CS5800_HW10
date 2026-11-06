package chat;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import message.*;

public class ChatServer {
	private Map<String, User> users;	// aggregation
	private Map<String, Set<String>> blockedUsers;	// composition

	public ChatServer() {
		this.users = new HashMap<>();
	}

	// other methods
	public void registerUser(User user) {
		if (user == null || user.getName() == null) {
			System.err.println("User cannot be empty");
			return;
		}

		if (users.containsKey(user.getName())) {
			System.err.println(user.getName() + " has already been registered");
			return;
		}

		users.put(user.getName(), user);
	}

	public void unregisterUser(User user) {
		if (user == null) {
			System.err.println("User cannot be empty");
			return;
		}

		if (!users.remove(user.getName(), user)) {
			System.err.println(user.getName() + " has not beem registered");
		}
	}

	public void blockUser(User blocker, String blockedName) {
		throw new UnsupportedOperationException();
	}

	// returns true if at least one recipient got the message
	public boolean sendMessage(Message message) {
		if (message == null) {
			System.err.println("Message cannot be empty");
			return false;
		}

		String sender = message.getSender();
		if (!users.containsKey(sender)) {
			System.err.println(sender + " is not registered");
			return false;
		}

		boolean delivered = false;
		for (String name : new LinkedHashSet<>(message.getRecipients())) {
			User recipient = users.get(name);
			if (recipient == null || name.equals(sender)) {
				System.err.println("Cannot deliver message to " + name);
				continue;
			}

			recipient.receiveMessage(message);
			delivered = true;
		}

		return delivered;
	}
}
