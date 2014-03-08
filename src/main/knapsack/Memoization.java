package knapsack;

import java.util.Arrays;

/**
 * From recursion to memoisation. This is the base for the dynamic programming solution.
 * 
 * @author monang
 * 
 */
public class Memoization implements Strategy {
	
	int[] values, weights;
	Tuple<Integer, int[]>[][] cache;

	@SuppressWarnings("unchecked")
	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {
		this.values = values;
		this.weights = weights;
		this.cache = new Tuple[cap + 1][values.length + 1];
		
		Tuple<Integer, int[]> ret = solve(cap, values.length, new int[values.length], 0);
		System.out.println(ret.fst);
		
		for (int i : ret.snd) {
			taken[i] = 1;
		}
	}

	// the state is (cap, n)
	Tuple<Integer, int[]> solve(int cap, int n, int[] path, int pi) {

		Tuple<Integer, int[]> value = cache[cap][n];
		if(value != null) { // we've computed this
			return value;
		}
		
		if (cap == 0 || n == 0) { // no more space or no more item
			int[] rpath = new int[pi];
			System.arraycopy(path, 0, rpath, 0, pi);
			value = Tuple.make(0, rpath); // value at the base
		} else {
			Tuple<Integer, int[]> ret = solve(cap, n - 1, path, pi);
			value = ret; // value when we don't pick current item
			int max = ret.fst;
			if (weights[n - 1] <= cap) { // we can afford this
				path[pi] = n - 1;
				Tuple<Integer, int[]> ret2 = solve(cap - weights[n - 1], n - 1, path, pi + 1);
				int max2 = values[n - 1] + ret2.fst;
				if (max2 > max) {
					value = Tuple.make(max2, ret2.snd); // value when we pick current item
				}
			}
		}
		
		cache[cap][n] = value; // remember this
		
		return value;
	}
}
