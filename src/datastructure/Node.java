/*
 * Node represent a node for Stack and Queue
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 * 
 */
package datastructure;

public class Node<E extends Comparable<E>>
{
	E element;
	Node<E> next;

	public Node(E element, Node<E> next)
	{
		this.element = element;
		this.next = next;
	}

	public Node(E element)
	{
		this(element, null);
	}

	public Node()
	{
		this(null);
	}
}
