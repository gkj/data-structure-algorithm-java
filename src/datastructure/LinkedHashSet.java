
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
	private Class<E> componentType;

	// Dummy value to associate with an Object in the backing Map
	private static final Object PRESENT = new Object();

	public LinkedHashSet(Class<E> componentType)
	{
		this.componentType = componentType;
		map = new LinkedHashMap<>(componentType, Object.class);
	}

	public LinkedHashSet(int initialCapacity, float loadFactor, Class<E> componentType)
	{
		map = new LinkedHashMap<>(initialCapacity, loadFactor, componentType, Object.class);
	}

	public LinkedHashSet(int initialCapacity, Class<E> componentType)
	{
		map = new LinkedHashMap<>(initialCapacity, componentType, Object.class);
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
	public LinkedHashSet<E> clone()
	{
		LinkedHashSet<E> clone = new LinkedHashSet<>(componentType);
		
		for(Object element : map.keys())
		{
			clone.add((E)element);
		}
		
		return clone;
	}
}
