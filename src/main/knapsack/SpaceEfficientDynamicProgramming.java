package knapsack;

import java.util.HashSet;
import java.util.Set;


/**
 * DP using less space. The idea is to use 2 arrays to track previous and
 * current score, and a set for backtracking. The set contains pairs of item
 * number and capacity, which denote the states of when we pick the item.
 * 
 * An even more space efficient solution would be to use single array and
 * filling the score from bottom to the top, but this is less intuitive.
 * 
 */
public class SpaceEfficientDynamicProgramming implements Strategy {
	
	int[] values, weights;
	int C;
	int[][] dp;
	Set<State> dir;

	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {

		this.values = values;
		this.weights = weights;
		this.C = cap;
		this.dp = new int[2][cap + 1];
		this.dir = new HashSet<State>();
		
		Tuple<Integer, int[]> ret = solve();
		
		for (int i : ret.snd) {
			taken[i] = 1;
		}
	}

	// the state is (cap, n)
	Tuple<Integer, int[]> solve() {

		// Initialize base cases (for the sake of clarity)
		for (int n = 0; n < dp.length; n++) {
			dp[n][0] = 0;
		}
		for (int cap = 0; cap < dp[0].length; cap++) {
			dp[0][cap] = 0;
		}
		
		// fill the table
		for (int n = 1; n < values.length + 1; n++) {
			System.out.println(n);
			int currentRow = n % 2;
			int prevRow = (n - 1) % 2;
			for (int cap = 1; cap < dp[0].length; cap++) {
				int max = dp[prevRow][cap]; // we don't pick current item
				if(weights[n - 1] <= cap) { // we can afford this item
					int tmax = values[n - 1] + dp[prevRow][cap - weights[n - 1]]; // we pick current item
					max = Math.max(max, tmax);
					dir.add(new State(n, cap));
				}
				dp[currentRow][cap] = max;
			}
		}
		
		
		// backtrack, start at lower right corner
		int cap = C;
		int[] path = new int[values.length];
		int pi = 0;
		for (int n = values.length; n > 0; n--) {
			if(dir.contains(new State(n, cap))){ // we picked this item
				path[pi++] = n - 1;
				cap -= weights[n - 1];
			}
		}

		int[] rpath = new int[pi];
		System.arraycopy(path, 0, rpath, 0, pi);
		return Tuple.make(dp[values.length % 2][dp[0].length - 1], rpath); // value at the base

	}
}

class State {
	final int n;
	final int cap;
	public State(int n, int cap) {
		this.n = n;
		this.cap = cap;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cap;
		result = prime * result + n;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (cap != other.cap)
			return false;
		if (n != other.n)
			return false;
		return true;
	}
	
}
