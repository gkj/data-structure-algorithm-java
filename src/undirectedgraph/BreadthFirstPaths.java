package undirectedgraph;

import datastructure.HashMap;
import datastructure.Queue;
import datastructure.Stack;

/**
 * The <tt>BreadthFirstPaths</tt> class represents a data type for finding
 * shortest paths (number of edges) from a source vertex <em>sourceVertex</em> (or a set of
 * source vertices) to every other vertex in an undirected graph.
 * 
 * @author Gilang Kusuma Jati
 *
 * @param <T>
 */
public class BreadthFirstPaths<T>
{
	private static final int INFINITY = Integer.MAX_VALUE;

	// marked.get(v) = is there an s-v path
	private HashMap<T, Boolean> connected;

	// edgeTo.get(v) = previous edge on shortest s-v path
	private HashMap<T, T> edgeTo;

	// distTo.get(v) = number of edges shortest s-v path
	private HashMap<T, Integer> distTo;

	/**
	 * Computes the shortest path between the source vertex <tt>s</tt> and every
	 * other vertex in the graph <tt>G</tt>.
	 * 
	 * @param graph
	 *            the graph
	 * @param sourceVertex
	 *            the source vertex
	 */
	public BreadthFirstPaths(UndirectedGraph<T> graph, T sourceVertex)
	{
		connected = new HashMap<>();
		distTo = new HashMap<>();
		edgeTo = new HashMap<>();

		for (Object object : graph.getVertices().toArray())
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			connected.put(vertex, false);
			distTo.put(vertex, 0);
			edgeTo.put(vertex, null);
		}

		bfs(graph, sourceVertex);

		assert check(graph, sourceVertex);
	}

	/**
	 * Computes the shortest path between any one of the source vertices in
	 * <tt>sources</tt> and every other vertex in graph <tt>G</tt>.
	 * 
	 * @param graph
	 *            the graph
	 * @param sources
	 *            the source vertices
	 */
	public BreadthFirstPaths(UndirectedGraph<T> graph, T[] sources)
	{
		connected = new HashMap<>();
		distTo = new HashMap<>();
		edgeTo = new HashMap<>();

		for (Object object : graph.getVertices().toArray())
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			connected.put(vertex, false);
			distTo.put(vertex, INFINITY);
			edgeTo.put(vertex, null);
		}

		bfs(graph, sources);
	}

	// breadth-first search from a single source
	@SuppressWarnings("unchecked")
	private void bfs(UndirectedGraph<T> graph, T sourceVertex)
	{
		Queue<T> queue = new Queue<T>();

		for (Object v : graph.getVertices().toArray())
		{
			distTo.put((T) v, INFINITY);
		}

		distTo.put(sourceVertex, 0);
		connected.put(sourceVertex, true);
		queue.add(sourceVertex);

		while (!queue.isEmpty())
		{
			T v = queue.poll();
			for (Object object : graph.getAdjacency(v).toArray())
			{
				T w = (T) object;
				if (!connected.get(w))
				{
					edgeTo.put(w, v);
					distTo.put(w, distTo.get(v) + 1);
					connected.put(w, true);

					queue.add(w);
				}
			}
		}
	}

	// breadth-first search from multiple sources
	private void bfs(UndirectedGraph<T> G, T[] sources)
	{
		Queue<T> queue = new Queue<T>();
		for (T s : sources)
		{
			connected.put(s, true);
			distTo.put(s, 0);
			queue.add(s);
		}
		while (!queue.isEmpty())
		{
			T v = queue.poll();
			for (Object object : G.getAdjacency(v).toArray())
			{
				@SuppressWarnings("unchecked")
				T w = (T) object;

				if (!connected.get(w))
				{
					edgeTo.put(w, v);
					distTo.put(w, distTo.get(v) + 1);
					connected.put(w, true);
					queue.add(w);
				}
			}
		}
	}

	/**
	 * Is there a path between the source vertex <tt>s</tt> (or sources) and
	 * vertex <tt>v</tt>?
	 * 
	 * @param vertex
	 *            the vertex
	 * @return <tt>true</tt> if there is a path, and <tt>false</tt> otherwise
	 */
	public boolean hasPathTo(T vertex)
	{
		return connected.get(vertex);
	}

	/**
	 * Returns the number of edges in a shortest path between the source vertex
	 * <tt>s</tt> (or sources) and vertex <tt>v</tt>?
	 * 
	 * @param vertex
	 *            the vertex
	 * @return the number of edges in a shortest path
	 */
	public int distTo(T vertex)
	{
		return distTo.get(vertex);
	}

	/**
	 * Returns a shortest path between the source vertex <tt>s</tt> (or sources)
	 * and <tt>v</tt>, or <tt>null</tt> if no such path.
	 * 
	 * @param vertex
	 *            the vertex
	 * @return the sequence of vertices on a shortest path, as an Iterable
	 */
	public Stack<T> pathTo(T vertex)
	{
		if (!hasPathTo(vertex))
			return null;
		Stack<T> path = new Stack<T>();
		T x;
		for (x = vertex; distTo.get(x) != null; x = edgeTo.get(x))
			path.push(x);

		path.push(x);

		return path;
	}

	// check optimality conditions for single source
	@SuppressWarnings("unchecked")
	private boolean check(UndirectedGraph<T> G, T sourceVertex)
	{
		Object[] vertices = G.getVertices().toArray();

		// check that the distance of s = 0
		if (distTo.get(sourceVertex) != 0)
		{
			System.out.println("distance of source " + sourceVertex + " to itself = " + distTo.get(sourceVertex));
			return false;
		}

		// check that for each edge v-w dist[w] <= dist[v] + 1
		// provided v is reachable from s
		for (Object object : vertices)
		{
			T vertex = (T) object;
			for (Object obj : G.getAdjacency(vertex).toArray())
			{
				T other = (T) obj;
				if (hasPathTo(vertex) != hasPathTo(other))
				{
					System.out.println("edge " + vertex + "-" + other);
					System.out.println("hasPathTo(" + vertex + ") = " + hasPathTo(vertex));
					System.out.println("hasPathTo(" + other + ") = " + hasPathTo(other));
					return false;
				}
				if (hasPathTo(vertex) && (distTo.get(other) > distTo.get(vertex) + 1))
				{
					System.out.println("edge " + vertex + "-" + other);
					System.out.println("distTo[" + vertex + "] = " + distTo.get(vertex));
					System.out.println("distTo[" + other + "] = " + distTo.get(other));
					return false;
				}
			}
		}

		// check that v = edgeTo.get(w) satisfies distTo.get(w) + distTo.get(v)
		// + 1
		// provided v is reachable from s
		for (Object object : vertices)
		{
			T other = (T) object;
			if (!hasPathTo(other) || other == sourceVertex)
				continue;
			T vertex = edgeTo.get(other);
			if (distTo.get(other) != distTo.get(vertex) + 1)
			{
				System.out.println("shortest path edge " + vertex + "-" + other);
				System.out.println("distTo[" + vertex + "] = " + distTo.get(vertex));
				System.out.println("distTo[" + other + "] = " + distTo.get(other));
				return false;
			}
		}

		return true;
	}
}
