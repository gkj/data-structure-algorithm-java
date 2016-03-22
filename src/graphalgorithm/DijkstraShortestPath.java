package graphalgorithm;

import datastructure.DirectedEdge;
import datastructure.EdgeWeightedDirectedGraph;
import datastructure.IndexedMinimumPriorityQueue;
import datastructure.LinkedList;
import datastructure.Stack;

public class DijkstraShortestPath
{
	private double[] distanceTo; // distanceTo[v] = distance of shortest s->v
									// path
	private DirectedEdge[] edgeTo; // edgeTo[v] = last edge on shortest s->v
									// path
	private IndexedMinimumPriorityQueue<Double> pq; // priority queue of
													// vertices

	// Computes a shortest paths tree from s to every other vertex in the
	// edge-weighted directed graph G.
	public DijkstraShortestPath(EdgeWeightedDirectedGraph graph, int s)
	{
		// check validity of every edge on a graph
		LinkedList<DirectedEdge> directedEdges = graph.edges();
		if (!directedEdges.isEmpty())
		{
			DirectedEdge[] edges = directedEdges.toArray(new DirectedEdge[directedEdges.size()]);
			for (DirectedEdge e : edges)
			{
				if (e.getWeight() < 0)
					throw new IllegalArgumentException("edge " + e + " has negative weight");
			}
		}

		distanceTo = new double[graph.getTotalVertices()];
		edgeTo = new DirectedEdge[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			distanceTo[v] = Double.POSITIVE_INFINITY;
		distanceTo[s] = 0.0;

		// relax vertices in order of distance from s
		pq = new IndexedMinimumPriorityQueue<Double>(graph.getTotalVertices());
		pq.insert(s, distanceTo[s]);
		while (!pq.isEmpty())
		{
			int v = pq.delMin();

			if (!graph.getAdjacency(v).isEmpty())
			{
				LinkedList<DirectedEdge> list = graph.getAdjacency(v);
				DirectedEdge[] edges = list.toArray(new DirectedEdge[list.size()]);
				for (DirectedEdge e : edges)
					relax(e);
			}
		}

		// check optimality conditions
		assert check(graph, s);
	}

	// relax edge e and update pq if changed
	private void relax(DirectedEdge e)
	{
		int v = e.getFrom(), w = e.getTo();
		if (distanceTo[w] > distanceTo[v] + e.getWeight())
		{
			distanceTo[w] = distanceTo[v] + e.getWeight();
			edgeTo[w] = e;

			if (pq.contains(w))
				pq.decreaseKey(w, distanceTo[w]);
			else
				pq.insert(w, distanceTo[w]);
		}
	}

	// returns the length of a shortest path from the source vertex s to vertex
	// v.
	public double getDistanceTo(int vertex)
	{
		return distanceTo[vertex];
	}

	// return true if there is a path from the source vertex s to vertex v
	public boolean hasPathTo(int vertex)
	{
		return distanceTo[vertex] < Double.POSITIVE_INFINITY;
	}

	// return the shortest path from the source vertex s to vertex v.
	public Stack<DirectedEdge> pathTo(int v)
	{
		if (!hasPathTo(v))
			return null;

		Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.getFrom()])
		{
			path.push(e);
		}

		return path;
	}

	// check optimality conditions:
	// (i) for all edges e: distanceTo[e.getTo()] <= distanceTo[e.getFrom()] +
	// e.getWeight()
	// (ii) for all edge e on the SPT: distanceTo[e.getTo()] ==
	// distanceTo[e.getFrom()] + e.getWeight()
	private boolean check(EdgeWeightedDirectedGraph graph, int s)
	{
		// check that edge weights are nonnegative
		if (!graph.edges().isEmpty())
		{
			DirectedEdge[] edges = graph.edges().toArray(new DirectedEdge[graph.edges().size()]);
			for (DirectedEdge e : edges)
			{
				if (e.getWeight() < 0)
				{
					System.err.println("negative edge weight detected");
					return false;
				}
			}
		}

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

		// check that all edges e = v->w satisfy distanceTo[w] <= distanceTo[v]
		// + e.getWeight()
		for (int v = 0; v < graph.getTotalVertices(); v++)
		{
			if (!graph.getAdjacency(v).isEmpty())
			{
				LinkedList<DirectedEdge> list = graph.getAdjacency(v);
				DirectedEdge[] edges = list.toArray(new DirectedEdge[list.size()]);
				for (DirectedEdge e : edges)
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
		return true;
	}

	public static void main(String[] args)
	{
		EdgeWeightedDirectedGraph graph = new EdgeWeightedDirectedGraph(9);
		graph.insertEdge(new DirectedEdge(0, 1, 4));
		graph.insertEdge(new DirectedEdge(0, 7, 8));
		graph.insertEdge(new DirectedEdge(1, 2, 8));
		graph.insertEdge(new DirectedEdge(1, 7, 11));
		graph.insertEdge(new DirectedEdge(2, 3, 7));
		graph.insertEdge(new DirectedEdge(2, 8, 2));
		graph.insertEdge(new DirectedEdge(2, 5, 4));
		graph.insertEdge(new DirectedEdge(3, 4, 9));
		graph.insertEdge(new DirectedEdge(3, 5, 14));
		graph.insertEdge(new DirectedEdge(4, 5, 10));
		graph.insertEdge(new DirectedEdge(5, 6, 2));
		graph.insertEdge(new DirectedEdge(6, 7, 1));
		graph.insertEdge(new DirectedEdge(6, 8, 6));
		graph.insertEdge(new DirectedEdge(7, 8, 7));

		graph.printGraph();

		int startVertex = 1;
		DijkstraShortestPath dijkstra = new DijkstraShortestPath(graph, startVertex);
		System.out.println("Done Dijkstra Algorithm");

		// print shortest path from start vertex to another vertex
		for (int i = 0; i < graph.getTotalVertices(); i++)
		{
			if (i != startVertex && dijkstra.hasPathTo(i))
			{
				System.out
						.println("Shortest path from " + startVertex + " to " + i + " = " + dijkstra.getDistanceTo(i));
				System.out.print("Path: ");
				dijkstra.pathTo(i).printStack();
				System.out.println();
			}
		}
	}
}
