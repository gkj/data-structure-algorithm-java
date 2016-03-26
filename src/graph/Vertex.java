package graph;

public class Vertex<T>
{
	private T id;
	
	public Vertex(T id)
	{
		this.id = id;
	}
	
	public T getId()
	{
		return id;
	}
	
	public String toString()
	{
		return id.toString();
	}
}
