package datastructure;

public class PriorityQueue<E>
{
	private static final int DEFAULT_INITIAL_CAPACITY = 11;

	private transient Object[] queue;

	private int size = 0;

	private final Comparator<? super E> comparator;

	private transient int modCount = 0;

	public PriorityQueue()
	{
		this(DEFAULT_INITIAL_CAPACITY, null);
	}

	public PriorityQueue(int initialCapacity)
	{
		this(initialCapacity, null);
	}

	public PriorityQueue(int initialCapacity, Comparator<? super E> comparator)
	{
		// Note: This restriction of at least one is not actually needed,
		// but continues for 1.5 compatibility
		if (initialCapacity < 1)
			throw new IllegalArgumentException();
		this.queue = new Object[initialCapacity];
		this.comparator = comparator;
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity)
	{
		int oldCapacity = queue.length;
		// Double size if small; else grow by 50%
		int newCapacity = oldCapacity + ((oldCapacity < 64) ? (oldCapacity + 2) : (oldCapacity >> 1));
		// overflow-conscious code
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		queue = copyOf(queue, newCapacity);
	}

	private static int hugeCapacity(int minCapacity)
	{
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	public boolean add(E e)
	{
		return offer(e);
	}

	public boolean offer(E e)
	{
		if (e == null)
			throw new NullPointerException();
		modCount++;
		int i = size;
		if (i >= queue.length)
			grow(i + 1);
		size = i + 1;
		if (i == 0)
			queue[0] = e;
		else
			siftUp(i, e);
		return true;
	}

	@SuppressWarnings("unchecked")
	public E peek()
	{
		if (size == 0)
			return null;
		return (E) queue[0];
	}

	private int indexOf(Object o)
	{
		if (o != null)
		{
			for (int i = 0; i < size; i++)
				if (o.equals(queue[i]))
					return i;
		}
		return -1;
	}

	public boolean remove(Object o)
	{
		int i = indexOf(o);
		if (i == -1)
			return false;
		else
		{
			removeAt(i);
			return true;
		}
	}

	boolean removeEq(Object o)
	{
		for (int i = 0; i < size; i++)
		{
			if (o == queue[i])
			{
				removeAt(i);
				return true;
			}
		}
		return false;
	}

	public boolean contains(Object o)
	{
		return indexOf(o) != -1;
	}

	public Object[] toArray()
	{
		return copyOf(queue, size);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a)
	{
		if (a.length < size)
			// Make a new array of a's runtime type, but my contents:
			return (T[]) copyOf(queue, size, a.getClass());
		System.arraycopy(queue, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	public int size()
	{
		return size;
	}

	public void clear()
	{
		modCount++;
		for (int i = 0; i < size; i++)
			queue[i] = null;
		size = 0;
	}

	@SuppressWarnings("unchecked")
	public E poll()
	{
		if (size == 0)
			return null;
		int s = --size;
		modCount++;
		E result = (E) queue[0];
		E x = (E) queue[s];
		queue[s] = null;
		if (s != 0)
			siftDown(0, x);
		return result;
	}

	@SuppressWarnings("unchecked")
	private E removeAt(int i)
	{
		assert i >= 0 && i < size;
		modCount++;
		int s = --size;
		if (s == i) // removed last element
			queue[i] = null;
		else
		{
			E moved = (E) queue[s];
			queue[s] = null;
			siftDown(i, moved);
			if (queue[i] == moved)
			{
				siftUp(i, moved);
				if (queue[i] != moved)
					return moved;
			}
		}
		return null;
	}

	private void siftUp(int k, E x)
	{
		if (comparator != null)
			siftUpUsingComparator(k, x);
		else
			siftUpComparable(k, x);
	}

	@SuppressWarnings("unchecked")
	private void siftUpComparable(int k, E x)
	{
		Comparable<? super E> key = (Comparable<? super E>) x;
		while (k > 0)
		{
			int parent = (k - 1) >>> 1;
			Object e = queue[parent];
			if (key.compareTo((E) e) >= 0)
				break;
			queue[k] = e;
			k = parent;
		}
		queue[k] = key;
	}

	@SuppressWarnings("unchecked")
	private void siftUpUsingComparator(int k, E x)
	{
		while (k > 0)
		{
			int parent = (k - 1) >>> 1;
			Object e = queue[parent];
			if (comparator.compare(x, (E) e) >= 0)
				break;
			queue[k] = e;
			k = parent;
		}
		queue[k] = x;
	}

	private void siftDown(int k, E x)
	{
		if (comparator != null)
			siftDownUsingComparator(k, x);
		else
			siftDownComparable(k, x);
	}

	@SuppressWarnings("unchecked")
	private void siftDownComparable(int k, E x)
	{
		Comparable<? super E> key = (Comparable<? super E>) x;
		int half = size >>> 1; // loop while a non-leaf
		while (k < half)
		{
			int child = (k << 1) + 1; // assume left child is least
			Object c = queue[child];
			int right = child + 1;
			if (right < size && ((Comparable<? super E>) c).compareTo((E) queue[right]) > 0)
				c = queue[child = right];
			if (key.compareTo((E) c) <= 0)
				break;
			queue[k] = c;
			k = child;
		}
		queue[k] = key;
	}

	@SuppressWarnings("unchecked")
	private void siftDownUsingComparator(int k, E x)
	{
		int half = size >>> 1;
		while (k < half)
		{
			int child = (k << 1) + 1;
			Object c = queue[child];
			int right = child + 1;
			if (right < size && comparator.compare((E) c, (E) queue[right]) > 0)
				c = queue[child = right];
			if (comparator.compare(x, (E) c) <= 0)
				break;
			queue[k] = c;
			k = child;
		}
		queue[k] = x;
	}

	@SuppressWarnings("unchecked")
	private void heapify()
	{
		for (int i = (size >>> 1) - 1; i >= 0; i--)
			siftDown(i, (E) queue[i]);
	}

	public Comparator<? super E> comparator()
	{
		return comparator;
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
