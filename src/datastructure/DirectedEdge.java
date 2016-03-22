/*
 * DirectedEdge
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 * 
 */
package datastructure;

public class DirectedEdge implements Comparable<DirectedEdge>
{
	private final int from;
	private final int to;
	private final double weight;

	// Construct a directed edge from vertex source to vertex destination with
	// the given weight.
	public DirectedEdge(int from, int to, double weight)
	{
		if (from < 0)
			throw new IndexOutOfBoundsException("Vertex names must be positive integers");

		if (to < 0)
			throw new IndexOutOfBoundsException("Vertex names must be positive integers");

		if (Double.isNaN(weight))
			throw new IllegalArgumentException("Weight is NaN");

		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	public int getFrom()
	{
		return from;
	}

	public int getTo()
	{
		return to;
	}

	// Returns the weight of the directed edge.
	public double getWeight()
	{
		return weight;
	}

	public String toString()
	{
		return from + "->" + to + " " + String.format("%5.2f", weight);
	}

	// compare directed edge
	public int compareTo(DirectedEdge other)
	{
		double diffWeight = this.getWeight() - other.getWeight();

		if (diffWeight == 0)
		{
			int diffSource = this.getFrom() - other.getFrom();

			if (diffSource == 0)
			{
				return this.getTo() - other.getTo();
			}
			return diffSource;
		}

		return (int) diffWeight;
	}
}
