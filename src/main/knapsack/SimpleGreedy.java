package knapsack;

public class SimpleGreedy implements Strategy {

	@Override
	public void solve(int capacity, int[] values, int[] weights, int[] taken) {
		// a trivial greedy algorithm for filling the knapsack
		// it takes items in-order until the knapsack is full
		int weight = 0;
		for (int i = 0; i < values.length; i++) {
			if (weight + weights[i] <= capacity) {
				taken[i] = 1;
				weight += weights[i];
			} else {
				taken[i] = 0;
			}
		}
	}

}
