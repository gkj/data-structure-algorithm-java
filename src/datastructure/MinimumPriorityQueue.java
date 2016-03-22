/*
 * Represent priority queue
 * 
 * Author: Gilang Kusuma Jati (gilang.k@samsung.com), SRIN
 *  
 */
package datastructure;

public class MinimumPriorityQueue<Key extends Comparable<Key>>
{
	private Key[] pq; // store items at indices 1 to N
	private int N; // number of items on priority queue
	private Class<Key> type;

	// construct an empty priority queue with the given initial capacity.
	public MinimumPriorityQueue(Class<Key> type, int initCapacity)
	{
		this.type = type;
		final Key[] array = (Key[]) java.lang.reflect.Array.newInstance(type, initCapacity);
		pq = array;
		N = 0;
	}

	// contruct an empty priority queue.
	public MinimumPriorityQueue(Class<Key> type)
	{
		this(type, 1);
	}

	// construct a priority queue from the array of keys.
	public MinimumPriorityQueue(Key[] keys)
	{
		N = keys.length;
		pq = (Key[]) new Object[keys.length + 1];
		for (int i = 0; i < N; i++)
			pq[i + 1] = keys[i];
		for (int k = N / 2; k >= 1; k--)
			sink(k);
		assert isMinHeap();
	}

	// return true if priority queue is empty
	public boolean isEmpty()
	{
		return N == 0;
	}

	// returns the number of keys on the priority queue.
	public int size()
	{
		return N;
	}

	// returns a smallest key on the priority queue.
	public Key min()
	{
		if (isEmpty())
			throw new RuntimeException("Priority queue underflow");

		return pq[1];
	}

	// helper function to double the size of the heap array
	private void resize(int capacity)
	{
		assert capacity > N;
		Key[] temp = (Key[]) java.lang.reflect.Array.newInstance(type, capacity);
		for (int i = 1; i <= N; i++)
		{
			temp[i] = pq[i];
		}
		pq = temp;
	}

	// adds a new key to the priority queue.
	public void insert(Key x)
	{
		// double size of array if necessary
		if (N == pq.length - 1)
			resize(2 * pq.length);

		// add x, and percolate it up to maintain heap invariant
		pq[++N] = x;
		swim(N);
		assert isMinHeap();
	}

	// removes and returns a smallest key on the priority queue.
	public Key delMin()
	{
		if (isEmpty())
			throw new RuntimeException("Priority queue underflow");

		exchange(1, N);
		Key min = pq[N--];
		sink(1);
		pq[N + 1] = null; // avoid loitering and help with garbage collection

		if ((N > 0) && (N == (pq.length - 1) / 4))
			resize(pq.length / 2);

		assert isMinHeap();

		return min;
	}

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

	private boolean greater(int i, int j)
	{
		return ((Comparable<Key>) pq[i]).compareTo(pq[j]) > 0;
	}

	private void exchange(int i, int j)
	{
		Key swap = pq[i];
		pq[i] = pq[j];
		pq[j] = swap;
	}

	// is pq[1..N] a min heap?
	private boolean isMinHeap()
	{
		return isMinHeap(1);
	}

	// is subtree of pq[1..N] rooted at k a min heap?
	private boolean isMinHeap(int k)
	{
		if (k > N)
			return true;

		int left = 2 * k, right = 2 * k + 1;

		if (left <= N && greater(k, left))
			return false;

		if (right <= N && greater(k, right))
			return false;

		return isMinHeap(left) && isMinHeap(right);
	}
}
