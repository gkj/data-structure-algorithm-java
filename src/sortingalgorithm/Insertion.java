package sortingalgorithm;

import datastructure.Comparator;

public class Insertion
{

	// This class should not be instantiated.
	private Insertion()
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
		int N = a.length;
		for (int i = 0; i < N; i++)
		{
			for (int j = i; j > 0 && less(a[j], a[j - 1]); j--)
			{
				exch(a, j, j - 1);
			}
			assert isSorted(a, 0, i);
		}
		assert isSorted(a);
	}

	/**
	 * Rearranges the subarray a[lo..hi] in ascending order, using the natural
	 * order.
	 * 
	 * @param a
	 *            the array to be sorted
	 * @param lo
	 *            left endpoint
	 * @param hi
	 *            right endpoint
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Comparable[] a, int lo, int hi)
	{
		for (int i = lo; i <= hi; i++)
		{
			for (int j = i; j > lo && less(a[j], a[j - 1]); j--)
			{
				exch(a, j, j - 1);
			}
		}
		assert isSorted(a, lo, hi);
	}

	/**
	 * Rearranges the array in ascending order, using a comparator.
	 * 
	 * @param a
	 *            the array
	 * @param comparator
	 *            the comparator specifying the order
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Object[] a, Comparator comparator)
	{
		int N = a.length;
		for (int i = 0; i < N; i++)
		{
			for (int j = i; j > 0 && less(a[j], a[j - 1], comparator); j--)
			{
				exch(a, j, j - 1);
			}
			assert isSorted(a, 0, i, comparator);
		}
		assert isSorted(a, comparator);
	}

	/**
	 * Rearranges the subarray a[lo..hi] in ascending order, using a comparator.
	 * 
	 * @param a
	 *            the array
	 * @param lo
	 *            left endpoint
	 * @param hi
	 *            right endpoint
	 * @param comparator
	 *            the comparator specifying the order
	 */
	@SuppressWarnings("rawtypes")
	public static void sort(Object[] a, int lo, int hi, Comparator comparator)
	{
		for (int i = lo; i <= hi; i++)
		{
			for (int j = i; j > lo && less(a[j], a[j - 1], comparator); j--)
			{
				exch(a, j, j - 1);
			}
		}
		assert isSorted(a, lo, hi, comparator);
	}

	// return a permutation that gives the elements in a[] in ascending order
	// do not change the original array a[]
	/**
	 * Returns a permutation that gives the elements in the array in ascending
	 * order.
	 * 
	 * @param a
	 *            the array
	 * @return a permutation <tt>p[]</tt> such that <tt>a[p[0]]</tt>,
	 *         <tt>a[p[1]]</tt>, ..., <tt>a[p[N-1]]</tt> are in ascending order
	 */
	@SuppressWarnings("rawtypes")
	public static int[] indexSort(Comparable[] a)
	{
		int N = a.length;
		int[] index = new int[N];
		for (int i = 0; i < N; i++)
			index[i] = i;

		for (int i = 0; i < N; i++)
			for (int j = i; j > 0 && less(a[index[j]], a[index[j - 1]]); j--)
				exch(index, j, j - 1);

		return index;
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

	// is v < w ?
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static boolean less(Object v, Object w, Comparator comparator)
	{
		return comparator.compare(v, w) < 0;
	}

	// exchange a[i] and a[j]
	private static void exch(Object[] a, int i, int j)
	{
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	// exchange a[i] and a[j] (for indirect sort)
	private static void exch(int[] a, int i, int j)
	{
		int swap = a[i];
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

	// is the array sorted from a[lo] to a[hi]
	@SuppressWarnings("rawtypes")
	private static boolean isSorted(Comparable[] a, int lo, int hi)
	{
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1]))
				return false;
		return true;
	}

	@SuppressWarnings("rawtypes")
	private static boolean isSorted(Object[] a, Comparator comparator)
	{
		return isSorted(a, 0, a.length - 1, comparator);
	}

	// is the array sorted from a[lo] to a[hi]
	@SuppressWarnings("rawtypes")
	private static boolean isSorted(Object[] a, int lo, int hi, Comparator comparator)
	{
		for (int i = lo + 1; i <= hi; i++)
			if (less(a[i], a[i - 1], comparator))
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
	 * Reads in a sequence of strings from standard input; insertion sorts them;
	 * and prints them to standard output in ascending order.
	 */
	public static void main(String[] args)
	{
		String[] a = { "Gilang", "Kusuma", "Jati" };
		Insertion.sort(a);
		print(a);
	}
}
