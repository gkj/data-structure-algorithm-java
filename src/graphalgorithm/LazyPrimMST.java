package graphalgorithm;

import datastructure.EdgeWeightedUndirectedGraph;
import datastructure.LinkedList;
import datastructure.MinimumPriorityQueue;
import datastructure.Queue;
import datastructure.UndirectedEdge;
import datastructure.UnionFind;

public class LazyPrimMST
{
	private static final double FLOATING_POINT_EPSILON = 1E-12;

	private double weight; // total weight of MST
	private Queue<UndirectedEdge> mst; // edges in the MST
	private boolean[] marked; // marked[v] = true if v on tree
	private MinimumPriorityQueue<UndirectedEdge> pq; // edges with one endpoint
														// in tree

	// compute a minimum spanning tree (or forest) of an edge-weighted graph.
	public LazyPrimMST(EdgeWeightedUndirectedGraph graph)
	{
		mst = new Queue<UndirectedEdge>();
		pq = new MinimumPriorityQueue<UndirectedEdge>(UndirectedEdge.class);
		marked = new boolean[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++) // run Prim from all
															// vertices to
			if (!marked[v])
				prim(graph, v); // get a minimum spanning forest

		// check optimality conditions
		assert check(graph);
	}

	// run Prim's algorithm
	private void prim(EdgeWeightedUndirectedGraph graph, int s)
	{
		scan(graph, s);
		while (!pq.isEmpty())
		{ // better to stop when mst has V-1 edges
			UndirectedEdge e = pq.delMin(); // smallest edge on pq
			int v = e.either(), w = e.other(v); // two endpoints
			assert marked[v] || marked[w];
			if (marked[v] && marked[w])
				continue; // lazy, both v and w already scanned
			mst.add(e); // add e to MST
			weight += e.getWeight();
			if (!marked[v])
				scan(graph, v); // v becomes part of tree
			if (!marked[w])
				scan(graph, w); // w becomes part of tree
		}
	}

	// add all edges e incident to v onto pq if the other endpoint has not yet
	// been scanned
	private void scan(EdgeWeightedUndirectedGraph graph, int v)
	{
		assert !marked[v];
		marked[v] = true;

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<UndirectedEdge> list = graph.getAdjacency(v);
			UndirectedEdge[] edges = list.toArray(new UndirectedEdge[list.size()]);

			for (UndirectedEdge e : edges)
				if (!marked[e.other(v)])
					pq.insert(e);
		}
	}

	// returns the edges in a minimum spanning tree (or forest).
	public Queue<UndirectedEdge> edges()
	{
		return mst;
	}

	// returns the sum of the edge weights in a minimum spanning tree (or
	// forest).
	public double getMSTWeight()
	{
		return weight;
	}

	// check optimality conditions (takes time proportional to E V lg* V)
	private boolean check(EdgeWeightedUndirectedGraph graph)
	{
		// check weight
		double totalWeight = 0.0;

		if (!mst.isEmpty())
		{
			UndirectedEdge[] edges = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : edges)
			{
				totalWeight += e.getWeight();
			}
		}

		if (Math.abs(totalWeight - getMSTWeight()) > FLOATING_POINT_EPSILON)
		{
			System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, getMSTWeight());
			return false;
		}

		// check that it is acyclic
		UnionFind uf = new UnionFind(graph.getTotalVertices());
		if (!mst.isEmpty())
		{
			UndirectedEdge[] edges = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : edges)
			{
				int v = e.either(), w = e.other(v);
				if (uf.isConnected(v, w))
				{
					System.err.println("Not a forest");
					return false;
				}
				uf.union(v, w);
			}
		}

		// check that it is a spanning forest
		if (!graph.getUndirectedEdges().isEmpty())
		{
			LinkedList<UndirectedEdge> list = graph.getUndirectedEdges();
			UndirectedEdge[] edges = list.toArray(new UndirectedEdge[list.size()]);

			for (UndirectedEdge e : edges)
			{
				int v = e.either(), w = e.other(v);
				if (!uf.isConnected(v, w))
				{
					System.err.println("Not a spanning forest");
					return false;
				}
			}
		}

		// check that it is a minimal spanning forest (cut optimality
		// conditions)
		if (!mst.isEmpty())
		{
			UndirectedEdge[] edges = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : edges)
			{
				// all edges in MST except e
				uf = new UnionFind(graph.getTotalVertices());
				UndirectedEdge[] temp = edges;
				for (UndirectedEdge f : temp)
				{
					int x = f.either(), y = f.other(x);
					if (f != e)
						uf.union(x, y);
				}

				// check that e is min weight edge in crossing cut
				if (!graph.getUndirectedEdges().isEmpty())
				{
					LinkedList<UndirectedEdge> list = graph.getUndirectedEdges();
					UndirectedEdge[] arr = list.toArray(new UndirectedEdge[list.size()]);

					for (UndirectedEdge f : arr)
					{
						int x = f.either(), y = f.other(x);
						if (!uf.isConnected(x, y))
						{
							if (f.getWeight() < e.getWeight())
							{
								System.err.println("Edge " + f + " violates cut optimality conditions");
								return false;
							}
						}
					}
				}
			}
		}

		return true;
	}
}
