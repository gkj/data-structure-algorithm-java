package datastructure;

/**
 * Queue implementation based-on linked-list.
 * 
 * @author Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 *
 * @param <E>
 *            the type/class of elements held in this linked-list. The type of
 *            elements should be implements interface java.lang.Comparable
 */
public class Queue<E extends Comparable<E>>
{
	private Node<E> front;
	private Node<E> back;
	private int queueSize;

	/**
	 * Construct and empty queue. Example: {@code Queue
	 * <Integer> queue = new Queue<Integer>(); }
	 */
	public Queue()
	{
		clear();
	}

	/**
	 * Make the queue logically empty
	 */
	public void clear()
	{
		front = null;
		back = null;
		queueSize = 0;
	}

	/**
	 * Returns {@code true} if this queue contains the specified element. More
	 * formally, returns {@code true} if and only if this queue contains at
	 * least one element {@code element} such that
	 * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(element))</tt>.
	 *
	 * @param element
	 *            element whose presence in this queue is to be tested
	 * @return {@code true} if this queue contains the specified element
	 * @throws RuntimeException
	 *             if queue is empty
	 */
	public boolean contains(E element)
	{
		if (isEmpty())
			throw new RuntimeException("queue is empty");

		Node<E> current = front;
		while (current != null)
		{
			if (current.element.compareTo(element) == 0)
				return true;

			current = current.next;
		}

		return false;
	}

	/**
	 * Insert a new element at the end of the queue
	 * 
	 * @param element
	 *            to be inserted to the queue
	 */
	public void add(E element)
	{
		if (isEmpty())
			back = front = new Node<E>(element); // make queue of one element
		else
			back = back.next = new Node<E>(element); // regular case
		queueSize++;
	}

	/**
	 * Insert a new element at the end of the queue
	 * 
	 * @param element
	 *            to be inserted to the queue
	 */
	public void offer(E element)
	{
		if (isEmpty())
			back = front = new Node<E>(element); // make queue of one element
		else
			back = back.next = new Node<E>(element); // regular case
		queueSize++;
	}

	/**
	 * Retrieves and removes the head of this queue. This method differs from
	 * {@link #poll poll} only in that it throws an exception if this queue is
	 * empty.
	 *
	 * @return the head of this queue
	 * @throws RuntimeException
	 *             if this queue is empty
	 */
	public E remove()
	{
		if (isEmpty())
			throw new RuntimeException("queue is empty");

		E frontElement = front.element;
		front = front.next;
		queueSize--;
		return frontElement;
	}

	/**
	 * Retrieves and removes the head of this queue, or returns {@code null} if
	 * this queue is empty.
	 *
	 * @return the head of this queue, or {@code null} if this queue is empty
	 */
	public E poll()
	{
		if (isEmpty())
			throw new RuntimeException("queue is empty");

		E frontElement = front.element;
		front = front.next;
		queueSize--;
		return frontElement;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue. This method
	 * differs from {@link #peek peek} only in that it throws an exception if
	 * this queue is empty.
	 *
	 * @return the head of this queue
	 * @throws RuntimeException
	 *             if this queue is empty
	 */
	public E element()
	{
		if (isEmpty())
			throw new RuntimeException("queue is empty");

		return front.element;
	}

	/**
	 * Retrieves, but does not remove, the head of this queue, or returns
	 * {@code null} if this queue is empty.
	 *
	 * @return the head of this queue, or {@code null} if this queue is empty
	 */
	public E peek()
	{
		return isEmpty() ? null : front.element;
	}

	/**
	 * Tests if this queue is empty.
	 *
	 * @return {@code true} if and only if this queue contains no items;
	 *         {@code false} otherwise.
	 */
	public boolean isEmpty()
	{
		return front == null;
	}

	/**
	 * Print all of the elements in this queue in proper sequence (from front to
	 * last element)
	 */
	public void printQueue()
	{
		Node<E> current = front;
		while (current != null)
		{
			if (current.next == null)
				System.out.print(current.element);
			else
				System.out.print(current.element + ", ");
			current = current.next;
		}
		System.out.println();
	}

	/**
	 * Returns the number of elements in this queue.
	 * 
	 * @return size of stack
	 */
	public int size()
	{
		return queueSize;
	}

	/**
	 * Returns an array containing all of the elements in this queue in proper
	 * sequence (from front to end element)
	 * 
	 * @param type
	 *            class or type of array. Example
	 *            {@code Integer[] array = queue.toArray(Integer.class); }
	 * @return an array of elements
	 */
	public E[] toArray(Class<E> type)
	{
		if (isEmpty())
			throw new RuntimeException("queue is empty");

		@SuppressWarnings("unchecked")
		final E[] array = (E[]) java.lang.reflect.Array.newInstance(type, size());

		// iterate from top of the stack
		int idx = 0;
		Node<E> current = front;
		while (current != null)
		{
			array[idx++] = current.element;
			current = current.next;
		}

		return array;
	}

	class Node<T extends Comparable<T>>
	{
		T element;
		Node<T> next;

		public Node(T element, Node<T> next)
		{
			this.element = element;
			this.next = next;
		}

		public Node(T element)
		{
			this(element, null);
		}

		public Node()
		{
			this(null);
		}
	}
}
