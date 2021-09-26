package chapter3;

public class RobotController {
	// our termination condition
	public static int maxGenerations = 1000;
	
	public static void main(String[] args) {
		/**
		 * 0 = empty
		 * 1 = wall
		 * 2 = start
		 * 3 = ideal route
		 * 4 = goal
		 */
		
		Maze maze = new Maze(new int[][] {
			{ 0, 0, 0, 0, 1, 0, 1, 3, 2 }, 
			{ 1, 0, 1, 1, 1, 0, 1, 3, 1 },
			{ 1, 0, 0, 1, 3, 3, 3, 3, 1 }, 
			{ 3, 3, 3, 1, 3, 1, 1, 0, 1 }, 
			{ 3, 1, 3, 3, 3, 1, 1, 0, 0 },
			{ 3, 3, 1, 1, 1, 1, 0, 1, 1 }, 
			{ 1, 3, 0, 1, 3, 3, 3, 3, 3 }, 
			{ 0, 3, 1, 1, 3, 1, 0, 1, 3 },
			{ 1, 3, 3, 3, 3, 1, 1, 1, 4 } 
		});
		
		// init genetic algorithm
		GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.05, 0.9, 2, 10);
		// init population
		Population population = ga.initPop(128);
		
		// eval initial population
		ga.evalPop(population, maze);
		
		int generation = 1;
		
		while(!ga.isTerminationCondiMet(generation, maxGenerations, population)) {
			
			Individual fittest = population.getFittest(0);
			System.out.println("G" + generation + " Best Solution (" + fittest.getFitness() + "):" + fittest.toString());
			
			// boolean value determines if we use two-point crossover
			population = ga.crossoverPopulation(population, false);
			
			population = ga.mutatePop(population);
			
			ga.evalPop(population, maze);
			
			generation++;
		}
		
		System.out.println("Stopped after " + generation + " generations.");
		Individual fittest = population.getFittest(0);
		System.out.println("Best solution (" + fittest.getFitness() + "): " + fittest.toString());
		
	}
}
