package graphalgorithm;

import datastructure.EdgeWeightedUndirectedGraph;
import datastructure.LinkedList;
import datastructure.MinimumPriorityQueue;
import datastructure.Queue;
import datastructure.UndirectedEdge;
import datastructure.UnionFind;

public class KruskalMST
{
	private static final double FLOATING_POINT_EPSILON = 1E-12;

	private double weight; // weight of MST
	private Queue<UndirectedEdge> mst = new Queue<UndirectedEdge>(); // edges in
																		// MST

	// compute a minimum spanning tree (or forest) of an edge-weighted graph.
	public KruskalMST(EdgeWeightedUndirectedGraph graph)
	{
		MinimumPriorityQueue<UndirectedEdge> pq = new MinimumPriorityQueue<UndirectedEdge>(UndirectedEdge.class);
		if (!graph.getUndirectedEdges().isEmpty())
		{
			LinkedList<UndirectedEdge> list = graph.getUndirectedEdges();
			UndirectedEdge[] edges = list.toArray(new UndirectedEdge[list.size()]);

			for (UndirectedEdge e : edges)
			{
				pq.insert(e);
			}
		}

		// run greedy algorithm
		UnionFind uf = new UnionFind(graph.getTotalVertices());
		while (!pq.isEmpty() && mst.size() < graph.getTotalVertices() - 1)
		{
			UndirectedEdge e = pq.delMin();
			int v = e.either();
			int w = e.other(v);
			if (!uf.isConnected(v, w)) // if v-w does not create a cycle
			{
				uf.union(v, w); // merge v and w components
				mst.add(e); // add edge e to mst
				weight += e.getWeight();
			}
		}

		// check optimality conditions
		assert check(graph);
	}

	// returns the edges in a minimum spanning tree (or forest).
	public Queue<UndirectedEdge> getMSTEdges()
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
		// check total weight
		double total = 0.0;

		if (!mst.isEmpty())
		{
			UndirectedEdge[] edges = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : edges)
			{
				total += e.getWeight();
			}
		}

		if (Math.abs(total - getMSTWeight()) > FLOATING_POINT_EPSILON)
		{
			System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", total, getMSTWeight());
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
				UndirectedEdge[] other = edges;
				for (UndirectedEdge f : other)
				{
					int x = f.either(), y = f.other(x);
					if (f != e)
						uf.union(x, y);
				}

				// check that e is min weight edge in crossing cut
				if (!graph.getUndirectedEdges().isEmpty())
				{
					LinkedList<UndirectedEdge> list = graph.getUndirectedEdges();
					UndirectedEdge[] temp = list.toArray(new UndirectedEdge[list.size()]);

					for (UndirectedEdge f : temp)
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

	public static void main(String[] args)
	{
		EdgeWeightedUndirectedGraph graph = new EdgeWeightedUndirectedGraph(9);
		graph.insertEdge(new UndirectedEdge(0, 1, 4));
		graph.insertEdge(new UndirectedEdge(0, 7, 8));
		graph.insertEdge(new UndirectedEdge(1, 2, 8));
		graph.insertEdge(new UndirectedEdge(1, 7, 11));
		graph.insertEdge(new UndirectedEdge(2, 3, 7));
		graph.insertEdge(new UndirectedEdge(2, 8, 2));
		graph.insertEdge(new UndirectedEdge(2, 5, 4));
		graph.insertEdge(new UndirectedEdge(3, 4, 9));
		graph.insertEdge(new UndirectedEdge(3, 5, 14));
		graph.insertEdge(new UndirectedEdge(4, 5, 10));
		graph.insertEdge(new UndirectedEdge(5, 6, 2));
		graph.insertEdge(new UndirectedEdge(6, 7, 1));
		graph.insertEdge(new UndirectedEdge(6, 8, 6));
		graph.insertEdge(new UndirectedEdge(7, 8, 7));

		graph.printGraph();

		KruskalMST kruskal = new KruskalMST(graph);
		System.out.println("Done Kruskal Algorithm");

		System.out.println("MST Edge");
		kruskal.getMSTEdges().printQueue();
		System.out.println("total weight : " + kruskal.getMSTWeight());
	}
}
