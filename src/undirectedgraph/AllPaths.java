package undirectedgraph;

import datastructure.HashMap;
import datastructure.Stack;

public class AllPaths<T>
{
	// vertices in current path
	private HashMap<T, Boolean> onPath;

	// the current path
	private Stack<T> path;

	// number of simple path
	private int numberOfPaths;

	// show all simple paths from s to t - use DFS
	public AllPaths(UndirectedGraph<T> G, T s, T t)
	{
		onPath = new HashMap<>();
		
		Object[] vertices = G.getVertices().toArray();
		for(Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			onPath.put(vertex, false);
		}
		
		path = new Stack<T>();
		
		dfs(G, s, t);
	}

	// use DFS
	private void dfs(UndirectedGraph<T> G, T v, T t)
	{

		// add v to current path
		path.push(v);
		onPath.put(v, true);

		// found path from s to t
		if (v == t)
		{
			processCurrentPath();
			numberOfPaths++;
		}

		// consider all neighbors that would continue path with repeating a node
		else
		{
			Object[] adj = G.getAdjacency(v).toArray();
			for (Object object : adj)
			{
				@SuppressWarnings("unchecked")
				T w = (T) object;
				if (!onPath.get(w))
					dfs(G, w, t);
			}
		}

		// done exploring from v, so remove from path
		path.pop();
		onPath.put(v, false);
	}

	// this implementation just prints the path to standard output
	private void processCurrentPath()
	{
		Stack<T> reverse = new Stack<T>();
		
		Object[] pathArr = path.toArray();
		
		for (Object object : pathArr)
		{
			@SuppressWarnings("unchecked")
			T v = (T) object;
			reverse.push(v);
		}
		
		if (reverse.size() >= 1)
			System.out.print(reverse.pop());
		
		while (!reverse.isEmpty())
			System.out.print("-" + reverse.pop());
		
		System.out.println();
	}

	// return number of simple paths between s and t
	public int numberOfPaths()
	{
		return numberOfPaths;
	}

	// test client
	public static void main(String[] args)
	{
		UndirectedGraph<Integer> graph = new UndirectedGraph<>();

		// add vertex
		for (int i = 0; i < 7; i++)
			graph.addVertex(i);

		// add edge
		graph.addEdge(0, 1);
		graph.addEdge(0, 2);
		graph.addEdge(2, 3);
		graph.addEdge(3, 4);
		graph.addEdge(2, 5);
		graph.addEdge(1, 5);
		graph.addEdge(5, 4);
		graph.addEdge(3, 6);
		graph.addEdge(4, 6);
		
		System.out.println(graph);

		System.out.println();
		System.out.println("all simple paths between 0 and 6:");
		
		AllPaths<Integer> allpaths1 = new AllPaths<>(graph, 0, 6);
		System.out.println("# paths = " + allpaths1.numberOfPaths());

		System.out.println();
		System.out.println("all simple paths between 1 and 5:");
		
		AllPaths<Integer> allpaths2 = new AllPaths<>(graph, 1, 5);
		System.out.println("# paths = " + allpaths2.numberOfPaths());
	}

}
