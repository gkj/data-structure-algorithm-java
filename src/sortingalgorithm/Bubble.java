package sortingalgorithm;

public class Bubble
{

	// This class should not be instantiated.
	private Bubble()
	{
	}

	/**
	 * Rearranges the array in ascending order, using the natural order.
	 * 
	 * @param a
	 *            the array to be sorted
	 */
	public static <Key extends Comparable<Key>> void sort(Key[] a)
	{
		int N = a.length;
		for (int i = 0; i < N; i++)
		{
			int exchanges = 0;
			for (int j = N - 1; j > i; j--)
			{
				if (less(a[j], a[j - 1]))
				{
					exch(a, j, j - 1);
					exchanges++;
				}
			}
			if (exchanges == 0)
				break;
		}
	}

	// is v < w ?
	private static <Key extends Comparable<Key>> boolean less(Key v, Key w)
	{
		return v.compareTo(w) < 0;
	}

	// exchange a[i] and a[j]
	private static <Key extends Comparable<Key>> void exch(Key[] a, int i, int j)
	{
		Key swap = a[i];
		a[i] = a[j];
		a[j] = swap;
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
	 * Reads in a sequence of strings from standard input; bubble sorts them;
	 * and prints them to standard output in ascending order.
	 */
	public static void main(String[] args)
	{
		String[] a = { "Gilang", "Kusuma", "Jati" };
		Bubble.sort(a);
		print(a);
	}
}
