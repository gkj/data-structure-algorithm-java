package undirectedgraph;

import datastructure.HashMap;

/**
 * The <tt>DepthFirstSearch</tt> class represents a data type for determining
 * the vertices connected to a given source vertex <em>sourceVertex</em> in an
 * undirected graph.
 * 
 * @author Gilang Kusuma Jati
 *
 * @param <T>
 *            type of vertex used in the graph
 */
public class DepthFirstSearch<T>
{
	// connected.get(v) = is source vertex and vertex v connected?
	private HashMap<T, Boolean> connected;

	// number of vertices connected to source vertex
	private int count;

	/**
	 * Construct object of DepthFirstSearch.
	 * {@code DepthFirstSearch
	 * <Integer> queue = new DepthFirstSearch<Integer>(graph); }
	 * 
	 * 
	 * @param graph
	 *            undirected graph*
	 * @param sourceVertex
	 *            starting vertex
	 */
	@SuppressWarnings("unchecked")
	public DepthFirstSearch(UndirectedGraph<T> graph, T sourceVertex)
	{
		connected = new HashMap<>();

		for (Object vertex : graph.getVertices().toArray())
			connected.put((T) vertex, false);

		dfs(graph, sourceVertex);
	}

	// depth first search from vertex
	private void dfs(UndirectedGraph<T> graph, T vertex)
	{
		count++;
		connected.put(vertex, true);
		for (Object object : graph.getAdjacency(vertex).toArray())
		{
			@SuppressWarnings("unchecked")
			T w = (T) object;
			if (!connected.get((T) w))
			{
				dfs(graph, w);
			}
		}
	}

	public boolean isVisited(T vertex)
	{
		return connected.get(vertex);
	}

	// return the number of vertices connected to source vertex
	public int count()
	{
		return count;
	}
}
