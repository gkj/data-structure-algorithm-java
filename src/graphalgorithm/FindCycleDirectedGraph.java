/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedGraph;
import datastructure.LinkedList;
import datastructure.Stack;

public class FindCycleDirectedGraph 
{
	private boolean[] visited;        
    private int[] parent;            
    private boolean[] onStack;       
    private Stack<Integer> cycle;    

    // determines whether the DirectedGraph has a directed cycle and, if so, finds such a cycle.
    public FindCycleDirectedGraph(DirectedGraph graph) 
    {
        visited  = new boolean[graph.getTotalVertices()];
        onStack = new boolean[graph.getTotalVertices()];
        parent  = new int[graph.getTotalVertices()];
        for (int v = 0; v < graph.getTotalVertices(); v++)
            if (!visited[v]) dfs(graph, v);
    }

    // check that algorithm computes either the topological order or finds a directed cycle
    private void dfs(DirectedGraph graph, int v) 
    {
        onStack[v] = true;
        visited[v] = true;
        
        if (!graph.getAdjacency(v).isEmpty())
        {
        	LinkedList<Integer> list = graph.getAdjacency(v);
        	Integer[] adj = list.toArray(new Integer[list.size()]);
	        for (int w : adj) 
	        {
	            // short circuit if directed cycle found
	            if (cycle != null) return;
	
	            //found new vertex, so recur
	            else if (!visited[w]) 
	            {
	                parent[w] = v;
	                dfs(graph, w);
	            }
	
	            // trace back directed cycle
	            else if (onStack[w]) 
	            {
	                cycle = new Stack<Integer>();
	                for (int x = v; x != w; x = parent[x]) 
	                {
	                    cycle.push(x);
	                }
	                cycle.push(w);
	                cycle.push(v);
	            }
	        }
        }
        
        onStack[v] = false;
    }

    // return  true if the DirectedGraph has a directed cycle, false otherwise
    public boolean hasCycle() 
    {
        return cycle != null;
    }

    // return a directed cycle if the DirectedGraph has a directed cycle, and null otherwise.
    public Stack<Integer> getCycle() 
    {
        return cycle;
    }


    // certify that DirectedGraph is either acyclic or has a directed cycle
    @SuppressWarnings("unused")
	private boolean check(DirectedGraph graph) 
    {
        if (hasCycle()) 
        {
            // verify cycle
            int first = -1, last = -1;
            
            if (!getCycle().isEmpty())
            {
            	Integer[] arr = getCycle().toArray(new Integer[getCycle().size()]);
            	
	            for (int v : arr) 
	            {
	                if (first == -1) 
	                	first = v;
	                
	                last = v;
	            }
            }
            if (first != last) 
            {
                System.out.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }

        return true;
    }
}
