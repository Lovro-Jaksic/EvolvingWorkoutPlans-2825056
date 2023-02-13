package MuscleBuilding_1st_Version;

/**
 * Main class used for running the algorithm
 */

public class MuscleBuildingGA {

    // The number of generations used for terminating the algorithm
    public static int maxGenerations = 500;

    public static void main(String[] args) {
        // Create a genetic algorithm object
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(100, 0.001, 0.95, 2, 5);

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
