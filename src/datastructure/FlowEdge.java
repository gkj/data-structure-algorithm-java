/*
 * FlodEdge represent an edge on network flow
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN
 *  
 */
package datastructure;

public class FlowEdge implements Comparable<FlowEdge>
{
	private final int from; // from
	private final int to; // to
	private final double capacity; // capacity
	private double flow; // flow

	// Initializes an edge from vertex v to vertex w with the given capacity and
	// flow.
	public FlowEdge(int from, int to, double capacity, double flow)
	{
		if (from < 0)
			throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
		if (to < 0)
			throw new IndexOutOfBoundsException("Vertex name must be a nonnegative integer");
		if (!(capacity >= 0.0))
			throw new IllegalArgumentException("Edge capacity must be nonnegaitve");
		if (!(flow <= capacity))
			throw new IllegalArgumentException("Flow exceeds capacity");
		if (!(flow >= 0.0))
			throw new IllegalArgumentException("Flow must be nonnnegative");

		this.from = from;
		this.to = to;
		this.capacity = capacity;
		this.flow = flow;
	}

	// Initializes an edge from vertex v to vertex w with the given capacity and
	// zero flow.
	public FlowEdge(int from, int to, double capacity)
	{
		this(from, to, capacity, 0.0);
	}

	// initializes a flow edge from another flow edge.
	public FlowEdge(FlowEdge e)
	{
		this(e.getFrom(), e.getTo(), e.getCapacity(), e.getFlow());
	}

	public int getFrom()
	{
		return from;
	}

	public int getTo()
	{
		return to;
	}

	public double getCapacity()
	{
		return capacity;
	}

	public double getFlow()
	{
		return flow;
	}

	public int other(int vertex)
	{
		if (vertex == from)
			return to;
		else if (vertex == to)
			return from;
		else
			throw new IllegalArgumentException("Illegal endpoint");
	}

	// returns the residual capacity of the edge in the direction to the given
	// vertex.
	public double residualCapacityTo(int vertex)
	{
		if (vertex == from)
			return flow; // backward edge
		else if (vertex == to)
			return capacity - flow; // forward edge
		else
			throw new IllegalArgumentException("Illegal endpoint");
	}

	// Increases the flow on the edge in the direction to the given vertex.
	// If vertex is the tail vertex, this increases the flow on the edge by
	// delta;
	// if vertex is the head vertex, this decreases the flow on the edge by
	// delta.
	public void addResidualFlowTo(int vertex, double delta)
	{
		if (vertex == from)
			flow -= delta; // backward edge
		else if (vertex == to)
			flow += delta; // forward edge
		else
			throw new IllegalArgumentException("Illegal endpoint");

		// check invalid value
		if (Double.isNaN(delta))
			throw new IllegalArgumentException("Change in flow = NaN");
		if (!(flow >= 0.0))
			throw new IllegalArgumentException("Flow is negative");
		if (!(flow <= capacity))
			throw new IllegalArgumentException("Flow exceeds capacity");
	}

	public String toString()
	{
		return from + "->" + to + " " + flow + "/" + capacity;
	}

	public int compareTo(FlowEdge other)
	{
		if (getFrom() == other.getFrom() && getTo() == other.getTo() && getCapacity() == other.getCapacity()
				&& getFlow() == other.getFlow())
			return 0;
		else
			return -1;
	}
}
