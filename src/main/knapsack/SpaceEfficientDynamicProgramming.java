package knapsack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * DP using less space. The idea is to use 2 arrays to track previous and
 * current score, and a list of list of range for backtracking.
 * 
 * An even more space efficient solution would be to use single array and
 * filling the score from bottom to the top, but this is less intuitive.
 * 
 */
public class SpaceEfficientDynamicProgramming implements Strategy {
	
	int[] values, weights;
	int C;
	int[][] dp;
	List<List<Range>> dir;

	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {

		this.values = values;
		this.weights = weights;
		this.C = cap;
		this.dp = new int[2][cap + 1];
		this.dir = new ArrayList<List<Range>>(values.length + 1);
		for (int i = 0; i < values.length + 1; i++) {
			dir.add(new ArrayList<Range>());
		}

		
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

			int currentRow = n % 2;
			int prevRow = (n - 1) % 2;
			for (int cap = 1; cap < dp[0].length; cap++) {
				int max = dp[prevRow][cap]; // we don't pick current item
				if(weights[n - 1] <= cap) { // we can afford this item
					int tmax = values[n - 1] + dp[prevRow][cap - weights[n - 1]]; // we pick current item
					if(tmax > max) {
						max = tmax;
						
						List<Range> list = dir.get(n);
						boolean addNew = true;
						if (!list.isEmpty()) {
							Range last = list.get(list.size() - 1);
							if (last.cap + last.count == cap) {
								last.count++;
								addNew = false;
							}
						}
						if(addNew) {
							Range last = new Range(cap);
							list.add(last);
						}
						
					}
					

				}
				dp[currentRow][cap] = max;
			}
		}
		
		// backtrack, start at lower right corner
		int cap = C;
		int[] path = new int[values.length];
		int pi = 0;
		for (int n = values.length; n > 0; n--) {
			List<Range> list = dir.get(n);
			Iterator<Range> iter = list.iterator();
			boolean stop = false;
			boolean found = false;
			while (iter.hasNext() && !stop) {
				Range current = iter.next();
				if (cap < current.cap) {
					stop = true;
				} else if (current.cap <= cap && cap < current.cap + current.count) {
					stop = true;
					found = true;
				}
			}
			if (found) { // we picked this item
				path[pi++] = n - 1;
				cap -= weights[n - 1];
			}
		}

		int[] rpath = new int[pi];
		System.arraycopy(path, 0, rpath, 0, pi);
		return Tuple.make(dp[values.length % 2][dp[0].length - 1], rpath); // value at the base

	}
}

class Range {
	final int cap;
	int count;
	public Range(int cap) {
		this.cap = cap;
		this.count = 1;
	}
	@Override
	public String toString() {
		return "(" + cap +", " + count + ")";
	}
	
	
}
