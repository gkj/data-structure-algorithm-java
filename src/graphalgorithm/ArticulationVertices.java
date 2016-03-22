/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.LinkedList;
import datastructure.UndirectedGraph;

public class ArticulationVertices
{
	private int[] low;
	private int[] pre;
	private int cnt;
	private boolean[] articulation;

	public ArticulationVertices(UndirectedGraph graph)
	{
		low = new int[graph.getTotalVertices()];
		pre = new int[graph.getTotalVertices()];
		articulation = new boolean[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			low[v] = -1;
		for (int v = 0; v < graph.getTotalVertices(); v++)
			pre[v] = -1;

		for (int v = 0; v < graph.getTotalVertices(); v++)
			if (pre[v] == -1)
				dfs(graph, v, v);
	}

	private void dfs(UndirectedGraph graph, int u, int v)
	{
		int children = 0;
		pre[v] = cnt++;
		low[v] = pre[v];

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<Integer> list = graph.getAdjacency(v);
			Integer[] adj = list.toArray(new Integer[list.size()]);
			for (int w : adj)
			{
				if (pre[w] == -1)
				{
					children++;
					dfs(graph, v, w);

					// update low number
					low[v] = Math.min(low[v], low[w]);

					// non-root of DFS is an articulation point if low[w] >=
					// pre[v]
					if (low[w] >= pre[v] && u != v)
						articulation[v] = true;
				}

				// update low number - ignore reverse of edge leading to v
				else if (w != u)
					low[v] = Math.min(low[v], pre[w]);
			}
		}

		// root of DFS is an articulation point if it has more than 1 child
		if (u == v && children > 1)
			articulation[v] = true;
	}

	// return true if vertex v is an articulation point
	public boolean isArticulation(int v)
	{
		return articulation[v];
	}
}
