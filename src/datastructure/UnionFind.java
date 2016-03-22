/*
 * Represent union find (disjoint set)
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN
 *  
 */

package datastructure;

public class UnionFind
{
	private int[] parent; // parent[i] = parent of i
	private byte[] rank; // rank[i] = rank of subtree rooted at i (never more
							// than 31)
	private int count; // number of components

	// initializes an empty union-find data structure with N isolated components
	// 0 through N-1.
	public UnionFind(int N)
	{
		if (N < 0)
			throw new IllegalArgumentException("number of components should be more than 0");

		count = N;
		parent = new int[N];
		rank = new byte[N];
		for (int i = 0; i < N; i++)
		{
			parent[i] = i;
			rank[i] = 0;
		}
	}

	// returns the component identifier for the component containing site p.
	public int find(int p)
	{
		if (p < 0 || p >= parent.length)
			throw new IndexOutOfBoundsException("p " + p + " is not between 0 and " + (p - 1));

		while (p != parent[p])
		{
			parent[p] = parent[parent[p]]; // path compression by halving
			p = parent[p];
		}
		return p;
	}

	// returns the number of components.
	public int getCount()
	{
		return count;
	}

	// return true if the two sites p and q are in the same component
	public boolean isConnected(int p, int q)
	{
		return find(p) == find(q);
	}

	// merges the component containing site p with the component containing site
	// q.
	public void union(int p, int q)
	{
		int rootP = find(p);
		int rootQ = find(q);
		if (rootP == rootQ)
			return;

		// make root of smaller rank point to root of larger rank
		if (rank[rootP] < rank[rootQ])
		{
			parent[rootP] = rootQ;
		}
		else if (rank[rootP] > rank[rootQ])
		{
			parent[rootQ] = rootP;
		}
		else
		{
			parent[rootQ] = rootP;
			rank[rootP]++;
		}
		count--;
	}
}
