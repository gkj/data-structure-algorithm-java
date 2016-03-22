package graphalgorithm;

import datastructure.EdgeWeightedUndirectedGraph;
import datastructure.LinkedList;
import datastructure.IndexedMinimumPriorityQueue;
import datastructure.Queue;
import datastructure.UndirectedEdge;
import datastructure.UnionFind;

public class PrimMST
{
	private static final double FLOATING_POINT_EPSILON = 1E-12;

	private UndirectedEdge[] edgeTo; // edgeTo[v] = shortest edge from tree
										// vertex to non-tree vertex
	private double[] distanceTo; // distanceTo[v] = weight of shortest such edge
	private boolean[] onTree; // onTree[v] = true if v on tree, false otherwise
	private IndexedMinimumPriorityQueue<Double> pq;

	// rompute a minimum spanning tree (or forest) of an edge-weighted
	// undirected graph.
	public PrimMST(EdgeWeightedUndirectedGraph graph)
	{
		edgeTo = new UndirectedEdge[graph.getTotalVertices()];
		distanceTo = new double[graph.getTotalVertices()];
		onTree = new boolean[graph.getTotalVertices()];
		pq = new IndexedMinimumPriorityQueue<Double>(graph.getTotalVertices());
		for (int v = 0; v < graph.getTotalVertices(); v++)
			distanceTo[v] = Double.POSITIVE_INFINITY;

		for (int v = 0; v < graph.getTotalVertices(); v++) // run from each
															// vertex to find
			if (!onTree[v])
				prim(graph, v); // minimum spanning forest

		// check optimality conditions
		assert check(graph);
	}

	// run Prim's algorithm in graph, starting from vertex s
	private void prim(EdgeWeightedUndirectedGraph graph, int s)
	{
		distanceTo[s] = 0.0;
		pq.insert(s, distanceTo[s]);
		while (!pq.isEmpty())
		{
			int v = pq.delMin();
			scan(graph, v);
		}
	}

	// scan vertex v
	private void scan(EdgeWeightedUndirectedGraph graph, int v)
	{
		onTree[v] = true;

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<UndirectedEdge> list = graph.getAdjacency(v);
			UndirectedEdge[] adj = list.toArray(new UndirectedEdge[list.size()]);

			for (UndirectedEdge e : adj)
			{
				int w = e.other(v);
				if (onTree[w])
					continue; // v-w is obsolete edge

				if (e.getWeight() < distanceTo[w])
				{
					distanceTo[w] = e.getWeight();
					edgeTo[w] = e;
					if (pq.contains(w))
						pq.decreaseKey(w, distanceTo[w]);
					else
						pq.insert(w, distanceTo[w]);
				}
			}
		}
	}

	// returns all edges in a minimum spanning tree (or forest).
	public Queue<UndirectedEdge> getMSTEdges()
	{
		Queue<UndirectedEdge> mst = new Queue<UndirectedEdge>();
		for (int v = 0; v < edgeTo.length; v++)
		{
			UndirectedEdge e = edgeTo[v];
			if (e != null)
			{
				mst.add(e);
			}
		}
		return mst;
	}

	// returns the sum of the edge weights in a minimum spanning tree (or
	// forest).
	public double getSumOfMSTWeight()
	{
		double weight = 0.0;
		Queue<UndirectedEdge> mst = getMSTEdges();
		if (!mst.isEmpty())
		{
			UndirectedEdge[] arr = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : arr)
				weight += e.getWeight();
		}
		return weight;
	}

	// check optimality conditions (takes time proportional to E V lg* V)
	private boolean check(EdgeWeightedUndirectedGraph graph)
	{
		// check weight
		double totalWeight = 0.0;

		Queue<UndirectedEdge> mst = getMSTEdges();
		if (!mst.isEmpty())
		{
			UndirectedEdge[] arr = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : arr)
			{
				totalWeight += e.getWeight();
			}
		}

		if (Math.abs(totalWeight - getSumOfMSTWeight()) > FLOATING_POINT_EPSILON)
		{
			System.err.printf("Weight of edges does not equal weight(): %f vs. %f\n", totalWeight, getSumOfMSTWeight());
			return false;
		}

		// check that it is acyclic
		UnionFind uf = new UnionFind(graph.getTotalVertices());
		mst = getMSTEdges();
		if (!mst.isEmpty())
		{
			UndirectedEdge[] arr = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : arr)
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
		LinkedList<UndirectedEdge> list = graph.getUndirectedEdges();
		if (!list.isEmpty())
		{
			UndirectedEdge[] arr = list.toArray(new UndirectedEdge[list.size()]);
			for (UndirectedEdge e : arr)
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
		mst = getMSTEdges();
		if (!mst.isEmpty())
		{
			UndirectedEdge[] arr = mst.toArray(UndirectedEdge.class);
			for (UndirectedEdge e : arr)
			{
				// all edges in MST except e
				uf = new UnionFind(graph.getTotalVertices());
				Queue<UndirectedEdge> temp = getMSTEdges();
				if (!temp.isEmpty())
				{
					UndirectedEdge[] tempArr = arr;
					for (UndirectedEdge f : tempArr)
					{
						int x = f.either(), y = f.other(x);
						if (f != e)
							uf.union(x, y);
					}
				}

				// check that e is min weight edge in crossing cut
				list = graph.getUndirectedEdges();
				if (!list.isEmpty())
				{
					UndirectedEdge[] tempList = list.toArray(new UndirectedEdge[list.size()]);
					for (UndirectedEdge f : tempList)
					{
						int x = f.either(), y = f.other(x);
						if (!uf.isConnected(x, y))
						{
							if (f.getWeight() < e.getWeight())
							{
								System.err.println("UndirectedEdge " + f + " violates cut optimality conditions");
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
		// build graph
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

		// print graph
		graph.printGraph();

		PrimMST prim = new PrimMST(graph);
		System.out.println("Done Prim Algorithm");

		System.out.println("MST Edge");
		prim.getMSTEdges().printQueue();
		System.out.println("total weight : " + prim.getSumOfMSTWeight());
	}
}
