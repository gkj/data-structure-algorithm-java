package networkflow;

import datastructure.FlowEdge;
import datastructure.FlowNetwork;
import datastructure.LinkedList;
import datastructure.Queue;

public class FordFulkerson
{
	private static final double FLOATING_POINT_EPSILON = 1E-11;

	private boolean[] marked; // marked[v] = true iff s->v path in residual
								// graph
	private FlowEdge[] edgeTo; // edgeTo[v] = last edge on shortest residual
								// s->v path
	private double value; // current value of max flow

	// compute a maximum flow and minimum cut in the network from vertex s to
	// vertex t.
	public FordFulkerson(FlowNetwork network, int s, int t)
	{
		validate(s, network.getTotalVertices());
		validate(t, network.getTotalVertices());

		if (s == t)
			throw new IllegalArgumentException("Source equals sink");
		if (!isFeasible(network, s, t))
			throw new IllegalArgumentException("Initial flow is infeasible");

		// while there exists an augmenting path, use it
		value = excess(network, t);
		while (hasAugmentingPath(network, s, t))
		{
			// compute bottleneck capacity
			double bottle = Double.POSITIVE_INFINITY;
			for (int v = t; v != s; v = edgeTo[v].other(v))
			{
				bottle = Math.min(bottle, edgeTo[v].residualCapacityTo(v));
			}

			// augment flow
			for (int v = t; v != s; v = edgeTo[v].other(v))
			{
				edgeTo[v].addResidualFlowTo(v, bottle);
			}

			value += bottle;
		}

		// check optimality conditions
		assert check(network, s, t);
	}

	// returns the value of the maximum flow.
	public double getValue()
	{
		return value;
	}

	// return true if vertex v is on the s side of the micut, and false if
	// vertex v is on the t side.
	public boolean inCut(int v)
	{
		validate(v, marked.length);
		return marked[v];
	}

	// throw an exception if v is outside prescibed range
	private void validate(int v, int range)
	{
		if (v < 0 || v >= range)
			throw new IndexOutOfBoundsException("vertex " + v + " is not between 0 and " + (range - 1));
	}

	// is there an augmenting path?
	// if so, upon termination edgeTo[] will contain a parent-link
	// representation of such a path
	// this implementation finds a shortest augmenting path (fewest number of
	// edges),
	// which performs well both in theory and in practice
	private boolean hasAugmentingPath(FlowNetwork network, int s, int t)
	{
		edgeTo = new FlowEdge[network.getTotalVertices()];
		marked = new boolean[network.getTotalVertices()];

		// breadth-first search
		Queue<Integer> queue = new Queue<Integer>();
		queue.add(s);
		marked[s] = true;
		while (!queue.isEmpty() && !marked[t])
		{
			int v = queue.poll();

			if (!network.getAdjacency(v).isEmpty())
			{
				LinkedList<FlowEdge> list = network.getAdjacency(v);
				FlowEdge[] edges = list.toArray(new FlowEdge[list.size()]);

				for (FlowEdge e : edges)
				{
					int w = e.other(v);

					// if residual capacity from v to w
					if (e.residualCapacityTo(w) > 0)
					{
						if (!marked[w])
						{
							edgeTo[w] = e;
							marked[w] = true;
							queue.add(w);
						}
					}
				}
			}
		}

		return marked[t];
	}

	// return excess flow at vertex v
	private double excess(FlowNetwork network, int v)
	{
		double excess = 0.0;

		if (!network.getAdjacency(v).isEmpty())
		{
			LinkedList<FlowEdge> list = network.getAdjacency(v);
			FlowEdge[] edges = list.toArray(new FlowEdge[list.size()]);

			for (FlowEdge e : edges)
			{
				if (v == e.getFrom())
					excess -= e.getFlow();
				else
					excess += e.getFlow();
			}
		}
		return excess;
	}

	// return excess flow at vertex v
	private boolean isFeasible(FlowNetwork network, int s, int t)
	{
		// check that capacity constraints are satisfied
		for (int v = 0; v < network.getTotalVertices(); v++)
		{
			if (!network.getAdjacency(v).isEmpty())
			{
				LinkedList<FlowEdge> list = network.getAdjacency(v);
				FlowEdge[] edges = list.toArray(new FlowEdge[list.size()]);

				for (FlowEdge e : edges)
				{
					if (e.getFlow() < -FLOATING_POINT_EPSILON || e.getFlow() > e.getCapacity() + FLOATING_POINT_EPSILON)
					{
						System.err.println("Edge does not satisfy capacity constraints: " + e);
						return false;
					}
				}
			}
		}

		// check that net flow into a vertex equals zero, except at source and
		// sink
		if (Math.abs(value + excess(network, s)) > FLOATING_POINT_EPSILON)
		{
			System.err.println("Excess at source = " + excess(network, s));
			System.err.println("Max flow         = " + value);
			return false;
		}
		if (Math.abs(value - excess(network, t)) > FLOATING_POINT_EPSILON)
		{
			System.err.println("Excess at sink   = " + excess(network, t));
			System.err.println("Max flow         = " + value);
			return false;
		}
		for (int v = 0; v < network.getTotalVertices(); v++)
		{
			if (v == s || v == t)
				continue;
			else if (Math.abs(excess(network, v)) > FLOATING_POINT_EPSILON)
			{
				System.err.println("Net flow out of " + v + " doesn't equal zero");
				return false;
			}
		}
		return true;
	}

	// check optimality conditions
	private boolean check(FlowNetwork network, int s, int t)
	{
		// check that flow is feasible
		if (!isFeasible(network, s, t))
		{
			System.err.println("Flow is infeasible");
			return false;
		}

		// check that s is on the source side of min cut and that t is not on
		// source side
		if (!inCut(s))
		{
			System.err.println("source " + s + " is not on source side of min cut");
			return false;
		}

		if (inCut(t))
		{
			System.err.println("sink " + t + " is on source side of min cut");
			return false;
		}

		// check that value of min cut = value of max flow
		double mincutValue = 0.0;
		for (int v = 0; v < network.getTotalVertices(); v++)
		{
			if (!network.getAdjacency(v).isEmpty())
			{
				LinkedList<FlowEdge> list = network.getAdjacency(v);
				FlowEdge[] edges = list.toArray(new FlowEdge[list.size()]);

				for (FlowEdge e : edges)
				{
					if ((v == e.getFrom()) && inCut(e.getFrom()) && !inCut(e.getTo()))
						mincutValue += e.getCapacity();
				}
			}
		}

		if (Math.abs(mincutValue - value) > FLOATING_POINT_EPSILON)
		{
			System.err.println("Max flow value = " + value + ", min cut value = " + mincutValue);
			return false;
		}

		return true;
	}
}
