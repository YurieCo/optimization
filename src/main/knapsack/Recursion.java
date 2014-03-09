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
		
		Tuple<Integer, Cons> ret = solve(cap, values.length);
		
		Cons path = ret.snd;
		while (path != null) {
			taken[path.car] = 1;
			path = path.cdr;
		}
	}

	// the best path is constructed using accumulator parameter (path), which
	// will be returned on the base cases
	Tuple<Integer, Cons> solve(int cap, int n) {
		if (cap == 0 || n == 0) { // no more space or no more item
			return Tuple.make(0, null);
		} else {
			Tuple<Integer, Cons> ret = solve(cap, n - 1);
			int max = ret.fst;
			if (weights[n - 1] <= cap) { // we can afford this
				Tuple<Integer, Cons> ret2 = solve(cap - weights[n - 1], n - 1);
				int max2 = values[n - 1] + ret2.fst;
				if (max2 > max) {
					Cons path = new Cons(n - 1, ret2.snd);
					return Tuple.make(max2, path);
				}
			}
			return ret;
		}
	}
}
