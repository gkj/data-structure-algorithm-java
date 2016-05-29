package graph;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import datastructure.HashMap;
import datastructure.LinkedHashSet;
import datastructure.LinkedList;

public class DirectedGraph<T>
{
	private final Class<T> dataType;
	private final Class<LinkedHashSet<T>> adjacencyType;
	
	private int totalEdges;
	private LinkedList<T> vertices;
	private HashMap<T, LinkedHashSet<T>> adjacency;
	

	@SuppressWarnings("unchecked")
	public DirectedGraph(Class<T> dataType)
	{
		this.dataType = dataType;
		adjacencyType = (Class<LinkedHashSet<T>>) new LinkedHashSet<T>(dataType).getClass();
		
		totalEdges = 0;
		vertices = new LinkedList<>(dataType);
		adjacency = new HashMap<>(dataType, adjacencyType);
	}

	public DirectedGraph(DirectedGraph<T> graph)
	{
		dataType = graph.getDataType();
		adjacencyType = graph.getAdjacencyType();
		
		totalEdges = graph.getTotalEdges();
		vertices = graph.getVertices().clone();
		
		adjacency = new HashMap<>(dataType, adjacencyType);

		for (T vertex : vertices.toArray())
		{
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
	
	public Class<T> getDataType()
	{
		return dataType;
	}
	
	public Class<LinkedHashSet<T>> getAdjacencyType()
	{
		return adjacencyType;
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
			adjacency.put(vertex, new LinkedHashSet<T>(dataType));
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
		return vertices;
	}

	public LinkedHashSet<T> getAdjacency(T vertex)
	{
		validateVertex(vertex);
		return adjacency.get(vertex);
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (T vertex : vertices.toArray())
		{
			sb.append(vertex);
			sb.append(" : ");
			for (T a :  adjacency.get(vertex).toArray())
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

		DirectedGraph<Integer> graph = new DirectedGraph<>(Integer.class);

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
		System.out.println("Finished");

		in.close();
	}
}
