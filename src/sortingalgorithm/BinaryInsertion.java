package sortingalgorithm;

public class BinaryInsertion
{

	// This class should not be instantiated.
	private BinaryInsertion()
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
		for (int i = 1; i < N; i++)
		{

			// binary search to determine index j at which to insert a[i]
			Comparable v = a[i];
			int lo = 0, hi = i;
			while (lo < hi)
			{
				int mid = lo + (hi - lo) / 2;
				if (less(v, a[mid]))
					hi = mid;
				else
					lo = mid + 1;
			}

			// insetion sort with "half exchanges"
			// (insert a[i] at index j and shift a[j], ..., a[i-1] to right)
			for (int j = i; j > lo; --j)
				a[j] = a[j - 1];
			a[lo] = v;
		}
		assert isSorted(a);
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

	// exchange a[i] and a[j]
	@SuppressWarnings("unused")
	private static void exch(Object[] a, int i, int j)
	{
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}

	// exchange a[i] and a[j] (for indirect sort)
	@SuppressWarnings("unused")
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
		BinaryInsertion.sort(a);
		print(a);
	}
}
