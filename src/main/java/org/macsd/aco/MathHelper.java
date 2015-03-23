package aco;

import java.util.Arrays;

public class MathHelper {

	static double[] log2table = {0, 0};
	
	/*
	 * Return log(x) in base 2.
	 */
	public static double log2(int x) {
		return Math.log(x)/Math.log(2);
	}
	
	/*
	 * Return the log base 2 of the factorial of x.
	 */
	public static double log2fact(int x) {
		if(x>=log2table.length) {
			log2table = Arrays.copyOf(log2table, x+1);
			log2table[x] = log2(x) + log2table[x-1];
		}
		return log2table[x];
	}
}
