package chat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import message.*;

public class ChatServer {
	private Map<String, User> users;	// aggregation
	private Map<String, Set<String>> blockedUsers;	// composition

	public ChatServer() {
		this.users = new HashMap<>();
		this.blockedUsers = new HashMap<>();
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
			System.err.println(user.getName() + " has not registered");
		}
	}

	public void blockUser(User blocker, String blockedName) {
		if (blocker == null || users.get(blocker.getName()) != blocker) {
			System.err.println("Only registered users can block");
			return;
		}

		if (blockedName == null || blockedName.isBlank()) {
			System.err.println("Blocked user name cannot be blank");
			return;
		}

		blockedUsers.computeIfAbsent(blocker.getName(), name -> new HashSet<>()).add(blockedName);
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

			if (blockedUsers.getOrDefault(name, Set.of()).contains(sender)) {
				System.err.println(name + " has blocked " + sender);
				continue;
			}

			recipient.receiveMessage(message);
			delivered = true;
		}

		return delivered;
	}

	public boolean undoMessage(String sender, MessageMemento memento) {
		if (!users.containsKey(sender)) {
			System.err.println(sender + " is not registered");
			return false;
		}

		for (User user : users.values()) {
			user.removeMessage(sender, memento);
		}

		return true;
	}
}
