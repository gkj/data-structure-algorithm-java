package graphalgorithm;

import datastructure.DirectedEdge;
import datastructure.EdgeWeightedDirectedGraph;
import datastructure.LinkedList;
import datastructure.Queue;
import datastructure.Stack;

public class BellmanFordShortestPath
{
	private double[] distanceTo; // distanceTo[v] = distance of shortest s->v
									// path
	private DirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v
									// path
	private boolean[] onQueue; // onQueue[v] = is vertex v currently on the
								// queue?
	private Queue<Integer> queue; // queue of vertices to relax
	private int cost; // number of calls to relax()
	private Stack<DirectedEdge> cycle; // negative cycle (or null if no such
										// cycle)

	// computes a shortest paths tree from s to every other vertex in the
	// edge-weighted digraph G.
	public BellmanFordShortestPath(EdgeWeightedDirectedGraph graph, int s)
	{
		distanceTo = new double[graph.getTotalVertices()];
		edgeTo = new DirectedEdge[graph.getTotalVertices()];
		onQueue = new boolean[graph.getTotalVertices()];

		for (int v = 0; v < graph.getTotalVertices(); v++)
			distanceTo[v] = Double.POSITIVE_INFINITY;
		distanceTo[s] = 0.0;

		// Bellman-Ford algorithm
		queue = new Queue<Integer>();
		queue.add(s);
		onQueue[s] = true;

		while (!queue.isEmpty() && !hasNegativeCycle())
		{
			int v = queue.poll();
			onQueue[v] = false;
			relax(graph, v);
		}

		assert check(graph, s);
	}

	// relax vertex v and put other endpoints on queue if changed
	private void relax(EdgeWeightedDirectedGraph graph, int v)
	{
		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<DirectedEdge> list = graph.getAdjacency(v);
			DirectedEdge[] adj = list.toArray(new DirectedEdge[list.size()]);
			for (DirectedEdge e : adj)
			{
				int w = e.getTo();
				if (distanceTo[w] > distanceTo[v] + e.getWeight())
				{
					distanceTo[w] = distanceTo[v] + e.getWeight();
					edgeTo[w] = e;
					if (!onQueue[w])
					{
						queue.add(w);
						onQueue[w] = true;
					}
				}
				if (cost++ % graph.getTotalVertices() == 0)
				{
					findNegativeCycle();
					if (hasNegativeCycle())
						return; // found a negative cycle
				}
			}
		}
	}

	// return true if there is a negative cycle reachable from the source vertex
	// s
	public boolean hasNegativeCycle()
	{
		return cycle != null;
	}

	// returns a negative cycle reachable from the source vertex s, or null if
	// there is no such cycle.
	public Stack<DirectedEdge> negativeCycle()
	{
		return cycle;
	}

	// by finding a cycle in predecessor graph
	private void findNegativeCycle()
	{
		int V = edgeTo.length;
		EdgeWeightedDirectedGraph spt = new EdgeWeightedDirectedGraph(V);
		for (int v = 0; v < V; v++)
			if (edgeTo[v] != null)
				spt.insertEdge(edgeTo[v]);

		FindCycleEdgeWeightedDirectedGraph finder = new FindCycleEdgeWeightedDirectedGraph(spt);
		cycle = finder.getCycle();
	}

	// returns the length of a shortest path from the source vertex s to vertex
	// v.
	public double distanceTo(int v)
	{
		if (hasNegativeCycle())
			throw new UnsupportedOperationException("Negative cost cycle exists");

		return distanceTo[v];
	}

	// return true if there is a path from the source vertex to vertex v
	public boolean hasPathTo(int v)
	{
		return distanceTo[v] < Double.POSITIVE_INFINITY;
	}

	// returns a shortest path from the source s to vertex v.
	public Stack<DirectedEdge> pathTo(int v)
	{
		if (hasNegativeCycle())
			throw new UnsupportedOperationException("Negative cost cycle exists");

		if (!hasPathTo(v))
			return null;

		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.getFrom()])
		{
			path.push(e);
		}

		return path;
	}

	// check optimality conditions: either
	// (i) there exists a negative cycle reacheable from s
	// or
	// (ii) for all edges e = v->w: distanceTo[w] <= distanceTo[v] +
	// e.getWeight()
	// (ii') for all edges e = v->w on the SPT: distanceTo[w] == distanceTo[v] +
	// e.getWeight()
	private boolean check(EdgeWeightedDirectedGraph graph, int s)
	{
		// has a negative cycle
		if (hasNegativeCycle())
		{
			double weight = 0.0;

			DirectedEdge[] temp = cycle.toArray(new DirectedEdge[cycle.size()]);
			for (DirectedEdge e : temp)
			{
				weight += e.getWeight();
			}

			if (weight >= 0.0)
			{
				System.err.println("error: weight of negative cycle = " + weight);
				return false;
			}
		}
		// no negative cycle reachable from source
		else
		{
			// check that distanceTo[v] and edgeTo[v] are consistent
			if (distanceTo[s] != 0.0 || edgeTo[s] != null)
			{
				System.err.println("distanceTo[s] and edgeTo[s] inconsistent");
				return false;
			}
			for (int v = 0; v < graph.getTotalVertices(); v++)
			{
				if (v == s)
					continue;

				if (edgeTo[v] == null && distanceTo[v] != Double.POSITIVE_INFINITY)
				{
					System.err.println("distanceTo[] and edgeTo[] inconsistent");
					return false;
				}
			}

			// check that all edges e = v->w satisfy distanceTo[w] <=
			// distanceTo[v] + e.getWeight()
			for (int v = 0; v < graph.getTotalVertices(); v++)
			{
				if (!graph.getAdjacency(v).isEmpty())
				{
					LinkedList<DirectedEdge> list = graph.getAdjacency(v);
					DirectedEdge[] adj = list.toArray(new DirectedEdge[list.size()]);
					for (DirectedEdge e : adj)
					{
						int w = e.getTo();
						if (distanceTo[v] + e.getWeight() < distanceTo[w])
						{
							System.err.println("edge " + e + " not relaxed");
							return false;
						}
					}
				}
			}

			// check that all edges e = v->w on SPT satisfy distanceTo[w] ==
			// distanceTo[v] + e.getWeight()
			for (int w = 0; w < graph.getTotalVertices(); w++)
			{
				if (edgeTo[w] == null)
					continue;

				DirectedEdge e = edgeTo[w];
				int v = e.getFrom();
				if (w != e.getTo())
					return false;

				if (distanceTo[v] + e.getWeight() != distanceTo[w])
				{
					System.err.println("edge " + e + " on shortest path not tight");
					return false;
				}
			}
		}

		return true;
	}

}
