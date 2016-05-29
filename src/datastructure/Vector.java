package datastructure;

public class Vector<E>
{
	private transient int modCount = 0;

	protected E[] elementData;

	protected int elementCount;

	protected int capacityIncrement;
	
	private Class<E> elementType;

	@SuppressWarnings("unchecked")
	public Vector(int initialCapacity, int capacityIncrement, Class<E> elementType)
	{
		super();
		if (initialCapacity < 0)
			throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
		
		this.elementType = elementType;
		this.elementData = (E[]) java.lang.reflect.Array.newInstance(elementType, initialCapacity);
		this.capacityIncrement = capacityIncrement;
	}

	public Vector(int initialCapacity, Class<E> elementType)
	{
		this(initialCapacity, 0, elementType);
	}

	public Vector(Class<E> elementType)
	{
		this(10, elementType);
	}

	public synchronized void copyInto(E[] anArray)
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

	public boolean contains(E o)
	{
		return indexOf(o, 0) >= 0;
	}

	public int indexOf(E o)
	{
		return indexOf(o, 0);
	}

	public synchronized int indexOf(E o, int index)
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

	public synchronized int lastIndexOf(E o)
	{
		return lastIndexOf(o, elementCount - 1);
	}

	public synchronized int lastIndexOf(E o, int index)
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

	public synchronized boolean removeElement(E obj)
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

	public synchronized E[] toArray()
	{
		@SuppressWarnings("unchecked")
		E[] array = (E[]) java.lang.reflect.Array.newInstance(elementType, elementCount);
		System.arraycopy(elementData, 0, array, 0, elementCount);
		
		return array;
		
		//return copyOf(elementData, elementCount);
	}

	public synchronized E[] toArray(E[] a)
	{
		if (a.length < elementCount)
			return toArray();

		System.arraycopy(elementData, 0, a, 0, elementCount);

		if (a.length > elementCount)
			a[elementCount] = null;

		return a;
	}

	// Positional Access Operations
	public E elementData(int index)
	{
		return elementData[index];
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

	public boolean remove(E o)
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
		Vector<E> vector = new Vector<E>(elementType);
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
	public E[] copyOf(E[] original, int newLength)
	{
		E[] copy = (E[]) java.lang.reflect.Array.newInstance(elementType, newLength);
		System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
		return copy;
		
		//return (T[]) copyOf(original, newLength, original.getClass());
	}
}
