package undirectedgraph;

import datastructure.HashMap;
import datastructure.Stack;

/**
 * The <tt>DepthFirstPaths</tt> class represents a data type for finding paths
 * from a source vertex <em>sourceVertex</em> to every other vertex in an
 * undirected graph.
 * 
 * @author Gilang Kusuma Jati
 *
 * @param <T>
 *            type of vertex used in the graph
 */
public class DepthFirstPaths<T>
{
	// connected.get(v) = is source vertex and vertex v connected?
	private HashMap<T, Boolean> connected;

	// edgeTo[v] = last edge on s-v path
	private HashMap<T, T> edgeTo;

	// source vertex
	private final T sourceVertex;

	/**
	 * Construct object of DepthFirstPaths.
	 * 
	 * {@code DepthFirstPaths
	 * <Integer> queue = new DepthFirstPaths<Integer>(graph); }
	 * 
	 * @param graph
	 * @param sourceVertex
	 */
	public DepthFirstPaths(UndirectedGraph<T> graph, T sourceVertex)
	{
		this.sourceVertex = sourceVertex;
		edgeTo = new HashMap<>();
		connected = new HashMap<>();

		for (Object object : graph.getVertices().toArray())
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			edgeTo.put(vertex, null);
			connected.put(vertex, false);
		}

		dfs(graph, sourceVertex);
	}

	// depth first search from v
	private void dfs(UndirectedGraph<T> graph, T vertex)
	{
		connected.put(vertex, true);

		for (Object object : graph.getAdjacency(vertex).toArray())
		{
			@SuppressWarnings("unchecked")
			T w = (T) object;

			if (!connected.get(w))
			{
				edgeTo.put(w, vertex);
				dfs(graph, w);
			}
		}
	}

	public boolean hasPathTo(T vertex)
	{
		return connected.get(vertex);
	}

	public Stack<T> getPathTo(T vertex)
	{
		if (!hasPathTo(vertex))
			return null;

		Stack<T> path = new Stack<T>();
		for (T x = vertex; x != sourceVertex; x = edgeTo.get(x))
			path.push(x);

		path.push(sourceVertex);
		return path;
	}
}
