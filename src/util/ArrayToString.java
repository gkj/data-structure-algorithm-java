package util;

public class ArrayToString
{

	public static String toString(long[] a)
	{
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(int[] a)
	{
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(short[] a)
	{
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(char[] a)
	{
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(byte[] a)
	{
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(boolean[] a)
	{
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(float[] a)
	{
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(double[] a)
	{
		if (a == null)
			return "null";
		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(a[i]);
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

	public static String toString(Object[] a)
	{
		if (a == null)
			return "null";

		int iMax = a.length - 1;
		if (iMax == -1)
			return "[]";

		StringBuilder b = new StringBuilder();
		b.append('[');
		for (int i = 0;; i++)
		{
			b.append(String.valueOf(a[i]));
			if (i == iMax)
				return b.append(']').toString();
			b.append(", ");
		}
	}

}
