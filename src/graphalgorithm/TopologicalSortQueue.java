/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedGraph;
import datastructure.LinkedList;
import datastructure.Queue;

public class TopologicalSortQueue
{
	private Queue<Integer> order; // vertices in topological order
	private int[] rank; // rank[v] = order where vertex v appers in order

	public TopologicalSortQueue(DirectedGraph graph)
	{
		// compute indegrees
		int[] indegree = new int[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
		{
			if (!graph.getAdjacency(v).isEmpty())
			{
				LinkedList<Integer> list = graph.getAdjacency(v);
				Integer[] adj = list.toArray(new Integer[list.size()]);

				for (int w : adj)
				{
					indegree[w]++;
				}
			}
		}

		// initialize
		rank = new int[graph.getTotalVertices()];
		order = new Queue<Integer>();
		int count = 0;

		// initialize queue to contain all vertices with indegree = 0
		Queue<Integer> queue = new Queue<Integer>();
		for (int v = 0; v < graph.getTotalVertices(); v++)
			if (indegree[v] == 0)
				queue.add(v);

		while (!queue.isEmpty())
		{
			int v = queue.poll();
			order.add(v);
			rank[v] = count++;

			if (!graph.getAdjacency(v).isEmpty())
			{
				LinkedList<Integer> list = graph.getAdjacency(v);
				Integer[] adj = list.toArray(new Integer[list.size()]);
				for (int w : adj)
				{
					indegree[w]--;
					if (indegree[w] == 0)
						queue.add(w);
				}
			}
		}

		if (count != graph.getTotalVertices())
		{
			order = null;
		}
	}

	// return true if graph is a directed acyclic graph (DAG)
	public boolean isDAG()
	{
		return order != null;
	}

	// the vertices in topological order; null if G is not a DAG
	public Queue<Integer> getOrder()
	{
		return order;
	}

	// the rank of vertex v in topological order; -1 if G is not a DAG
	public int getRank(int v)
	{
		if (isDAG())
			return rank[v];
		else
			return -1;
	}

	// certify that DirectedGraph is acyclic
	@SuppressWarnings("unused")
	private boolean check(DirectedGraph graph)
	{
		// DirectedGraph is acyclic
		if (isDAG())
		{
			// check that ranks are a permutation of 0 to V-1
			boolean[] found = new boolean[graph.getTotalVertices()];
			for (int i = 0; i < graph.getTotalVertices(); i++)
			{
				found[getRank(i)] = true;
			}
			for (int i = 0; i < graph.getTotalVertices(); i++)
			{
				if (!found[i])
				{
					System.err.println("No vertex with rank " + i);
					return false;
				}
			}

			// check that ranks provide a valid toplogical order
			for (int v = 0; v < graph.getTotalVertices(); v++)
			{
				if (!graph.getAdjacency(v).isEmpty())
				{
					LinkedList<Integer> list = graph.getAdjacency(v);
					Integer[] adj = list.toArray(new Integer[list.size()]);
					for (int w : adj)
					{
						if (getRank(v) > getRank(w))
						{
							System.err.printf("%d-%d: rank(%d) = %d, rank(%d) = %d\n", v, w, v, getRank(v), w,
									getRank(w));
							return false;
						}
					}
				}
			}

			// check that order() is consistent with rank()
			int r = 0;

			if (!order.isEmpty())
			{
				Integer[] arr = order.toArray(Integer.class);
				for (int v : arr)
				{
					if (getRank(v) != r)
					{
						System.err.println("order() and rank() inconsistent");
						return false;
					}
					r++;
				}
			}
		}

		return true;
	}
}
