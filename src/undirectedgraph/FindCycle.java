package undirectedgraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

import datastructure.HashMap;
import datastructure.Stack;

public class FindCycle<T>
{
	private HashMap<T, Boolean> marked;
	private HashMap<T, T> edgeTo;
	private Stack<T> cycle;

	private Object[] vertices;

	/**
	 * Determines whether the undirected graph <tt>G</tt> has a cycle and, if
	 * so, finds such a cycle.
	 *
	 * @param G
	 *            the undirected graph
	 */
	public FindCycle(UndirectedGraph<T> G)
	{
		vertices = G.getVertices().toArray();

		if (hasSelfLoop(G))
			return;
		if (hasParallelEdges(G))
			return;

		marked = new HashMap<>();
		edgeTo = new HashMap<>();

		for (Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T v = (T) object;
			marked.put(v, false);
			edgeTo.put(v, null);
		}

		for (Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T v = (T) object;
			if (!marked.get(v))
				dfs(G, null, v);
		}
	}

	// does this graph have a self loop?
	// side effect: initialize cycle to be self loop
	private boolean hasSelfLoop(UndirectedGraph<T> G)
	{
		for (Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T v = (T) object;

			for (Object obj : G.getAdjacency(v).toArray())
			{
				@SuppressWarnings("unchecked")
				T w = (T) obj;
				if (v.equals(w))
				{
					cycle = new Stack<T>();
					cycle.push(v);
					cycle.push(v);
					return true;
				}
			}
		}
		return false;
	}

	// does this graph have two parallel edges?
	// side effect: initialize cycle to be two parallel edges
	private boolean hasParallelEdges(UndirectedGraph<T> G)
	{
		marked = new HashMap<>();
		for (Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T v = (T) object;
			marked.put(v, false);
		}

		for (Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T v = (T) object;

			Object[] adj = G.getAdjacency(v).toArray();

			// check for parallel edges incident to v
			for (Object obj : adj)
			{
				@SuppressWarnings("unchecked")
				T w = (T) obj;

				if (marked.get(w))
				{
					cycle = new Stack<T>();
					cycle.push(v);
					cycle.push(w);
					cycle.push(v);
					return true;
				}
				marked.put(w, true);
			}

			// reset so marked[v] = false for all v
			for (Object obj : adj)
			{
				@SuppressWarnings("unchecked")
				T w = (T) obj;
				marked.put(w, false);
			}
		}
		return false;
	}

	/**
	 * Returns true if the graph <tt>G</tt> has a cycle.
	 *
	 * @return <tt>true</tt> if the graph has a cycle; <tt>false</tt> otherwise
	 */
	public boolean hasCycle()
	{
		return cycle != null;
	}

	/**
	 * Returns a cycle in the graph <tt>G</tt>.
	 * 
	 * @return a cycle if the graph <tt>G</tt> has a cycle, and <tt>null</tt>
	 *         otherwise
	 */
	public Stack<T> getCycle()
	{
		return cycle;
	}

	private void dfs(UndirectedGraph<T> G, T u, T v)
	{
		marked.put(v, true);
		for (Object object : G.getAdjacency(v).toArray())
		{
			@SuppressWarnings("unchecked")
			T w = (T) object;

			// short circuit if cycle already found
			if (cycle != null)
				return;

			if (!marked.get(w))
			{
				edgeTo.put(w, v);
				dfs(G, v, w);
			}

			// check for cycle (but disregard reverse of edge leading to v)
			else if (!w.equals(u))
			{
				cycle = new Stack<T>();
				for (T x = v; !x.equals(w); x = edgeTo.get(x))
				{
					cycle.push(x);
				}
				cycle.push(w);
				cycle.push(v);
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException
	{
		File file = new File("data/undirectedgraph/routes.txt");
		Scanner in = new Scanner(file);
		
		UndirectedGraph<String> graph = new UndirectedGraph<>();

		while (in.hasNextLine())
		{
			String[] data = in.nextLine().split(" ");

			graph.addVertex(data[0]);
			graph.addVertex(data[1]);
			graph.addEdge(data[0], data[1]);
		}

		System.out.println(graph);

		FindCycle<String> finder = new FindCycle<>(graph);

		if (finder.hasCycle())
		{
			System.out.println("Graph has a cycle");
			Object[] cycle = finder.getCycle().toArray();
			System.out.println("Cycle : ");
			for (Object o : cycle)
			{
				System.out.print(o + " ");
			}
		}
		else
		{
			System.out.println("Graph doesn't have a cycle");
		}
		
		in.close();
	}
}
