package graph;

import java.io.File;
import java.util.Scanner;

import datastructure.HashMap;
import datastructure.Stack;

public class DepthFirstPaths<T>
{
	private HashMap<T, Boolean> marked; // marked[v] = true if v is reachable
										// from s
	private HashMap<T, T> edgeTo; // edgeTo[v] = last edge on path from s
									// to v
	private final T sourceVertex; // source vertex
	
	private final T[] vertices;

	/**
	 * Computes a directed path from <tt>s</tt> to every other vertex in
	 * DirectedGraph<T> <tt>G</tt>.
	 * 
	 * @param graph
	 *            the DirectedGraph<T>
	 * @param sourceVertex
	 *            the source vertex
	 */
	public DepthFirstPaths(DirectedGraph<T> graph, T sourceVertex)
	{
		vertices = graph.getVertices().toArray();
		
		this.sourceVertex = sourceVertex;
		marked = new HashMap<>(graph.getDataType(), Boolean.class);
		edgeTo = new HashMap<>(graph.getDataType(), graph.getDataType());

		for (T vertex : vertices)
		{
			marked.put(vertex, false);
			edgeTo.put(vertex, null);
		}

		dfs(graph, sourceVertex);
	}

	private void dfs(DirectedGraph<T> graph, T vertex)
	{
		marked.put(vertex, true);
		
		for (T w : graph.getAdjacency(vertex).toArray())
		{
			if (!marked.get(w))
			{
				edgeTo.put(w, vertex);
				dfs(graph, w);
			}
		}
	}

	/**
	 * Is there a directed path from the source vertex <tt>s</tt> to vertex
	 * <tt>v</tt>?
	 * 
	 * @param v
	 *            the vertex
	 * @return <tt>true</tt> if there is a directed path from the source vertex
	 *         <tt>s</tt> to vertex <tt>v</tt>, <tt>false</tt> otherwise
	 */
	public boolean hasPathTo(T v)
	{
		return marked.get(v);
	}

	/**
	 * Returns a directed path from the source vertex <tt>s</tt> to vertex
	 * <tt>v</tt>, or <tt>null</tt> if no such path.
	 * 
	 * @param vertex
	 *            the vertex
	 * @return the sequence of vertices on a directed path from the source
	 *         vertex <tt>s</tt> to vertex <tt>v</tt>, as an Iterable
	 */
	public Stack<T> pathTo(T vertex)
	{
		if (!hasPathTo(vertex))
			return null;
		Stack<T> path = new Stack<T>();
		for (T x = vertex; !x.equals(sourceVertex); x = edgeTo.get(x))
			path.push(x);
		path.push(sourceVertex);
		return path;
	}

	/**
	 * Unit tests the <tt>DepthFirstDirectedPaths</tt> data type.
	 */
	public static void main(String[] args)
	{
		File file = new File("data/directedgraph/tinyDAG.txt");
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

		int s = 0;
		DepthFirstPaths<Integer> dfs = new DepthFirstPaths<>(graph, s);

		for (int v = 0; v < G.V(); v++)
		{
			if (dfs.hasPathTo(v))
			{
				System.out.printf("%d to %d:  ", s, v);
				for (int x : dfs.pathTo(v))
				{
					if (x == s)
						System.out.print(x);
					else
						System.out.print("-" + x);
				}
				System.out.println();
			}

			else
			{
				System.out.printf("%d to %d:  not connected\n", s, v);
			}

		}
	}
}
