/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedGraph;
import datastructure.LinkedList;
import datastructure.Queue;
import datastructure.Stack;

public class FindPathDirectedGraphBFS
{
	private static final int INFINITY = Integer.MAX_VALUE;
	private boolean[] visited;
	private int[] parent;
	private int[] distanceTo;

	// computes the shortest path from sourceVertex and every other vertex in
	// graph.
	public FindPathDirectedGraphBFS(DirectedGraph graph, int sourceVertex)
	{
		visited = new boolean[graph.getTotalVertices()];
		distanceTo = new int[graph.getTotalVertices()];
		parent = new int[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			distanceTo[v] = INFINITY;
		bfs(graph, sourceVertex);
	}

	// BFS from single source
	private void bfs(DirectedGraph graph, int s)
	{
		Queue<Integer> q = new Queue<Integer>();
		visited[s] = true;
		distanceTo[s] = 0;
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

	// returns the number of edges in a shortest path from the source vertex to
	// vertex v
	public int getDistanceTo(int v)
	{
		return distanceTo[v];
	}

	// returns a path between the source vertex and vertex v, or return null if
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
