/*
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 */
package graphalgorithm;

import datastructure.DirectedEdge;
import datastructure.DirectedGraph;
import datastructure.EdgeWeightedDirectedGraph;
import datastructure.LinkedList;
import datastructure.Queue;
import datastructure.Stack;

public class DepthFirstOrder
{
	// visited[v] = has vertex v been visited in dfs?
	private boolean[] visited;

	// pre[v] = preorder number of vertex v
	private int[] pre;

	// post[v] = postorder number of v
	private int[] post;

	// vertices in preorder
	private Queue<Integer> preorder;

	// vertices in postorder
	private Queue<Integer> postorder;

	// counter or preorder numbering
	private int preCounter;

	// counter for postorder numbering
	private int postCounter;

	// determines a depth-first order for the directed graph.
	public DepthFirstOrder(DirectedGraph graph)
	{
		pre = new int[graph.getTotalVertices()];
		post = new int[graph.getTotalVertices()];
		postorder = new Queue<Integer>();
		preorder = new Queue<Integer>();
		visited = new boolean[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			if (!visited[v])
				dfs(graph, v);
	}

	// determines a depth-first order for the edge-weighted directed graph.
	public DepthFirstOrder(EdgeWeightedDirectedGraph graph)
	{
		pre = new int[graph.getTotalVertices()];
		post = new int[graph.getTotalVertices()];
		postorder = new Queue<Integer>();
		preorder = new Queue<Integer>();
		visited = new boolean[graph.getTotalVertices()];
		for (int v = 0; v < graph.getTotalVertices(); v++)
			if (!visited[v])
				dfs(graph, v);
	}

	// run DFS in directed graph from vertex v and compute preorder/postorder
	private void dfs(DirectedGraph graph, int v)
	{
		visited[v] = true;
		pre[v] = preCounter++;
		preorder.add(v);

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<Integer> list = graph.getAdjacency(v);
			Integer[] adj = list.toArray(new Integer[list.size()]);

			for (int w : adj)
			{
				if (!visited[w])
				{
					dfs(graph, w);
				}
			}
		}

		postorder.add(v);
		post[v] = postCounter++;
	}

	// run DFS in edge-weighted directed graph from vertex v and compute
	// preorder/postorder
	private void dfs(EdgeWeightedDirectedGraph graph, int v)
	{
		visited[v] = true;
		pre[v] = preCounter++;
		preorder.add(v);

		if (!graph.getAdjacency(v).isEmpty())
		{
			LinkedList<DirectedEdge> list = graph.getAdjacency(v);
			DirectedEdge[] adj = list.toArray(new DirectedEdge[list.size()]);
			for (DirectedEdge e : adj)
			{
				int w = e.getTo();
				if (!visited[w])
				{
					dfs(graph, w);
				}
			}
		}

		postorder.add(v);
		post[v] = postCounter++;
	}

	// returns the preorder number of vertex v.
	public int getPreOrderNumber(int v)
	{
		return pre[v];
	}

	// returns the postorder number of vertex v.
	public int getPostOrderNumber(int v)
	{
		return post[v];
	}

	// returns the vertices in postorder.
	public Queue<Integer> getPostOrder()
	{
		return postorder;
	}

	// returns the vertices in preorder.
	public Queue<Integer> getPreOrder()
	{
		return preorder;
	}

	// returns the vertices in reverse postorder
	public Stack<Integer> getReversePostOrder()
	{
		Stack<Integer> reverse = new Stack<Integer>();

		if (!postorder.isEmpty())
		{
			Integer[] arr = postorder.toArray(Integer.class);
			for (int v : arr)
				reverse.push(v);
		}

		return reverse;
	}

	// check that pre() and post() are consistent with pre(v) and post(v)
	@SuppressWarnings("unused")
	private boolean check(DirectedGraph G)
	{
		// check that post(v) is consistent with post()
		int r = 0;

		if (!postorder.isEmpty())
		{
			Integer[] arr = postorder.toArray(Integer.class);
			for (int v : arr)
			{
				if (getPostOrderNumber(v) != r)
				{
					System.out.println("post(v) and post() inconsistent");
					return false;
				}
				r++;
			}
		}
		// check that pre(v) is consistent with pre()
		r = 0;

		if (!preorder.isEmpty())
		{
			Integer[] arr = preorder.toArray(Integer.class);
			for (int v : arr)
			{
				if (getPreOrderNumber(v) != r)
				{
					System.out.println("pre(v) and pre() inconsistent");
					return false;
				}
				r++;
			}
		}
		return true;
	}
}
