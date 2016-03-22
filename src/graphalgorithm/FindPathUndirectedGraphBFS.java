/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.LinkedList;
import datastructure.Queue;
import datastructure.Stack;
import datastructure.UndirectedGraph;

public class FindPathUndirectedGraphBFS
{
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] visited;
	private int[] parent;
	private int[] distanceTo;

	// Computes the shortest path between the source vertex and every other
	// vertex in the graph G.
	public FindPathUndirectedGraphBFS(UndirectedGraph graph, int s)
	{
		visited = new boolean[graph.getTotalVertices()];
		distanceTo = new int[graph.getTotalVertices()];
		parent = new int[graph.getTotalVertices()];
		bfs(graph, s);
	}

	// breadth-first search from a single source
	private void bfs(UndirectedGraph graph, int s)
	{
		Queue<Integer> q = new Queue<Integer>();

		for (int v = 0; v < graph.getTotalVertices(); v++)
			distanceTo[v] = INFINITY;

		distanceTo[s] = 0;
		visited[s] = true;
		q.add(s);

		while (!q.isEmpty())
		{
			int v = q.poll();

			if (!graph.getAdjacency(v).isEmpty())
			{
				LinkedList<Integer> list = graph.getAdjacency(v);
				Integer[] adj = list.toArray(new Integer[list.size()]);

				for (int w : adj)
				{
					if (!visited[w])
					{
						parent[w] = v;
						distanceTo[w] = distanceTo[v] + 1;
						visited[w] = true;
						q.add(w);
					}
				}
			}
		}
	}

	// return true there if there is a path between the source vertex and vertex
	// v
	public boolean hasPathTo(int v)
	{
		return visited[v];
	}

	// return the number of edges in a shortest path between the source vertex
	// and vertex v
	public int getDistanceTo(int v)
	{
		return distanceTo[v];
	}

	// return a shortest path between the source vertex and vertex v, or null if
	// no such path.
	public Stack<Integer> getPathTo(int v)
	{
		if (!hasPathTo(v))
			return null;

		Stack<Integer> path = new Stack<Integer>();
		int x;
		for (x = v; distanceTo[x] != 0; x = parent[x])
			path.push(x);

		path.push(x);

		return path;
	}
}
