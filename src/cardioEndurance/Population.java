package cardioEndurance;

import MuscleBuilding_1st_Version.Individual;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Population {
    private MuscleBuilding_1st_Version.Individual population[];

    private double populationFitness = -1;

    public Population(int populationSize) {
        this.population = new MuscleBuilding_1st_Version.Individual[populationSize];
    }

    /**
     * A constructor to create a population of individuals
     * based on the specified population size and length of individual's chromosomes
     * @param populationSize
     * @param chromosomeLength
     */
    public Population(int populationSize, int chromosomeLength) {
        this.population = new MuscleBuilding_1st_Version.Individual[populationSize];

        for (int individualCount = 0; individualCount < populationSize; individualCount++) {
            MuscleBuilding_1st_Version.Individual individual = new MuscleBuilding_1st_Version.Individual(chromosomeLength);
            this.population[individualCount] = individual;
        }
    }

    public MuscleBuilding_1st_Version.Individual[] getIndividuals() {
        return this.population;
    }

    public MuscleBuilding_1st_Version.Individual getFittest(int position) {
        Arrays.sort(this.population, new Comparator<MuscleBuilding_1st_Version.Individual>() {
            @Override
            public int compare(MuscleBuilding_1st_Version.Individual o1, MuscleBuilding_1st_Version.Individual o2) {
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

    public MuscleBuilding_1st_Version.Individual setIndividual(int position, MuscleBuilding_1st_Version.Individual individual) {
        return population[position] = individual;
    }

    public MuscleBuilding_1st_Version.Individual getIndividual(int position) {
        return population[position];
    }

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
