package knapsack;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class TestSolver {
	
//	@Rule
//	public Timeout globalTimeout = new Timeout(30000); // 10 seconds max per method tested

	private static String DIR = "C:/Users/monang/Documents/coursera/Discrete Optimization/knapsack";

	private static List<File> provisionalTests = new ArrayList<File>();
	private static List<File> exampleTests = new ArrayList<File>();

	@BeforeClass
	public static void beforeClass() throws Exception {
		
		File metadata = new File(DIR, "_metadata");
		Scanner sc = new Scanner(metadata);
		sc.nextLine();
		sc.nextLine();
		int n = Integer.parseInt(sc.nextLine());
		for (int i = 0; i < n; i++) {
			String line = sc.nextLine();
			File testInput = new File(DIR, line.split(",")[1].trim());
			provisionalTests.add(testInput);
		}
		sc.close();
		
		File sampleDir = new File(DIR, "data");
		for (File file : sampleDir.listFiles()) {
			exampleTests.add(file);
		}
		// sort by size ascending
		Collections.sort(exampleTests, new Comparator<File>() {
			@Override
			public int compare(File f1, File f2) {
				return Long.signum(f1.length() - f2.length());
			}
		});
		
	}
	
	private String getInput(String name){
		return new File(DIR, "data/" + name).getAbsolutePath();
	}

	private int solve(String test) throws IOException {
		System.out.println(test);
		int value = Solver.solve(new String[]{ "-file=" + test });
		return value;
	}
	
	@Test
	public void testRecursion() throws Exception {
		String test = getInput("ks_4_0");
		
		Solver.strategy = new Recursion();
		int value = solve(test);
		assertTrue(value >= 19);
	}
	
	@Test
	public void testMemoization() throws Exception {
		String test = getInput("ks_4_0");

		Solver.strategy = new Memoization();
		int value = solve(test);
		assertTrue(value >= 19);
	}

	@Test
	public void testSpaceEfficientDynamicProgramming() throws Exception {
		String test = getInput("ks_4_0");

		Solver.strategy = new SpaceEfficientDynamicProgramming();
		int value = solve(test);
		assertTrue(value >= 19);
	}
	
	@Test
	public void testDiskBasedDynamicProgramming() throws Exception {
		String test = getInput("ks_4_0");

		Solver.strategy = new DiskBackedDynamicProgramming();
		int value = solve(test);
		assertTrue(value >= 19);
	}	
	
	@Test
	public void testSubmit1() throws Exception {
		Solver.strategy = new Recursion();
		String test = provisionalTests.get(0).getAbsolutePath();
		int value = solve(test);
		assertTrue(value >= 99798);
	}

	
	@Test
	public void testSubmit2() throws Exception {
		String test = provisionalTests.get(1).getAbsolutePath();

		Solver.strategy = new Memoization();
		int value1 = solve(test);
		assertTrue(value1 >= 141956);
		Solver.strategy = new DiskBackedDynamicProgramming();
		int value2 = solve(test);

	}
	
	@Test
	public void testSubmit3() throws Exception {
		String test = provisionalTests.get(2).getAbsolutePath();

		Solver.strategy = new DynamicProgramming();
		int value2 = solve(test);
		assertTrue(value2 >= 100236);
	}
	
	@Test
	public void testSubmit4() throws Exception {
		String test = provisionalTests.get(3).getAbsolutePath();

		Solver.strategy = new DiskBackedDynamicProgramming();
		int value2 = solve(test);
		assertTrue(value2 >= 3967180);
	}
	
	@Test
	public void testSubmit5() throws Exception {
		String test = provisionalTests.get(4).getAbsolutePath();

		Solver.strategy = new DiskBackedDynamicProgramming();
		int value2 = solve(test);
		assertTrue(value2 >= 109899);
	}
	
	@Test
	public void testAllSubmission() throws Exception {
		for (File file : provisionalTests) {
			String test = file.getAbsolutePath();
			System.out.println(test);
			Solver.strategy = new DynamicProgramming();
			int value = Solver.solve(new String[]{ "-file=" + test });
			System.out.println(value);
		}
	}
	
	@Test
	public void testAllExamples() throws Exception {
		for (File file : exampleTests) {
			String test = file.getAbsolutePath();
			System.out.println(test);
			Solver.strategy = new DynamicProgramming();
			int value = Solver.solve(new String[]{ "-file=" + test });
			System.out.println(value);
		}
	}

}
