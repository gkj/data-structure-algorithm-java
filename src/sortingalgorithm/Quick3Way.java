package sortingalgorithm;

import util.StdRandom;

public class Quick3Way
{

	// This class should not be instantiated.
	private Quick3Way()
	{
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * 
	 * @param a
	 *            the array to be sorted
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a)
	{
		StdRandom.shuffle(a);
		sort(a, 0, a.length - 1);
		assert isSorted(a);
	}

	// quicksort the subarray a[lo .. hi] using 3-way partitioning
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static void sort(Comparable[] a, int lo, int hi)
	{
		if (hi <= lo)
			return;
		int lt = lo, gt = hi;
		Comparable v = a[lo];
		int i = lo;
		while (i <= gt)
		{
			int cmp = a[i].compareTo(v);
			if (cmp < 0)
				exch(a, lt++, i++);
			else if (cmp > 0)
				exch(a, i, gt--);
			else
				i++;
		}

		// a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi].
		sort(a, lo, lt - 1);
		sort(a, gt + 1, hi);
		assert isSorted(a, lo, hi);
	}

	/***************************************************************************
	 * Helper sorting functions.
	 ***************************************************************************/

	// is v < w ?
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean less(Comparable v, Comparable w)
	{
		return v.compareTo(w) < 0;
	}

	// does v == w ?
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private static boolean eq(Comparable v, Comparable w)
	{
		return v.compareTo(w) == 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j)
	{
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	/***************************************************************************
	 * Check if array is sorted - useful for debugging.
	 ***************************************************************************/
	@SuppressWarnings("rawtypes")
	private static boolean isSorted(Comparable[] a)
	{
		return isSorted(a, 0, a.length - 1);
	}

	@SuppressWarnings("rawtypes")
	private static boolean isSorted(Comparable[] a, int lo, int hi)
	{
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	// print array to standard output
	@SuppressWarnings("rawtypes")
	private static void print(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			System.out.println(a[i]);
		}
	}

	/**
	 * Reads in a sequence of strings from standard input; 3-way quicksorts
	 * them; and prints them to standard output in ascending order.
	 */
	public static void main(String[] args)
	{
		String[] a = { "Gilang", "Kusuma", "Jati" };
		Quick3Way.sort(a);
		print(a);
	}

}
