package sorting;

public class ByteArray
{

	public static void sort(byte[] a)
	{
		doSort(a, 0, a.length - 1);
	}

	public static void sort(byte[] a, int fromIndex, int toIndex)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		doSort(a, fromIndex, toIndex - 1);
	}

	private static void rangeCheck(int arrayLength, int fromIndex, int toIndex)
	{
		if (fromIndex > toIndex)
		{
			throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
		}
		if (fromIndex < 0)
		{
			throw new ArrayIndexOutOfBoundsException(fromIndex);
		}
		if (toIndex > arrayLength)
		{
			throw new ArrayIndexOutOfBoundsException(toIndex);
		}
	}

	private static void doSort(byte[] a, int left, int right)
	{
		// Use counting sort on large arrays
		if (right - left > 29)
		{
			int[] count = new int[256];

			for (int i = left - 1; ++i <= right; count[a[i] - Byte.MIN_VALUE]++)
				;
			for (int i = 256, k = right + 1; k > left;)
			{
				while (count[--i] == 0)
					;
				byte value = (byte) (i + Byte.MIN_VALUE);
				int s = count[i];

				do
				{
					a[--k] = value;
				}
				while (--s > 0);
			}
		}
		else
		{ // Use insertion sort on small arrays
			for (int i = left, j = i; i < right; j = ++i)
			{
				byte ai = a[i + 1];
				while (ai < a[j])
				{
					a[j + 1] = a[j];
					if (j-- == left)
					{
						break;
					}
				}
				a[j + 1] = ai;
			}
		}
	}

}
