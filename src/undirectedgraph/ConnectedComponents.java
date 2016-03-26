package undirectedgraph;

import datastructure.HashMap;

/**
 * The <tt>CC</tt> class represents a data type for determining the connected
 * components in an undirected graph. The <em>id</em> operation determines in
 * which connected component a given vertex lies; the <em>connected</em>
 * operation determines whether two vertices are in the same connected
 * component; the <em>count</em> operation determines the number of connected
 * components; and the <em>size</em> operation determines the number of vertices
 * in the connect component containing a given vertex.
 * 
 * @author Gilang Kusuma Jati
 *
 */
public class ConnectedComponents<T>
{
	// marked[v] = has vertex v been marked?
	private HashMap<T, Boolean> marked; 
	
	// id[v] = id of connected component containing v
	private HashMap<T, Integer> id;
	
	// size[id] = number of vertices in given component
	private HashMap<T, Integer> size; 
	
	// number of connected components
	private int count; 

	/**
	 * Computes the connected components of the undirected graph <tt>G</tt>.
	 *
	 * @param G
	 *            the undirected graph
	 */
	public ConnectedComponents(UndirectedGraph<T> G)
	{
		marked = new HashMap<>();
		id = new HashMap<>();
		size = new HashMap<>();
		
		Object[] vertices = G.getVertices().toArray();
		
		for(Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			marked.put(vertex, false);
			id.put(vertex, 0);
			size.put(vertex, 0);
		}
		
		for(Object object : vertices)
		{
			@SuppressWarnings("unchecked")
			T vertex = (T) object;
			
			if (!marked.get(vertex))
			{
				dfs(G, v);
				count++;
			}
		}
	}

	// depth-first search
	private void dfs(UndirectedGraph G, int v)
	{
		marked.put(v, true);
		id.put(v, count);
		size.put(count, size.get(count));
		
		
		for (int w : G.adj(v))
		{
			if (!marked[w])
			{
				dfs(G, w);
			}
		}
	}

	/**
	 * Returns the component id of the connected component containing vertex
	 * <tt>v</tt>.
	 *
	 * @param v
	 *            the vertex
	 * @return the component id of the connected component containing vertex
	 *         <tt>v</tt>
	 */
	public int id(int v)
	{
		return id[v];
	}

	/**
	 * Returns the number of vertices in the connected component containing
	 * vertex <tt>v</tt>.
	 *
	 * @param v
	 *            the vertex
	 * @return the number of vertices in the connected component containing
	 *         vertex <tt>v</tt>
	 */
	public int size(int v)
	{
		return size[id[v]];
	}

	/**
	 * Returns the number of connected components in the graph <tt>G</tt>.
	 *
	 * @return the number of connected components in the graph <tt>G</tt>
	 */
	public int count()
	{
		return count;
	}

	/**
	 * Returns true if vertices <tt>v</tt> and <tt>w</tt> are in the same
	 * connected component.
	 *
	 * @param v
	 *            one vertex
	 * @param w
	 *            the other vertex
	 * @return <tt>true</tt> if vertices <tt>v</tt> and <tt>w</tt> are in the
	 *         same connected component; <tt>false</tt> otherwise
	 */
	public boolean connected(int v, int w)
	{
		return id(v) == id(w);
	}
}
