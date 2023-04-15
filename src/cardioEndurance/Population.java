package cardioEndurance;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

/**
 * A population is an abstraction of a collection of individuals.
 * The population class is generally used to perform group-level operations on its individuals,
 * such as finding the strongest individuals, collecting stats on the population as a whole,
 * and selecting individuals to mutate or crossover
 */

public class Population {
    private Individual population[];

    private double populationFitness = -1;

    public Population(int populationSize) {
        this.population = new Individual[populationSize];
    }

    /**
     * A constructor to create a population of individuals
     * based on the specified population size and length of individual's chromosomes
     */
    public Population(int populationSize, int chromosomeLength) {
        this.population = new Individual[populationSize];

        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            Individual individual = new Individual(chromosomeLength);
            this.population[individualCount] = individual;
        }
    }

    public Individual[] getIndividuals() {
        return this.population;
    }

    /**
     * Retrieves the fittest individual at the specified position after sorting the population by fitness
     *
     * @param position - The position of the fittest individual in the sorted population
     * @return The fittest individual at the specified position
     */
    public Individual getFittest(int position) {
        Arrays.sort(this.population, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            }
        });
        return this.population[position];
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

    public Individual setIndividual(int position, Individual individual) {
        return population[position] = individual;
    }

    public Individual getIndividual(int position) {
        return population[position];
    }

    /**
     * A method used for shuffling the population array
     */
    public void shuffle() {
        Random random = new Random();
        for (int i = population.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            Individual individual = population[index];
            population[index] = population[i];
            population[i] = individual;
        }
    }
}
