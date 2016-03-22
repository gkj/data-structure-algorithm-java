/*
 * Edge-weighted directed graph using adjacency matrix
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 * 
 */
package datastructure;

public class AdjacencyMatrixEdgeWeightedDirectedGraph
{
	private int totalVertices;
	private int totalEdge;
	private DirectedEdge[][] adjacency;

	// initializes an empty edge-weighted directed graph with V vertices and 0
	// edges.
	public AdjacencyMatrixEdgeWeightedDirectedGraph(int totalVertices)
	{
		if (totalVertices < 0)
			throw new RuntimeException("Number of vertices must be nonnegative");

		this.totalVertices = totalVertices;
		this.totalEdge = 0;
		this.adjacency = new DirectedEdge[totalVertices][totalVertices];
	}

	public int getTotalVertices()
	{
		return totalVertices;
	}

	public int getTotalEdges()
	{
		return totalEdge;
	}

	public DirectedEdge[] getAdjacency(int vertex)
	{
		DirectedEdge[] array = new DirectedEdge[totalVertices];
		for (int i = 0; i < totalVertices; i++)
		{
			array[i] = adjacency[vertex][i];
		}
		return array;
	}

	// insert the directed edge e to the edge-weighted digraph (if there is not
	// already an edge with the same endpoints)
	public void insertEdge(DirectedEdge edge)
	{
		int v = edge.getFrom();
		int w = edge.getTo();
		if (adjacency[v][w] == null)
		{
			totalEdge++;
			adjacency[v][w] = edge;
		}
	}

	public void deleteEdge(DirectedEdge edge)
	{
		int v = edge.getFrom();
		int w = edge.getTo();
		if (adjacency[v][w] != null && adjacency[v][w].compareTo(edge) == 0)
		{
			totalEdge--;
			adjacency[v][w] = null;
		}
	}
}
