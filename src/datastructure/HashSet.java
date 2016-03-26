
package datastructure;

/**
 * An alternative class for java.util.HashSet. No iterator.
 * 
 * @author Gilang Kusuma Jati
 *
 * @param <E>
 *            the type/class of elements held in this set.
 */
public class HashSet<E>
{
	private transient HashMap<E, Object> map;

	// Dummy value to associate with an Object in the backing Map
	private static final Object PRESENT = new Object();

	public HashSet()
	{
		map = new HashMap<>();
	}

	public HashSet(int initialCapacity, float loadFactor)
	{
		map = new HashMap<>(initialCapacity, loadFactor);
	}

	public HashSet(int initialCapacity)
	{
		map = new HashMap<>(initialCapacity);
	}

	public int size()
	{
		return map.size();
	}

	public boolean isEmpty()
	{
		return map.isEmpty();
	}

	public boolean contains(Object o)
	{
		return map.containsKey(o);
	}

	public boolean add(E e)
	{
		return map.put(e, PRESENT) == null;
	}

	public boolean remove(Object o)
	{
		return map.remove(o) == PRESENT;
	}

	public void clear()
	{
		map.clear();
	}

	public Object[] toArray()
	{
		return map.keys();
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<E> clone()
	{
		HashSet<E> clone = new HashSet<>();
		
		for(Object element : map.keys())
		{
			clone.add((E)element);
		}
		
		return clone;
	}
}
