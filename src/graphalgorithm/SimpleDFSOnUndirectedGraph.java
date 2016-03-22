/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.LinkedList;
import datastructure.UndirectedGraph;

public class SimpleDFSOnUndirectedGraph
{
	private boolean[] visited;

	// total vertex connected to source vertex
	private int count;

	// computes the vertices in graph G that are connected to the source vertex.
	public SimpleDFSOnUndirectedGraph(UndirectedGraph graph, int sourceVertex)
	{
		visited = new boolean[graph.getTotalVertices()];
		dfs(graph, sourceVertex);
	}

	// depth first search from vertex v
	private void dfs(UndirectedGraph graph, int v)
	{
		count++;
		visited[v] = true;

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<Integer> list = graph.getAdjacency(v);
			Integer[] adj = list.toArray(new Integer[list.size()]);

			for (int w : adj)
			{
				if (!visited[w])
				{
					dfs(graph, w);
				}
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
