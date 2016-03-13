package datastructure;

public class Vector<E>
{
	private transient int modCount = 0;

	protected Object[] elementData;

	protected int elementCount;

	protected int capacityIncrement;

	public Vector(int initialCapacity, int capacityIncrement)
	{
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		this.elementData = new Object[initialCapacity];
		this.capacityIncrement = capacityIncrement;
	}

	public Vector(int initialCapacity)
	{
		this(initialCapacity, 0);
	}

	public Vector()
	{
		this(10);
	}

	public synchronized void copyInto(Object[] anArray)
	{
		System.arraycopy(elementData, 0, anArray, 0, elementCount);
	}

	public synchronized void trimToSize()
	{
		modCount++;
		int oldCapacity = elementData.length;
		if (elementCount < oldCapacity)
		{
			elementData = copyOf(elementData, elementCount);
		}
	}

	public synchronized void ensureCapacity(int minCapacity)
	{
		if (minCapacity > 0)
		{
			modCount++;
			ensureCapacityHelper(minCapacity);
		}
	}

	private void ensureCapacityHelper(int minCapacity)
	{
		// overflow-conscious code
		if (minCapacity - elementData.length > 0)
			grow(minCapacity);
	}

	private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

	private void grow(int minCapacity)
	{
		// overflow-conscious code
		int oldCapacity = elementData.length;
		int newCapacity = oldCapacity + ((capacityIncrement > 0) ? capacityIncrement : oldCapacity);
		if (newCapacity - minCapacity < 0)
			newCapacity = minCapacity;
		if (newCapacity - MAX_ARRAY_SIZE > 0)
			newCapacity = hugeCapacity(minCapacity);
		elementData = copyOf(elementData, newCapacity);
	}

	private static int hugeCapacity(int minCapacity)
	{
		if (minCapacity < 0) // overflow
			throw new OutOfMemoryError();
		return (minCapacity > MAX_ARRAY_SIZE) ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
	}

	public synchronized void setSize(int newSize)
	{
		modCount++;
		if (newSize > elementCount)
		{
			ensureCapacityHelper(newSize);
		}
		else
		{
			for (int i = newSize; i < elementCount; i++)
			{
				elementData[i] = null;
			}
		}
		elementCount = newSize;
	}

	public synchronized int capacity()
	{
		return elementData.length;
	}

	public synchronized int size()
	{
		return elementCount;
	}

	public synchronized boolean isEmpty()
	{
		return elementCount == 0;
	}

	public boolean contains(Object o)
	{
		return indexOf(o, 0) >= 0;
	}

	public int indexOf(Object o)
	{
		return indexOf(o, 0);
	}

	public synchronized int indexOf(Object o, int index)
	{
		if (o == null)
		{
			for (int i = index; i < elementCount; i++)
				if (elementData[i] == null)
					return i;
		}
		else
		{
			for (int i = index; i < elementCount; i++)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	public synchronized int lastIndexOf(Object o)
	{
		return lastIndexOf(o, elementCount - 1);
	}

	public synchronized int lastIndexOf(Object o, int index)
	{
		if (index >= elementCount)
			throw new IndexOutOfBoundsException(index + " >= " + elementCount);

		if (o == null)
		{
			for (int i = index; i >= 0; i--)
				if (elementData[i] == null)
					return i;
		}
		else
		{
			for (int i = index; i >= 0; i--)
				if (o.equals(elementData[i]))
					return i;
		}
		return -1;
	}

	public synchronized E elementAt(int index)
	{
		if (index >= elementCount)
		{
			throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
		}

		return elementData(index);
	}

	public synchronized E firstElement()
	{
		if (elementCount == 0)
		{
			throw new RuntimeException("No such element exception");
		}
		return elementData(0);
	}

	public synchronized E lastElement()
	{
		if (elementCount == 0)
		{
			throw new RuntimeException("No such element exception");
		}
		return elementData(elementCount - 1);
	}

	public synchronized void setElementAt(E obj, int index)
	{
		if (index >= elementCount)
		{
			throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
		}
		elementData[index] = obj;
	}

	public synchronized void removeElementAt(int index)
	{
		modCount++;
		if (index >= elementCount)
		{
			throw new ArrayIndexOutOfBoundsException(index + " >= " + elementCount);
		}
		else if (index < 0)
		{
			throw new ArrayIndexOutOfBoundsException(index);
		}
		int j = elementCount - index - 1;
		if (j > 0)
		{
			System.arraycopy(elementData, index + 1, elementData, index, j);
		}
		elementCount--;
		elementData[elementCount] = null;
	}

	public synchronized void insertElementAt(E obj, int index)
	{
		modCount++;
		if (index > elementCount)
		{
			throw new ArrayIndexOutOfBoundsException(index + " > " + elementCount);
		}
		ensureCapacityHelper(elementCount + 1);
		System.arraycopy(elementData, index, elementData, index + 1, elementCount - index);
		elementData[index] = obj;
		elementCount++;
	}

	public synchronized void addElement(E obj)
	{
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = obj;
	}

	public synchronized boolean removeElement(Object obj)
	{
		modCount++;
		int i = indexOf(obj);
		if (i >= 0)
		{
			removeElementAt(i);
			return true;
		}
		return false;
	}

	public synchronized void removeAllElements()
	{
		modCount++;
		// Let gc do its work
		for (int i = 0; i < elementCount; i++)
			elementData[i] = null;

		elementCount = 0;
	}

	public synchronized Object clone()
	{
		try
		{
			@SuppressWarnings("unchecked")
			Vector<E> v = (Vector<E>) super.clone();
			v.elementData = copyOf(elementData, elementCount);
			v.modCount = 0;
			return v;
		}
		catch (CloneNotSupportedException e)
		{
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}

	public synchronized Object[] toArray()
	{
		return copyOf(elementData, elementCount);
	}

	@SuppressWarnings("unchecked")
	public synchronized <T> T[] toArray(T[] a)
	{
		if (a.length < elementCount)
			return (T[]) copyOf(elementData, elementCount, a.getClass());

		System.arraycopy(elementData, 0, a, 0, elementCount);

		if (a.length > elementCount)
			a[elementCount] = null;

		return a;
	}

	// Positional Access Operations

	@SuppressWarnings("unchecked")
	E elementData(int index)
	{
		return (E) elementData[index];
	}

	public synchronized E get(int index)
	{
		if (index >= elementCount)
			throw new ArrayIndexOutOfBoundsException(index);

		return elementData(index);
	}

	public synchronized E set(int index, E element)
	{
		if (index >= elementCount)
			throw new ArrayIndexOutOfBoundsException(index);

		E oldValue = elementData(index);
		elementData[index] = element;
		return oldValue;
	}

	public synchronized boolean add(E e)
	{
		modCount++;
		ensureCapacityHelper(elementCount + 1);
		elementData[elementCount++] = e;
		return true;
	}

	public boolean remove(Object o)
	{
		return removeElement(o);
	}

	public void add(int index, E element)
	{
		insertElementAt(element, index);
	}

	public synchronized E remove(int index)
	{
		modCount++;
		if (index >= elementCount)
			throw new ArrayIndexOutOfBoundsException(index);
		E oldValue = elementData(index);

		int numMoved = elementCount - index - 1;
		if (numMoved > 0)
			System.arraycopy(elementData, index + 1, elementData, index, numMoved);
		elementData[--elementCount] = null; // Let gc do its work

		return oldValue;
	}

	public void clear()
	{
		removeAllElements();
	}

	// Bulk Operations
	public synchronized boolean equals(Object o)
	{
		return super.equals(o);
	}

	public synchronized int hashCode()
	{
		return super.hashCode();
	}

	public synchronized String toString()
	{
		return super.toString();
	}

	public synchronized Vector<E> subList(int fromIndex, int toIndex)
	{
		Vector<E> vector = new Vector<E>();
		for (int i = fromIndex; i <= toIndex; i++)
			vector.add(get(i));

		return vector;
	}

	protected synchronized void removeRange(int fromIndex, int toIndex)
	{
		modCount++;
		int numMoved = elementCount - toIndex;
		System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);

		// Let gc do its work
		int newElementCount = elementCount - (toIndex - fromIndex);
		while (elementCount != newElementCount)
			elementData[--elementCount] = null;
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
