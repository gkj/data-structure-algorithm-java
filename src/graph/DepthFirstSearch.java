package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import datastructure.HashMap;

public class DepthFirstSearch<T>
{
	private HashMap<T, Boolean> marked; // marked[v] = true if v is reachable
	// from source (or sources)
	private int count; // number of vertices reachable from s

	/**
	 * Computes the vertices in digraph <tt>G</tt> that are reachable from the
	 * source vertex <tt>s</tt>.
	 * 
	 * @param G
	 *            the digraph
	 * @param s
	 *            the source vertex
	 */
	@SuppressWarnings("unchecked")
	public DepthFirstSearch(DirectedGraph<T> G, T s)
	{
		marked = new HashMap<>();
		for (Object vertex : G.getVertices().toArray())
			marked.put((T) vertex, false);

		dfs(G, s);
	}

	/**
	 * Computes the vertices in digraph <tt>G</tt> that are connected to any of
	 * the source vertices <tt>sources</tt>.
	 * 
	 * @param G
	 *            the graph
	 * @param sources
	 *            the source vertices
	 */
	@SuppressWarnings("unchecked")
	public DepthFirstSearch(DirectedGraph<T> G, T[] sources)
	{
		marked = new HashMap<>();
		for (Object vertex : G.getVertices().toArray())
			marked.put((T) vertex, false);

		for (T v : sources)
		{
			if (!marked.get(v))
				dfs(G, v);
		}
	}

	private void dfs(DirectedGraph<T> G, T v)
	{
		count++;
		marked.put(v, true);
		for (Object object : G.getAdjacency(v).toArray())
		{
			@SuppressWarnings("unchecked")
			T w = (T) object;
			if (!marked.get(w))
				dfs(G, w);
		}
	}

	/**
	 * Is there a directed path from the source vertex (or any of the source
	 * vertices) and vertex <tt>v</tt>?
	 * 
	 * @param v
	 *            the vertex
	 * @return <tt>true</tt> if there is a directed path, <tt>false</tt>
	 *         otherwise
	 */
	public boolean marked(int v)
	{
		return marked.get(v);
	}

	/**
	 * Returns the number of vertices reachable from the source vertex (or
	 * source vertices).
	 * 
	 * @return the number of vertices reachable from the source vertex (or
	 *         source vertices)
	 */
	public int count()
	{
		return count;
	}

	/**
	 * Unit tests the <tt>DirectedDFS</tt> data type.
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException
	{
		File file = new File("data/directedgraph/tinyDG.txt");
		Scanner in = new Scanner(file);

		DirectedGraph<Integer> graph = new DirectedGraph<>();

		while (in.hasNextLine())
		{
			String raw = in.nextLine();
			String[] data = raw.trim().split(" ");

			int from = Integer.parseInt(data[0].trim());
			int to = Integer.parseInt(data[1].trim());
			
			graph.addVertex(from);
			graph.addVertex(to);
			graph.addEdge(from, to);
		}

		System.out.println(graph);
		
		in.close();

		// read in sources from command-line arguments
		Integer[] sources = {2};

		// multiple-source reachability
		DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(graph, sources);

		// print out vertices reachable from sources
		for (Integer v: graph.getVertices().toArray(new Integer[graph.getVertices().size()]))
		{
			if (dfs.marked(v))
				System.out.print(v + " ");
		}
		System.out.println();
	}
}
