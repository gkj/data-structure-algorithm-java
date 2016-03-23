package sortingalgorithm;

public class InsertionX
{

	// This class should not be instantiated.
	private InsertionX()
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

		// put smallest element in position to serve as sentinel
		int exchanges = 0;
		for (int i = N - 1; i > 0; i--)
		{
			if (less(a[i], a[i - 1]))
			{
				exch(a, i, i - 1);
				exchanges++;
			}
		}
		if (exchanges == 0)
			return;

		// insertion sort with half-exchanges
		for (int i = 2; i < N; i++)
		{
			Comparable v = a[i];
			int j = i;
			while (less(v, a[j - 1]))
			{
				a[j] = a[j - 1];
				j--;
			}
			a[j] = v;
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
		for (int i = 1; i < a.length; i++)
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
		InsertionX.sort(a);
		print(a);
	}

}
