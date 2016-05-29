package numbertheory;

import java.util.Scanner;

public class LeastCommonMultiple
{
	private static long lcm(long a, long b)
	{
		return a * (b / GreatestCommonDivisor.gcd(a, b));
	}

	@SuppressWarnings("unused")
	private static long lcm(long[] input)
	{
		long result = input[0];
		for (int i = 1; i < input.length; i++)
			result = lcm(result, input[i]);
		return result;
	}

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		int a = scanner.nextInt();
		int b = scanner.nextInt();

		System.out.println(lcm(a, b));

		scanner.close();
	}

}
