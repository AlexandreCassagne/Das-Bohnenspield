package student_player.mytools;

import java.util.*;

public class Queue<T> {
	
	private LinkedList<T> list;
	private int head = 0;
	
	public boolean empty() {
		return list.size() == 0;
	}
	public int size() {
		return list.size();
	}
	
	public void enqueue(T element) {
		list.add(element);
	}
	
	public T dequeue() {
		if (empty())
			return null;
		else {
			return list.removeFirst();
		}
	}
	
	public T peek() {
		if (empty())
			return null;
		else
			return list.getFirst();
	}
}
