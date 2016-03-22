/*
 * Represent undirected edge on graph
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN
 *  
 */
package datastructure;

public class UndirectedEdge implements Comparable<UndirectedEdge>
{
	private int v;
	private int w;
	private double weight;

	public UndirectedEdge(int v, int w, double weight)
	{
		if (v < 0)
			throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");

		if (w < 0)
			throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");

		if (Double.isNaN(weight))
			throw new IllegalArgumentException("Weight is NaN");

		this.v = v;
		this.w = w;
		this.weight = weight;
	}

	// returns the weight of the edge.
	public double getWeight()
	{
		return weight;
	}

	// returns either endpoint of the edge.
	public int either()
	{
		return v;
	}

	public int other(int vertex)
	{
		if (vertex == v)
			return w;
		else if (vertex == w)
			return v;
		else
			throw new IllegalArgumentException("Illegal endpoint");
	}

	public int compareTo(UndirectedEdge other)
	{
		if (this.getWeight() < other.getWeight())
			return -1;
		else if (this.getWeight() > other.getWeight())
			return +1;
		else
			return 0;
	}

	public String toString()
	{
		return String.format("%d-%d %.5f", v, w, weight);
	}
}
