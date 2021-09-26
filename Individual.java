package chapter3;

public class Individual {
	// each individual will have a given chromosome and fitness based on said chromosome
	private int[] chromosome;
	private double fitness = -1;
	
	// create an individual with some given chromosome
	public Individual(int[] chromosome) {
		this.chromosome = chromosome;
	}
	
	// initialize a randomly generated individual
	public Individual(int chromosomeLength) {
		this.chromosome = new int[chromosomeLength];
		for(int gene=0; gene < chromosomeLength; gene++) {
			if(0.5 < Math.random())
				this.setGene(gene, 1);
			else
				this.setGene(gene, 0);
		}
	}
	
	public int[] getChromosome() {
		return chromosome;
	}

	public int getChromosomeLength() {
		return this.chromosome.length;
	}
	
	public int getGene(int offset) {
		return this.chromosome[offset];
	}

	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}
	
	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public String toString() {
		String output = "";
		for(int gene=0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene];
		}
		return output;
	}
}
