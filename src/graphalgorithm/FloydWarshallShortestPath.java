package graphalgorithm;

import datastructure.AdjacencyMatrixEdgeWeightedDirectedGraph;
import datastructure.DirectedEdge;
import datastructure.EdgeWeightedDirectedGraph;
import datastructure.Stack;

public class FloydWarshallShortestPath 
{
	private boolean hasNegativeCycle;  // is there a negative cycle?
    private double[][] distanceTo;  // distanceTo[v][w] = length of shortest v->w path
    private DirectedEdge[][] edgeTo;  // edgeTo[v][w] = last edge on shortest v->w path

    // Computes a shortest paths tree from each vertex to to every other vertex in
    // the edge-weighted digraph G. If no such shortest path exists for
    // some pair of vertices, it computes a negative cycle.
    public FloydWarshallShortestPath(AdjacencyMatrixEdgeWeightedDirectedGraph G) 
    {
        int V = G.getTotalVertices();
        distanceTo = new double[V][V];
        edgeTo = new DirectedEdge[V][V];

        // initialize distances to infinity
        for (int v = 0; v < V; v++) 
        {
            for (int w = 0; w < V; w++) 
            {
                distanceTo[v][w] = Double.POSITIVE_INFINITY;
            }
        }

        // initialize distances using edge-weighted digraph's
        for (int v = 0; v < G.getTotalVertices(); v++) 
        {
        	DirectedEdge[] adj = G.getAdjacency(v);
            for (DirectedEdge e : adj) 
            {
                distanceTo[e.getFrom()][e.getTo()] = e.getWeight();
                edgeTo[e.getFrom()][e.getTo()] = e;
            }
            // in case of self-loops
            if (distanceTo[v][v] >= 0.0) 
            {
                distanceTo[v][v] = 0.0;
                edgeTo[v][v] = null;
            }
        }

        // Floyd-Warshall updates
        for (int i = 0; i < V; i++) 
        {
            // compute shortest paths using only 0, 1, ..., i as intermediate vertices
            for (int v = 0; v < V; v++) 
            {
                if (edgeTo[v][i] == null) continue;  // optimization
                for (int w = 0; w < V; w++) 
                {
                    if (distanceTo[v][w] > distanceTo[v][i] + distanceTo[i][w]) 
                    {
                        distanceTo[v][w] = distanceTo[v][i] + distanceTo[i][w];
                        edgeTo[v][w] = edgeTo[i][w];
                    }
                }
                // check for negative cycle
                if (distanceTo[v][v] < 0.0) 
                {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }
    }

    // return true if graph has negative cycle
    public boolean hasNegativeCycle() 
    {
        return hasNegativeCycle;
    }

    // return a negative cycle, or null if there is no such cycle.
    public Stack<DirectedEdge> getNegativeCycle() 
    {
        for (int v = 0; v < distanceTo.length; v++) 
        {
            // negative cycle in v's predecessor graph
            if (distanceTo[v][v] < 0.0) 
            {
                int V = edgeTo.length;
                EdgeWeightedDirectedGraph spt = new EdgeWeightedDirectedGraph(V);
                
                for (int w = 0; w < V; w++)
                    if (edgeTo[v][w] != null)
                        spt.insertEdge(edgeTo[v][w]);
                
                FindCycleEdgeWeightedDirectedGraph finder = new FindCycleEdgeWeightedDirectedGraph(spt);
                assert finder.hasCycle();
                return finder.getCycle();
            }
        }
        return null;
    }

    // return true if there is a path from the vertex s to vertex t?
    public boolean hasPath(int s, int t) 
    {
        return distanceTo[s][t] < Double.POSITIVE_INFINITY;
    }

    // returns the length of a shortest path from vertex s to vertex t.
    public double dist(int s, int t) 
    {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        
        return distanceTo[s][t];
    }

    
    public Stack<DirectedEdge> getShortestPath(int s, int t) 
    {
        if (hasNegativeCycle())
            throw new UnsupportedOperationException("Negative cost cycle exists");
        
        if (!hasPath(s, t)) 
        	return null;
        
        Stack<DirectedEdge> path = new Stack<DirectedEdge>();
        for (DirectedEdge e = edgeTo[s][t]; e != null; e = edgeTo[s][e.getFrom()]) 
        {
            path.push(e);
        }
        
        return path;
    }

    // check optimality conditions
    @SuppressWarnings("unused")
	private boolean check(AdjacencyMatrixEdgeWeightedDirectedGraph G, int s) 
    {
        // no negative cycle
        if (!hasNegativeCycle()) 
        {
            for (int v = 0; v < G.getTotalVertices(); v++) 
            {
            	DirectedEdge[] adj = G.getAdjacency(v);
                for (DirectedEdge e : adj) 
                {
                    int w = e.getTo();
                    for (int i = 0; i < G.getTotalVertices(); i++) 
                    {
                        if (distanceTo[i][w] > distanceTo[i][v] + e.getWeight()) 
                        {
                            System.err.println("edge " + e + " is eligible");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
