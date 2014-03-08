package knapsack;


/**
 * The dynamic programming solution.
 * 
 * @author monang
 * 
 */
public class DynamicProgramming implements Strategy {
	
	int[] values, weights;
	int[][] dp;

	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {
		this.values = values;
		this.weights = weights;
		this.dp = new int[cap + 1][values.length + 1];
		
		Tuple<Integer, int[]> ret = solve();
		
		for (int i : ret.snd) {
			taken[i] = 1;
		}
	}

	// the state is (cap, n)
	Tuple<Integer, int[]> solve() {

		// Initialize base cases (for the sake of clarity)
		for (int cap = 0; cap < dp.length; cap++) {
			dp[cap][0] = 0;
		}
		for (int n = 0; n < dp[0].length; n++) {
			dp[0][n] = 0;
		}
		
		// fill the table
		for (int cap = 1; cap < dp.length; cap++) {
			for (int n = 1; n < dp[cap].length; n++) {
				int max = dp[cap][n - 1]; // we don't pick current item
				if(weights[n-1] <= cap) { // we can afford this item
					int tmax = values[n - 1] + dp[cap - weights[n - 1]][n - 1]; // we pick current item
					max = Math.max(max, tmax);
				}
				dp[cap][n] = max;
			}
		}
		
		// backtrack, start at lower right corner
		int cap = dp.length - 1;
		int[] path = new int[values.length];
		int pi = 0;
		for (int n = values.length; n > 0; n--) {
			if(dp[cap][n] > dp[cap][n-1]){ // we picked this item
				path[pi++] = n - 1;
				cap -= weights[n - 1];
			}
		}

		int[] rpath = new int[pi];
		System.arraycopy(path, 0, rpath, 0, pi);
		return Tuple.make(dp[dp.length-1][values.length], rpath); // value at the base

	}
}
