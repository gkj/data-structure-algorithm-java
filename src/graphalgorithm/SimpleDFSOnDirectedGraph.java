/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedGraph;
import datastructure.LinkedList;

public class SimpleDFSOnDirectedGraph
{
	private boolean[] visited;
	private int count;

	public SimpleDFSOnDirectedGraph(DirectedGraph graph, int sourceVertex)
	{
		visited = new boolean[graph.getTotalVertices()];
		dfs(graph, sourceVertex);
	}

	public SimpleDFSOnDirectedGraph(DirectedGraph graph, Iterable<Integer> sources)
	{
		visited = new boolean[graph.getTotalVertices()];
		for (int v : sources)
		{
			if (!visited[v])
				dfs(graph, v);
		}
	}

	private void dfs(DirectedGraph graph, int vertex)
	{
		count++;
		visited[vertex] = true;

		if (!graph.getAdjacency(vertex).isEmpty())
		{
			LinkedList<Integer> list = graph.getAdjacency(vertex);
			Integer[] adj = list.toArray(new Integer[list.size()]);

			for (int w : adj)
			{
				if (!visited[w])
					dfs(graph, w);
			}
		}
	}

	// return true if there is a path between the source vertex and vertex v
	public boolean hasPathTo(int v)
	{
		return visited[v];
	}

	// returns the number of vertices connected to the source vertex
	public int count()
	{
		return count;
	}
}
