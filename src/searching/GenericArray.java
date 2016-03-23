package searching;

import datastructure.Comparator;

public class GenericArray
{

	public static <T> int binarySearch(T[] a, T key, Comparator<? super T> c)
	{
		return binarySearch0(a, 0, a.length, key, c);
	}

	public static <T> int binarySearch(T[] a, int fromIndex, int toIndex, T key, Comparator<? super T> c)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key, c);
	}

	// Like public version, but without range checks.
	private static <T> int binarySearch0(T[] a, int fromIndex, int toIndex, T key, Comparator<? super T> c)
	{
		if (c == null)
		{
			return binarySearch0(a, fromIndex, toIndex, key);
		}
		int low = fromIndex;
		int high = toIndex - 1;

		while (low <= high)
		{
			int mid = (low + high) >>> 1;
			T midVal = a[mid];
			int cmp = c.compare(midVal, key);
			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
	}

	private static int binarySearch0(Object[] a, int fromIndex, int toIndex, Object key)
	{
		int low = fromIndex;
		int high = toIndex - 1;

		while (low <= high)
		{
			int mid = (low + high) >>> 1;
			@SuppressWarnings("rawtypes")
			Comparable midVal = (Comparable) a[mid];
			@SuppressWarnings("unchecked")
			int cmp = midVal.compareTo(key);

			if (cmp < 0)
				low = mid + 1;
			else if (cmp > 0)
				high = mid - 1;
			else
				return mid; // key found
		}
		return -(low + 1); // key not found.
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

}
