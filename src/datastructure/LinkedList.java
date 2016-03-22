package datastructure;

public class LinkedList<E>
{
	private transient int modCount = 0;
	transient int size = 0;

	transient Node<E> first;

	transient Node<E> last;

	public LinkedList()
	{
	}

	private void linkFirst(E e)
	{
		final Node<E> f = first;
		final Node<E> newNode = new Node<>(null, e, f);
		first = newNode;
		if (f == null)
			last = newNode;
		else
			f.prev = newNode;
		size++;
		modCount++;
	}

	void linkLast(E e)
	{
		final Node<E> l = last;
		final Node<E> newNode = new Node<>(l, e, null);
		last = newNode;
		if (l == null)
			first = newNode;
		else
			l.next = newNode;
		size++;
		modCount++;
	}

	void linkBefore(E e, Node<E> succ)
	{
		// assert succ != null;
		final Node<E> pred = succ.prev;
		final Node<E> newNode = new Node<>(pred, e, succ);
		succ.prev = newNode;
		if (pred == null)
			first = newNode;
		else
			pred.next = newNode;
		size++;
		modCount++;
	}

	private E unlinkFirst(Node<E> f)
	{
		// assert f == first && f != null;
		final E element = f.item;
		final Node<E> next = f.next;
		f.item = null;
		f.next = null; // help GC
		first = next;
		if (next == null)
			last = null;
		else
			next.prev = null;
		size--;
		modCount++;
		return element;
	}

	private E unlinkLast(Node<E> l)
	{
		// assert l == last && l != null;
		final E element = l.item;
		final Node<E> prev = l.prev;
		l.item = null;
		l.prev = null; // help GC
		last = prev;
		if (prev == null)
			first = null;
		else
			prev.next = null;
		size--;
		modCount++;
		return element;
	}

	E unlink(Node<E> x)
	{
		// assert x != null;
		final E element = x.item;
		final Node<E> next = x.next;
		final Node<E> prev = x.prev;

		if (prev == null)
		{
			first = next;
		}
		else
		{
			prev.next = next;
			x.prev = null;
		}

		if (next == null)
		{
			last = prev;
		}
		else
		{
			next.prev = prev;
			x.next = null;
		}

		x.item = null;
		size--;
		modCount++;
		return element;
	}

	public E getFirst()
	{
		final Node<E> f = first;
		if (f == null)
			throw new RuntimeException("No such element exception");
		return f.item;
	}

	public E getLast()
	{
		final Node<E> l = last;
		if (l == null)
			throw new RuntimeException("No such element exception");
		return l.item;
	}

	public E removeFirst()
	{
		final Node<E> f = first;
		if (f == null)
			throw new RuntimeException("No such element exception");
		return unlinkFirst(f);
	}

	public E removeLast()
	{
		final Node<E> l = last;
		if (l == null)
			throw new RuntimeException("No such element exception");
		return unlinkLast(l);
	}

	public void addFirst(E e)
	{
		linkFirst(e);
	}

	public void addLast(E e)
	{
		linkLast(e);
	}

	public boolean contains(Object o)
	{
		return indexOf(o) != -1;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public boolean add(E e)
	{
		linkLast(e);
		return true;
	}

	public boolean remove(Object o)
	{
		if (o == null)
		{
			for (Node<E> x = first; x != null; x = x.next)
			{
				if (x.item == null)
				{
					unlink(x);
					return true;
				}
			}
		}
		else
		{
			for (Node<E> x = first; x != null; x = x.next)
			{
				if (o.equals(x.item))
				{
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}

	public void clear()
	{
		// Clearing all of the links between nodes is "unnecessary", but:
		// - helps a generational GC if the discarded nodes inhabit
		// more than one generation
		// - is sure to free memory even if there is a reachable Iterator
		for (Node<E> x = first; x != null;)
		{
			Node<E> next = x.next;
			x.item = null;
			x.next = null;
			x.prev = null;
			x = next;
		}
		first = last = null;
		size = 0;
		modCount++;
	}

	// Positional Access Operations

	public E get(int index)
	{
		checkElementIndex(index);
		return node(index).item;
	}

	public E set(int index, E element)
	{
		checkElementIndex(index);
		Node<E> x = node(index);
		E oldVal = x.item;
		x.item = element;
		return oldVal;
	}

	public void add(int index, E element)
	{
		checkPositionIndex(index);

		if (index == size)
			linkLast(element);
		else
			linkBefore(element, node(index));
	}

	public E remove(int index)
	{
		checkElementIndex(index);
		return unlink(node(index));
	}

	private boolean isElementIndex(int index)
	{
		return index >= 0 && index < size;
	}

	private boolean isPositionIndex(int index)
	{
		return index >= 0 && index <= size;
	}

	private String outOfBoundsMsg(int index)
	{
		return "Index: " + index + ", Size: " + size;
	}

	private void checkElementIndex(int index)
	{
		if (!isElementIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void checkPositionIndex(int index)
	{
		if (!isPositionIndex(index))
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	Node<E> node(int index)
	{
		// assert isElementIndex(index);

		if (index < (size >> 1))
		{
			Node<E> x = first;
			for (int i = 0; i < index; i++)
				x = x.next;
			return x;
		}
		else
		{
			Node<E> x = last;
			for (int i = size - 1; i > index; i--)
				x = x.prev;
			return x;
		}
	}

	// Search Operations

	public int indexOf(Object o)
	{
		int index = 0;
		if (o == null)
		{
			for (Node<E> x = first; x != null; x = x.next)
			{
				if (x.item == null)
					return index;
				index++;
			}
		}
		else
		{
			for (Node<E> x = first; x != null; x = x.next)
			{
				if (o.equals(x.item))
					return index;
				index++;
			}
		}
		return -1;
	}

	public int lastIndexOf(Object o)
	{
		int index = size;
		if (o == null)
		{
			for (Node<E> x = last; x != null; x = x.prev)
			{
				index--;
				if (x.item == null)
					return index;
			}
		}
		else
		{
			for (Node<E> x = last; x != null; x = x.prev)
			{
				index--;
				if (o.equals(x.item))
					return index;
			}
		}
		return -1;
	}

	// Queue operations.

	public E peek()
	{
		final Node<E> f = first;
		return (f == null) ? null : f.item;
	}

	public E element()
	{
		return getFirst();
	}

	public E poll()
	{
		final Node<E> f = first;
		return (f == null) ? null : unlinkFirst(f);
	}

	public E remove()
	{
		return removeFirst();
	}

	public boolean offer(E e)
	{
		return add(e);
	}

	// Deque operations

	public boolean offerFirst(E e)
	{
		addFirst(e);
		return true;
	}

	public boolean offerLast(E e)
	{
		addLast(e);
		return true;
	}

	public E peekFirst()
	{
		final Node<E> f = first;
		return (f == null) ? null : f.item;
	}

	public E peekLast()
	{
		final Node<E> l = last;
		return (l == null) ? null : l.item;
	}

	public E pollFirst()
	{
		final Node<E> f = first;
		return (f == null) ? null : unlinkFirst(f);
	}

	public E pollLast()
	{
		final Node<E> l = last;
		return (l == null) ? null : unlinkLast(l);
	}

	public void push(E e)
	{
		addFirst(e);
	}

	public E pop()
	{
		return removeFirst();
	}

	public boolean removeFirstOccurrence(Object o)
	{
		return remove(o);
	}

	public boolean removeLastOccurrence(Object o)
	{
		if (o == null)
		{
			for (Node<E> x = last; x != null; x = x.prev)
			{
				if (x.item == null)
				{
					unlink(x);
					return true;
				}
			}
		}
		else
		{
			for (Node<E> x = last; x != null; x = x.prev)
			{
				if (o.equals(x.item))
				{
					unlink(x);
					return true;
				}
			}
		}
		return false;
	}

	private static class Node<E>
	{
		E item;
		Node<E> next;
		Node<E> prev;

		Node(Node<E> prev, E element, Node<E> next)
		{
			this.item = element;
			this.next = next;
			this.prev = prev;
		}
	}

	@SuppressWarnings("unchecked")
	private LinkedList<E> superClone()
	{
		try
		{
			return (LinkedList<E>) super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			throw new InternalError();
		}
	}

	public Object clone()
	{
		LinkedList<E> clone = superClone();

		// Put clone into "virgin" state
		clone.first = clone.last = null;
		clone.size = 0;
		clone.modCount = 0;

		// Initialize clone with our elements
		for (Node<E> x = first; x != null; x = x.next)
			clone.add(x.item);

		return clone;
	}

	public Object[] toArray()
	{
		Object[] result = new Object[size];
		int i = 0;
		for (Node<E> x = first; x != null; x = x.next)
			result[i++] = x.item;
		return result;
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a)
	{
		if (a.length < size)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		int i = 0;
		Object[] result = a;
		for (Node<E> x = first; x != null; x = x.next)
			result[i++] = x.item;

		if (a.length > size)
			a[size] = null;

		return a;
	}

	public void printList()
	{
		Node<E> node = first;
		while (node != null)
		{
			System.out.println(node.item + " ");
			node = node.next;
		}
	}
}
