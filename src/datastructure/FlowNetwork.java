/*
 * Represent flow network using adjacency list
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN
 *  
 */

package datastructure;

public class FlowNetwork
{
	private int totalVertices;
	private int totalEdges;
	private LinkedList<FlowEdge>[] adjacency;

	// initializes an empty flow network with V vertices and 0 edges.
	@SuppressWarnings("unchecked")
	public FlowNetwork(int totalVertices)
	{
		if (totalVertices < 0)
			throw new IllegalArgumentException("Number of vertices in a Graph must be nonnegative");

		this.totalVertices = totalVertices;
		this.totalEdges = 0;
		final LinkedList<FlowEdge>[] array = (LinkedList<FlowEdge>[]) java.lang.reflect.Array
				.newInstance(LinkedList.class, totalVertices);
		adjacency = array;
		for (int v = 0; v < totalVertices; v++)
			adjacency[v] = new LinkedList<FlowEdge>();
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

	public void insertEdge(FlowEdge e)
	{
		int v = e.getFrom();
		int w = e.getTo();
		validateVertex(v);
		validateVertex(w);
		adjacency[v].add(e);
		adjacency[w].add(e);
		totalEdges++;
	}

	// Returns the edges incident on vertex v (includes both edges pointing to
	// and from v).
	public LinkedList<FlowEdge> getAdjacency(int v)
	{
		validateVertex(v);
		return adjacency[v];
	}

	// return list of all edges - excludes self loops
	public LinkedList<FlowEdge> edges()
	{
		LinkedList<FlowEdge> list = new LinkedList<FlowEdge>();
		for (int v = 0; v < totalVertices; v++)
		{
			if (!adjacency[v].isEmpty())
			{
				FlowEdge[] edges = adjacency[v].toArray(new FlowEdge[adjacency[v].size()]);
				for (FlowEdge e : edges)
				{
					if (e.getTo() != v)
						list.add(e);
				}
			}
		}
		return list;
	}
}
