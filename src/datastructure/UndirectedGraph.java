/*
 * Undirected Graph implementation using adjacency list
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 * 
 */
package datastructure;

public class UndirectedGraph
{
	private int totalVertices;
	private int totalEdges;
	private LinkedList<Integer>[] adjacency;

	@SuppressWarnings("unchecked")
	public UndirectedGraph(int totalVertices)
	{
		this.totalVertices = totalVertices;
		totalEdges = 0;
		final LinkedList<Integer>[] array = (LinkedList<Integer>[]) java.lang.reflect.Array
				.newInstance(LinkedList.class, totalVertices);
		adjacency = array;

		for (int i = 0; i < totalVertices; i++)
		{
			adjacency[i] = new LinkedList<Integer>();
		}
	}

	public int getTotalVertices()
	{
		return totalVertices;
	}

	public int getTotalEdges()
	{
		return totalEdges;
	}

	public LinkedList<Integer>[] getAdjacency()
	{
		return adjacency;
	}

	public LinkedList<Integer> getAdjacency(int vertex)
	{
		validateVertex(vertex);
		return adjacency[vertex];
	}

	// throw and IndexOutOfBoundsException unless 0 <= v < totalVertices
	private void validateVertex(int vertex)
	{
		if (vertex < 0 || vertex >= totalVertices)
			throw new IndexOutOfBoundsException("vertex " + vertex + " is not between 0 and " + (totalVertices - 1));
	}

	public void insertEdge(int from, int to)
	{
		validateVertex(from);
		validateVertex(to);
		adjacency[from].add(to);
		adjacency[to].add(from);
		totalEdges++;
	}

	public void deleteEdge(int from, int to)
	{
		validateVertex(from);
		validateVertex(to);
		adjacency[from].remove(to);
		adjacency[to].remove(from);
		totalEdges--;
	}

	// return degree of a vertex
	public int getDegree(int vertex)
	{
		validateVertex(vertex);
		return adjacency[vertex].size();
	}

	public void printGraph()
	{
		for (int i = 0; i < totalVertices; i++)
		{
			System.out.print(i + ": ");
			adjacency[i].printList();
		}
	}
}
