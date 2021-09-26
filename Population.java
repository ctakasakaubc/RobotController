package chapter3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Population {
	//each population will have a set of individuals and a summated fitness (for selection)
	private Individual population[];
	private double populationFitness = -1;
	
	// initialize an empty population
	public Population(int populationSize) {
		this.population = new Individual[populationSize];
	}
	
	// initialize a population, filling each spot with a random individual
	public Population(int populationSize, int chromosomeLength) {
		this.population = new Individual[populationSize];
		for(int indivCount=0; indivCount < populationSize; indivCount++) {
			Individual indiv = new Individual(chromosomeLength);
			this.population[indivCount] = indiv;
		}
	}
	
	// return the entire population
	public Individual[] getIndividuals() {
		return this.population;
	}
	
	// return the individual at a given index, when sorted by fitness
	public Individual getFittest(int offset) {
		Arrays.sort(this.population, new Comparator<Individual>() {
			public int compare(Individual i1, Individual i2) {
				if(i1.getFitness() > i2.getFitness()) {
					return -1;
				} else if(i1.getFitness() < i2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});
		return this.population[offset];
	}
	
	public void setPopulationFitness(double fitness) {
		this.populationFitness = fitness;
	}
	
	public double getPopulationFitness() {
		return this.populationFitness;
	}
	
	public int size() {
		return this.population.length;
	}
	
	public Individual setIndividual(int offset, Individual individual) {
		return population[offset] = individual;
	}
	
	public Individual getIndividual(int offset) {
		return population[offset];
	}
	
	// shuffles the population (redundant...)
	public void shuffle() {
		Random gen = new Random();
		for(int i=population.length-1; i>0; i--) {
			int index = gen.nextInt(i+1);
			Individual a = population[index];
			population[index] = population[i];
			population[i] = a;
		}
	}
}
