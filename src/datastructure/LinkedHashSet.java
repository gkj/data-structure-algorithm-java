
package datastructure;

/**
 * An alternative class for java.util.LinkedHashSet. No iterator.
 * 
 * @author Gilang Kusuma Jati
 *
 * @param <E>
 *            the type/class of elements held in this set.
 */
public class LinkedHashSet<E>
{

	private transient LinkedHashMap<E, Object> map;

	// Dummy value to associate with an Object in the backing Map
	private static final Object PRESENT = new Object();

	public LinkedHashSet()
	{
		map = new LinkedHashMap<>();
	}

	public LinkedHashSet(int initialCapacity, float loadFactor)
	{
		map = new LinkedHashMap<>(initialCapacity, loadFactor);
	}

	public LinkedHashSet(int initialCapacity)
	{
		map = new LinkedHashMap<>(initialCapacity);
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
	public LinkedHashSet<E> clone()
	{
		LinkedHashSet<E> clone = new LinkedHashSet<>();
		
		for(Object element : map.keys())
		{
			clone.add((E)element);
		}
		
		return clone;
	}
}
