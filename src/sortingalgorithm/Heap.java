package sortingalgorithm;

public class Heap
{

	// This class should not be instantiated.
	private Heap()
	{
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * 
	 * @param pq
	 *            the array to be sorted
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] pq)
	{
		int N = pq.length;
		for (int k = N / 2; k >= 1; k--)
			sink(pq, k, N);
		while (N > 1)
		{
			exch(pq, 1, N--);
			sink(pq, 1, N);
		}
	}

	/***************************************************************************
	 * Helper functions to restore the heap invariant.
	 ***************************************************************************/

	@SuppressWarnings("rawtypes")
	private static void sink(Comparable[] pq, int k, int N)
	{
		while (2 * k <= N)
		{
			int j = 2 * k;
			if (j < N && less(pq, j, j + 1))
				j++;
			if (!less(pq, k, j))
				break;
			exch(pq, k, j);
			k = j;
		}
	}

	/***************************************************************************
	 * Helper functions for comparisons and swaps. Indices are "off-by-one" to
	 * support 1-based indexing.
	 ***************************************************************************/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean less(Comparable[] pq, int i, int j)
	{
		return pq[i - 1].compareTo(pq[j - 1]) < 0;
	}

	private static void exch(Object[] pq, int i, int j)
	{
		Object swap = pq[i - 1];
		pq[i - 1] = pq[j - 1];
		pq[j - 1] = swap;
	}

	// is v < w ?
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean less(Comparable v, Comparable w)
	{
		return v.compareTo(w) < 0;
	}

	/***************************************************************************
	 * Check if array is sorted - useful for debugging.
	 ***************************************************************************/
	@SuppressWarnings({ "rawtypes", "unused" })
	private static boolean isSorted(Comparable[] a)
	{
		for (int i = 1; i < a.length; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	// print array to standard output
	@SuppressWarnings("rawtypes")
	private static void show(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			System.out.println(a[i]);
		}
	}

	/**
	 * Reads in a sequence of strings from standard input; heapsorts them; and
	 * prints them to standard output in ascending order.
	 */
	public static void main(String[] args)
	{
		String[] a = { "Gilang", "Kusuma", "Jati" };
		Heap.sort(a);
		show(a);
	}
}
