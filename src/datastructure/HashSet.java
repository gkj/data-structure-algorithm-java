
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
	
	private Class<E> componentType;

	public HashSet(Class<E> componentType)
	{
		this.componentType = componentType;
		map = new HashMap<>(componentType, Object.class);
	}

	public HashSet(int initialCapacity, float loadFactor, Class<E> componentType)
	{
		this.componentType = componentType;
		map = new HashMap<>(initialCapacity, loadFactor, componentType, Object.class);
	}

	public HashSet(int initialCapacity, Class<E> componentType)
	{
		this.componentType = componentType;
		map = new HashMap<>(initialCapacity, componentType, Object.class);
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

	public E[] toArray()
	{
		return map.keys();
	}
	
	@SuppressWarnings("unchecked")
	public HashSet<E> clone()
	{
		HashSet<E> clone = new HashSet<>(componentType);
		
		for(Object element : map.keys())
		{
			clone.add((E)element);
		}
		
		return clone;
	}
}
