package graphalgorithm;

import datastructure.EdgeWeightedUndirectedGraph;
import datastructure.LinkedList;
import datastructure.UndirectedEdge;
import datastructure.UnionFind;

public class BoruvkaMST
{
	private static final double FLOATING_POINT_EPSILON = 1E-12;

	private LinkedList<UndirectedEdge> mst = new LinkedList<UndirectedEdge>(); // edges
																				// in
																				// MST
	private double weight; // weight of MST

	// compute a minimum spanning tree of an edge-weighted undirected graph.
	public BoruvkaMST(EdgeWeightedUndirectedGraph G)
	{
		UnionFind uf = new UnionFind(G.getTotalVertices());

		// repeat at most log V times or until we have V-1 edges
		for (int t = 1; t < G.getTotalVertices() && mst.size() < G.getTotalVertices() - 1; t = t + t)
		{
			// foreach tree in forest, find closest edge
			// if edge weights are equal, ties are broken in favor of first edge
			// in G.edges()
			UndirectedEdge[] closest = new UndirectedEdge[G.getTotalVertices()];

			if (!G.getUndirectedEdges().isEmpty())
			{
				LinkedList<UndirectedEdge> list = G.getUndirectedEdges();
				UndirectedEdge[] edges = list.toArray(new UndirectedEdge[list.size()]);
				for (UndirectedEdge e : edges)
				{
					int v = e.either(), w = e.other(v);
					int i = uf.find(v), j = uf.find(w);
					if (i == j)
						continue; // same tree
					if (closest[i] == null || less(e, closest[i]))
						closest[i] = e;
					if (closest[j] == null || less(e, closest[j]))
						closest[j] = e;
				}
			}

			// add newly discovered edges to MST
			for (int i = 0; i < G.getTotalVertices(); i++)
			{
				UndirectedEdge e = closest[i];
				if (e != null)
				{
					int v = e.either(), w = e.other(v);
					// don't add the same edge twice
					if (!uf.isConnected(v, w))
					{
						mst.add(e);
						weight += e.getWeight();
						uf.union(v, w);
					}
				}
			}
		}

		// check optimality conditions
		assert check(G);
	}

	// returns the edges in a minimum spanning tree
	public LinkedList<UndirectedEdge> edges()
	{
		return mst;
	}

	// returns the sum of the edge weights in a minimum spanning tree
	public double getMSTWeight()
	{
		return weight;
	}

	// return true if the weight of edge e is strictly less than that of edge f
	private static boolean less(UndirectedEdge e, UndirectedEdge f)
	{
		return e.getWeight() < f.getWeight();
	}

	// check optimality conditions (takes time proportional to E V lg* V)
	private boolean check(EdgeWeightedUndirectedGraph G)
	{
		// check weight
		double totalWeight = 0.0;
		if (!mst.isEmpty())
		{
			UndirectedEdge[] edges = mst.toArray(new UndirectedEdge[mst.size()]);
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
		UnionFind uf = new UnionFind(G.getTotalVertices());
		if (!mst.isEmpty())
		{
			UndirectedEdge[] edges = mst.toArray(new UndirectedEdge[mst.size()]);
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
		if (!G.getUndirectedEdges().isEmpty())
		{
			LinkedList<UndirectedEdge> list = G.getUndirectedEdges();
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
			UndirectedEdge[] edges = mst.toArray(new UndirectedEdge[mst.size()]);
			for (UndirectedEdge e : edges)
			{
				// all edges in MST except e
				uf = new UnionFind(G.getTotalVertices());

				UndirectedEdge[] temp = edges;
				for (UndirectedEdge f : temp)
				{
					int x = f.either(), y = f.other(x);
					if (f != e)
						uf.union(x, y);
				}

				// check that e is min weight edge in crossing cut
				if (!G.getUndirectedEdges().isEmpty())
				{
					LinkedList<UndirectedEdge> list = G.getUndirectedEdges();
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
