package chat;

import java.util.Iterator;
import java.util.List;

import message.*;

public class searchMessagesByUser implements Iterator<Message> {
	private List<Message> messages;
	private User user;
	private int index;

	public searchMessagesByUser(List<Message> messages, User user) {
	}

	// other methods
	@Override
	public boolean hasNext() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Message next() {
		throw new UnsupportedOperationException();
	}
}
