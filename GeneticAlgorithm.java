package chapter3;

import java.util.Arrays;

import chapter3.Maze;
import chapter3.Robot;

public class GeneticAlgorithm {
	// each GA will have some values to aid in computation
	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	protected int tournamentSize;
	
	public GeneticAlgorithm(int popSize, double mutRate, double crossRate, int elite, int tournamentSize) {
		this.populationSize = popSize;
		this.mutationRate = mutRate;
		this.crossoverRate = crossRate;
		this.elitismCount = elite;
		this.tournamentSize = tournamentSize;
	}
	
	// create a population for the GA
	public Population initPop(int chromosomeLength) {
		Population pop = new Population(this.populationSize, chromosomeLength);
		return pop;
	}
	
	// calculate the fitness of a certain individual
	public double calcFitness(Individual individual, Maze maze) {
		int[] chromosome = individual.getChromosome();
		Robot bot = new Robot(chromosome, maze, 100);
		bot.run();
		int fitness = maze.scoreRoute(bot.getRoute());
		individual.setFitness(fitness);
		return fitness;
	}
	
	// calculate the fitness of an entire population, individual by individual
	public void evalPop(Population pop, Maze maze) {
		double populationFitness = 0;
		
		for(Individual indiv : pop.getIndividuals()) {
			populationFitness += calcFitness(indiv, maze);
		}
		pop.setPopulationFitness(populationFitness);
	}
	
	// break after some number of generations
	public boolean isTerminationCondiMet(int generationsCount, int maxGenerations, Population pop) {

		if(generationsCount >= maxGenerations)
			return true;
		
		if(pop.getFittest(0).getFitness() == 29.0)
			return true;
		
		return false;
	}
	
	// select a parent through tournament selection
	public Individual selectParent(Population population) {
		// init tourney
		Population tournament = new Population(this.tournamentSize);
		
		// add random sol'ns to tourney
		population.shuffle();
		for(int i=0; i < this.tournamentSize; i++) {
			Individual tournamentIndiv = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndiv);
		}
		return tournament.getFittest(0);
	}
	
	public Individual selectParent(Population population, double selectionPressure) {
		// init tourney
		Population tournament = new Population(this.tournamentSize);
		
		// add random sol'ns to tourney
		population.shuffle();
		for(int i=0; i < this.tournamentSize; i++) {
			Individual tournamentIndiv = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndiv);
		}
		// uh maybe this is what they mean?
		for(int i=0; i < this.tournamentSize; i++) {
			if(selectionPressure > Math.random())
				return tournament.getFittest(i);
		}
		return tournament.getFittest(this.tournamentSize - 1);
	}
	
	// going down the list of individuals, mate them (unless they are an elite)
	public Population crossoverPopulation(Population pop, boolean twoPoint) {
		
		Population newPop = new Population(pop.size());
		
		if(twoPoint) {
			for(int i=0; i<pop.size(); i++) {
				Individual p1 = pop.getFittest(i);
				
				if(this.crossoverRate > Math.random() && i > this.elitismCount) {
					Individual child = new Individual(p1.getChromosomeLength());
					
					Individual p2 = this.selectParent(pop);
					
					// get crossover points
					int swapPoints[] = {(int) (Math.random() * (p1.getChromosomeLength() + 1)), (int) (Math.random() * (p1.getChromosomeLength() + 1))};
					Arrays.sort(swapPoints);
					
					// add genes based on position (locus) & crossover point
					for(int locus=0; locus < p1.getChromosomeLength(); locus++) {
						if(locus < swapPoints[0] || locus > swapPoints[1]) {
							child.setGene(locus, p1.getGene(locus));
						}else {
							child.setGene(locus, p2.getGene(locus));
						}	
					}
					newPop.setIndividual(i, child);
				}else {
					// just don't crossover
					newPop.setIndividual(i,p1);
				}
			}
			return newPop;
		}
		// single point
		else {
			for(int i=0; i<pop.size(); i++) {
				Individual p1 = pop.getFittest(i);
				
				if(this.crossoverRate > Math.random() && i > this.elitismCount) {
					Individual child = new Individual(p1.getChromosomeLength());
					
					Individual p2 = this.selectParent(pop);
					
					// get crossover point
					int swapPoint = (int) (Math.random() * (p1.getChromosomeLength() + 1));
					// add genes based on position (locus) & crossover point
					for(int locus=0; locus < p1.getChromosomeLength(); locus++) {
						if(locus < swapPoint) {
							child.setGene(locus, p1.getGene(locus));
						}else {
							child.setGene(locus, p2.getGene(locus));
						}	
					}
					newPop.setIndividual(i, child);
				}else {
					// just don't crossover
					newPop.setIndividual(i,p1);
				}
			}
			return newPop;
		}
	}
	
	// going down the list of individuals, mate them (unless they are an elite)
		public Population crossoverPopulation(Population pop, boolean twoPoint, double selectionPressure) {
			
			Population newPop = new Population(pop.size());
			
			if(twoPoint) {
				for(int i=0; i<pop.size(); i++) {
					Individual p1 = pop.getFittest(i);
					
					if(this.crossoverRate > Math.random() && i > this.elitismCount) {
						Individual child = new Individual(p1.getChromosomeLength());
						
						Individual p2 = this.selectParent(pop, selectionPressure);
						
						// get crossover points
						int swapPoint1 = (int) (Math.random() * (p1.getChromosomeLength() + 1));
						int swapPoint2 = (int) (Math.random() * (p1.getChromosomeLength() + 1));
						
						// add genes based on position (locus) & crossover point
						for(int locus=0; locus < p1.getChromosomeLength(); locus++) {
							if(locus < swapPoint1 || locus > swapPoint2) {
								child.setGene(locus, p1.getGene(locus));
							}else {
								child.setGene(locus, p2.getGene(locus));
							}	
						}
						newPop.setIndividual(i, child);
					}else {
						// just don't crossover
						newPop.setIndividual(i,p1);
					}
				}
				return newPop;
			}
			// single point
			else {
				for(int i=0; i<pop.size(); i++) {
					Individual p1 = pop.getFittest(i);
					
					if(this.crossoverRate > Math.random() && i > this.elitismCount) {
						Individual child = new Individual(p1.getChromosomeLength());
						
						Individual p2 = this.selectParent(pop);
						
						// get crossover point
						int swapPoint = (int) (Math.random() * (p1.getChromosomeLength() + 1));
						// add genes based on position (locus) & crossover point
						for(int locus=0; locus < p1.getChromosomeLength(); locus++) {
							if(locus < swapPoint) {
								child.setGene(locus, p1.getGene(locus));
							}else {
								child.setGene(locus, p2.getGene(locus));
							}	
						}
						newPop.setIndividual(i, child);
					}else {
						// just don't crossover
						newPop.setIndividual(i,p1);
					}
				}
				return newPop;
			}
		}
	
	// randomly give a chance for a gene to mutate
	public Population mutatePop(Population pop) {
		Population newPop = new Population(this.populationSize);
		
		for(int i=0; i<pop.size(); i++) {
			Individual indiv = pop.getFittest(i);
			
			for(int gene=0; gene < indiv.getChromosomeLength(); gene++) {
				if(i >= this.elitismCount) {
					if(this.mutationRate > Math.random()) {
						int newGene = 1;
						if (indiv.getGene(gene) == 1) {
							newGene = 0;
						}
						// Mutate gene
						indiv.setGene(gene, newGene);
					}
				}
			}
			newPop.setIndividual(i, indiv);
		}
		return newPop;
	}
}
