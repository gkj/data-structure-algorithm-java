/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedGraph;
import datastructure.LinkedList;
import datastructure.Stack;

public class StronglyConnectedComponent
{
	private boolean[] visited; // visited[v] = has vertex v been visited?
	private int[] id; // id[v] = id of strong component containing v
	private int count; // number of strongly-connected components

	// computes the strong components of the directed graph.
	public StronglyConnectedComponent(DirectedGraph graph)
	{

		// compute reverse postorder of reverse graph
		DepthFirstOrder dfs = new DepthFirstOrder(graph.reverse());

		// run DFS on G, using reverse postorder to guide calculation
		visited = new boolean[graph.getTotalVertices()];
		id = new int[graph.getTotalVertices()];

		if (!dfs.getReversePostOrder().isEmpty())
		{
			Stack<Integer> list = dfs.getReversePostOrder();
			Integer[] arr = list.toArray(new Integer[list.size()]);

			for (int v : arr)
			{
				if (!visited[v])
				{
					dfs(graph, v);
					count++;
				}
			}
		}

		// check that id[] gives strong components
		// assert check(graph);
	}

	// DFS on graph G
	private void dfs(DirectedGraph graph, int v)
	{
		visited[v] = true;
		id[v] = count;

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<Integer> list = graph.getAdjacency(v);
			Integer[] adj = list.toArray(new Integer[list.size()]);
			for (int w : adj)
			{
				if (!visited[w])
					dfs(graph, w);
			}
		}
	}

	// return the number of strong components.
	public int count()
	{
		return count;
	}

	// return true if vertices v and w is in the same strong component
	public boolean isStronglyConnected(int v, int w)
	{
		return id[v] == id[w];
	}

	// Returns the component id of the strong component containing vertex v.
	public int getId(int v)
	{
		return id[v];
	}

	// does the id[] array contain the strongly connected components?
	@SuppressWarnings("unused")
	private boolean check(DirectedGraph graph)
	{
		TransitiveClosure tc = new TransitiveClosure(graph);
		for (int v = 0; v < graph.getTotalVertices(); v++)
		{
			for (int w = 0; w < graph.getTotalVertices(); w++)
			{
				if (isStronglyConnected(v, w) != (tc.isReachable(v, w) && tc.isReachable(w, v)))
					return false;
			}
		}
		return true;
	}
}
