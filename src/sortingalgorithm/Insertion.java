package sortingalgorithm;

public class Insertion
{
	// This class should not be instantiated.
	private Insertion()
	{
	}

	
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
	private static void show(Comparable[] a)
	{
		for (int i = 0; i < a.length; i++)
		{
			System.out.println(a[i]);
		}
	}
	
	public static void main(String[] args)
	{
		String[] a = {"Gilang", "Kusuma", "Jati"};
		Insertion.sort(a);
		show(a);
	}

}
