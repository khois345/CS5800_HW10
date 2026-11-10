import java.util.Iterator;
import java.util.List;

import chat.*;
import message.*;

public class initChatApp {

	public static void main(String[] args) {
		ChatServer server = new ChatServer();
		User alice = new User("Alice", server);
		User bob = new User("Bob", server);
		User annie = new User("Annie", server);
		server.registerUser(alice);
		server.registerUser(bob);
		server.registerUser(annie);

		// Users can send messages to one or more other users through the chat server
		System.out.println("Send messages " + "-".repeat(50));
		alice.sendMessage(List.of("Bob"), "Hi Bob");
		bob.sendMessage(List.of("Alice"), "Hi Alice!");
		annie.sendMessage(List.of("Alice", "Bob"), "Hi, everyone");
		printHistory(alice);
		printHistory(bob);
		printHistory(annie);
		System.out.println();

		// Users can undo the last message they sent using the Memento design pattern
		System.out.println("Undo last message " + "-".repeat(50));
		alice.sendMessage(List.of("Bob", "Annie"), "Sorry. This message was a mistake");
		System.out.println("  Before undo:");
		printHistory(alice);
		printHistory(bob);
		printHistory(annie);
		alice.undoLastMessage();
		System.out.println("  After undo:");
		printHistory(alice);
		printHistory(bob);
		printHistory(annie);
		System.out.println();

		// Users can block messages from specific users using the Mediator design pattern.
		System.out.println("Block messages " + "-".repeat(50));
		annie.blockUser("Bob");
		bob.sendMessage(List.of("Alice", "Annie"), "Hello, can you both see this message? I should be blocked");
		printHistory(alice);
		printHistory(bob);
		printHistory(annie);
		System.out.println();

		// Allow Users to iterate over their message history by a specific user
		System.out.println("Iterate messages by user " + "-".repeat(50));
		printMessagesWith(alice, bob);
		printMessagesWith(alice, annie);
		printMessagesWith(annie, bob);
	}

	// Users can receive messages from other users and view the chat history for a specific user.
	private static void printHistory(User user) {
		System.out.println("  " + user.getName() + "'s chat history log:");
		for (Message message : user.getChatHistory()) {
			System.out.println("    " + message);
		}
	}

	private static void printMessagesWith(User user, User otherUser) {
		System.out.println("  " + user.getName() + "'s messages with " + otherUser.getName() + ":");
		Iterator<Message> iterator = user.iterator(otherUser);
		while (iterator.hasNext()) {
			System.out.println("    " + iterator.next());
		}
	}
}
