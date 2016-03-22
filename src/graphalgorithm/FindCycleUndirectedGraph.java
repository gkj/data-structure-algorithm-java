/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.LinkedList;
import datastructure.Stack;
import datastructure.UndirectedGraph;

public class FindCycleUndirectedGraph
{
	private boolean[] visited;
	private int[] parent;
	private Stack<Integer> cycle;

	// determines whether the undirected graph has a cycle and, if so, finds
	// such a cycle.
	public FindCycleUndirectedGraph(UndirectedGraph graph)
	{
		if (hasSelfLoop(graph))
			return;

		if (hasParallelEdges(graph))
			return;

		visited = new boolean[graph.getTotalVertices()];
		parent = new int[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			if (!visited[v])
				dfs(graph, -1, v);
	}

	// return true iff graph have a self loop
	// side effect: initialize cycle to be self loop
	private boolean hasSelfLoop(UndirectedGraph graph)
	{
		for (int v = 0; v < graph.getTotalVertices(); v++)
		{
			if (!graph.getAdjacency(v).isEmpty())
			{
				LinkedList<Integer> list = graph.getAdjacency(v);
				Integer[] adj = list.toArray(new Integer[list.size()]);

				for (int w : adj)
				{
					if (v == w)
					{
						cycle = new Stack<Integer>();
						cycle.push(v);
						cycle.push(v);
						return true;
					}
				}
			}
		}
		return false;
	}

	// return true if graph have two parallel edges
	// side effect: initialize cycle to be two parallel edges
	private boolean hasParallelEdges(UndirectedGraph graph)
	{
		visited = new boolean[graph.getTotalVertices()];

		for (int v = 0; v < graph.getTotalVertices(); v++)
		{
			if (!graph.getAdjacency(v).isEmpty())
			{
				LinkedList<Integer> list = graph.getAdjacency(v);
				Integer[] adj = list.toArray(new Integer[list.size()]);

				// check for parallel edges incident to v
				for (int w : adj)
				{
					if (visited[w])
					{
						cycle = new Stack<Integer>();
						cycle.push(v);
						cycle.push(w);
						cycle.push(v);
						return true;
					}
					visited[w] = true;
				}

				// reset so visited[v] = false for all v
				for (int w : adj)
				{
					visited[w] = false;
				}
			}
		}
		return false;
	}

	// return true if graph has cycle
	public boolean hasCycle()
	{
		return cycle != null;
	}

	// returns a cycle if the graph has a cycle, and null otherwise
	public Stack<Integer> cycle()
	{
		return cycle;
	}

	private void dfs(UndirectedGraph graph, int u, int v)
	{
		visited[v] = true;

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<Integer> list = graph.getAdjacency(v);
			Integer[] adj = list.toArray(new Integer[list.size()]);

			for (int w : adj)
			{
				// short circuit if cycle already found
				if (cycle != null)
					return;

				if (!visited[w])
				{
					parent[w] = v;
					dfs(graph, v, w);
				}

				// check for cycle (but disregard reverse of edge leading to v)
				else if (w != u)
				{
					cycle = new Stack<Integer>();
					for (int x = v; x != w; x = parent[x])
					{
						cycle.push(x);
					}
					cycle.push(w);
					cycle.push(v);
				}
			}
		}
	}
}
