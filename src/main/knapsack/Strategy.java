package knapsack;

interface Strategy {
	void solve(int capacity, int[] values, int[] weights, int[] taken);
}
