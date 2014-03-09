package knapsack;


/**
 * From recursion to memoisation. This is the base for the dynamic programming solution.
 * 
 * @author monang
 * 
 */
public class Memoization implements Strategy {
	
	int[] values, weights;
	Tuple<Integer, Cons>[][] cache;

	@SuppressWarnings("unchecked")
	@Override
	public void solve(int cap, int[] values, int[] weights, int[] taken) {
		this.values = values;
		this.weights = weights;
		this.cache = new Tuple[cap + 1][values.length + 1];
		
		Tuple<Integer, Cons> ret = solve(cap, values.length);

		Cons path = ret.snd;
		while (path != null) {
			taken[path.car] = 1;
			path = path.cdr;
		}
	}

	// the state is (cap, n)
	Tuple<Integer, Cons> solve(int cap, int n) {

		Tuple<Integer, Cons> value = cache[cap][n];
		if(value != null) { // we've computed this
			return value;
		}
		
		if (cap == 0 || n == 0) { // no more space or no more item
			return Tuple.make(0, null);
		} else {
			Tuple<Integer, Cons> ret = solve(cap, n - 1);
			value = ret; // value when we don't pick current item
			int max = ret.fst;
			if (weights[n - 1] <= cap) { // we can afford this
				Tuple<Integer, Cons> ret2 = solve(cap - weights[n - 1], n - 1);
				int max2 = values[n - 1] + ret2.fst;
				if (max2 > max) {
					Cons path = new Cons(n - 1, ret2.snd);
					value = Tuple.make(max2, path); // value when we pick current item
				}
			}
		}
		
		cache[cap][n] = value; // remember this
		
		return value;
	}
}
