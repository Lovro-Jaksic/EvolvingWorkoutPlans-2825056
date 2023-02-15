package MuscleBuilding_1st_Version;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class used for running the algorithm
 */

public class MuscleBuildingGA {

    // The number of generations used for terminating the algorithm
    public static int maxGenerations = 250;

    public static void main(String[] args) {
        // Define the hyperparameter grid
        Map<String, double[]> hyperParameters = new HashMap<>();
        hyperParameters.put("populationSize", new double[] {50, 70, 100, 150, 200});
        hyperParameters.put("mutationRate", new double[] {0.001, 0.005, 0.01, 0.02, 0.05});
        hyperParameters.put("crossoverRate", new double[] {0.5, 0.7, 0.8, 0.9, 0.95});
        hyperParameters.put("elitismCount", new double[] {1, 2, 4, 5, 8});
        hyperParameters.put("selectionSize", new double[] {1, 3, 5, 8, 10});

        runGeneticAlgorithm((int) hyperParameters.get("populationSize")[2], hyperParameters.get("mutationRate")[0],
                hyperParameters.get("crossoverRate")[4], (int) hyperParameters.get("elitismCount")[1],
                (int) hyperParameters.get("selectionSize")[2]);

        // Create a genetic algorithm object
//        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100, 0.001, 0.95, 2, 5);

    }

    public static void runGeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int selectionSize) {
        // Create a genetic algorithm object
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(populationSize, mutationRate, crossoverRate, elitismCount, selectionSize);

        // Initialize the population with a specified length of the individual's chromosomes
        Population population = geneticAlgorithm.initPopulation(252);

        // Evaluate the population
        geneticAlgorithm.evaluatePopulation(population);

        // Used for keeping track of the generations
        int generation = 1;

        /**
         * The evolution loop
         *
         * Terminates after it reaches the maximum number of generations
         *
         * TODO: Create different conditions for termination and test them
         */
        while (!geneticAlgorithm.isTerminationConditionMet(generation, maxGenerations)) {
            // Print the fittest individual from the population
//            System.out.println("Generation: " + generation + " --> Best workout: " + population.getFittest(0).toString());

            // Apply crossover
            population = geneticAlgorithm.uniformCrossover(population);

            // Apply mutation
            population = geneticAlgorithm.mutatePopulation(population);

            // Evaluate the population
            geneticAlgorithm.evaluatePopulation(population);

            // Increment the generation count
            generation++;
        }
        // Print the final solution
        System.out.println("Stopped after " + maxGenerations + " generations");
//        System.out.println("Generation: " + generation + " --> Best workout: " + population.getFittest(0).toString());

        // Testing the new printing of the solution
        Individual solution = population.getFittest(0);
        solution.solutionToString();
        // Print the fitness of the individual
        System.out.println(solution.getFitness());

    }
}
