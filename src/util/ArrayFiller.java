package util;

public class ArrayFiller
{

	// Filling

	public static void fill(long[] a, long val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(long[] a, int fromIndex, int toIndex, long val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(int[] a, int val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(int[] a, int fromIndex, int toIndex, int val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(short[] a, short val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(short[] a, int fromIndex, int toIndex, short val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(char[] a, char val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(char[] a, int fromIndex, int toIndex, char val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(byte[] a, byte val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(byte[] a, int fromIndex, int toIndex, byte val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(boolean[] a, boolean val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(boolean[] a, int fromIndex, int toIndex, boolean val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(double[] a, double val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(double[] a, int fromIndex, int toIndex, double val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(float[] a, float val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(float[] a, int fromIndex, int toIndex, float val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
	}

	public static void fill(Object[] a, Object val)
	{
		for (int i = 0, len = a.length; i < len; i++)
			a[i] = val;
	}

	public static void fill(Object[] a, int fromIndex, int toIndex, Object val)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		for (int i = fromIndex; i < toIndex; i++)
			a[i] = val;
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
