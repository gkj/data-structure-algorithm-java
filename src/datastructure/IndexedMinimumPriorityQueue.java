/*
 * PriorityQueue implementation uses a binary heap along with 
 * an array to associate keys with integers in the given range.
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN.
 * 
 */
package datastructure;

public class IndexedMinimumPriorityQueue<E extends Comparable<E>>
{
	private int maxN; // maximum number of keys on PQ
	private int N; // number of keys on PQ
	private int[] pq; // binary heap using 1-based indexing
	private int[] qp; // inverse of pq - qp[pq[i]] = pq[qp[i]] = i
	private E[] keys; // keys[i] = priority of i

	@SuppressWarnings("unchecked")
	public IndexedMinimumPriorityQueue(int maxN)
	{
		if (maxN < 0)
			throw new IllegalArgumentException();

		this.maxN = maxN;
		keys = (E[]) new Comparable[maxN + 1]; // make this of length maxN??
		pq = new int[maxN + 1];
		qp = new int[maxN + 1]; // make this of length maxN??
		for (int i = 0; i <= maxN; i++)
			qp[i] = -1;
	}

	// return true if the priority queue is empty
	public boolean isEmpty()
	{
		return N == 0;
	}

	// return true if idx is an index on the priority queue
	public boolean contains(int idx)
	{
		if (idx < 0 || idx >= maxN)
			throw new IndexOutOfBoundsException();
		return qp[idx] != -1;
	}

	// returns the number of keys on the priority queue.
	public int size()
	{
		return N;
	}

	// associates key with index i.
	public void insert(int i, E key)
	{
		if (i < 0 || i >= maxN)
			throw new IndexOutOfBoundsException();

		if (contains(i))
			throw new IllegalArgumentException("index is already in the priority queue");

		N++;
		qp[i] = N;
		pq[N] = i;
		keys[i] = key;
		swim(N);
	}

	// returns an index associated with a minimum key.
	public int minIndex()
	{
		if (N == 0)
			throw new RuntimeException("Priority queue underflow");

		return pq[1];
	}

	// returns a minimum key
	public E minKey()
	{
		if (N == 0)
			throw new RuntimeException("Priority queue underflow");

		return keys[pq[1]];
	}

	// removes a minimum key and returns its associated index.
	public int delMin()
	{
		if (N == 0)
			throw new RuntimeException("Priority queue underflow");

		int min = pq[1];
		exchange(1, N--);
		sink(1);
		qp[min] = -1; // delete
		keys[pq[N + 1]] = null; // triggger garbage collection
		pq[N + 1] = -1; // not needed
		return min;
	}

	// returns the key associated with index i.
	public E keyOf(int idx)
	{
		if (idx < 0 || idx >= maxN)
			throw new IndexOutOfBoundsException("index " + idx + " is not between 0 and " + (idx - 1));

		if (!contains(idx))
			throw new RuntimeException("index is not in the priority queue");
		else
			return keys[idx];
	}

	// change the key associated with index i to the specified value.
	public void changeKey(int idx, E key)
	{
		if (idx < 0 || idx >= maxN)
			throw new IndexOutOfBoundsException("index " + idx + " is not between 0 and " + (idx - 1));

		if (!contains(idx))
			throw new RuntimeException("index is not in the priority queue");

		keys[idx] = key;
		swim(qp[idx]);
		sink(qp[idx]);
	}

	// decrease the key associated with index i to the specified value.
	public void decreaseKey(int i, E key)
	{
		if (i < 0 || i >= maxN)
			throw new IndexOutOfBoundsException();
		if (!contains(i))
			throw new RuntimeException("index is not in the priority queue");
		if (keys[i].compareTo(key) <= 0)
			throw new IllegalArgumentException(
					"Calling decreaseKey() with given argument would not strictly decrease the key");
		keys[i] = key;
		swim(qp[i]);
	}

	// increase the key associated with index i to the specified value.
	public void increaseKey(int i, E key)
	{
		if (i < 0 || i >= maxN)
			throw new IndexOutOfBoundsException();

		if (!contains(i))
			throw new RuntimeException("index is not in the priority queue");

		if (keys[i].compareTo(key) >= 0)
			throw new IllegalArgumentException(
					"Calling increaseKey() with given argument would not strictly increase the key");

		keys[i] = key;
		sink(qp[i]);
	}

	// remove the key associated with index i.
	public void delete(int i)
	{
		if (i < 0 || i >= maxN)
			throw new IndexOutOfBoundsException();

		if (!contains(i))
			throw new RuntimeException("index is not in the priority queue");

		int index = qp[i];
		exchange(index, N--);
		swim(index);
		sink(index);
		keys[i] = null;
		qp[i] = -1;
	}

	/***************************************************************************
	 * helper functions.
	 ***************************************************************************/
	private boolean greater(int i, int j)
	{
		return keys[pq[i]].compareTo(keys[pq[j]]) > 0;
	}

	private void exchange(int i, int j)
	{
		int swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
		qp[pq[i]] = i;
		qp[pq[j]] = j;
	}

	/***************************************************************************
	 * Heap helper functions.
	 ***************************************************************************/
	private void swim(int k)
	{
		while (k > 1 && greater(k / 2, k))
		{
			exchange(k, k / 2);
			k = k / 2;
		}
	}

	private void sink(int k)
	{
		while (2 * k <= N)
		{
			int j = 2 * k;

			if (j < N && greater(j, j + 1))
				j++;

			if (!greater(k, j))
				break;

			exchange(k, j);
			k = j;
		}
	}
}
