package util;

public class ArrayHashCode
{

	public static int hashCode(long a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (long element : a)
		{
			int elementHash = (int) (element ^ (element >>> 32));
			result = 31 * result + elementHash;
		}

		return result;
	}

	public static int hashCode(int a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (int element : a)
			result = 31 * result + element;

		return result;
	}

	public static int hashCode(short a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (short element : a)
			result = 31 * result + element;

		return result;
	}

	public static int hashCode(char a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (char element : a)
			result = 31 * result + element;

		return result;
	}

	public static int hashCode(byte a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (byte element : a)
			result = 31 * result + element;

		return result;
	}

	public static int hashCode(boolean a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (boolean element : a)
			result = 31 * result + (element ? 1231 : 1237);

		return result;
	}

	public static int hashCode(float a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (float element : a)
			result = 31 * result + Float.floatToIntBits(element);

		return result;
	}

	public static int hashCode(double a[])
	{
		if (a == null)
			return 0;

		int result = 1;
		for (double element : a)
		{
			long bits = Double.doubleToLongBits(element);
			result = 31 * result + (int) (bits ^ (bits >>> 32));
		}
		return result;
	}

	public static int hashCode(Object a[])
	{
		if (a == null)
			return 0;

		int result = 1;

		for (Object element : a)
			result = 31 * result + (element == null ? 0 : element.hashCode());

		return result;
	}

	public static int deepHashCode(Object a[])
	{
		if (a == null)
			return 0;

		int result = 1;

		for (Object element : a)
		{
			int elementHash = 0;
			if (element instanceof Object[])
				elementHash = deepHashCode((Object[]) element);
			else if (element instanceof byte[])
				elementHash = hashCode((byte[]) element);
			else if (element instanceof short[])
				elementHash = hashCode((short[]) element);
			else if (element instanceof int[])
				elementHash = hashCode((int[]) element);
			else if (element instanceof long[])
				elementHash = hashCode((long[]) element);
			else if (element instanceof char[])
				elementHash = hashCode((char[]) element);
			else if (element instanceof float[])
				elementHash = hashCode((float[]) element);
			else if (element instanceof double[])
				elementHash = hashCode((double[]) element);
			else if (element instanceof boolean[])
				elementHash = hashCode((boolean[]) element);
			else if (element != null)
				elementHash = element.hashCode();

			result = 31 * result + elementHash;
		}

		return result;
	}

}
