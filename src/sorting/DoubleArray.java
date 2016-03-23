package sorting;

public class DoubleArray
{

	public static void sort(double[] a)
	{
		sort(a, 0, a.length - 1, null, 0, 0);
	}

	public static void sort(double[] a, int fromIndex, int toIndex)
	{
		rangeCheck(a.length, fromIndex, toIndex);
		sort(a, fromIndex, toIndex - 1, null, 0, 0);
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

	private static void sort(double[] a, int left, int right, double[] work, int workBase, int workLen)
	{

		while (left <= right && Double.isNaN(a[right]))
		{
			--right;
		}
		for (int k = right; --k >= left;)
		{
			double ak = a[k];
			if (ak != ak)
			{ // a[k] is NaN
				a[k] = a[right];
				a[right] = ak;
				--right;
			}
		}

		doSort(a, left, right, work, workBase, workLen);

		int hi = right;

		while (left < hi)
		{
			int middle = (left + hi) >>> 1;
			double middleValue = a[middle];

			if (middleValue < 0.0d)
			{
				left = middle + 1;
			}
			else
			{
				hi = middle;
			}
		}

		while (left <= right && Double.doubleToRawLongBits(a[left]) < 0)
		{
			++left;
		}

		for (int k = left, p = left - 1; ++k <= right;)
		{
			double ak = a[k];
			if (ak != 0.0d)
			{
				break;
			}
			if (Double.doubleToRawLongBits(ak) < 0)
			{ // ak is -0.0d
				a[k] = 0.0d;
				a[++p] = -0.0d;
			}
		}
	}

	private static void doSort(double[] a, int left, int right, double[] work, int workBase, int workLen)
	{
		// Use Quicksort on small arrays
		if (right - left < 286)
		{
			sort(a, left, right, true);
			return;
		}

		int[] run = new int[67 + 1];
		int count = 0;
		run[0] = left;

		// Check if the array is nearly sorted
		for (int k = left; k < right; run[count] = k)
		{
			if (a[k] < a[k + 1])
			{ // ascending
				while (++k <= right && a[k - 1] <= a[k])
					;
			}
			else if (a[k] > a[k + 1])
			{ // descending
				while (++k <= right && a[k - 1] >= a[k])
					;
				for (int lo = run[count] - 1, hi = k; ++lo < --hi;)
				{
					double t = a[lo];
					a[lo] = a[hi];
					a[hi] = t;
				}
			}
			else
			{ // equal
				for (int m = 33; ++k <= right && a[k - 1] == a[k];)
				{
					if (--m == 0)
					{
						sort(a, left, right, true);
						return;
					}
				}
			}

			if (++count == 67)
			{
				sort(a, left, right, true);
				return;
			}
		}

		// Check special cases
		// Implementation note: variable "right" is increased by 1.
		if (run[count] == right++)
		{ // The last run contains one element
			run[++count] = right;
		}
		else if (count == 1)
		{ // The array is already sorted
			return;
		}

		// Determine alternation base for merge
		byte odd = 0;
		for (int n = 1; (n <<= 1) < count; odd ^= 1)
			;

		// Use or create temporary array b for merging
		double[] b; // temp array; alternates with a
		int ao, bo; // array offsets from 'left'
		int blen = right - left; // space needed for b
		if (work == null || workLen < blen || workBase + blen > work.length)
		{
			work = new double[blen];
			workBase = 0;
		}
		if (odd == 0)
		{
			System.arraycopy(a, left, work, workBase, blen);
			b = a;
			bo = 0;
			a = work;
			ao = workBase - left;
		}
		else
		{
			b = work;
			ao = 0;
			bo = workBase - left;
		}

		// Merging
		for (int last; count > 1; count = last)
		{
			for (int k = (last = 0) + 2; k <= count; k += 2)
			{
				int hi = run[k], mi = run[k - 1];
				for (int i = run[k - 2], p = i, q = mi; i < hi; ++i)
				{
					if (q >= hi || p < mi && a[p + ao] <= a[q + ao])
					{
						b[i + bo] = a[p++ + ao];
					}
					else
					{
						b[i + bo] = a[q++ + ao];
					}
				}
				run[++last] = hi;
			}
			if ((count & 1) != 0)
			{
				for (int i = right, lo = run[count - 1]; --i >= lo; b[i + bo] = a[i + ao])
					;
				run[++last] = right;
			}
			double[] t = a;
			a = b;
			b = t;
			int o = ao;
			ao = bo;
			bo = o;
		}
	}

	private static void sort(double[] a, int left, int right, boolean leftmost)
	{
		int length = right - left + 1;

		// Use insertion sort on tiny arrays
		if (length < 47)
		{
			if (leftmost)
			{

				for (int i = left, j = i; i < right; j = ++i)
				{
					double ai = a[i + 1];
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
			else
			{

				do
				{
					if (left >= right)
					{
						return;
					}
				}
				while (a[++left] >= a[left - 1]);

				for (int k = left; ++left <= right; k = ++left)
				{
					double a1 = a[k], a2 = a[left];

					if (a1 < a2)
					{
						a2 = a1;
						a1 = a[left];
					}
					while (a1 < a[--k])
					{
						a[k + 2] = a[k];
					}
					a[++k + 1] = a1;

					while (a2 < a[--k])
					{
						a[k + 1] = a[k];
					}
					a[k + 1] = a2;
				}
				double last = a[right];

				while (last < a[--right])
				{
					a[right + 1] = a[right];
				}
				a[right + 1] = last;
			}
			return;
		}

		// Inexpensive approximation of length / 7
		int seventh = (length >> 3) + (length >> 6) + 1;

		int e3 = (left + right) >>> 1; // The midpoint
		int e2 = e3 - seventh;
		int e1 = e2 - seventh;
		int e4 = e3 + seventh;
		int e5 = e4 + seventh;

		// Sort these elements using insertion sort
		if (a[e2] < a[e1])
		{
			double t = a[e2];
			a[e2] = a[e1];
			a[e1] = t;
		}

		if (a[e3] < a[e2])
		{
			double t = a[e3];
			a[e3] = a[e2];
			a[e2] = t;
			if (t < a[e1])
			{
				a[e2] = a[e1];
				a[e1] = t;
			}
		}
		if (a[e4] < a[e3])
		{
			double t = a[e4];
			a[e4] = a[e3];
			a[e3] = t;
			if (t < a[e2])
			{
				a[e3] = a[e2];
				a[e2] = t;
				if (t < a[e1])
				{
					a[e2] = a[e1];
					a[e1] = t;
				}
			}
		}
		if (a[e5] < a[e4])
		{
			double t = a[e5];
			a[e5] = a[e4];
			a[e4] = t;
			if (t < a[e3])
			{
				a[e4] = a[e3];
				a[e3] = t;
				if (t < a[e2])
				{
					a[e3] = a[e2];
					a[e2] = t;
					if (t < a[e1])
					{
						a[e2] = a[e1];
						a[e1] = t;
					}
				}
			}
		}

		// Pointers
		int less = left; // The index of the first element of center part
		int great = right; // The index before the first element of right part

		if (a[e1] != a[e2] && a[e2] != a[e3] && a[e3] != a[e4] && a[e4] != a[e5])
		{

			double pivot1 = a[e2];
			double pivot2 = a[e4];

			a[e2] = a[left];
			a[e4] = a[right];

			while (a[++less] < pivot1)
				;
			while (a[--great] > pivot2)
				;

			outer: for (int k = less - 1; ++k <= great;)
			{
				double ak = a[k];
				if (ak < pivot1)
				{ // Move a[k] to left part
					a[k] = a[less];

					a[less] = ak;
					++less;
				}
				else if (ak > pivot2)
				{ // Move a[k] to right part
					while (a[great] > pivot2)
					{
						if (great-- == k)
						{
							break outer;
						}
					}
					if (a[great] < pivot1)
					{ // a[great] <= pivot2
						a[k] = a[less];
						a[less] = a[great];
						++less;
					}
					else
					{ // pivot1 <= a[great] <= pivot2
						a[k] = a[great];
					}

					a[great] = ak;
					--great;
				}
			}

			// Swap pivots into their final positions
			a[left] = a[less - 1];
			a[less - 1] = pivot1;
			a[right] = a[great + 1];
			a[great + 1] = pivot2;

			// Sort left and right parts recursively, excluding known pivots
			sort(a, left, less - 2, leftmost);
			sort(a, great + 2, right, false);

			if (less < e1 && e5 < great)
			{

				while (a[less] == pivot1)
				{
					++less;
				}

				while (a[great] == pivot2)
				{
					--great;
				}

				outer: for (int k = less - 1; ++k <= great;)
				{
					double ak = a[k];
					if (ak == pivot1)
					{ // Move a[k] to left part
						a[k] = a[less];
						a[less] = ak;
						++less;
					}
					else if (ak == pivot2)
					{ // Move a[k] to right part
						while (a[great] == pivot2)
						{
							if (great-- == k)
							{
								break outer;
							}
						}
						if (a[great] == pivot1)
						{ // a[great] < pivot2
							a[k] = a[less];

							a[less] = a[great];
							++less;
						}
						else
						{ // pivot1 < a[great] < pivot2
							a[k] = a[great];
						}
						a[great] = ak;
						--great;
					}
				}
			}

			// Sort center part recursively
			sort(a, less, great, false);

		}
		else
		{ // Partitioning with one pivot

			double pivot = a[e3];

			for (int k = less; k <= great; ++k)
			{
				if (a[k] == pivot)
				{
					continue;
				}
				double ak = a[k];
				if (ak < pivot)
				{ // Move a[k] to left part
					a[k] = a[less];
					a[less] = ak;
					++less;
				}
				else
				{ // a[k] > pivot - Move a[k] to right part
					while (a[great] > pivot)
					{
						--great;
					}
					if (a[great] < pivot)
					{ // a[great] <= pivot
						a[k] = a[less];
						a[less] = a[great];
						++less;
					}
					else
					{ // a[great] == pivot

						a[k] = a[great];
					}
					a[great] = ak;
					--great;
				}
			}

			sort(a, left, less - 1, leftmost);
			sort(a, great + 1, right, false);
		}
	}
}