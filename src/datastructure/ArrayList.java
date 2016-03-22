package datastructure;

public class ArrayList<E>
{
	private transient int modCount = 0;

	private static final int DEFAULT_CAPACITY = 10;

	private static final Object[] EMPTY_ELEMENTDATA = {};

	private transient Object[] elementData;

	private int size;

	public ArrayList(int initialCapacity)
	{
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		this.elementData = new Object[initialCapacity];
	}

	public ArrayList()
	{
		super();
		this.elementData = EMPTY_ELEMENTDATA;
	}

	public void trimToSize()
	{
		modCount++;
		if (size < elementData.length)
		{
			elementData = copyOf(elementData, size);
		}
	}

	public void ensureCapacity(int minCapacity)
	{
		int minExpand = (elementData != EMPTY_ELEMENTDATA)
				// any size if real element table
				? 0
				// larger than default for empty table. It's already supposed to
				// be
				// at default size.
				: DEFAULT_CAPACITY;

		if (minCapacity > minExpand)
		{
			ensureExplicitCapacity(minCapacity);
		}
	}

	private void ensureCapacityInternal(int minCapacity)
	{
		if (elementData == EMPTY_ELEMENTDATA)
		{
			minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
		}

		ensureExplicitCapacity(minCapacity);
	}

	private void ensureExplicitCapacity(int minCapacity)
	{
		modCount++;

		// overflow-conscious code
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity)
	{
		// overflow-conscious code
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + (oldCapacity >> 1);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		// minCapacity is usually close to size, so this is a win:
		elementData = copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity)
	{
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	public int size()
	{
		return size;
	}

	public boolean isEmpty()
	{
		return size == 0;
	}

	public boolean contains(Object o)
	{
		return indexOf(o) >= 0;
	}

	public int indexOf(Object o)
	{
		if (o == null)
		{
			for (int i = 0; i < size; i++)
				if (elementData[i] == null)
					return i;
		}
		else
		{
			for (int i = 0; i < size; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	public int lastIndexOf(Object o)
	{
		if (o == null)
		{
			for (int i = size - 1; i >= 0; i--)
				if (elementData[i] == null)
					return i;
		}
		else
		{
			for (int i = size - 1; i >= 0; i--)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	public Object clone()
	{
		try
		{
			@SuppressWarnings("unchecked")
			ArrayList<E> v = (ArrayList<E>) super.clone();
			v.elementData = copyOf(elementData, size);
			v.modCount = 0;
			return v;
		}
		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	public Object[] toArray()
	{
		return copyOf(elementData, size);
	}

	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a)
	{
		if (a.length < size)
			// Make a new array of a's runtime type, but my contents:
			return (T[]) copyOf(elementData, size, a.getClass());
		System.arraycopy(elementData, 0, a, 0, size);
		if (a.length > size)
			a[size] = null;
		return a;
	}

	// Positional Access Operations

	@SuppressWarnings("unchecked")
	E elementData(int index)
	{
		return (E) elementData[index];
	}

	public E get(int index)
	{
		rangeCheck(index);

		return elementData(index);
	}

	public E set(int index, E element)
	{
		rangeCheck(index);

		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	public boolean add(E e)
	{
		ensureCapacityInternal(size + 1); // Increments modCount!!
		elementData[size++] = e;
		return true;
	}

	public void add(int index, E element)
	{
		rangeCheckForAdd(index);

		ensureCapacityInternal(size + 1); // Increments modCount!!
		System.arraycopy(elementData, index, elementData, index + 1, size - index);
		elementData[index] = element;
		size++;
	}

	public E remove(int index)
	{
		rangeCheck(index);

		modCount++;
		E oldValue = elementData(index);

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null; // clear to let GC do its work

		return oldValue;
	}

	public boolean remove(Object o)
	{
		if (o == null)
		{
			for (int index = 0; index < size; index++)
				if (elementData[index] == null)
				{
					fastRemove(index);
					return true;
				}
		}
		else
		{
			for (int index = 0; index < size; index++)
				if (o.equals(elementData[index]))
				{
					fastRemove(index);
					return true;
				}
		}
		return false;
	}

	private void fastRemove(int index)
	{
		modCount++;
		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--size] = null; // clear to let GC do its work
	}

	public void clear()
	{
		modCount++;

		// clear to let GC do its work
		for (int i = 0; i < size; i++)
			elementData[i] = null;

		size = 0;
	}

	protected void removeRange(int fromIndex, int toIndex)
	{
		modCount++;
		int numMoved = size - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);

		// clear to let GC do its work
		int newSize = size - (toIndex - fromIndex);
		for (int i = newSize; i < size; i++)
		{
			elementData[i] = null;
		}
		size = newSize;
	}

	private void rangeCheck(int index)
	{
		if (index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private void rangeCheckForAdd(int index)
	{
		if (index > size || index < 0)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
	}

	private String outOfBoundsMsg(int index)
	{
		return "Index: " + index + ", Size: " + size;
	}

	public ArrayList<E> subList(int fromIndex, int toIndex)
	{
		subListRangeCheck(fromIndex, toIndex, size);
		ArrayList<E> arrayList = new ArrayList<>();

		for (int i = fromIndex; i <= toIndex; i++)
			arrayList.add(get(i));

		return arrayList;
	}

	static void subListRangeCheck(int fromIndex, int toIndex, int size)
	{
		if (fromIndex < 0)
			throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
		if (toIndex > size)
			throw new IndexOutOfBoundsException("toIndex = " + toIndex);
		if (fromIndex > toIndex)
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
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
