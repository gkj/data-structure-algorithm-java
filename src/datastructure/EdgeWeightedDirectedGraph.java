/*
 * EdgeWeightedDirectedGraph an directed graph that have a weight on each edge
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 * 
 */
package datastructure;

public class EdgeWeightedDirectedGraph
{
	private final int totalVertices;
	private int totalEdges;
	private LinkedList<DirectedEdge>[] adjacency;

	@SuppressWarnings("unchecked")
	public EdgeWeightedDirectedGraph(int totalVertices)
	{
		if (totalVertices < 0)
			throw new IllegalArgumentException("Number of vertices in a Digraph must be nonnegative");

		this.totalVertices = totalVertices;
		this.totalEdges = 0;

		final LinkedList<DirectedEdge>[] array = (LinkedList<DirectedEdge>[]) java.lang.reflect.Array
				.newInstance(LinkedList.class, this.totalVertices);
		adjacency = array;

		for (int v = 0; v < totalVertices; v++)
			adjacency[v] = new LinkedList<DirectedEdge>();
	}

	public int getTotalVertices()
	{
		return totalVertices;
	}

	public int getTotalEdges()
	{
		return totalEdges;
	}

	// throw an IndexOutOfBoundsException unless 0 <= v < V
	private void validateVertex(int v)
	{
		if (v < 0 || v >= totalVertices)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (totalVertices - 1));
	}

	public void insertEdge(DirectedEdge e)
	{
		int v = e.getFrom();
		int w = e.getTo();
		validateVertex(v);
		validateVertex(w);
		adjacency[v].add(e);
		totalEdges++;
	}

	public void deleteEdge(DirectedEdge e)
	{
		int v = e.getFrom();
		int w = e.getTo();
		validateVertex(v);
		validateVertex(w);
		adjacency[v].remove(e);
		totalEdges--;
	}

	// Returns the list of directed edges incident from vertex v.
	public LinkedList<DirectedEdge> getAdjacency(int v)
	{
		validateVertex(v);
		return adjacency[v];
	}

	// Returns the number of directed edges incident from vertex v (out degree).
	public int outdegree(int v)
	{
		validateVertex(v);
		return adjacency[v].size();
	}

	// returns all directed edges in the edge-weighted digraph.
	public LinkedList<DirectedEdge> edges()
	{
		LinkedList<DirectedEdge> list = new LinkedList<DirectedEdge>();
		for (int v = 0; v < totalVertices; v++)
		{
			if (!getAdjacency(v).isEmpty())
			{
				DirectedEdge[] adj = getAdjacency(v).toArray(new DirectedEdge[getAdjacency(v).size()]);
				for (DirectedEdge e : adj)
				{
					list.add(e);
				}
			}
		}
		return list;
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
