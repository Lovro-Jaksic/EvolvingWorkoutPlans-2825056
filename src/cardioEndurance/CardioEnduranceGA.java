package cardioEndurance;

import java.util.HashMap;
import java.util.Map;

/**
 * Main class used for running the algorithm
 */
public class CardioEnduranceGA {
    // Array to store all the available exercises for beginner users (currently 20)
    static String[] beginnerHIITExercises = {
            "Jumping Jacks",
            "High Knees",
            "Butt Kicks",
            "Jump Rope",
            "Plank Jacks",
            "Pushups",
            "Mountain Climbers",
            "Skater Jumps",
            "Jump Squats",
            "Bear Crawls",
            "Burpees",
            "Alternating Lunges",
            "Box Jumps",
            "Tricep Dips",
            "Side Plank",
            "Dumbbell Thrusters",
            "Renegade Rows",
            "Russian Twists",
            "Medicine Ball Slams",
            "Battle Ropes"
    };

    // Array to store all the available exercises for intermediate users (currently 20)
    static String[] intermediateHIITExercises = {
            "Box jumps",
            "Battle ropes swings",
            "Jumping lunges",
            "Kettlebell swings",
            "Mountain climbers",
            "Plank jacks",
            "Burpees",
            "Jump squats",
            "Rowing machine sprints",
            "Medicine ball slams",
            "Bike sprints",
            "Jumping jacks",
            "High knees",
            "Jump rope",
            "Dumbbell thrusters",
            "Russian twists",
            "Treadmill sprints",
            "TRX pushups",
            "Sled pushes",
            "TRX rowing"
    };

    // Array to store all the available exercises for advanced users (currently 20)
    static String[] advancedHIITExercises = {
            "Burpee box jumps",
            "Battle ropes",
            "Jumping lunges",
            "Plyometric push-ups",
            "Kettlebell swings",
            "Medicine ball slams",
            "Sled pushes",
            "Rowing sprints",
            "Boxing bag drills",
            "Squat jumps with overhead press",
            "Sprint intervals on the treadmill",
            "Jump rope double unders",
            "Bear crawl with push-up",
            "Turkish get-ups",
            "Treadmill sprints",
            "Wall balls",
            "Assault bike sprints",
            "Broad jumps",
            "Barbell thrusters",
            "Power snatches"
    };

    // Exercise and rest time goals for each week, used for progressive overload, defined in ranges for all 3 plans
    // First 2 elements represent exercise time ranges, last 2 rest time ranges
    static int[][] beginnerPlan = {
            // Week 1
            {20, 30, 20, 40},
            // Week 2
            {25, 35, 20, 40},
            // Week 3
            {35, 40, 15, 35},
            // Week 4
            {35, 40, 10, 30}
    };

    static int[][] intermediatePlan = {
            // Week 1
            {25, 35, 15, 35},
            // Week 2
            {35, 40, 15, 35},
            // Week 3
            {45, 50, 10, 30},
            // Week 4
            {45, 50, 10, 25}
    };

    static int[][] advancedPlan = {
            // Week 1
            {40, 45, 10, 30},
            // Week 2
            {50, 55, 10, 25},
            // Week 3
            {60, 65, 10, 20},
            // Week 4
            {60, 65, 10, 15}
    };

    // Workouts per week - number of exercises per workout - total number of workouts over 4 weeks for all 3 levels - total genes in the chromosome
    static int [] beginnerConfig = {3, 4, 12, 144};
    static int [] intermediateConfig = {4, 5, 16, 240};
    static int [] advancedConfig = {5, 6, 20, 360};

    // The number of generations used for terminating the algorithm
    public static int maxGenerations = 500;

    // Define the hyperparameter grid
    public static Map<String, double[]> hyperParameters = new HashMap<>();

    // Initialize the best result and its corresponding hyperparameters
    public static double bestFitness = Double.NEGATIVE_INFINITY;
    public static Map<String, Double> bestHyperparameters = new HashMap<>();
    public static int meanIndividualScore;

    public static void main(String[] args) {
        // Populate the hyperparameter grid
        hyperParameters.put("populationSize", new double[] {50, 70, 100, 150, 200});
        hyperParameters.put("mutationRate", new double[] {0.001, 0.005, 0.01, 0.02, 0.05});
        hyperParameters.put("crossoverRate", new double[] {0.5, 0.7, 0.8, 0.9, 0.95});
        hyperParameters.put("elitismCount", new double[] {1, 2, 4, 5, 8});
        hyperParameters.put("selectionSize", new double[] {1, 3, 5, 8, 10});

//        // Loop through all possible combinations of hyperparameters
//        for (double populationSize : hyperParameters.get("populationSize")) {
//            for (double mutationRate : hyperParameters.get("mutationRate")) {
//                for (double crossoverRate : hyperParameters.get("crossoverRate")) {
//                    for (double elitismCount : hyperParameters.get("elitismCount")) {
//                        for (double selectionSize : hyperParameters.get("selectionSize")) {
//                            // Call the method for running the genetic algorithm
//                            runGeneticAlgorithm((int) populationSize, mutationRate, crossoverRate,
//                                    (int) elitismCount, (int) selectionSize);
//                        }
//                    }
//                }
//            }
//        }
//        // Print the best result and its corresponding hyperparameters
//        System.out.println("Best result: " + bestFitness);
//        System.out.println("Best hyperparameters: " + bestHyperparameters.toString());

        /**
         * Currently takes around 50 seconds for the 30 runs loop -> which would
         * equate to around 40.5 - 41 hours for the hyperparameter grid search
         */
//        for (int i = 0; i < 30; i++) {
//            runGeneticAlgorithm((int) hyperParameters.get("populationSize")[2], hyperParameters.get("mutationRate")[0],
//                    hyperParameters.get("crossoverRate")[4], (int) hyperParameters.get("elitismCount")[1],
//                    (int) hyperParameters.get("selectionSize")[2]);
//        }

        runGeneticAlgorithm((int) hyperParameters.get("populationSize")[2], hyperParameters.get("mutationRate")[0],
                hyperParameters.get("crossoverRate")[4], (int) hyperParameters.get("elitismCount")[1],
                (int) hyperParameters.get("selectionSize")[2]);
    }

    public static void runGeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount, int selectionSize) {
        // Create a genetic algorithm object
        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(populationSize, mutationRate, crossoverRate, elitismCount, selectionSize);

        // Initialize the population with a specified length of the individual's chromosomes
        Population population = geneticAlgorithm.initPopulation(advancedConfig[3]);

        // Evaluate the population
        geneticAlgorithm.evaluatePopulation(population, advancedPlan, advancedConfig);

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
//            System.out.println("Generation: " + generation + " --> Best workout: ");
//            population.getFittest(0).solutionToString();

            // Apply crossover
            population = geneticAlgorithm.uniformCrossover(population);

            // Apply mutation
            population = geneticAlgorithm.mutatePopulation(population);

            // Evaluate the population
            geneticAlgorithm.evaluatePopulation(population, advancedPlan, advancedConfig);

            // Increment the generation count
            generation++;
        }

//         Check if the current result is better than the previous best result
//        double currentFitness = population.getFittest(0).getFitness();
//        if (currentFitness > bestFitness) {
//            bestFitness = currentFitness;
//            bestHyperparameters.put("populationSize", (double) populationSize);
//            bestHyperparameters.put("mutationRate", mutationRate);
//            bestHyperparameters.put("crossoverRate", crossoverRate);
//            bestHyperparameters.put("elitismCount", (double) elitismCount);
//            bestHyperparameters.put("selectionSize", (double) selectionSize);
//        }

        // Print the final solution
        System.out.println("Stopped after " + maxGenerations + " generations");
//        System.out.println("Generation: " + generation + " --> Best workout: " + population.getFittest(0).toString());

        // Testing the new printing of the solution
        Individual solution = population.getFittest(0);
        solution.solutionToString(advancedHIITExercises,advancedConfig);
        // Print the fitness of the individual
        System.out.println(solution.getFitness());

    }
}
