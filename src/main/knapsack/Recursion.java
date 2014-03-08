package knapsack;

/**
 * Simplest optimal solution. We're going to use this as the base of the
 * memoization strategy
 * 
 * @author monang
 * 
 */
public class Recursion implements Strategy {
	
	int[] values, weights;

	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {
		this.values = values;
		this.weights = weights;
		
		Tuple<Integer, int[]> ret = solve(cap, values.length, new int[values.length], 0);
		
		for (int i : ret.snd) {
			taken[i] = 1;
		}
	}

	// the best path is constructed using accumulator parameter (path), which
	// will be returned on the base cases
	Tuple<Integer, int[]> solve(int cap, int n, int[] path, int pi) {
		if (cap == 0 || n == 0) { // no more space or no more item
			int[] rpath = new int[pi];
			System.arraycopy(path, 0, rpath, 0, pi);
			return Tuple.make(0, rpath);
		} else {
			Tuple<Integer, int[]> ret = solve(cap, n - 1, path, pi);
			int max = ret.fst;
			if (weights[n - 1] <= cap) { // we can afford this
				path[pi] = n - 1;
				Tuple<Integer, int[]> ret2 = solve(cap - weights[n - 1], n - 1, path, pi + 1);
				int max2 = values[n - 1] + ret2.fst;
				if (max2 > max) {
					return Tuple.make(max2, ret2.snd);
				}
			}
			return ret;
		}
	}
}
