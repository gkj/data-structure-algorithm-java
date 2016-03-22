/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedEdge;
import datastructure.EdgeWeightedDirectedGraph;
import datastructure.LinkedList;
import datastructure.Stack;

public class FindCycleEdgeWeightedDirectedGraph
{
	private boolean[] visited;
	private DirectedEdge[] parent;
	private boolean[] onStack;
	private Stack<DirectedEdge> cycle;

	// determines whether the edge-weighted directed graph has a directed cycle
	// and, if so, finds such a cycle.
	public FindCycleEdgeWeightedDirectedGraph(EdgeWeightedDirectedGraph graph)
	{
		visited = new boolean[graph.getTotalVertices()];
		onStack = new boolean[graph.getTotalVertices()];
		parent = new DirectedEdge[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			if (!visited[v])
				dfs(graph, v);
	}

	// check that algorithm computes either the topological order or finds a
	// directed cycle
	private void dfs(EdgeWeightedDirectedGraph graph, int v)
	{
		onStack[v] = true;
		visited[v] = true;

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<DirectedEdge> list = graph.getAdjacency(v);
			DirectedEdge[] adj = list.toArray(new DirectedEdge[list.size()]);
			for (DirectedEdge e : adj)
			{
				int w = e.getTo();

				// short circuit if directed cycle found
				if (cycle != null)
					return;

				// found new vertex, so recur
				else if (!visited[w])
				{
					parent[w] = e;
					dfs(graph, w);
				}

				// trace back directed cycle
				else if (onStack[w])
				{
					cycle = new Stack<DirectedEdge>();
					while (e.getFrom() != w)
					{
						cycle.push(e);
						e = parent[e.getFrom()];
					}
					cycle.push(e);
					return;
				}
			}
		}

		onStack[v] = false;
	}

	// return true if the edge-weighted directed graph has a directed cycle
	public boolean hasCycle()
	{
		return cycle != null;
	}

	// return a directed cycle if the edge-weighted directed graph has a
	// directed cycle, and null otherwise.
	public Stack<DirectedEdge> getCycle()
	{
		return cycle;
	}

	// certify that directed graph is either acyclic or has a directed cycle
	@SuppressWarnings("unused")
	private boolean check(EdgeWeightedDirectedGraph graph)
	{
		// edge-weighted directed graph is cyclic
		if (hasCycle())
		{
			// verify cycle
			DirectedEdge first = null, last = null;

			if (!cycle.isEmpty())
			{
				DirectedEdge[] arr = cycle.toArray(new DirectedEdge[cycle.size()]);
				for (DirectedEdge e : arr)
				{
					if (first == null)
						first = e;

					if (last != null)
					{
						if (last.getTo() != e.getFrom())
						{
							System.out.printf("cycle edges %s and %s not incident\n", last, e);
							return false;
						}
					}
					last = e;
				}
			}

			if (last.getTo() != first.getFrom())
			{
				System.out.printf("cycle edges %s and %s not incident\n", last, first);
				return false;
			}
		}

		return true;
	}
}
