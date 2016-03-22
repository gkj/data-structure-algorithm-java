/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedGraph;

public class TransitiveClosure
{
	private SimpleDFSOnDirectedGraph[] tc; // tc[v] = is reachable from v

	public TransitiveClosure(DirectedGraph graph)
	{
		tc = new SimpleDFSOnDirectedGraph[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			tc[v] = new SimpleDFSOnDirectedGraph(graph, v);
	}

	// return true if there is a directed path from vertex v to vertex w in the
	// directed graph
	public boolean isReachable(int v, int w)
	{
		return tc[v].hasPathTo(w);
	}

}
