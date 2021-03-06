package datastructure;

public class Stack<E> extends Vector<E>
{
	public Stack(Class<E> elementType)
	{
		super(elementType);
	}

	public E push(E item)
	{
		addElement(item);

		return item;
	}

	public synchronized E pop()
	{
		E obj;
		int len = size();

		obj = peek();
		removeElementAt(len - 1);

		return obj;
	}

	public synchronized E peek()
	{
		int len = size();

		if (len == 0)
			throw new RuntimeException("Stack is empty");
		return elementAt(len - 1);
	}

	public boolean empty()
	{
		return size() == 0;
	}

	public synchronized int search(E o)
	{
		int i = lastIndexOf(o);

		if (i >= 0)
		{
			return size() - i;
		}
		return -1;
	}
}
