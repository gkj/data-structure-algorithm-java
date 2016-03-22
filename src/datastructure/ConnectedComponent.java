/*
 * Connected Component
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 * 
 */
package datastructure;

public class ConnectedComponent
{
	// visited[v] = has vertex v has been visited ?
	private boolean[] visited;

	// id[v] = id of connected component containing vertex v
	private int[] id;

	// size[id] = number of vertices in given component
	private int[] size;

	// number of connected components
	private int count;

	/**
	 * Computes the connected components of the undirected graph.
	 * 
	 * @param graph
	 *            the undirected graph
	 */
	public ConnectedComponent(UndirectedGraph graph)
	{
		visited = new boolean[graph.getTotalVertices()];
		id = new int[graph.getTotalVertices()];
		size = new int[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
		{
			if (!visited[v])
			{
				dfs(graph, v);
				count++;
			}
		}
	}

	// depth-first search
	private void dfs(UndirectedGraph graph, int vertex)
	{
		visited[vertex] = true;
		id[vertex] = count;
		size[count]++;

		if (!graph.getAdjacency(vertex).isEmpty())
		{
			LinkedList<Integer> list = graph.getAdjacency(vertex);
			Integer[] adj = list.toArray(new Integer[list.size()]);
			for (int w : adj)
			{
				if (!visited[w])
				{
					dfs(graph, w);
				}
			}
		}
	}

	// returns the component id of the connected component containing vertex v.
	public int id(int vertex)
	{
		return id[vertex];
	}

	// returns the number of vertices in the connected component containing
	// vertex v.
	public int size(int vertex)
	{
		return size[id[vertex]];
	}

	// returns the number of connected components.
	public int count()
	{
		return count;
	}

	// check are the vertices v and w in the same connected component?
	public boolean isConnected(int v, int w)
	{
		return id(v) == id(w);
	}
}
