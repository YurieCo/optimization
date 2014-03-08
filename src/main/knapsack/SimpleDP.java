package knapsack;

public class SimpleDP implements Strategy {

	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {
//		int[][] dp = new int[values.length + 1][capacity + 1];
		int value = solve(cap, values.length, values, weights);
		System.out.println("result: " + value);
	}
	
	int solve(int cap, int n, int[] values, int[] weights) {
//		System.out.println(cap + " " + n);
		if (cap == 0) { // no more space
			return 0;
		} else if (n == 0) {
			return 0;
		}
		else {
			int max = solve(cap, n - 1, values, weights);
			if (weights[n - 1] <= cap) {
				int tmax = values[n - 1] + solve(cap - weights[n - 1], n - 1, values, weights);
				max = Math.max(max, tmax);
			}
			return max;
		}
	}

}
