package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import datastructure.HashMap;
import datastructure.LinkedHashSet;
import datastructure.LinkedList;

public class DirectedGraph<T>
{
	private int totalEdges;
	private LinkedList<T> vertices;
	private HashMap<T, LinkedHashSet<T>> adjacency;

	public DirectedGraph()
	{
		totalEdges = 0;
		vertices = new LinkedList<>();
		adjacency = new HashMap<>();
	}
	
	public DirectedGraph(DirectedGraph<T> graph)
	{
		totalEdges = graph.getTotalEdges();
		vertices = graph.getVertices().clone();
		adjacency = new HashMap<>();
		
		Object[] temp = vertices.toArray();
		for(Object object : temp)
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			adjacency.put(vertex, graph.getAdjacency(vertex).clone());
		}
	}

	public int getTotalVertices()
	{
		return vertices.size();
	}

	public int getTotalEdges()
	{
		return totalEdges;
	}

	private void validateVertex(T vertex)
	{
		if (!adjacency.containsKey(vertex))
			throw new RuntimeException("Vertex " + vertex + " not found.");
	}

	public boolean hasEdge(T from, T to)
	{
		validateVertex(from);
		validateVertex(to);
		return adjacency.get(from).contains(to);
	}

	public void addVertex(T vertex)
	{
		if (!adjacency.containsKey(vertex))
		{
			vertices.add(vertex);
			adjacency.put(vertex, new LinkedHashSet<T>());
		}
	}

	public void addEdge(T from, T to)
	{
		validateVertex(from);
		validateVertex(to);
		adjacency.get(from).add(to);
		totalEdges++;
	}

	public void removeEdge(T from, T to)
	{
		validateVertex(from);
		validateVertex(to);
		adjacency.get(from).remove(to);
		totalEdges--;
	}

	public int getDegree(T vertex)
	{
		validateVertex(vertex);
		return adjacency.get(vertex).size();
	}

	public LinkedList<T> getVertices()
	{
		return vertices.clone();
	}

	public LinkedHashSet<T> getAdjacency(T vertex)
	{
		validateVertex(vertex);
		return adjacency.get(vertex);
	}

	public String toString()
	{
		Object[] v = vertices.toArray();
		StringBuilder sb = new StringBuilder();
		for (Object object : v)
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			sb.append(vertex);
			sb.append(" : ");
			Object[] adj = adjacency.get(vertex).toArray();
			for (Object a : adj)
			{
				sb.append(a.toString());
				sb.append(" ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}
	
	public DirectedGraph<T> clone()
	{
		return new DirectedGraph<T>(this);
	}
	
	// unit testing
	public static void main(String[] args) throws FileNotFoundException
	{
		File file = new File("data/directedgraph/tinyDAG.txt");
		Scanner in = new Scanner(file);

		DirectedGraph<Integer> graph = new DirectedGraph<>();

		while (in.hasNextLine())
		{
			String raw = in.nextLine();
			String[] data = raw.trim().split(" ");

			int from = Integer.parseInt(data[0].trim());
			int to = Integer.parseInt(data[1].trim());
			
			graph.addVertex(from);
			graph.addVertex(to);
			graph.addEdge(from, to);
		}

		System.out.println(graph);
		
		in.close();
	}
}
