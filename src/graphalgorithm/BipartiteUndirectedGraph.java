/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.LinkedList;
import datastructure.Stack;
import datastructure.UndirectedGraph;

public class BipartiteUndirectedGraph
{
	// is the graph bipartite?
	private boolean isBipartite;

	// color[v] gives vertices on one side of bipartition
	private boolean[] color;

	// visited[v] = true if v has been visited in DFS
	private boolean[] visited;

	// parent[v] = last edge on path to v
	private int[] parent;

	// odd-length cycle
	private Stack<Integer> cycle;

	// Determines whether an undirected graph is bipartite and finds either a
	// bipartition or an odd-length cycle.
	public BipartiteUndirectedGraph(UndirectedGraph graph)
	{
		// assume graph is bipartite at first
		isBipartite = true;

		color = new boolean[graph.getTotalVertices()];
		visited = new boolean[graph.getTotalVertices()];
		parent = new int[graph.getTotalVertices()];

		for (int v = 0; v < graph.getTotalVertices(); v++)
		{
			if (!visited[v])
			{
				dfs(graph, v);
			}
		}
	}

	private void dfs(UndirectedGraph graph, int v)
	{
		visited[v] = true;

		if (!graph.getAdjacency(v).isEmpty())
		{
			Integer[] adj = graph.getAdjacency(v).toArray(new Integer[graph.getAdjacency(v).size()]);
			for (int w : adj)
			{
				// short circuit if odd-length cycle found
				if (cycle != null)
					return;

				// found uncolored vertex, so recur
				if (!visited[w])
				{
					parent[w] = v;
					color[w] = !color[v];
					dfs(graph, w);
				}

				// if v-w create an odd-length cycle, find it
				else if (color[w] == color[v])
				{
					isBipartite = false;
					cycle = new Stack<Integer>();
					cycle.push(w); // don't need this unless you want to include
									// start vertex twice
					for (int x = v; x != w; x = parent[x])
					{
						cycle.push(x);
					}
					cycle.push(w);
				}
			}
		}
	}

	public boolean isBipartite()
	{
		return isBipartite;
	}

	// returns the side of the bipartite that vertex v is on. (the color either
	// true or false)
	public boolean getColor(int v)
	{
		if (!isBipartite)
			throw new UnsupportedOperationException("Graph is not bipartite");

		return color[v];
	}

	// Returns an odd-length cycle if the graph is not bipartite, and null
	// otherwise.
	public Stack<Integer> getOddCycle()
	{
		return cycle;
	}

	@SuppressWarnings("unused")
	private boolean check(UndirectedGraph graph)
	{
		// graph is bipartite
		if (isBipartite)
		{
			for (int v = 0; v < graph.getTotalVertices(); v++)
			{
				if (!graph.getAdjacency(v).isEmpty())
				{
					LinkedList<Integer> list = graph.getAdjacency(v);
					Integer[] adj = list.toArray(new Integer[list.size()]);

					for (int w : adj)
					{
						if (color[v] == color[w])
						{
							System.out.printf("edge %d-%d with %d and %d in same side of bipartition\n", v, w, v, w);
							return false;
						}
					}
				}
			}
		}

		// graph has an odd-length cycle
		else
		{
			// verify cycle
			int first = -1, last = -1;
			Integer[] oddCycle = getOddCycle().toArray(new Integer[getOddCycle().size()]);
			for (int v : oddCycle)
			{
				if (first == -1)
					first = v;
				last = v;
			}
			if (first != last)
			{
				System.err.printf("cycle begins with %d and ends with %d\n", first, last);
				return false;
			}
		}

		return true;
	}
}
