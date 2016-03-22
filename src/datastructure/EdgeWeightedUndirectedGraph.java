/*
 * Edge-weighted undirected graph using adjacency list
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN
 *  
 */
package datastructure;

public class EdgeWeightedUndirectedGraph
{
	private int totalVertices;
	private int totalEdges;
	private LinkedList<UndirectedEdge>[] adjacency;

	@SuppressWarnings("unchecked")
	public EdgeWeightedUndirectedGraph(int V)
	{
		if (V < 0)
			throw new IllegalArgumentException("Number of vertices must be nonnegative");
		this.totalVertices = V;
		this.totalEdges = 0;

		final LinkedList<UndirectedEdge>[] array = (LinkedList<UndirectedEdge>[]) java.lang.reflect.Array
				.newInstance(LinkedList.class, this.totalVertices);
		adjacency = array;

		for (int v = 0; v < V; v++)
		{
			adjacency[v] = new LinkedList<UndirectedEdge>();
		}
	}

	// returns the number of vertices in the UndirectedEdge-weighted graph.
	public int getTotalVertices()
	{
		return totalVertices;
	}

	// returns the number of UndirectedEdges in the UndirectedEdge-weighted
	// graph.
	public int getTotalEdges()
	{
		return totalEdges;
	}

	// throw an IndexOutOfBoundsException unless 0 <= v < V
	private void validateVertex(int vertex)
	{
		if (vertex < 0 || vertex >= totalVertices)
			throw new IndexOutOfBoundsException("vertex " + vertex + " is not between 0 and " + (totalVertices - 1));
	}

	public void insertEdge(UndirectedEdge e)
	{
		int v = e.either();
		int w = e.other(v);
		validateVertex(v);
		validateVertex(w);
		adjacency[v].add(e);
		adjacency[w].add(e);
		totalEdges++;
	}

	public void deleteEdge(UndirectedEdge e)
	{
		int v = e.either();
		int w = e.other(v);
		validateVertex(v);
		validateVertex(w);
		adjacency[v].remove(e);
		adjacency[w].remove(e);
		totalEdges--;
	}

	// get adjacency of a vertex
	public LinkedList<UndirectedEdge> getAdjacency(int vertex)
	{
		validateVertex(vertex);
		return adjacency[vertex];
	}

	// return degree of a vertex
	public int getDegree(int vertex)
	{
		validateVertex(vertex);
		return adjacency[vertex].size();
	}

	public LinkedList<UndirectedEdge> getUndirectedEdges()
	{
		LinkedList<UndirectedEdge> list = new LinkedList<UndirectedEdge>();
		for (int v = 0; v < totalVertices; v++)
		{
			int selfLoops = 0;

			if (!adjacency[v].isEmpty())
			{
				UndirectedEdge[] adj = adjacency[v].toArray(new UndirectedEdge[adjacency[v].size()]);
				for (UndirectedEdge e : adj)
				{
					if (e.other(v) > v)
					{
						list.add(e);
					}
					// only add one copy of each self loop (self loops will be
					// consecutive)
					else if (e.other(v) == v)
					{
						if (selfLoops % 2 == 0)
							list.add(e);
						selfLoops++;
					}
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
