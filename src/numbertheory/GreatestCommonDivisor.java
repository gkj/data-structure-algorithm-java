package numbertheory;

import java.util.Scanner;

public class GreatestCommonDivisor
{
	public static long gcd(long[] input)
	{
		long result = input[0];
		for (int i = 1; i < input.length; i++)
			result = gcd(result, input[i]);
		return result;
	}

	public static long gcd(long a, long b)
	{
		if (b == 0)
			return a;

		return gcd(b, a % b);
	}

	public static void main(String args[])
	{
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();

		System.out.println(gcd(a, b));

		scanner.close();
	}
}
