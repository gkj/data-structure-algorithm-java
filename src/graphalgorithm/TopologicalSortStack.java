/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedGraph;
import datastructure.EdgeWeightedDirectedGraph;
import datastructure.Stack;

public class TopologicalSortStack
{
	private Stack<Integer> order; // topological order

	// determines whether the DirectedGraph has a topological order and, if so,
	// finds such a topological order.
	public TopologicalSortStack(DirectedGraph graph)
	{
		FindCycleDirectedGraph finder = new FindCycleDirectedGraph(graph);
		if (!finder.hasCycle())
		{
			DepthFirstOrder dfs = new DepthFirstOrder(graph);
			order = dfs.getReversePostOrder();
		}
	}

	// determines whether the edge-weighted DirectedGraph has a topological
	// order and, if so, finds such an order.
	public TopologicalSortStack(EdgeWeightedDirectedGraph graph)
	{
		FindCycleEdgeWeightedDirectedGraph finder = new FindCycleEdgeWeightedDirectedGraph(graph);
		if (!finder.hasCycle())
		{
			DepthFirstOrder dfs = new DepthFirstOrder(graph);
			order = dfs.getReversePostOrder();
		}
	}

	// returns a topological order if the DirectedGraph has a topologial order,
	// and null otherwise.
	public Stack<Integer> getOrder()
	{
		return order;
	}

	// return true if the DirectedGraph has a topological order (or
	// equivalently, if the DirectedGraph is a DAG), and false otherwise
	public boolean hasOrder()
	{
		return order != null;
	}
}
