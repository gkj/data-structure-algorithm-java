package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import datastructure.HashMap;

public class DepthFirstSearch<T>
{
	// marked[v] = true if v is reachable
	private HashMap<T, Boolean> marked; 
	
	// from source (or sources)
	private int count; // number of vertices reachable from s

	private T[] vertices;
	
	/**
	 * Computes the vertices in digraph <tt>G</tt> that are reachable from the
	 * source vertex <tt>s</tt>.
	 * 
	 * @param graph
	 *            the digraph
	 * @param sourceVertex
	 *            the source vertex
	 */
	public DepthFirstSearch(DirectedGraph<T> graph, T sourceVertex)
	{
		vertices = graph.getVertices().toArray();
		
		marked = new HashMap<>(graph.getDataType(), Boolean.class);
		
		for (T vertex : vertices)
			marked.put(vertex, false);

		dfs(graph, sourceVertex);
	}

	/**
	 * Computes the vertices in digraph <tt>G</tt> that are connected to any of
	 * the source vertices <tt>sources</tt>.
	 * 
	 * @param graph
	 *            the graph
	 * @param sources
	 *            the source vertices
	 */
	public DepthFirstSearch(DirectedGraph<T> graph, T[] sources)
	{
		vertices = graph.getVertices().toArray();
		
		marked = new HashMap<>(graph.getDataType(), Boolean.class);
		
		for (T vertex : vertices)
			marked.put(vertex, false);

		for (T v : sources)
		{
			if (!marked.get(v))
				dfs(graph, v);
		}
	}

	private void dfs(DirectedGraph<T> G, T v)
	{
		count++;
		marked.put(v, true);
		for (T w : G.getAdjacency(v).toArray())
		{
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

		DirectedGraph<Integer> graph = new DirectedGraph<>(Integer.class);

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
		for (Integer v: graph.getVertices().toArray())
		{
			if (dfs.marked(v))
				System.out.print(v + " ");
		}
		System.out.println();
	}
}
