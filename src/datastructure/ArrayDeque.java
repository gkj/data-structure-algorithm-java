
package datastructure;

public class ArrayDeque<E>
{
	private transient E[] elements;

	private transient int head;

	private transient int tail;

	private static final int MIN_INITIAL_CAPACITY = 8;

	// ****** Array allocation and resizing utilities ******

	@SuppressWarnings("unchecked")
	private void allocateElements(int numElements)
	{
		int initialCapacity = MIN_INITIAL_CAPACITY;
		// Find the best power of two to hold elements.
		// Tests "<=" because arrays aren't kept full.
		if (numElements >= initialCapacity)
		{
			initialCapacity = numElements;
			initialCapacity |= (initialCapacity >>> 1);
			initialCapacity |= (initialCapacity >>> 2);
			initialCapacity |= (initialCapacity >>> 4);
			initialCapacity |= (initialCapacity >>> 8);
			initialCapacity |= (initialCapacity >>> 16);
			initialCapacity++;

			if (initialCapacity < 0) // Too many elements, must back off
				initialCapacity >>>= 1;// Good luck allocating 2 ^ 30 elements
		}
		elements = (E[]) new Object[initialCapacity];
	}

	@SuppressWarnings("unchecked")
	private void doubleCapacity()
	{
		assert head == tail;
		int p = head;
		int n = elements.length;
		int r = n - p; // number of elements to the right of p
		int newCapacity = n << 1;
		if (newCapacity < 0)
			throw new IllegalStateException("Sorry, deque too big");
		Object[] a = new Object[newCapacity];
		System.arraycopy(elements, p, a, 0, r);
		System.arraycopy(elements, 0, a, r, p);
		elements = (E[]) a;
		head = 0;
		tail = n;
	}

	private <T> T[] copyElements(T[] a)
	{
		if (head < tail)
		{
			System.arraycopy(elements, head, a, 0, size());
		}
		else if (head > tail)
		{
			int headPortionLen = elements.length - head;
			System.arraycopy(elements, head, a, 0, headPortionLen);
			System.arraycopy(elements, 0, a, headPortionLen, tail);
		}
		return a;
	}

	@SuppressWarnings("unchecked")
	public ArrayDeque()
	{
		elements = (E[]) new Object[16];
	}

	public ArrayDeque(int numElements)
	{
		allocateElements(numElements);
	}

	// The main insertion and extraction methods are addFirst,
	// addLast, pollFirst, pollLast. The other methods are defined in
	// terms of these.

	public void addFirst(E e)
	{
		if (e == null)
			throw new NullPointerException();
		elements[head = (head - 1) & (elements.length - 1)] = e;
		if (head == tail)
			doubleCapacity();
	}

	public void addLast(E e)
	{
		if (e == null)
			throw new NullPointerException();
		elements[tail] = e;
		if ((tail = (tail + 1) & (elements.length - 1)) == head)
			doubleCapacity();
	}

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

	public E removeFirst()
	{
		E x = pollFirst();
		if (x == null)
			throw new RuntimeException("No such element exception");
		return x;
	}

	public E removeLast()
	{
		E x = pollLast();
		if (x == null)
			throw new RuntimeException("No such element exception");
		return x;
	}

	public E pollFirst()
	{
		int h = head;
		E result = elements[h]; // Element is null if deque empty
		if (result == null)
			return null;
		elements[h] = null; // Must null out slot
		head = (h + 1) & (elements.length - 1);
		return result;
	}

	public E pollLast()
	{
		int t = (tail - 1) & (elements.length - 1);
		E result = elements[t];
		if (result == null)
			return null;
		elements[t] = null;
		tail = t;
		return result;
	}

	public E getFirst()
	{
		E x = elements[head];
		if (x == null)
			throw new RuntimeException("No such element exception");
		return x;
	}

	public E getLast()
	{
		E x = elements[(tail - 1) & (elements.length - 1)];
		if (x == null)
			throw new RuntimeException("No such element exception");
		return x;
	}

	public E peekFirst()
	{
		return elements[head]; // elements[head] is null if deque empty
	}

	public E peekLast()
	{
		return elements[(tail - 1) & (elements.length - 1)];
	}

	public boolean removeFirstOccurrence(Object o)
	{
		if (o == null)
			return false;
		int mask = elements.length - 1;
		int i = head;
		E x;
		while ((x = elements[i]) != null)
		{
			if (o.equals(x))
			{
				delete(i);
				return true;
			}
			i = (i + 1) & mask;
		}
		return false;
	}

	public boolean removeLastOccurrence(Object o)
	{
		if (o == null)
			return false;
		int mask = elements.length - 1;
		int i = (tail - 1) & mask;
		E x;
		while ((x = elements[i]) != null)
		{
			if (o.equals(x))
			{
				delete(i);
				return true;
			}
			i = (i - 1) & mask;
		}
		return false;
	}

	// *** Queue methods ***

	public boolean add(E e)
	{
		addLast(e);
		return true;
	}

	public boolean offer(E e)
	{
		return offerLast(e);
	}

	public E remove()
	{
		return removeFirst();
	}

	public E poll()
	{
		return pollFirst();
	}

	public E element()
	{
		return getFirst();
	}

	public E peek()
	{
		return peekFirst();
	}

	// *** Stack methods ***

	public void push(E e)
	{
		addFirst(e);
	}

	public E pop()
	{
		return removeFirst();
	}

	private void checkInvariants()
	{
		assert elements[tail] == null;
		assert head == tail ? elements[head] == null
				: (elements[head] != null && elements[(tail - 1) & (elements.length - 1)] != null);
		assert elements[(head - 1) & (elements.length - 1)] == null;
	}

	private boolean delete(int i)
	{
		checkInvariants();
		final E[] elements = this.elements;
		final int mask = elements.length - 1;
		final int h = head;
		final int t = tail;
		final int front = (i - h) & mask;
		final int back = (t - i) & mask;

		// Invariant: head <= i < tail mod circularity
		if (front >= ((t - h) & mask))
			throw new RuntimeException("Concurrent modification exception");

		// Optimize for least element motion
		if (front < back)
		{
			if (h <= i)
			{
				System.arraycopy(elements, h, elements, h + 1, front);
			}
			else
			{ // Wrap around
				System.arraycopy(elements, 0, elements, 1, i);
				elements[0] = elements[mask];
				System.arraycopy(elements, h, elements, h + 1, mask - h);
			}
			elements[h] = null;
			head = (h + 1) & mask;
			return false;
		}
		else
		{
			if (i < t)
			{ // Copy the null tail as well
				System.arraycopy(elements, i + 1, elements, i, back);
				tail = t - 1;
			}
			else
			{ // Wrap around
				System.arraycopy(elements, i + 1, elements, i, mask - i);
				elements[mask] = elements[0];
				System.arraycopy(elements, 1, elements, 0, t);
				tail = (t - 1) & mask;
			}
			return true;
		}
	}

	// *** Collection Methods ***

	public int size()
	{
		return (tail - head) & (elements.length - 1);
	}

	public boolean isEmpty()
	{
		return head == tail;
	}

	public boolean contains(Object o)
	{
		if (o == null)
			return false;
		int mask = elements.length - 1;
		int i = head;
		E x;
		while ((x = elements[i]) != null)
		{
			if (o.equals(x))
				return true;
			i = (i + 1) & mask;
		}
		return false;
	}

	public boolean remove(Object o)
	{
		return removeFirstOccurrence(o);
	}

	public void clear()
	{
		int h = head;
		int t = tail;
		if (h != t)
		{ // clear all cells
			head = tail = 0;
			int i = h;
			int mask = elements.length - 1;
			do
			{
				elements[i] = null;
				i = (i + 1) & mask;
			}
			while (i != t);
		}
	}

	public Object[] toArray()
	{
		return copyElements(new Object[size()]);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a)
	{
		int size = size();
		if (a.length < size)
			a = (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		copyElements(a);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	// *** Object methods ***

	@SuppressWarnings("unchecked")
	public ArrayDeque<E> clone()
	{
		try
		{
			ArrayDeque<E> result = (ArrayDeque<E>) super.clone();
			result.elements = copyOf(elements, elements.length);
			return result;

		}
		catch (CloneNotSupportedException e)
		{
			throw new AssertionError();
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] copyOf(T[] original, int newLength)
	{
		return (T[]) copyOf(original, newLength, original.getClass());
	}

	@SuppressWarnings("unchecked")
	public static <T, U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType)
	{
		T[] copy = ((Object) newType == (Object) Object[].class) ? (T[]) new Object[newLength]
				: (T[]) java.lang.reflect.Array.newInstance(newType.getComponentType(), newLength);
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
	}
}
