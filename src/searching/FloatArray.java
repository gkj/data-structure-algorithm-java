package searching;

public class FloatArray
{

	public static int binarySearch(float[] a, float key)
	{
		return binarySearch0(a, 0, a.length, key);
	}

	public static int binarySearch(float[] a, int fromIndex, int toIndex, float key)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		return binarySearch0(a, fromIndex, toIndex, key);
	}

	// Like public version, but without range checks.
	private static int binarySearch0(float[] a, int fromIndex, int toIndex, float key)
	{
		int low = fromIndex;
		int high = toIndex - 1;

		while (low <= high)
		{
			int mid = (low + high) >>> 1;
			float midVal = a[mid];

			if (midVal < key)
				low = mid + 1; // Neither val is NaN, thisVal is smaller
			else if (midVal > key)
				high = mid - 1; // Neither val is NaN, thisVal is larger
			else
			{
				int midBits = Float.floatToIntBits(midVal);
				int keyBits = Float.floatToIntBits(key);
				if (midBits == keyBits) // Values are equal
					return mid; // Key found
				else if (midBits < keyBits) // (-0.0, 0.0) or (!NaN, NaN)
					low = mid + 1;
				else // (0.0, -0.0) or (NaN, !NaN)
					high = mid - 1;
			}
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
